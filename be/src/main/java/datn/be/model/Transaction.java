package datn.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private Integer total_price;

    @Column(nullable = true)
    private String created_at;

    @Column(nullable = true)
    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;
}
