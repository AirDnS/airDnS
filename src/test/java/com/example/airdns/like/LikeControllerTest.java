package com.example.airdns.like;

import com.example.airdns.domain.like.controller.LikesController;
import com.example.airdns.domain.like.dto.LikesResponseDto;
import com.example.airdns.domain.like.service.LikesService;
import com.example.airdns.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@WebMvcTest(LikesController.class)
@ContextConfiguration(classes = LikesController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikesService likesService;

    @Test
    @DisplayName("LikesController getLikeList")
    void getLikeListSuccess() throws Exception{
        // given
        Long roomsId = 1L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        List<LikesResponseDto.GetLikeResponseDto> responseDtoList = Arrays.asList(
                LikesResponseDto.GetLikeResponseDto.builder().build(),
                LikesResponseDto.GetLikeResponseDto.builder().build()
        );

        when(likesService.getLikeList(anyLong(), any())).thenReturn(responseDtoList);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/rooms/{roomsId}/likes", roomsId)
                        .param("userId", String.valueOf(user.getId())) // add any additional parameters if needed
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("룸 좋아요 조회 성공"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").exists());
    }

    @Test
    @DisplayName("LikesController addLike")
    void addLikeSuccess() throws Exception {
        // given
        Long roomsId = 1L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        LikesResponseDto.AddLikeResponseDto responseDto = LikesResponseDto.AddLikeResponseDto.builder().build();

        when(likesService.postLike(anyLong(), any())).thenReturn(responseDto);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/rooms/{roomsId}/likes", roomsId)
                        .param("userId", String.valueOf(user.getId())) // add any additional parameters if needed
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("msg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());

    }

    @Test
    @DisplayName("LikesController cancelLike")
    void cancelLikeSuccess() throws Exception {
        // given
        Long roomsId = 1L;
        Users user = Users.builder().id(1L).nickName("TestUser").build();
        LikesResponseDto.DeleteLikeResponseDto responseDto = LikesResponseDto.DeleteLikeResponseDto.builder().build();

        when(likesService.cancelLike(anyLong(), any())).thenReturn(responseDto);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/rooms/{roomsId}/likes", roomsId)
                        .param("userId", String.valueOf(user.getId())) // add any additional parameters if needed
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("msg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());

    }
}
