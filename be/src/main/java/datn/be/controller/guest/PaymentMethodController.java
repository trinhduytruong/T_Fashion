package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.PaymentMethod;
import datn.be.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments-method")

public class PaymentMethodController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentMethodController.class);

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public PaginatedResponse<PaymentMethod> getListPaymentsMethod(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListPaymentsMethod) #####");
        try {
            Page<PaymentMethod> productPage = this.paymentMethodService.getListPaymentsMethod(name, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", productPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListPaymentsMethod) #####");
        }
    }
}
