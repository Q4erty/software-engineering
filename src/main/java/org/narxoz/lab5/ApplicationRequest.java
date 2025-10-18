package org.narxoz.lab5;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "application_request")
@Data
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String commentary;

    private String phone;

    private boolean handled;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToMany
    @JoinTable(
            name = "request_operators",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id")
    )
    private List<Operators> operators;
}