package com.example.airdns.domain.image.repository;

import java.util.List;
import java.util.Map;

public interface ImagesRepositoryQuery {
    Map<Long, List<String>> findImageUrlGroupByRoomsId(List<Long> roomsIdList);

}
