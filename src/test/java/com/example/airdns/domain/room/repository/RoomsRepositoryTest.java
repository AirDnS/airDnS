package com.example.airdns.domain.room.repository;

import com.example.airdns.domain.room.entity.Rooms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("RoomsRepository 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoomsRepositoryTest extends RoomsTestConstant{

    @Autowired
    private RoomsRepository roomsRepository;

    @Test
    @DisplayName("스터디 룸 등록")
    void roomInsertTest() {

        //when
        Rooms room = Rooms.builder()
                .name(testName)
                .price(testPrice)
                .description(testDescription)
                .build();

        Rooms result = roomsRepository.save(room);


        //then
        assertThat(result.getAddress()).isNull();
        assertThat(result.getDescription()).isEqualTo(testDescription);
        assertThat(result.getPrice()).isEqualTo(testPrice);
    }

}