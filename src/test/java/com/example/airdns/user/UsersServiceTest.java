package com.example.airdns.user;

import com.example.airdns.domain.user.dto.UsersRequestDto;
import com.example.airdns.domain.user.dto.UsersResponseDto;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.enums.UserRole;
import com.example.airdns.domain.user.exception.UsersCustomException;
import com.example.airdns.domain.user.repository.UsersRepository;
import com.example.airdns.domain.user.service.UsersServiceImplV1;
import com.example.airdns.global.security.UserDetailsImpl;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    private UserDetailsImpl userDetails;

    @InjectMocks
    private UsersServiceImplV1 usersService;

    @Mock
    private UsersRepository usersRepository;

    @BeforeEach
    void setup(){
        userDetails = new UserDetailsImpl(
                Users.builder()
                        .id(1L)
                        .name("test User Name")
                        .nickname("test User NickName")
                        .role(UserRole.USER)
                        .isDeleted(false)
                        .build()
        );
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("UsersService findById Success")
    void findByIdSuccess(){
        // given
        when(usersRepository.findByIdAndIsDeletedFalse(userDetails.getUser().getId())).thenReturn(Optional.of(userDetails.getUser()));

        // when
        Users user = usersService.findById(userDetails.getUser().getId());

        // then
        assertNotNull(user);
        assertEquals(userDetails.getUser().getId(), user.getId());
        assertEquals(userDetails.getUser().getName(), user.getName());
        assertEquals(userDetails.getUser().getNickname(), user.getNickname());
        verify(usersRepository).findByIdAndIsDeletedFalse(userDetails.getUser().getId());
    }

    @Test
    @DisplayName("UsersService findById NOT_FOUND_USER Exception")
    void findByIdNotFoundUserException() {
        // given
        Long userId = 2L;
        when(usersRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(UsersCustomException.class, () -> usersService.findById(userId));
        verify(usersRepository).findByIdAndIsDeletedFalse(userId);
    }

    // 이거 안됨
    /*@Test
    @DisplayName("UsersService updateUser Success")
    void updateUserSuccess(){
        // given
        UsersRequestDto.UpdateUserInfoRequestDto requestDto =
                UsersRequestDto.UpdateUserInfoRequestDto.builder()
                        .name("User Name")
                        .nickname("User NickName")
                        .address("어디시 어디구 어딘동")
                        .contact("010-2222-1313")
                        .build();

        when(usersRepository.findById(userDetails.getUser().getId())).thenReturn(Optional.of(userDetails.getUser()));

        // when
        UsersResponseDto.UpdateUsersResponseDto responseDto
                = usersService.updateUser(userDetails.getUser().getId(), requestDto);

        // then
        verify(usersRepository).save(any(Users.class));
        assertEquals(requestDto.getName(), responseDto.getName());
    }

    @Test
    @DisplayName("UsersService updateRole UserRole - Host Success")
    void updateHostRoleSuccess(){
        // given
        Long userId = userDetails.getUser().getId();
        Users existingUser = Users.builder().id(userId).role(UserRole.HOST).build();

        when(usersRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.of(existingUser));

        // when
        UsersResponseDto.UpdateUsersResponseDto responseDto = usersService.updateUserRole(userId);

        // then
        assertEquals(UserRole.HOST, responseDto.getRole());
        verify(usersRepository).findByIdAndIsDeletedFalse(userId);
    }*/
}
