package com.examplesoft.ecommercemonolite.mailsender.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.FavoriteProductCampaignEvent;
import com.examplesoft.ecommercemonolite.mailsender.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailListener {
    private final MailServiceImpl mailService;

    @EventListener
    public void favoriteProductJobMailEvent(FavoriteProductCampaignEvent event) {
        String body = MailTemplates.FAVORITE_PRODUCT_CAMPAIGN_MAIL;

        body = body
                .replace("{product.image}", event.image())
                .replace("{product.description}", event.description())
                .replace("{product.name}", event.name())
                .replace("{product.url}", event.url());

        mailService.sendMail(MailDto.builder()
                .to(event.to())
                .isHtml(Boolean.TRUE)
                .isMultipart(Boolean.FALSE)
                .subject("Favori Ürünler Şimdi İndirimde")
                .body(body)
                .build());

    }
}
