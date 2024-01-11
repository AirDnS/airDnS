package com.example.airdns.review;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.NotModifyReviewException;
import com.example.airdns.domain.review.exception.ReviewAlreadyExistsException;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.review.service.ReviewsServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
    @Mock
    private RoomsService roomsService;

    @Mock
    private ReviewsRepository reviewsRepository;

    @Mock
    private RoomsRepository roomsRepository;

    @InjectMocks
    private ReviewsServiceImplV1 reviewsService;

    @Test
    @DisplayName("ReviewsService getReview Success")
    void getReviewSuccess() {
        // given
        Long roomId = 1L;
        Long reviewId = 1L;

        Reviews mockReview = Reviews.builder()
                .users(Users.builder().nickName("User number 1").build())
                .rooms(Rooms.builder().name("Room Number 1").build())
                .content("review Content")
                .build();

        when(reviewsRepository.findByRoomsId(roomId)).thenReturn(Optional.of(mockReview));

        // when
        ReviewsResponseDto.ReadReviewResponseDto result = reviewsService.getReview(roomId, reviewId);

        // then
        assertNotNull(result);
        assertEquals("review Content", result.getContent());
    }

    @Test
    @DisplayName("ReviewsService getReviews Success")
    void getReviewsSuccess() {
        // given
        Long roomId = 1L;

        List<Reviews> mockReviews = new ArrayList<>();
        Rooms room1 = Rooms.builder().id(1L).name("Room number1").build();

        Reviews mockReview1 = Reviews.builder()
                .users(Users.builder().nickName("User number1").build())
                .rooms(room1)
                .content("review Content1")
                .build();

        mockReviews.add(mockReview1);

        Rooms room2 = Rooms.builder().id(2L).name("Room number2").build();
        Reviews mockReview2 = Reviews.builder()
                .users(Users.builder().nickName("User number2").build())
                .rooms(room2)
                .content("review Content2")
                .build();

        mockReviews.add(mockReview2);
        roomsRepository.save(room1);
        roomsRepository.save(room2);

        when(roomsService.findById(roomId)).thenReturn(room1);

        when(reviewsRepository.findAllByRoomsId(roomId)).thenReturn(mockReviews);

        // when
        List<ReviewsResponseDto.ReadReviewResponseDto> result = reviewsService.getReviews(roomId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("review Content1", result.get(0).getContent());
        assertEquals("review Content2", result.get(1).getContent());
    }

    @Test
    @DisplayName("ReviewsService addReview Success")
    void addReviewSuccess() {
        // given
        Long roomId = 1L;

        UserDetailsImplV1 userDetails = mock(UserDetailsImplV1.class);
        Users mockUser = Users.builder().nickName("test").build();
        when(userDetails.getUser()).thenReturn(mockUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ReviewsRequestDto.AddReviewRequestDto requestDto
                = new ReviewsRequestDto.AddReviewRequestDto("Test review content");
        Rooms room = Rooms.builder().id(roomId).name("Room number1").build();
        when(roomsService.findById(roomId)).thenReturn(room);

        when(reviewsRepository.existsByRoomsId(roomId)).thenReturn(Optional.of(
                Reviews.builder()
                        .rooms(room)
                        .users(mockUser)
                        .content(requestDto.getContent())
                        .build())
        );

        Reviews savedReview = Reviews.builder()
                .id(1L)
                .rooms(room)
                .users(mockUser)
                .content(requestDto.getContent())
                .build();

        when(reviewsRepository.save(any(Reviews.class))).thenReturn(savedReview);

        // when
        ReviewsResponseDto.CreateReviewResponseDto result = reviewsService.addReview(roomId, userDetails, requestDto);

        // then
        assertNotNull(result);
        assertEquals(userDetails.getUser().getNickName(), result.getNickName());
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(requestDto.getContent(), result.getContent());
    }

    /*@Test
    @DisplayName("ReviewsService addReview ReviewAlreadyExistsException")
    void addReviewNotModifyReviewException() {
        // given
        Long roomsId = 1L;
        UserDetailsImplV1 userDetails = mock(UserDetailsImplV1.class);
        ReviewsRequestDto.AddReviewRequestDto requestDto = new ReviewsRequestDto.AddReviewRequestDto("Test review content");

        when(roomsService.findById(roomsId)).thenReturn(mock(Rooms.class));
        when(reviewsRepository.existsByRoomsId(roomsId)).thenReturn(Optional.empty());

        when(reviewsRepository.findByIdAndUsersId(anyLong(), anyLong())).thenReturn(Optional.of(mock(Reviews.class)));

        // when & then
        assertThrows(ReviewAlreadyExistsException.class, () -> {
            reviewsService.addReview(roomsId, userDetails, requestDto);
        });

        verify(reviewsRepository, never()).save(any(Reviews.class));
    }*/
}
