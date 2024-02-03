package com.example.airdns.user;

import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.domain.user.repository.UsersRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users savedUser;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        for(Long i = 1L; i< 30; i++){
            savedUser = usersRepository.save(
                    Users.builder()
                            .id(i)
                            .email("test"+i+"@example.com")
                            .nickname("TestUser")
                            .isDeleted(false)
                            .build()
            );
        }
    }

    @Test
    @Transactional
    @DisplayName("UserRepository findByEmail Success")
    void findByEmailSuccess(){
        // given
        entityManager.persist(savedUser);

        // when
        Optional<Users> result = usersRepository.findByEmail(savedUser.getEmail());

        // then
        assertEquals(savedUser.getEmail(), result.get().getEmail());
    }

    @Test
    @Transactional
    @DisplayName("UserRepository findByIdAndIsDeletedFalse Success")
    void findByIdAndIsDeletedFalseSuccess(){
        // given
        entityManager.persist(savedUser);
        // when
        Optional<Users> result = usersRepository.findByIdAndIsDeletedFalse(savedUser.getId());
        // then
        assertEquals(savedUser.getIsDeleted(), result.get().getIsDeleted());
    }
}
