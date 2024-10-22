package datn.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bl_tags")

@Getter
@Setter

public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String slug;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Article> articles = new HashSet<>();
}

