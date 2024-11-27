package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.dto.request.ServiceRequestDto;
import datn.be.model.ServiceModel;
import datn.be.service.ServiceModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/services")
public class AdminServiceController {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceController.class);

    @Autowired
    private ServiceModelService service;

    @GetMapping
    public PaginatedResponse<?> getListsService(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsService) [Admin] #####");
        try {
            Page<ServiceModel> lists = service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", lists);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, e.getMessage(), null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsService) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<?> createService(@RequestBody ServiceRequestDto serviceRequestDto) {
        logger.info("##### REQUEST RECEIVED (createService) [Admin] #####");
        try {
            ServiceModel dataCreate = service.create(serviceRequestDto);
            PaginatedResponse.SingleResponse<ServiceModel> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", dataCreate
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (createService) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<ServiceModel>> updateService(
            @PathVariable Long id,
            @RequestBody ServiceRequestDto serviceRequestDto) {
        logger.info("##### REQUEST RECEIVED (updateService) [Admin] #####");
        try {
            ServiceModel updateModel = service.update(id, serviceRequestDto);
            PaginatedResponse.SingleResponse<ServiceModel> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updateModel
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<ServiceModel> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateService) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteService) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteService) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<ServiceModel>> findServiceById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findServiceById) [Admin] #####");
        try{
            ServiceModel modelData = service.findById(id);
            PaginatedResponse.SingleResponse<ServiceModel> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<ServiceModel> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findServiceById) [Admin] #####");
        }
    }
}

