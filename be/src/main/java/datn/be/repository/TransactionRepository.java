package datn.be.repository;

import datn.be.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    void deleteByOrderId(Long orderId);
}
