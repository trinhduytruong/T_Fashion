package datn.be.repository;

import datn.be.model.ProductLabels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductLabelsRepository extends JpaRepository<ProductLabels, Long> {
    @Query("SELECT pl FROM ProductLabels pl")
    Page<ProductLabels> getLists(Pageable pageable);
}
