package datn.be.service;

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

    public Page<Article> getLists(int page, int size, Set<Long> tagIds) {
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            if (tagIds != null && !tagIds.isEmpty()) {
                Page<Article> articleList = repository.getListsByTagIds(tagIds, pageable);
                logger.info("articleList: " + articleList);
                return articleList;
            } else {
                Page<Article> articleList = repository.getLists(pageable);
                logger.info("articleList: " + articleList);
                return articleList;
            }
        } catch (Exception e){
            logger.error("ArticleService.getLists() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    @Transactional
    public Article create(Article article, Set<Long> tagIds) {
        try {
            if (tagIds == null) {
                tagIds = Collections.emptySet();
            }

            // Tìm các Tag từ danh sách ID
            Set<Tag> tags = tagIds.stream()
                    .map(id -> tagRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Tag not found: " + id)))
                    .collect(Collectors.toSet());

            article.setTags(tags);
            return repository.save(article);
        } catch (Exception e){
            logger.error("ArticleService.create() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    public Article update(Long id, Article article) {
        try {
            Optional<Article> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Article existingUpdate = existingOpt.get();
                existingUpdate.setName(article.getName());
                existingUpdate.setSlug(article.getSlug());
                existingUpdate.setStatus(article.getStatus());
                existingUpdate.setAvatar(article.getAvatar());
                existingUpdate.setDescription(article.getDescription());
                existingUpdate.setUpdated_at(article.getUpdated_at());

                logger.info("update article with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
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
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ArticleService.delete() ", e);
            throw new RuntimeException("Failed to fetch article", e);
        }
    }

    public Article findById(Long id) {
        logger.info("get article with id " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }

    public Article findBySlug(String slug) {
        logger.info("get article by slug " + slug);
        return repository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Data not found with slug " + slug));
    }
}
