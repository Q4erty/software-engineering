package org.narxoz.lab5;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "operators")
@Data
public class Operators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String department;

    @ManyToMany(mappedBy = "operators")
    private List<ApplicationRequest> requests;
}
