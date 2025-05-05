package com.music.application.be.modules.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteResponseDTO {
    private String message;
    private int totalFavorites;
}
