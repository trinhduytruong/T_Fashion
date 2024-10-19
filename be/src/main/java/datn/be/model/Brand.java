package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ec_brands")

@Getter
@Setter

public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String slug;

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;
}
