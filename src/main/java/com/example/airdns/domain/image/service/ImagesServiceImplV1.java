package com.example.airdns.domain.image.service;

import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.image.exception.ImagesCustomException;
import com.example.airdns.domain.image.exception.ImagesExceptionCode;
import com.example.airdns.domain.image.repository.ImagesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.global.awss3.S3FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImagesServiceImplV1 implements ImagesService {

    private final ImagesRepository imagesRepository;

    private final S3FileUtil s3FileUtil;

    @Override
    public Images createImages(Rooms rooms, MultipartFile file) {
        String fileUrl = s3FileUtil.uploadFile(file, getFilePrefix(rooms));
        return imagesRepository.save(Images.builder()
                .rooms(rooms)
                .imageUrl(fileUrl)
                .build());
    }

    @Transactional
    @Override
    public void deleteImages(Long imagesId, Rooms rooms) {
        Images images = imagesRepository.findById(imagesId)
                .orElseThrow(() -> new ImagesCustomException(ImagesExceptionCode.INVALID_IMAGES_ID));

        if (!rooms.getId().equals(images.getRooms().getId())) {
            throw new ImagesCustomException(ImagesExceptionCode.NO_PERMISSION_USER_IMAGES);
        }

        s3FileUtil.removeFile(images.getImageUrl(), getFilePrefix(images.getRooms()));
        imagesRepository.delete(images);
        rooms.deleteImages(images);
    }


    private String getFilePrefix(Rooms rooms) {
        return rooms.getId() + "_";
    }
}
