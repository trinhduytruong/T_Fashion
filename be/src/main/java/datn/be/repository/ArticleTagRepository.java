package datn.be.repository;

import datn.be.model.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    void deleteByArticleId(Long articleId);
}
