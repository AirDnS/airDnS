package com.example.airdns.review;

import com.example.airdns.domain.review.controller.ReviewsController;
import com.example.airdns.domain.review.dto.ReviewsRequestDto;
import com.example.airdns.domain.review.dto.ReviewsResponseDto;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.review.service.ReviewsService;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.config.WebSecurityConfig;
import com.example.airdns.global.jwt.JwtAuthorizationFilter;
import com.example.airdns.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(
        controllers = ReviewsController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, JwtAuthorizationFilter.class})
)
// @EnableJpaAuditing 이거 때문에 해야함
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewsService reviewsService;

    private ObjectMapper objectMapper;

    private UserDetailsImpl userDetails;

    private Users notLoginUser;
    private Rooms room1;
    private Rooms room2;
    private Reviews review1;
    private Reviews review2;
    private Reviews review3;
    private Reviews review4;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();

        userDetails = new UserDetailsImpl(
                Users.builder().name("testUser").role(UserRole.USER).build()
        );

        notLoginUser = Users.builder().name("User Name").build();
        room1 = Rooms.builder().users(userDetails.getUser()).name("Room name1").build();
        room2 = Rooms.builder().users(notLoginUser).name("Room name2").build();

        review1 = Reviews.builder()
                .users(userDetails.getUser())
                .content("너무 별로에요!")
                .rooms(room1)
                .build();

        review2 = Reviews.builder()
                .users(notLoginUser)
                .content("너무 멋있어요!")
                .rooms(room1)
                .build();

        review3 = Reviews.builder()
                .users(userDetails.getUser())
                .content("너무 심각해요!")
                .rooms(room2)
                .build();

        review4 = Reviews.builder()
                .users(notLoginUser)
                .content("또 오고 싶어요!")
                .rooms(room2)
                .build();
    }

    @Test
    @DisplayName("ReviewsController readReviewList Success")
    public void readReviewListSuccess() throws Exception {
        // given
        Long roomsId = 1L;

        List<ReviewsResponseDto.ReadReviewResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(
                ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .reviewsId(review1.getId())
                        .createdAt(review1.getCreatedAt())
                        .nickName(review1.getUsers().getName())
                        .roomName(review1.getRooms().getName())
                        .content(review1.getContent())
                        .build()
        );
        responseDtoList.add(
                ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .reviewsId(review2.getId())
                        .createdAt(review2.getCreatedAt())
                        .nickName(review2.getUsers().getName())
                        .roomName(review2.getRooms().getName())
                        .content(review2.getContent())
                        .build()
        );
        responseDtoList.add(
                ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .reviewsId(review3.getId())
                        .createdAt(review3.getCreatedAt())
                        .nickName(review3.getUsers().getName())
                        .roomName(review3.getRooms().getName())
                        .content(review3.getContent())
                        .build()
        );
        responseDtoList.add(
                ReviewsResponseDto.ReadReviewResponseDto.builder()
                        .reviewsId(review4.getId())
                        .createdAt(review4.getCreatedAt())
                        .nickName(review4.getUsers().getName())
                        .roomName(review4.getRooms().getName())
                        .content(review4.getContent())
                        .build()
        );
        when(reviewsService.readReviewList(roomsId)).thenReturn(responseDtoList);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rooms/{roomsId}/review", roomsId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 전체 조회 성공"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reviewsId").value(responseDtoList.get(0).getReviewsId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].nickName").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roomName").value("Room name1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("너무 별로에요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].reviewsId").value(responseDtoList.get(1).getReviewsId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].nickName").value("User Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].roomName").value("Room name1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("너무 멋있어요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].reviewsId").value(responseDtoList.get(2).getReviewsId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].nickName").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].roomName").value("Room name2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].content").value("너무 심각해요!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].reviewsId").value(responseDtoList.get(3).getReviewsId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].nickName").value("User Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].roomName").value("Room name2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3].content").value("또 오고 싶어요!"));
    }

    @Test
    @DisplayName("ReviewsController createReview Success")
    public void createReviewSuccess() throws Exception {
        // given
        Long roomsId = 1L;

        ReviewsRequestDto.CreateReviewRequestDto requestDto =
                new ReviewsRequestDto.CreateReviewRequestDto("add room review!");

        Reviews createReview = Reviews.builder()
                .rooms(room1)
                .users(userDetails.getUser())
                .content(requestDto.getContent())
                .build();

        ReviewsResponseDto.CreateReviewResponseDto responseDto =
                ReviewsResponseDto.CreateReviewResponseDto.builder()
                        .content(createReview.getContent())
                        .nickName(userDetails.getUsername())
                        .roomName(room1.getName())
                        .build();

        when(reviewsService.createReview(roomsId, userDetails.getUser(), requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/rooms/{roomsId}/review", roomsId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 작성 성공"));
    }

    @Test
    @DisplayName("ReviewsController updateReview Success")
    public void updateReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long reviewId = 1L;

        ReviewsRequestDto.UpdateReviewRequestDto requestDto =
                new ReviewsRequestDto.UpdateReviewRequestDto("update room review!");

        review1.update(requestDto);

        ReviewsResponseDto.UpdateReviewResponseDto responseDto =
                ReviewsResponseDto.UpdateReviewResponseDto.builder()
                        .content(review1.getContent())
                        .reviewsId(review1.getId())
                        .roomName(review1.getRooms().getName())
                        .nickName(review1.getUsers().getName())
                        .build();

        when(reviewsService.updateReview(roomId, reviewId, userDetails.getUser(), requestDto)).thenReturn(responseDto);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/rooms/{roomsId}/review/{reviewId}", roomId, reviewId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                        .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 수정 성공"));
    }

    @Test
    @DisplayName("ReviewsController deleteReview Success")
    public void deleteReviewSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long reviewId = 1L;

        Reviews deleteReview =
                Reviews.builder()
                        .users(userDetails.getUser())
                        .content("delete Review!")
                        .rooms(room1)
                        .build();

        ReviewsResponseDto.DeleteReviewResponseDto responseDto =
                ReviewsResponseDto.DeleteReviewResponseDto.builder().content(deleteReview.getContent()).build();

        when(reviewsService.deleteReview(deleteReview.getId(), deleteReview.getId(), deleteReview.getUsers())).thenReturn(responseDto);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/rooms/{roomsId}/review/{reviewId}", roomId, reviewId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 리뷰 삭제 성공"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
