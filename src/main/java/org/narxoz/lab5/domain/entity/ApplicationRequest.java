package org.narxoz.lab5.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "c_application_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "t_username", nullable = false)
    private String username;

    @Column(name = "t_commentary")
    private String commentary;

    @Column(name = "t_phone_number", nullable = false)
    private String phone;

    @Column(name = "t_handled", nullable = false)
    private boolean handled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "t_course_id")
    private Courses course;

    @ManyToMany
    @JoinTable(
            name = "c_request_operators",
            joinColumns = @JoinColumn(name = "t_request_id"),
            inverseJoinColumns = @JoinColumn(name = "t_operator_id")
    )
    private List<Operators> operators;

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", commentary='" + commentary + '\'' +
                ", phone='" + phone + '\'' +
                ", handled=" + handled +
                ", course=" + course +
                ", operators=" + operators +
                '}';
    }
}