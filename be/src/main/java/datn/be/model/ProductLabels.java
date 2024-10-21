package datn.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "ec_product_labels")

@Getter
@Setter

public class ProductLabels {
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

    // Mối quan hệ nhiều-nhiều với `Product`
    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    private Set<Product> products;
}
