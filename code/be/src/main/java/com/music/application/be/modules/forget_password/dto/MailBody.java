package com.music.application.be.modules.forget_password.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
