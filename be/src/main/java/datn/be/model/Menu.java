package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bl_menus")

@Getter
@Setter

public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String slug;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true, columnDefinition = "enum('published', 'draft', 'pending') DEFAULT 'pending'")
    private String status;

    @Column(nullable = true, columnDefinition = " DEFAULT 1")
    private int is_featured;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;
}
