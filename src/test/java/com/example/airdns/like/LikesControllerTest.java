package com.example.airdns.like;

import com.example.airdns.domain.like.controller.LikesController;
import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
import com.example.airdns.domain.room.entity.Rooms;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.global.config.WebSecurityConfig;
import com.example.airdns.global.jwt.JwtAuthorizationFilter;
import com.example.airdns.global.security.UserDetailsImpl;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = LikesController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, JwtAuthorizationFilter.class})
)
// @EnableJpaAuditing 이거 때문에 해야함
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
public class LikesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikesService likesService;

    @Test
    @DisplayName("LikesController readRoomLike Success")
    void readRoomLikeSuccess() throws Exception{
        // given
        Long roomId = 1L;
        Users notLoginUser = Users.builder().id(1L).name("User Name").build();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                Users.builder().name("User Name").role(UserRole.USER).build()
        );

        Rooms room = Rooms.builder().id(roomId).users(notLoginUser).build();

        LikesResponseDto.ReadLikeResponseDto responseDto =
                LikesResponseDto.ReadLikeResponseDto.builder()
                        .ownerName(room.getUsers().getName())
                        .roomName(room.getName())
                        .likeCount(5)
                        .build();

        when(likesService.readRoomLike(room.getId())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/rooms/{roomId}/likes", roomId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("룸 좋아요 조회 성공"))
                .andExpect(jsonPath("$.data.likeCount").value(5))
                .andExpect(jsonPath("$.data.roomName").value(room.getName()))
                .andExpect(jsonPath("$.data.ownerName").value(room.getUsers().getName()));
    }

    @Test
    @WithMockUser(username = "testUser")
    @DisplayName("LikeController createLike Success")
    void createLikeSuccess() throws Exception {
        // given
        Long roomId = 1L;

        UserDetailsImpl userDetails = new UserDetailsImpl(
                Users.builder().nickname("testUser").role(UserRole.USER).build()
        );

        Rooms.builder().id(roomId).users(userDetails.getUser()).name("Room Name").build();

        LikesResponseDto.CreateLikeResponseDto responseDto
                = new LikesResponseDto.CreateLikeResponseDto("testUser","Room Name"); // Assuming '10' is the new like count

        when(likesService.createLike(eq(roomId), any())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/rooms/{roomId}/likes", roomId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("룸 좋아요 성공"))
                .andExpect(jsonPath("$.data.roomName").value("Room Name"));
    }

    @Test
    @WithMockUser(username = "testUser")
    @DisplayName("LikeController deleteLike Success")
    void deleteLikeSuccess() throws Exception {
        // given
        Long roomId = 1L;
        Long likeId = 1L;

        UserDetailsImpl userDetails = new UserDetailsImpl(
                Users.builder().nickname("testUser").role(UserRole.USER).build()
        );

        Rooms.builder().id(roomId).users(userDetails.getUser()).name("Room Name").build();

        LikesResponseDto.DeleteLikeResponseDto responseDto = new LikesResponseDto.DeleteLikeResponseDto();
        when(likesService.deleteLike(eq(roomId), eq(likeId), any())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(delete("/api/v1/rooms/{roomsId}/likes/{likeId}", roomId, likeId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("룸 좋아요 취소 성공"));
    }
}
