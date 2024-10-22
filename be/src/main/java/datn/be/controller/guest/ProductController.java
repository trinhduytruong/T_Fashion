package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Product;
import datn.be.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @GetMapping
    public PaginatedResponse<Product> getAllProduct(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "product_labels", required = false) List<Long> productLabelIds,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getAllProduct) #####");
        try {
            Page<Product> dataResponse = this.service.getListProducts(name, status, productLabelIds, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", dataResponse);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getAllProduct) #####");
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Product>> findProductBySlug(@PathVariable String slug) {
        logger.info("##### REQUEST RECEIVED (findProductBySlug) #####");
        try{
            Product modelData = service.findBySlug(slug);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findProductBySlug) #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Product>> findProductById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findProductById) #####");
        try{
            Product category = this.service.getProductById(id);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", category
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Product> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findProductById) #####");
        }
    }
}

