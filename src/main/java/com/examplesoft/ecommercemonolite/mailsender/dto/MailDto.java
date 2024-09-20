package com.examplesoft.ecommercemonolite.mailsender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDto {
    private String to;
    private String subject;
    private String body;
    private boolean isHtml;
    private boolean isMultipart;
}
