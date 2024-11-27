package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.dto.request.OrderRequest;
import datn.be.model.Order;
import datn.be.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/order")
public class AdminOrderController {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

    @Autowired
    private OrderService service;


    @GetMapping
    public PaginatedResponse<?> getListsOrder(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") int size,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "code", required = false, defaultValue = "") String code) {
        logger.info("##### REQUEST RECEIVED (getListsOrder) [Admin] #####");
        try {
            Page<Order> listsOrder = this.service.getLists(code, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", listsOrder);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, e.getMessage(), null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsOrder) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> createOrder(@RequestBody OrderRequest modelData) {
        logger.info("##### REQUEST RECEIVED (createOrder) [Admin] #####");
        try {
            Order order = service.create(modelData);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", order
            ));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "error", 0, e.getMessage(), null
            ));
        } finally {
            logger.info("##### REQUEST FINISHED (createOrder) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderRequest modelData) {
        logger.info("##### REQUEST RECEIVED (updateOrder) [Admin] #####");
        try {
            Order order = service.update(id, modelData);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", order
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "error", 1, e.getMessage(), null
            ));
        } finally {
            logger.info("##### REQUEST FINISHED (updateOrder) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteOrder) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteOrder) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> findOrderById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findOrderById) [Admin] #####");
        try{
            Order order = service.findById(id);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", order
            ));
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            ));
        } finally {
            logger.info("##### REQUEST FINISHED (findOrderById) [Admin] #####");
        }
    }
}
