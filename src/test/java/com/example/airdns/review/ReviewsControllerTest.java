package com.example.airdns.review;

import com.example.airdns.domain.review.controller.ReviewsController;
import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.jwt.UserDetailsImplV1;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(ReviewsController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewsService reviewsService;

    private ReviewsResponseDto.ReadReviewResponseDto fakeReviewResponseDto;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("ReviewsController getReview Success")
    public void getReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long reviewId = 123L;

        String username = "testUser";
        String password = "testPassword";

        // 가상 사용자를 생성하는 부분을 업데이트
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.setContext(securityContext);

        UserDetailsImplV1 userDetails = new UserDetailsImplV1(
                Users.builder()
                        .nickName(username)
                        .password(password)
                        .build()
        );

        ReviewsResponseDto.ReadReviewResponseDto mockReview = ReviewsResponseDto.ReadReviewResponseDto.builder()
                .content("review1")
                .nickName(userDetails.getUser().getNickName())
                .build();

        when(reviewsService.getReview(roomId, reviewId)).thenReturn(mockReview);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomsId}/review/{reviewId}", roomId, reviewId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("리뷰 단건 조회 성공"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("review1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nickName").value(userDetails.getUser().getNickName()));
    }


    @Test
    @DisplayName("ReviewsController getReviews Success")
    public void getReviewsSuccess() throws Exception {
        // given
        Long roomId = 1L;

        String username = "testUser";
        String password = "testPassword";

        // 가상 사용자를 생성하는 부분을 업데이트
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.setContext(securityContext);

        UserDetailsImplV1 userDetails = new UserDetailsImplV1(
                Users.builder()
                        .nickName(username)
                        .password(password)
                        .build()
        );

        Rooms rooms = Rooms.builder().name("Room Number1").users(userDetails.getUser()).build();

        List<ReviewsResponseDto.ReadReviewResponseDto> mockReviews = new ArrayList<>();
        mockReviews.add(
                ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .content("review1")
                        .nickName(userDetails.getUser().getNickName())
                        .roomName(rooms.getName())
                        .build()
        );

        Page<ReviewsResponseDto.ReadReviewResponseDto> mockPage = new PageImpl<>(mockReviews);

        when(reviewsService.getReviews(roomId, Pageable.unpaged())).thenReturn(mockPage);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomsId}/review", roomId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 전체 조회 성공"));
                //.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].content").value("review1"));
    }

    @Test
    @DisplayName("ReviewsController addReview Success")
    public void addReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;

        String username = "testUser";
        String password = "testPassword";

        UserDetailsImplV1 userDetails = new UserDetailsImplV1(
                Users.builder()
                        .nickName(username)
                        .password(password)
                        .build()
        );

        ReviewsRequestDto.AddReviewRequestDto requestDto =
                new ReviewsRequestDto.AddReviewRequestDto("Great room!");

        System.out.println("addReviewRequestDto.getCotent : "+requestDto.getContent());

        ReviewsResponseDto.CreateReviewResponseDto mockResponseDto = ReviewsResponseDto.CreateReviewResponseDto.builder()
                .content(requestDto.getContent())
                .nickName(userDetails.getUser().getNickName())
                .build();

        when(reviewsService.addReview(roomId, userDetails, requestDto)).thenReturn(mockResponseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rooms/{roomsId}/review", roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 작성 성공"));
    }

    @Test
    @DisplayName("ReviewsController modifyReview Success")
    public void modifyReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long reviewId = 2L;

        String username = "testUser";
        String password = "testPassword";

        UserDetailsImplV1 userDetails = new UserDetailsImplV1(
                Users.builder()
                        .nickName(username)
                        .password(password)
                        .build()
        );

        ReviewsRequestDto.UpdateReviewRequestDto requestDto =
                new ReviewsRequestDto.UpdateReviewRequestDto("Updated room review!");

        ReviewsResponseDto.UpdateReviewResponseDto mockResponseDto = ReviewsResponseDto.UpdateReviewResponseDto.builder()
                .content(requestDto.getContent())
                .nickName(userDetails.getUser().getNickName())
                .build();

        when(reviewsService.modifyReview(roomId, reviewId, userDetails, requestDto)).thenReturn(mockResponseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/rooms/{roomsId}/review/{reviewId}", roomId, reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 수정 성공"));
    }

    @Test
    @DisplayName("ReviewsController removeReview Success")
    public void removeReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long reviewId = 2L;

        String username = "testUser";
        String password = "testPassword";

        UserDetailsImplV1 userDetails = new UserDetailsImplV1(
                Users.builder()
                        .nickName(username)
                        .password(password)
                        .build()
        );

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rooms/{roomsId}/review/{reviewId}", roomId, reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 삭제 성공"));
    }
}
