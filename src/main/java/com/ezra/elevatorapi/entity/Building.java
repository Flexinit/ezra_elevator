package com.ezra.elevatorapi.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Building {
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
    private String location;
    private int numOfFloors;

    //Audit fields
    private String createdBy;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
    private String updatedBy;
    private LocalDateTime updatedAt;
    
    
}
