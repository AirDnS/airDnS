package com.example.airdns.review;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewsRepositoryTest {

    /*@Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
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
    }*/
}
