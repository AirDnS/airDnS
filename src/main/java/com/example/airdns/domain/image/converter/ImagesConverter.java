package com.example.airdns.domain.image.converter;

import com.example.airdns.domain.image.dto.ImagesResponseDto;
import com.example.airdns.domain.image.entity.Images;

public class ImagesConverter {
    public static ImagesResponseDto.ReadImagesResponseDto toDto(Images images) {
        return ImagesResponseDto.ReadImagesResponseDto.builder()
                .id(images.getId())
                .imageUrl(images.getImageUrl())
                .build();
    }
}
