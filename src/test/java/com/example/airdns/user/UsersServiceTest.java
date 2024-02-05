package com.example.airdns.user;

import com.example.airdns.domain.payment.service.PaymentServiceImplV1;
import com.example.airdns.domain.reservation.service.ReservationServiceImplV1;
import com.example.airdns.domain.reservation.servicefacade.ReservationServiceFacadeImplV1;
import com.example.airdns.domain.room.service.RoomsServiceImplV1;
import com.example.airdns.domain.room.servicefacade.RoomsServiceFacadeImplV1;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    @Mock
    private RoomsServiceImplV1 roomsService;

    @Mock
    private ReservationServiceImplV1 reservationService;

    @Mock
    private PaymentServiceImplV1 paymentService;

    @BeforeEach
    void setup(){
        userDetails = new UserDetailsImpl(
                Users.builder()
                        .id(1L)
                        .name("test User Name")
                        .nickname("test User NickName")
                        .contact("010-2222-1212")
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

    @Test
    @DisplayName("UsersService updateUser Success")
    void updateUserSuccess(){
        // given
        Long userId = userDetails.getUser().getId();
        Users user = userDetails.getUser(); // 실제 업데이트 대상 객체

        UsersRequestDto.UpdateUserInfoRequestDto requestDto =
                UsersRequestDto.UpdateUserInfoRequestDto.builder()
                        .name("User Name")
                        .nickname("User NickName")
                        .contact("010-2222-1313")
                        .address("어디시 어디구 어딘동")
                        .build();

        when(usersRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.of(user));

        // when
        UsersResponseDto.UpdateUsersResponseDto responseDto = usersService.updateUser(userId, requestDto);

        // then
        assertEquals(requestDto.getName(), responseDto.getName());
        assertEquals(requestDto.getNickname(), responseDto.getNickname());
        assertEquals(requestDto.getAddress(), responseDto.getAddress()); // 수정된 부분
        assertEquals(requestDto.getContact(), responseDto.getContact()); // 수정된 부분
    }

    @Test
    @DisplayName("UsersService updateRole UserRole - Host Success")
    void updateHostRoleSuccess(){
        // given
        Long userId = userDetails.getUser().getId();
        Users initialUser = Users.builder()
                .id(userId)
                .role(UserRole.USER)
                .build();

        when(usersRepository.findByIdAndIsDeletedFalse(userId)).thenReturn(Optional.of(initialUser));
        // save 메소드가 호출될 때 입력된 사용자 객체를 그대로 반환하도록 설정
        lenient().when(usersRepository.save(initialUser)).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        UsersResponseDto.UpdateUsersResponseDto responseDto = usersService.updateUserRole(userId);

        // then
        assertEquals(UserRole.HOST, responseDto.getRole());
        verify(usersRepository).findByIdAndIsDeletedFalse(userId);
    }

    @Test
    @DisplayName("UsersService readUserInfo Success")
    void readUserInfoSuccess(){
        // given
        when(usersRepository.findByIdAndIsDeletedFalse(userDetails.getUser().getId())).thenReturn(Optional.of(userDetails.getUser()));
        // when
        UsersResponseDto.ReadUserResponseDto responseDto = usersService.readUserInfo(userDetails.getUser().getId());
        // then
        assertNotNull(responseDto);
        assertEquals(userDetails.getUser().getName(), responseDto.getName());
        assertEquals(userDetails.getUser().getNickname(), responseDto.getNickname());
        assertEquals(userDetails.getUser().getRole(), responseDto.getRole());
    }
}
