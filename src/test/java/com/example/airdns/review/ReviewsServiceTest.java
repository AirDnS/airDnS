package com.example.airdns.review;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.exception.ReviewsCustomException;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.review.service.ReviewsServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.service.RoomsServiceImplV1;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
    @Mock
    private RoomsServiceImplV1 roomsService;
    @InjectMocks
    private ReviewsServiceImplV1 reviewsService;
    private UserDetailsImpl userDetails;
    private Users notLoginUser;
    @Mock
    private ReviewsRepository reviewsRepository;
    @BeforeEach
    void setup(){
        userDetails = new UserDetailsImpl(
                Users.builder().id(1L).nickname("testUser").role(UserRole.USER).build()
        );
        notLoginUser = Users.builder().id(2L).name("User Name").role(UserRole.USER).build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @Test
    @DisplayName("ReviewsService readReviewList Success")
    void readReviewListSuccess() {
        // given
        Long roomId = 1L; // 방 ID를 명시적으로 설정
        Users user = Users.builder().nickname(userDetails.getUser().getNickname()).build();
        Rooms room = Rooms.builder().id(roomId).name("RoomName").users(user).build();

        Reviews review1 = Reviews.builder()
                .rooms(room)
                .content("review content1")
                .users(user)
                .rooms(room)
                .build();

        Reviews review2 = Reviews.builder()
                .rooms(room)
                .content("review content2")
                .users(user)
                .rooms(room)
                .build();

        room.addReview(review1);
        room.addReview(review2);

        when(roomsService.findById(room.getId())).thenReturn(room);

        // when
        List<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = reviewsService.readReviewList(room.getId());

        // then
        assertNotNull(responseDtoList);
        assertEquals(2, responseDtoList.size());
        // 해당 사항은 user 객체에 username만 넣었기에 가능
        assertEquals(review1.getUsers().getNickname(), responseDtoList.get(0).getNickName());
        assertEquals(review2.getUsers().getNickname(), responseDtoList.get(1).getNickName());
        assertEquals(review1.getRooms().getName(), responseDtoList.get(0).getRoomName());
        assertEquals(review2.getRooms().getName(), responseDtoList.get(1).getRoomName());
        assertEquals(review1.getContent(), responseDtoList.get(0).getContent());
        assertEquals(review2.getContent(), responseDtoList.get(1).getContent());
    }

    @Test
    @DisplayName("ReviewsService createReview ALREADY_EXISTS_REVIEW Exception")
    void createReviewAlreadyExistsReview(){
        // given
        Long roomId = 5000L;
        Rooms room = Rooms.builder().id(roomId).users(notLoginUser).name("Room Name").build();
        ReviewsRequestDto.CreateReviewRequestDto requestDto =
                ReviewsRequestDto.CreateReviewRequestDto.builder().content("create review").build();

        when(roomsService.findById(roomId)).thenReturn(room);
        // 존재 여부 확인을 위해 true를 반환하도록 설정
        when(reviewsRepository.existsByRoomsId(roomId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ReviewsCustomException.class, () -> reviewsService.createReview(roomId, userDetails.getUser(), requestDto));
    }

    @Test
    @DisplayName("ReviewsService updateReview Success")
    void updateReviewSuccess(){
        Rooms room  = Rooms.builder().users(userDetails.getUser()).build();

        ReviewsRequestDto.UpdateReviewRequestDto requestDto = new ReviewsRequestDto.UpdateReviewRequestDto("Updated Content");

        Reviews review = Reviews.builder().users(userDetails.getUser()).rooms(room).content(requestDto.getContent()).build();
        when(reviewsRepository.findById(review.getId())).thenReturn(Optional.of(review));

        ReviewsResponseDto.UpdateReviewResponseDto updatedReview = reviewsService.updateReview(room.getId(), review.getId(), userDetails.getUser(), requestDto);

        assertNotNull(updatedReview);
        assertEquals("Updated Content", updatedReview.getContent());
        assertEquals(userDetails.getUser().getId(), updatedReview.getUsersId());

        verify(reviewsRepository, times(1)).save(review);
    }

    @Test
    @DisplayName("ReviewsService updateReview NOT_FOUND_REVIEW Exception")
    void updateReviewNotFoundReview(){
        // given
        Long roomId = 1L;
        Long reviewId = 1L;
        ReviewsRequestDto.UpdateReviewRequestDto requestDto
                = new ReviewsRequestDto.UpdateReviewRequestDto("updated content");

        when(reviewsRepository.findById(reviewId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ReviewsCustomException.class,
                () -> reviewsService.updateReview(roomId, reviewId, userDetails.getUser(), requestDto));
    }

    @Test
    @DisplayName("ReviewsService updateReview NOT_MODIFY_REVIEW Exception")
    void updateReviewNotModifyReview(){
        // given
        Long roomId = 1L;
        Rooms room = Rooms.builder()
                .id(roomId)
                .name("Room Name")
                .users(notLoginUser)
                .build();

        Long reviewId = 1L;

        Reviews review = Reviews.builder()
                .users(notLoginUser)
                .id(reviewId)
                .rooms(room)
                .content("original Review!")
                .build();

        ReviewsRequestDto.UpdateReviewRequestDto requestDto = new ReviewsRequestDto.UpdateReviewRequestDto("Update review");

        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.of(review));

        // when & then
        assertThrows(ReviewsCustomException.class,
                () -> reviewsService.updateReview(roomId, reviewId, userDetails.getUser(), requestDto));
    }
    @Test
    @DisplayName("ReviewsService deleteReview Success")
    void deleteReviewSuccess(){
        Rooms room = Rooms.builder()
                .users(userDetails.getUser())
                .name("Room Name")
                .build();

        Reviews review = Reviews.builder()
                .users(userDetails.getUser())
                .rooms(room)
                .content("delete Review!")
                .build();

        when(reviewsRepository.findByIdAndUsersId(review.getId(), userDetails.getUser().getId())).thenReturn(Optional.of(review));

        ReviewsResponseDto.DeleteReviewResponseDto responseDto = reviewsService.deleteReview(room.getId(), review.getId(), userDetails.getUser());

        assertNotNull(responseDto);
        assertEquals("delete Review!", responseDto.getContent());

        verify(reviewsRepository, times(1)).delete(review);
    }

    @Test
    @DisplayName("ReviewsService deleteReview NOT_DELETE_REVIEW Exception")
    void deleteReviewNotDeleteReview(){
        Users loginUser = userDetails.getUser();
        Rooms room = Rooms.builder().id(1L).users(notLoginUser).build();

        Reviews review = Reviews.builder()
                .id(1L)
                .users(notLoginUser)
                .rooms(room)
                .content("delete Reivew! Exception")
                .build();

        // Stubbing : Repository나 Service 클래스의 메서드를 Stubbing하여
        // DB 연결 없이 원하는 결과를 반환하도록 설정할 수 있습니다.
        // Mockito가 사용하지 않는 stubbing을 사용하기에 lenient()... 사용
        lenient().when(reviewsRepository.findById(review.getId())).thenReturn(Optional.of(review));

        assertThrows(ReviewsCustomException.class, () -> reviewsService.deleteReview(room.getId(), review.getId(), loginUser));

        verify(reviewsRepository, never()).delete(any(Reviews.class));
    }
}
