package com.example.airdns.domain.image.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.room.entity.Rooms;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ImagesService {

    Images createImages(Rooms rooms, MultipartFile file);

    void deleteImages(Long imagesId, Rooms rooms);

    Map<Long, List<String>> findAllByRoomsId(List<Long> roomsIdList);
}
