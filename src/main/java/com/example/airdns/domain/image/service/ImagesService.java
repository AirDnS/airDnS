package com.example.airdns.domain.image.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {

    Images createImages(Rooms rooms, MultipartFile file);

    void deleteImages(Long imagesId, Rooms rooms);

}
