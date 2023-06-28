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
public class ElevatorRequests {
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
    private UUID buildingId;
    private UUID elevatorId;
    private String requestedBy;
    private int currentFloorNumber;
    private int destinationFloorNumber;

    @Hidden
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime requestedAt;
}
