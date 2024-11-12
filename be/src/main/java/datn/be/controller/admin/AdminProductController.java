package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.dto.request.ProductRequest;
import datn.be.model.Category;
import datn.be.model.Product;
import datn.be.service.CategoryService;
import datn.be.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")

public class AdminProductController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PaginatedResponse<Product> getListProducts(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "product_labels", required = false) List<Long> productLabelIds,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getListProducts) [Admin] #####");
        try {
            Page<Product> productPage = this.productService.getListProducts(name, status, productLabelIds, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", productPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListProducts) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Product>> createProduct(
            @RequestBody ProductRequest request) {
        logger.info("##### REQUEST RECEIVED (createProduct) [Admin] #####");
        try {
            if (request.getCategoryId() == null) {
                throw new RuntimeException("Category ID must not be null");
            }

            Category category = categoryService.getCategoryById(request.getCategoryId());

            Product product = new Product();
            product.setCategory(category);
            product.setName(request.getName());
            product.setNumber(request.getNumber());
            product.setSlug(request.getSlug());
            product.setPrice(request.getPrice());
            product.setSale(request.getSale());
            product.setStatus(request.getStatus());
            product.setAvatar(request.getAvatar());
            product.setDescription(request.getDescription());
            product.setContents(request.getContents());

            Product createdProduct = productService.createProduct(product, request.getProductsLabels());

            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", createdProduct
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (createProduct) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        logger.info("##### REQUEST RECEIVED (updateProduct) [Admin] #####");
       try {
           Product updatedProduct = productService.updateProduct(id, request);
           PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                   "success", 0, "successfully", updatedProduct
           );

           return ResponseEntity.ok(response);
       } catch (Exception e) {
           logger.info("Exception: " + e.getMessage(), e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(null);
       } finally {
           logger.info("##### REQUEST FINISHED (updateProduct) [Admin] #####");
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteProduct) [Admin] #####");
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteProduct) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Product>> getProductById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (getProductById) [Admin] #####");
        try {
            Product product = productService.getProductById(id);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", product
            );
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (getProductById) [Admin] #####");
        }
    }
}
