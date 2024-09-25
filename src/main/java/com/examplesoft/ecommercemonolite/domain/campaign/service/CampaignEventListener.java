package com.examplesoft.ecommercemonolite.domain.campaign.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignNotifyEvent;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductCampaignEvent;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductDto;
import com.examplesoft.ecommercemonolite.domain.favoriteproduct.service.FavoriteProductService;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.socket.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignEventListener {
    private static final String NOTIFICATION_TOPIC = "/topic/notifications";
    @Value("${client.domain}")
    private String domain;

    private final ApplicationEventPublisher publisher;
    private final UserService userService;
    private final ProductService productService;
    private final FavoriteProductService favoriteProductService;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void campaignSaveEvent(CampaignNotifyEvent event) {
        List<UserDto> users = userService.getAll();

        users.forEach(user -> {
            try {
                if (user.getEmail() == null) {
                    return;
                }
                List<FavoriteProductDto> favoriteProducts = favoriteProductService.getUserFavoriteProducts(user.getId());
                Set<String> productTargets = favoriteProducts.stream().map(favoriteProductDto -> favoriteProductDto.getProduct().getId()).collect(Collectors.toSet());
                productTargets.retainAll(event.productIds());

                List<ProductDto> products = productService.getAllByIdIn(productTargets);

                products.forEach(product -> {
                    final String link = domain + "/products/" + product.getSlug();
                    publisher.publishEvent(new FavoriteProductCampaignEvent(link, user.getEmail(), product.getImage(), product.getName(), product.getDescription()));
                    MessageDto message = MessageDto.builder()
                            .title("BILGI")
                            .subTitle(product.getName() + " Kampanya")
                            .message("Favori ürününüz " + product.getName() + " için kampanya başlamıştır.Detaylar mail adresinize iletilmiştir.")
                            .build();

                    messagingTemplate.convertAndSend(NOTIFICATION_TOPIC,message);
                    log.info("İşlem Başarılı -> {}", user.getUsername());
                });
            } catch (Exception e) {
                log.error("İşlem Başarısız -> {}", user.getUsername());
                throw new RuntimeException(e);
            }
        });
    }
}
