package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")

@Getter
@Setter

public class UserView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;
}
