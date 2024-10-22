package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.ProductLabels;
import datn.be.service.ProductLabelsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product-labels")

public class ProductLabelController {

    private static final Logger logger = LoggerFactory.getLogger(ProductLabelController.class);

    @Autowired
    private ProductLabelsService service;

    @GetMapping
    public PaginatedResponse<ProductLabels> getListsProductLabels(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsProductLabels) #####");
        try {
            Page<ProductLabels> dataResponse = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", dataResponse);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsProductLabels) #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<ProductLabels>> findProductLabelsById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findProductLabelsById) #####");
        try{
            ProductLabels modelData = service.findById(id);
            PaginatedResponse.SingleResponse<ProductLabels> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<ProductLabels> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findProductLabelsById) #####");
        }
    }
}
