package datn.be.service;

import datn.be.dto.request.ArticleRequest;
import datn.be.model.Article;
import datn.be.model.Tag;
import datn.be.repository.ArticleRepository;
import datn.be.repository.ArticleTagRepository;
import datn.be.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    public Page<Article> getLists(int page, int size, String name, Set<Long> tagIds, Long menu_id) {
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Article> articlePage = repository.getLists(pageable, name, tagIds, menu_id);
            logger.info("articlePage: " + articlePage);
            return articlePage;
        } catch (Exception e){
            logger.error("ArticleService.getLists() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    @Transactional
    public Article create(Article article, Set<Long> tagIds) {
        try {
            // Kiểm tra nếu tagIds là null, set nó thành một tập rỗng
            if (!tagIds.isEmpty()) {
                Set<Tag> tags = tagIds.stream()
                        .map(tagId -> tagRepository.findById( tagId)
                                .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId)))
                        .collect(Collectors.toSet());
                article.setTags(tags);
            }

            logger.info("create article: " + article);
            return repository.save(article);
        } catch (Exception e){
            logger.error("ArticleService.create() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    public Article update(Long id, ArticleRequest articleRequest) {
        try {
            Optional<Article> articleOptional = repository.findById(id);

            if (articleOptional.isPresent()) {
                Article article = articleOptional.get();
                article.setName(articleRequest.getName());
                article.setSlug(articleRequest.getSlug());
                article.setStatus(articleRequest.getStatus());
                article.setAvatar(articleRequest.getAvatar());
                article.setDescription(articleRequest.getDescription());
                article.setUpdated_at(new Date());

                if (articleRequest.getTags() != null && !articleRequest.getTags().isEmpty()) {
                    Set<Tag> tags = articleRequest.getTags().stream()
                            .map(tagId -> tagRepository.findById( tagId)
                                    .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId)))
                            .collect(Collectors.toSet());
                    article.setTags(tags);
                }

                logger.info("Update article with ID: " + id);
                return repository.save(article);
            } else {
                throw new RuntimeException("Article not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ArticleService.update() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete article with ID: " + id);
            } else {
                throw new RuntimeException("Article not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ArticleService.delete() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    public Article findById(Long id) {
        logger.info("get article with id " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id " + id));
    }

    public Article findBySlug(String slug) {
        logger.info("get article by slug " + slug);
        return repository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug " + slug));
    }
}
