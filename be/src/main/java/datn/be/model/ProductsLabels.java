package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ec_products_labels")

@Getter
@Setter

public class ProductsLabels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_label_id")
    private ProductLabels label;

    // Thêm các trường khác nếu cần
    private String additionalInfo;
}

