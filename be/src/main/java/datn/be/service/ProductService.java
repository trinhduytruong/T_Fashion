package datn.be.service;

import datn.be.dto.ProductRequest;
import datn.be.model.Category;
import datn.be.model.Product;
import datn.be.model.ProductLabels;
import datn.be.repository.CategoryRepository;
import datn.be.repository.ProductLabelsRepository;
import datn.be.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductLabelsRepository productLabelsRepository;

    public Page<Product> getListProducts(String name, String status, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Product> productList = productRepository.getListProducts(name, status, pageable);
            logger.info("productList:" + productList);
            return productList;
        } catch (Exception e) {
            logger.error("ProductService.getListProducts(): " + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Product createProduct(Product product, Set<Long> productLabelIds) {
        try {
            if (productLabelIds == null) {
                productLabelIds = Collections.emptySet();
            }

            // Tìm các ProductLabels từ danh sách ID
            Set<ProductLabels> labels = productLabelIds.stream()
                    .map(id -> productLabelsRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Label not found: " + id)))
                    .collect(Collectors.toSet());

            product.setLabels(labels);
            logger.info("create product: " + product);
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error("ProductService.createProduct(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(Long id, ProductRequest request) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setName(request.getName());
            product.setSlug(request.getSlug());
            product.setAvatar(request.getAvatar());
            product.setStatus(request.getStatus());

            // Xử lý category nếu có
            if (request.getCategoryId() != null) {
                Category category = categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                product.setCategory(category);
            }

            if (request.getProductsLabels() == null) {
                product.getLabels().clear();  // Xoá tất cả liên kết labels
            } else {
                Set<ProductLabels> labels = request.getProductsLabels().stream()
                        .map(idLabel -> productLabelsRepository.findById(idLabel)
                                .orElseThrow(() -> new RuntimeException("Label not found: " + idLabel)))
                        .collect(Collectors.toSet());

                product.setLabels(labels);
            }

            logger.info("update product with ID: " + id);
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error("ProductService.updateProduct(): " + e);
            throw new RuntimeException(e);
        }
    }

    public void deleteProduct(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                logger.info("delete product with ID: " + id);
            } else {
                throw new RuntimeException("Product not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("ProductService.deleteProduct(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(Long id) {
        logger.info("get product with ID: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }
}

