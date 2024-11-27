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
    @Query("SELECT m FROM Article m LEFT JOIN m.tags t WHERE " +
            "(:name = '' OR :name IS NULL OR m.name LIKE %:name%) AND " +
            "(:menu_id IS NULL OR m.menu.id = :menu_id) AND " +
            "(COALESCE(:tagIds, NULL) IS NULL OR t.id IN (:tagIds))")
    Page<Article> getLists(Pageable pageable,
                           @Param("name") String name,
                           @Param("tagIds")  Set<Long> tagIds ,
                           @Param("menu_id") Long menu_id
    );

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id IN :tagIds GROUP BY a.id")
    Page<Article> getListsByTagIds(@Param("tagIds") Set<Long> tagIds, Pageable pageable);

    Optional<Article> findBySlug(String slug);
}
