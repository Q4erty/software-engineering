package org.narxoz.lab5.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "t_courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "c_name", unique = true, nullable = false)
    private String name;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_price", nullable = false)
    private Long price;
}