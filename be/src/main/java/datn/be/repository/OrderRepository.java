package datn.be.repository;

import datn.be.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT od FROM Order od WHERE " +
            "(:code = '' OR :code IS NULL OR od.code LIKE %:code%) AND " +
            "(:status = '' OR :status IS NULL OR od.status = :status)")

    Page<Order> getLists(@Param("code") String code,
                         @Param("status") String status,
                         Pageable pageable);

    @Query(value = " SELECT MONTH(created_at) AS month, SUM(sub_total) AS revenue FROM ec_orders " +
            " WHERE YEAR(created_at) = YEAR(CURDATE())"  +
            " GROUP BY MONTH(created_at) ORDER BY MONTH(created_at)", nativeQuery = true)
    List<Object[]> getMonthlyRevenue();

    @Query(value = " SELECT DAY(created_at) AS day, SUM(sub_total) AS revenue FROM ec_orders " +
            " WHERE MONTH(created_at)=:month AND YEAR(created_at) = :year"  +
            " GROUP BY DAY(created_at) ORDER BY DAY(created_at)", nativeQuery = true)
    List<Object[]> getDailyRevenue(@Param("month") String month, @Param("year") String year);


    @Query(value = " SELECT o.id, o.code, u.name AS customer, " +
            "o.sub_total AS totalAmount, o.created_at AS date FROM ec_orders o JOIN " +
            " users u ON o.user_id = u.id " +
            " ORDER BY o.created_at LIMIT :page_size", nativeQuery = true)
    List<Object[]> getNewOrder(@Param("page_size") int page_size);

}
