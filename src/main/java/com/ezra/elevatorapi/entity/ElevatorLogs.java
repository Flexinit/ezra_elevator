package com.ezra.elevatorapi.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ElevatorLogs {
    @Id
    @SequenceGenerator(
            name = "elevator_sequence",
            sequenceName = "elevator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.UUID,
            generator = "elevator_sequence"
    )

    @Hidden
    private UUID id;
    private UUID elevatorId;
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
