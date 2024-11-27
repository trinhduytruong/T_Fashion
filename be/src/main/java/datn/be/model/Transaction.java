package datn.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ec_transactions")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Integer qty;

    @Column(nullable = true)
    private Integer price;

    @Column(nullable = true, columnDefinition = "DEFAULT 'pending'")
    private String status;

    @Column(nullable = true, precision = 16, scale = 2, columnDefinition = "DEFAULT 0")
    private BigDecimal total_price;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
}

