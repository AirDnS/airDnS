package com.example.airdns.domain.image.repository;

import com.example.airdns.domain.image.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long>, ImagesRepositoryQuery {

}
