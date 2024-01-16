package com.example.airdns.review;

import com.example.airdns.domain.oauth2.common.OAuth2UserPrincipal;
import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.QReviews;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.NotModifyReviewException;
import com.example.airdns.domain.review.exception.NotRemoveReviewException;
import com.example.airdns.domain.review.exception.ReviewAlreadyExistsException;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.review.service.ReviewsServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.room.service.RoomsService;
import com.example.airdns.domain.user.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
    @Mock
    private RoomsService roomsService;

    @Mock
    private ReviewsRepository reviewsRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private QReviews qReviews;

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
    @DisplayName("ReviewsService addReview Success")
    void addReviewSuccess() {
        // given
        Long roomId = 1L;

        Users user = saveUsersInfo();

        ReviewsRequestDto.AddReviewRequestDto requestDto
                = new ReviewsRequestDto.AddReviewRequestDto("Test review content");
        Rooms room = Rooms.builder().id(roomId).name("Room number1").build();
        when(roomsService.findById(roomId)).thenReturn(room);

        when(reviewsRepository.existsByRoomsId(roomId)).thenReturn(Optional.of(
                Reviews.builder()
                        .rooms(room)
                        .users(user)
                        .content(requestDto.getContent())
                        .build())
        );

        Reviews savedReview = Reviews.builder()
                .id(1L)
                .rooms(room)
                .users(user)
                .content(requestDto.getContent())
                .build();

        when(reviewsRepository.save(any(Reviews.class))).thenReturn(savedReview);

        // when
        ReviewsResponseDto.CreateReviewResponseDto result = reviewsService.addReview(roomId, user, requestDto);

        // then
        assertNotNull(result);
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(requestDto.getContent(), result.getContent());
    }

    @Test
    @DisplayName("ReviewsService addReview ReviewAlreadyExistsException")
    void addReviewReviewAlreadyExists() {
        // given
        Long roomsId = 1L;
        OAuth2UserPrincipal userDetails = mock(OAuth2UserPrincipal.class);
        Rooms room = Rooms.builder().id(roomsId).name("Room Number1").build();

        ReviewsRequestDto.AddReviewRequestDto requestDto = ReviewsRequestDto.AddReviewRequestDto.builder()
                .content("Great stay!")
                .build();

        when(roomsService.findById(roomsId)).thenReturn(room);

        // when / then
        assertThrows(ReviewAlreadyExistsException.class,
                () -> reviewsService.addReview(roomsId, userDetails.getUser(), requestDto));

        verify(reviewsRepository, never()).save(any());
    }

    @Test
    @DisplayName("ReviewsService modifyReview Success")
    void modifyReviewSuccess() {
        // given
        Long roomId = 1L;
        Long reviewId = 1L;

        Users user = saveUsersInfo();

        Rooms room = Rooms.builder().id(roomId).users(user).name("Room number1").build();

        Reviews originalReview = Reviews.builder()
                .id(reviewId)
                .rooms(room)
                .users(user)
                .content("original review")
                .build();

        when(reviewsRepository.findByIdAndUsersId(user.getId(), reviewId)).thenReturn(Optional.of(originalReview));

        ReviewsRequestDto.UpdateReviewRequestDto requestDto
                = new ReviewsRequestDto.UpdateReviewRequestDto("modify review");

        // when
        ReviewsResponseDto.UpdateReviewResponseDto result
                = reviewsService.modifyReview(roomId, reviewId, user, requestDto);

        // then
        assertNotNull(result);
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(room.getName(), result.getRoomName());
        assertEquals(requestDto.getContent(), result.getContent());
    }

    @Test
    @DisplayName("ReviewsService modifyReview NotModifyReviewException")
    public void modifyReviewNotModifyReviewException(){
        Long mockUserId = 1L;
        Long mockReviewId = 1L;
        Long mockRoomId = 1L;

        Users user = saveUsersInfo();

        ReviewsRequestDto.UpdateReviewRequestDto mockRequestDto = new ReviewsRequestDto.UpdateReviewRequestDto();

        when(reviewsRepository.findByIdAndUsersId(mockUserId, mockReviewId)).thenReturn(Optional.empty());

        assertThrows(NotModifyReviewException.class, () -> {
            reviewsService.modifyReview(mockRoomId, mockReviewId, user, mockRequestDto);
        });
    }

    @Test
    @DisplayName("ReviewsService deleteReview Success")
    public void deleteReviewSuccess(){
        // given
        Long userId = 1L;
        Long reviewId = 1L;
        Long roomId = 1L;

        Users user = saveUsersInfo();

        Rooms room = Rooms.builder().name("Room Number1").build();
        Reviews review = Reviews.builder()
                .users(user)
                .rooms(room)
                .content("content").build();

        when(reviewsRepository.findByIdAndUsersId(reviewId, userId)).thenReturn(Optional.of(review));

        // when
        reviewsService.removeReview(roomId, reviewId, user);

        // then
        verify(reviewsRepository).deleteById(reviewId);
    }

    @Test
    @DisplayName("ReviewsService deleteReview Success")
    public void testNotRemoveReviewException() {
        // given
        Long userId = 1L;
        Long reviewId = 1L;
        Long roomId = 1L;
        Users user = saveUsersInfo();

        when(reviewsRepository.findByIdAndUsersId(reviewId, userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotRemoveReviewException.class, () -> {
            reviewsService.removeReview(roomId, reviewId, user);
        });
    }

    private Users saveUsersInfo(){
        OAuth2UserPrincipal userDetails = mock(OAuth2UserPrincipal.class);
        Users mockUser = Users.builder().id(1L).nickName("test").build();
        when(userDetails.getUser()).thenReturn(mockUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userDetails.getUser();
    }
}
