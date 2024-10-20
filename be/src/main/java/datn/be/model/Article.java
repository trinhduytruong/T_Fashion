package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bl_articles")

@Getter
@Setter
public class Article {
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
    private String description;

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private Integer is_featured;

    @Column(nullable = true)
    private Integer menu_id;

    @Column(nullable = true)
    private Integer views;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private Menu menu;
}

