package org.narxoz.lab5.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "t_operators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "c_name", nullable = false)
    private String name;

    @Column(name = "c_surname", nullable = false)
    private String surname;

    @Column(name = "c_department", nullable = false)
    private String department;

    @ManyToMany(mappedBy = "operators")
    private List<ApplicationRequest> requests;
}
