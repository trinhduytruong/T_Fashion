package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "slides")

@Getter
@Setter

public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true)
    private String page;

    @Column(nullable = true)
    private String link;

    @Column(nullable = true)
    private Integer position;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;
}
