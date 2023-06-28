package com.ezra.elevatorapi.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Floor {
    @Id
    @SequenceGenerator(
            name = "elevator_sequence",
            sequenceName = "elevator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "elevator_sequence"
    )

    @Hidden
    private Long id;
    private String name;
    private int floorNumber;//e.g floor -1 stands for basement 1, floor 0 is  ground floor
    private Long buildingId;
    //Audit fields
    private String createdBy;
    private LocalDateTime createAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
}
