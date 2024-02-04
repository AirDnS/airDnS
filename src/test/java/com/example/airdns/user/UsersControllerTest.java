package com.example.airdns.user;

import com.example.airdns.domain.review.controller.ReviewsController;
import com.example.airdns.domain.user.controller.UsersController;
import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.domain.user.service.UsersService;
import com.example.airdns.global.common.dto.CommonResponse;
import com.example.airdns.global.config.WebSecurityConfig;
import com.example.airdns.global.jwt.JwtAuthorizationFilter;
import com.example.airdns.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(
        controllers = UsersController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, JwtAuthorizationFilter.class})
)
// @EnableJpaAuditing 이거 때문에 해야함
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService usersService;

    private UserDetailsImpl userDetails;

    private Users notLoginUser;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        // given
        objectMapper = new ObjectMapper();

        userDetails = new UserDetailsImpl(
                Users.builder()
                        .name("User Name")
                        .role(UserRole.USER)
                        .nickname("User Nickname")
                        .email("test@gmail.com")
                        .contact("010-1111-1111")
                        .address("어디시 어디구 어디동")
                        .build()
        );

        notLoginUser = Users.builder().name("User Name")
                .address("어디시 어딘구 어딘동")
                .contact("010-2222-3333")
                .email("test@gmail.com")
                .role(UserRole.USER)
                .build();
    }

    @Test
    @DisplayName("UsersController updateProfile Success")
    void updateProfileSuccess() throws Exception{
        // given
        UsersRequestDto.UpdateUserInfoRequestDto requestDto =
                UsersRequestDto.UpdateUserInfoRequestDto.builder()
                        .name("테스트")
                        .address("어디시 어디구 어디동")
                        .contact("010-2222-3333")
                        .nickname("홍길동테스트")
                        .build();

        UsersResponseDto.UpdateUsersResponseDto responseDto  =
                UsersResponseDto.UpdateUsersResponseDto.builder()
                        .address(requestDto.getAddress())
                        .contact(requestDto.getContact())
                        .nickname(requestDto.getNickname())
                        .name(requestDto.getName())
                        .build();

        when(usersService.updateUser(userDetails.getUser().getId(), requestDto)).thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/users/profile")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("프로필 수정 성공"));
    }

    @Test
    @DisplayName("UsersController updateRole Success")
    void updateRoleSuccess() throws Exception{
        // given
        UsersResponseDto.UpdateUsersResponseDto responseDto  =
                UsersResponseDto.UpdateUsersResponseDto.builder()
                        .address(userDetails.getUser().getAddress())
                        .contact(userDetails.getUser().getContact())
                        .nickname(userDetails.getUser().getNickname())
                        .name(userDetails.getUser().getName())
                        .build();

        when(usersService.updateUserRole(userDetails.getUser().getId())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/users/role")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("권한 변경 성공"));
    }

    @Test
    @DisplayName("UsersController readUserSilence Success")
    void readUserInfoSuccess() throws Exception{
        // given
        UsersResponseDto.ReadUserResponseDto responseDto =
                UsersResponseDto.ReadUserResponseDto.builder()
                        .address(userDetails.getUser().getAddress())
                        .contact(userDetails.getUser().getContact())
                        .email(userDetails.getUser().getEmail())
                        .role(userDetails.getUser().getRole())
                        .name(userDetails.getUser().getName())
                        .nickname(userDetails.getUser().getNickname())
                        .build();

        when(usersService.readUserInfo(userDetails.getUser().getId())).thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("유저 정보 조회 성공"));
    }

    @Test
    @DisplayName("UsersController readUserSilence Success")
    void readUserSilenceSuccess() throws Exception{
        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/silence")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Silence Login"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
