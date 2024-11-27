package datn.be.controller.user;

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
@RequestMapping("/api/v1/users/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;

    @GetMapping
    public PaginatedResponse<Order> getAllOrder(
            @RequestParam(value = "code", required = false, defaultValue = "") String code,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getAllOrder) #####");
        try {
            Page<Order> dataResponse = this.service.getLists(code, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", dataResponse);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getAllOrder) #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> findOrderById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findOrderById) #####");
        try{
            Order order = this.service.findById(id);
            PaginatedResponse.SingleResponse<Order> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", order
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Order> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findOrderById) #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> createOrder(@RequestBody OrderRequest modelData) {
        logger.info("##### REQUEST RECEIVED (createOrder) #####");
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
            logger.info("##### REQUEST FINISHED (createOrder) #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Order>> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderRequest modelData) {
        logger.info("##### REQUEST RECEIVED (updateOrder) #####");
        try {
            Order order = service.update(id, modelData);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", order
            ));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.ok(ResponseHelper.createSingleResponse(
                    "error", 1, e.getMessage(), null
            ));
        } finally {
            logger.info("##### REQUEST FINISHED (updateOrder) #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteOrder) #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteOrder) #####");
        }
    }
}

