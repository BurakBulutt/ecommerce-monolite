package com.examplesoft.ecommercemonolite.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class MessageSourceProvider {
    @Getter
    private static MessageSource messageSource;

    @Autowired
    public MessageSourceProvider(MessageSource messageSource) {
        MessageSourceProvider.messageSource = messageSource;
    }
}
