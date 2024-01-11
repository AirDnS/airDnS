package com.example.airdns.domain.image.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.stereotype.Service;

public interface ImagesService {

    Images createImages(Rooms rooms, String imagesUrl);


}
