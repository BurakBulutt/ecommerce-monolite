package com.examplesoft.ecommercemonolite.subscripeservice.service;

import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.subscripeservice.entity.Subscription;
import com.examplesoft.ecommercemonolite.subscripeservice.repo.SubscriptionRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.jose4j.lang.BouncyCastleProviderHelp;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@Transactional(readOnly = true)
public class SubscriptionServiceImpl {
    @Value("${vapid.public}")
    private String PUBLIC_VAPID_KEY;
    @Value("${vapid.private}")
    private String PRIVATE_VAPID_KEY;

    private final SubscriptionRepository repository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Subscription getUserSubscription(String userId) {
        return repository.findByUserId(userId).orElseThrow(() -> new BaseException(MessageUtil.SUBSCRIPTION_NOT_FOUND,Subscription.class.getSimpleName(),userId));
    }

    @Transactional
    public void createOrUpdateSubscription(Subscription subscription) {
        if (JwtUtil.extractUserId().equals("UNKNOWN_USER")){
            throw new BaseException(MessageUtil.SUBSCRIPTION_NOT_FOUND,Subscription.class.getSimpleName(),JwtUtil.extractUserId());
        }
        repository.findByUserId(JwtUtil.extractUserId())
                .ifPresentOrElse(subscription1 -> {
                    subscription1.setEndpoint(subscription.getEndpoint());
                    subscription1.setKeys(subscription.getKeys());
                    repository.save(subscription1);
                },() -> {
                    subscription.setUserId(JwtUtil.extractUserId());
                    repository.save(subscription);
                });
    }

    @Transactional
    public void sendPushMessage(Subscription subscription, String payload) {
        BouncyCastleProviderHelp.enableBouncyCastleProvider();
        try {
            PushService pushService = new PushService();
            pushService.setPublicKey(Utils.loadPublicKey(PUBLIC_VAPID_KEY)); //TODO HATA SATIRI
            pushService.setPrivateKey(Utils.loadPrivateKey(PRIVATE_VAPID_KEY));

            Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getKeys().get("p256dh"),
                    subscription.getKeys().get("auth"),
                    payload.getBytes(StandardCharsets.UTF_8)
            );

            pushService.send(notification);
            log.info("Push notification sent");

        } catch (JoseException | GeneralSecurityException | IOException | ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
