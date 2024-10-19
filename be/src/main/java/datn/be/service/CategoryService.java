package datn.be.service;

import datn.be.model.Category;
import datn.be.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getListCategories(String name, String status, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            return this.categoryRepository.getListCategories(name, status, pageable);
        } catch (Exception e) {
            logger.error("CategoryService.getListCategories(): " + e);
            throw new RuntimeException(e);
        }
    }
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        try {
            logger.info("Create category: " + category);
            return categoryRepository.save(category);
        } catch (Exception e) {
            logger.error("CategoryService.createCategory(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Category updateCategory(Long id, Category category) {
        try {
            Optional<Category> existingCategoryOpt = categoryRepository.findById(id);

            logger.info("existingCategoryOpt.isPresent() = " + existingCategoryOpt.isPresent());
            if (existingCategoryOpt.isPresent()) {
                Category existingCategory = existingCategoryOpt.get();
                existingCategory.setName(category.getName());
                existingCategory.setSlug(category.getSlug());
                existingCategory.setAvatar(category.getAvatar());
                existingCategory.setIcon(category.getIcon());
                existingCategory.setStatus(category.getStatus());
                existingCategory.setDescription(category.getDescription());
                existingCategory.setParent_id(category.getParent_id());
                existingCategory.setTitle_seo(category.getTitle_seo());
                existingCategory.setDescription_seo(category.getDescription_seo());
                existingCategory.setKeywords_seo(category.getKeywords_seo());
                existingCategory.setIndex_seo(category.getIndex_seo());
                existingCategory.setUpdated_at(category.getUpdated_at());
                logger.info("Updated category with ID: " + id);

                return categoryRepository.save(existingCategory);
            } else {
                throw new RuntimeException("Category not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("CategoryService.updateCategory(): " + e);
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
                logger.info("Deleted category with ID: " + id);
            } else {
                throw new RuntimeException("Category not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("CategoryService.deleteCategory(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }
}
