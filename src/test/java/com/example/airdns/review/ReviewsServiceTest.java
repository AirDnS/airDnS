package com.example.airdns.review;

import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.repository.ReviewsRepository;
import com.example.airdns.domain.review.service.ReviewsServiceImplV1;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.room.repository.RoomsRepository;
import com.example.airdns.domain.room.service.RoomsServiceImplV1;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.security.UserDetailsImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@Transactional
@ExtendWith(MockitoExtension.class)
public class ReviewsServiceTest {
    @Mock
    private RoomsServiceImplV1 roomsService;

    @Mock
    private ReviewsRepository reviewsRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private RoomsRepository roomsRepository;

    @InjectMocks
    private ReviewsServiceImplV1 reviewsService;

    @Test
    @DisplayName("ReviewsService readReviewList Success")
    void readReviewListSuccess() {
        // given
        Users user = Users.builder().nickname("username").build();
        Rooms room = Rooms.builder().id(1L).name("RoomName").users(user).build();

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
    @DisplayName("ReviewsService createReview Success")
    void createReviewSuccess(){
        // given
        ReviewsRequestDto.CreateReviewRequestDto requestDto =
                ReviewsRequestDto.CreateReviewRequestDto.builder()
                        .content("create content")
                        .build();

        Rooms room = Rooms.builder().name("room name").build();
        // when

        // then
    }
}
