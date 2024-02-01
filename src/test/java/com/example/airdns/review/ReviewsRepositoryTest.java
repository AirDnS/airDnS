package com.example.airdns.review;

import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewsRepositoryTest {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("ReviewsRepository findByRoomsId Success")
    void findByRoomsIdSuccess() {
        // given
        Rooms room = Rooms.builder().name("Room Number").build();
        entityManager.persist(room);

        Reviews review = Reviews.builder().rooms(room).build();
        entityManager.persist(review);
        entityManager.flush();

        // when
        Optional<Reviews> result = reviewsRepository.findByRoomsId(review.getRooms().getId());

        // then
        assertTrue(result.isPresent());
        assertEquals(review.getRooms().getId(), result.get().getRooms().getId());
    }

    @Test
    @Transactional
    @DisplayName("ReviewsRepository existsByRoomsId Success")
    void existsByRoomsIdSuccess() {
        // given
        Rooms room = Rooms.builder().name("Room Number").build();
        entityManager.persist(room);

        Reviews review = Reviews.builder().rooms(room).build();
        entityManager.persist(review);
        entityManager.flush();

        // when
        Optional<Reviews> result = reviewsRepository.existsByRoomsId(review.getRooms().getId());

        // then
        assertTrue(result.isPresent());
    }

    @Test
    @Transactional
    @DisplayName("ReviewsRepository findByIdAndUsersId Success")
    void findByIdAndUsersIdSuccess() {
        // given
        Users user = Users.builder().name("User Name").build();
        entityManager.persist(user);

        Rooms room = Rooms.builder().users(user).name("Room Number").build();
        entityManager.persist(room);

        Reviews review = Reviews.builder().users(user).rooms(room).build();
        entityManager.persist(review);
        entityManager.flush();

        // when
        Optional<Reviews> result = reviewsRepository.findByIdAndUsersId(review.getId(), review.getUsers().getId());

        // then
        assertTrue(result.isPresent());
        assertEquals(review.getId(), result.get().getId());
        assertEquals(review.getUsers().getId(), result.get().getUsers().getId());
    }
}
