package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "ec_products")

@Getter
@Setter
public class Product {
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
    private String contents;

    @Column(nullable = true)
    private Integer sale;

    @Column(nullable = true)
    private Integer number;

    @Column(nullable = true)
    private Integer price;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ec_products_labels",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_label_id")
    )
    private Set<ProductLabels> labels;
}
