package datn.be.service;

import datn.be.model.Product;
import datn.be.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

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

    public Product createProduct(Product product) {
        try {
            logger.info("create product: " + product);
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error("ProductService.createProduct(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Product updateProduct(Long id, Product product) {
        try {
            Optional<Product> existingProductOpt = productRepository.findById(id);

            if (existingProductOpt.isPresent()) {
                Product existingProduct = existingProductOpt.get();
                existingProduct.setName(product.getName());
                existingProduct.setSlug(product.getSlug());
                existingProduct.setAvatar(product.getAvatar());
                logger.info("update product: " + existingProduct);
                return productRepository.save(existingProduct);
//            return existingProduct;
            } else {
                throw new RuntimeException("Product not found with id " + id);
            }
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

