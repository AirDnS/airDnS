package com.example.airdns.like;

import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.like.repository.LikesRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("LikesRepository findByRoomsId Success")
    public void findByRoomsIdSuccess() {
        // Likes 엔티티를 생성하고 저장
        Users user = Users.builder().nickname("User Nickname").build();
        Rooms room = Rooms.builder().users(user).name("Room Name").build();

        entityManager.persist(room);
        entityManager.persist(user);

        Likes likes = Likes.builder().users(user).rooms(room).build();
        entityManager.persist(likes);

        Optional<Likes> foundLikes = likesRepository.findByRoomsId(room.getId());

        assertTrue(foundLikes.isPresent());

        assertEquals(room.getId(), foundLikes.get().getRooms().getId());
    }

    @Test
    @Transactional
    @DisplayName("LikesRepository ExistsByRoomsAndUsers Success")
    public void ExistsByRoomsAndUsers() {
        // Likes 엔티티를 생성하고 저장
        Users user = Users.builder().nickname("User Nickname").build();
        Rooms room = Rooms.builder().users(user).name("Room Name").build();

        entityManager.persist(room);
        entityManager.persist(user);

        Likes likes = Likes.builder().users(user).rooms(room).build();
        entityManager.persist(likes);

        // LikesRepository를 사용하여 existsByRoomsAndUsers 메서드를 호출
        boolean exists = likesRepository.existsByRoomsAndUsers(room, user);

        // 해당 Rooms와 Users로 좋아요가 존재해야 합니다.
        assertTrue(exists);
    }
}
