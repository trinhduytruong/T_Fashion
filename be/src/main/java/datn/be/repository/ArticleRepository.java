package datn.be.repository;

import datn.be.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT m FROM Article m")
    Page<Article> getLists(Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id IN :tagIds GROUP BY a.id")
    Page<Article> getListsByTagIds(@Param("tagIds") Set<Long> tagIds, Pageable pageable);

    // Tìm bài viết dựa trên slug
    Optional<Article> findBySlug(String slug);
}
