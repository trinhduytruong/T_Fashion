package datn.be.repository;

import datn.be.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT od FROM Order od WHERE " +
            "(:code = '' OR :code IS NULL OR od.code LIKE %:code%) AND " +
            "(:status = '' OR :status IS NULL OR od.status = :status)")
    Page<Order> getLists(@Param("code") String code,
                         @Param("status") String status,
                         Pageable pageable);
}
