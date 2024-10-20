package datn.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ec_orders")

@Getter
@Setter


public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String code;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private String payment_status;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @Column(nullable = true)
    private Integer total_shipping_fee;

    @Column(nullable = true)
    private Integer amount;

    @Column(nullable = true)
    private Integer shipping_amount;

    @Column(nullable = true)
    private Integer tax_amount;

    @Column(nullable = true)
    private Integer discount_amount;

    @Column(nullable = true)
    private Integer sub_total;

    @Column(nullable = true)
    private String coupon_code;

    @Column(nullable = true)
    private String completed_at;

    @Column(nullable = true)
    private String notes;

    // Thêm liên kết với Transaction
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}

