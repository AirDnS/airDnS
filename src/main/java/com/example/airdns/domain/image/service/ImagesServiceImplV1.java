package com.example.airdns.domain.image.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.image.repository.ImagesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImagesServiceImplV1 implements ImagesService {

    private final ImagesRepository imagesRepository;

    @Override
    public Images createImages(Rooms rooms, String imagesUrl) {
        return imagesRepository.save(Images.builder()
                .rooms(rooms)
                .imageUrl(imagesUrl)
                .build());
    }
}
