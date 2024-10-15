package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")

@Getter
@Setter

public class Category {
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
    private String icon;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Long parent_id;

    @Column(nullable = true)
    private String title_seo;

    @Column(nullable = true)
    private String description_seo;

    @Column(nullable = true)
    private String keywords_seo;

    @Column(nullable = true)
    private Integer index_seo;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;
}
