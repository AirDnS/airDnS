package com.example.airdns.like;

import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.like.repository.LikesRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LikesRepositoryTest {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("LikesRepository deleteByRoomsId Success")
    void deleteByRoomsIdSuccess() {
        // given
        Rooms room1 = Rooms.builder().name("Room Number1").build();
        entityManager.persist(room1);

        Likes like = Likes.builder().rooms(room1).build();
        entityManager.persist(like);
        entityManager.flush();

        // when
        likesRepository.deleteByRoomsId(like.getRooms().getId());

        // then
        List<Likes> likesList = likesRepository.findAll();
        assertEquals(0, likesList.size());
    }

    @Test
    @DisplayName("LikesRepository findAllByRoomsId Success")
    void findAllByRoomsIdSuccess() {
        // given
        Rooms room1 = Rooms.builder().name("room number1").build();
        Rooms room2 = Rooms.builder().name("room number2").build();

        entityManager.persist(room1);
        entityManager.persist(room2);
        entityManager.flush();

        Likes like1 = Likes.builder().rooms(room1).build();
        Likes like2 = Likes.builder().rooms(room1).build();
        Likes like3 = Likes.builder().rooms(room2).build();

        entityManager.persist(like1);
        entityManager.persist(like2);
        entityManager.persist(like3);
        entityManager.flush();

        // when
        List<Likes> likesList = likesRepository.findAllByRoomsId(room1.getId());

        // then
        assertEquals(2, likesList.size());
    }

    @Test
    @Transactional
    @DisplayName("LikesRepository findByRoomsId Success")
    void findByRoomsIdSuccess() {
        // given
        Rooms room1 = Rooms.builder().name("room number1").build();

        entityManager.persist(room1);
        entityManager.flush();

        Likes like = Likes.builder().rooms(room1).build();
        entityManager.persist(like);
        entityManager.flush();

        // when
        Optional<Likes> foundLike = likesRepository.findByRoomsId(1L);

        // then
        // 이거 테스트 머리 식히고 하겠음...
        // assertEquals(like, foundLike.orElse(null));
    }

    @Test
    @DisplayName("LikesRepository findBuRoomsIdAndUsersId Success")
    void findByRoomsIdAndUsersIdSuccess() {
        // given
        Rooms room1 = Rooms.builder().name("room number1").build();
        Users user1 = Users.builder().nickName("users number1").build();

        entityManager.persist(room1);
        entityManager.persist(user1);
        entityManager.flush();

        Likes like = Likes.builder().rooms(room1).users(user1).build();
        entityManager.persist(like);
        entityManager.flush();

        // when
        Optional<Likes> foundLike = likesRepository.findByRoomsIdAndUsersId(room1.getId(), user1.getId());

        // then
        assertEquals(like, foundLike.orElse(null));
    }
}
