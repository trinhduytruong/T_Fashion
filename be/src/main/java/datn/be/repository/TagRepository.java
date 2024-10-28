package datn.be.repository;

import datn.be.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t")
    Page<Tag> getLists(Pageable pageable);

    Optional<Tag> findBySlug(String slug);
}

