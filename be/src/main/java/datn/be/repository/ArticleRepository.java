package datn.be.repository;

import datn.be.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT m FROM Article m")
    Page<Article> getLists(Pageable pageable);

    // Tìm bài viết dựa trên slug
    Optional<Article> findBySlug(String slug);
}
