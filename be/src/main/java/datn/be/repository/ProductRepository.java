package datn.be.repository;

import datn.be.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p LEFT JOIN p.labels l WHERE " +
            "(:name = '' OR :name IS NULL OR p.name LIKE %:name%) AND " +
            "(:status = '' OR :status IS NULL OR p.status = :status) AND " +
            "(COALESCE(:productLabelIds, NULL) IS NULL OR l.id IN (:productLabelIds))")
    Page<Product> getListProducts(@Param("name") String name,
                                  @Param("status") String status,
                                  @Param("productLabelIds") List<Long> productLabelIds,
                                  Pageable pageable);

    Optional<Product> findBySlug(String slug);
}
