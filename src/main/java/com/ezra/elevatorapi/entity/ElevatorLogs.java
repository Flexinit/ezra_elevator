package com.ezra.elevatorapi.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
public class ElevatorLogs {
    @Id
    @SequenceGenerator(
            name = "elevator_sequence",
            sequenceName = "elevator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "elevator_sequence"
    )

    @Hidden
    private Long id;
    private Long elevatorId;
    private Long buildingId;
    private int currentFloorNumber;
    private int destinationFloorNumber;
    private String direction;
    private String status;
    //Audit fields
    private String createdBy;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
    private String updatedBy;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;
}
