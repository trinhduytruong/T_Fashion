package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Product;
import datn.be.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")

public class AdminProductController {

    private static final Logger logger = LoggerFactory.getLogger(AdminProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public PaginatedResponse<Product> getListProducts(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getListProducts) [Admin] #####");
        try {
            Page<Product> productPage = this.productService.getListProducts(name, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", productPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListProducts) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        logger.info("##### REQUEST RECEIVED (createProduct) [Admin] #####");
        try {
            Product createdProduct = productService.createProduct(product);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", createdProduct
            );
            return ResponseEntity.ok(createdProduct);
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
            @RequestBody Product product) {
        logger.info("##### REQUEST RECEIVED (updateProduct) [Admin] #####");
       try {
           Product updatedProduct = productService.updateProduct(id, product);
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
