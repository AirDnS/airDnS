package com.example.airdns.domain.room.entity;

import com.example.airdns.domain.restschedule.entity.RestSchedule;
import com.example.airdns.domain.image.entity.Images;
import com.example.airdns.domain.like.entity.Likes;
import com.example.airdns.domain.reservation.entity.Reservation;
import com.example.airdns.domain.review.entity.Reviews;
import com.example.airdns.domain.roomequipment.entity.RoomEquipments;
import com.example.airdns.domain.user.entity.Users;
import com.example.airdns.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE rooms SET is_deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Rooms extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String address;

    @Column
    private Integer size;

    @Column
    private String description;

    @Column
    private Boolean isClosed;

    @Column
    private Boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<Reservation> reservationList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<Images> imagesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<RestSchedule> restScheduleList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<RoomEquipments> roomEquipmentsList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<Likes> likesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private List<Reviews> reviewsList = new ArrayList<>();

  
    public void addImage(Images images) {
        this.imagesList.add(images);
    }

    public void deleteImages(Images images) {
        this.imagesList.remove(images);
    }

    public void resetEquipments() {this.roomEquipmentsList.clear();}
    public void addEquipments(RoomEquipments roomEquipments) {
        this.roomEquipmentsList.add(roomEquipments);
    }

    public void updateRooms(
            String name,
            BigDecimal price,
            String address,
            Integer size,
            String description) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.size = size;
        this.description = description;
    }

    public void updateIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }
      
    public void addReview(Reviews review){
        reviewsList.add(review);
    }

    public void addRestSchedule(RestSchedule restSchedule) {
        this.restScheduleList.add(restSchedule);
    }

    public void deleteRestSchedule(RestSchedule restSchedule) {
        this.restScheduleList.remove(restSchedule);
    }
}
