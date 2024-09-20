package com.examplesoft.ecommercemonolite.domain.campaign.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignSaveEvent;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductCampaignEvent;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductDto;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.service.FavoriteProductService;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.subscripeservice.entity.Subscription;
import com.examplesoft.ecommercemonolite.subscripeservice.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignEventListener {
    @Value("${client.domain}")
    private String domain;

    private final ApplicationEventPublisher publisher;
    private final UserService userService;
    private final ProductService productService;
    private final FavoriteProductService favoriteProductService;
    private final SubscriptionServiceImpl subscriptionService;

    @EventListener
    public void campaignSaveEvent(CampaignSaveEvent event) {
        List<UserDto> users = userService.getAll();

        users.forEach(user -> {
            try {
                if (user.getEmail() == null) {
                    return;
                }
                Subscription subscription = subscriptionService.getUserSubscription(user.getId());

                List<FavoriteProductDto> favoriteProducts = favoriteProductService.getUserFavoriteProducts(user.getId());
                Set<String> productTargets = favoriteProducts.stream().map(favoriteProductDto -> favoriteProductDto.getProduct().getId()).collect(Collectors.toSet());
                productTargets.retainAll(event.productIds());

                List<ProductDto> products = productService.getAllByIdIn(productTargets);

                products.forEach(product -> {
                    final String link = domain + "/products/" + product.getSlug();
                    publisher.publishEvent(new FavoriteProductCampaignEvent(link, user.getEmail(), product.getImage(), product.getName(), product.getDescription()));
                    subscriptionService.sendPushMessage(subscription, getPayload(link));
                    log.info("İşlem Başarılı -> {}", user.getUsername());
                });
            } catch (Exception e) {
                log.error("İşlem Başarısız -> {}", user.getUsername());
                throw new RuntimeException(e);
            }
        });
    }

    public String getPayload(String url) {
        return "{" +
                "\"title\": \"BİLGİ\", " +
                "\"body\": \"Favori ürününüz indirime girdi.Detaylar mail olarak iletilmiştir.\", " +
                "\"url\": \"" + url + "\"" +
                "}";
    }
}
