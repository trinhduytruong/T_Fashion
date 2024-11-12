package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.UserView;
import datn.be.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserService service;


    @GetMapping
    public PaginatedResponse<UserView> getListsUser(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsUser) [Admin] #####");
        try {
            Page<UserView> lists = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", lists);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsUser) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<UserView>> createUser(@RequestBody UserView userView) {
        logger.info("##### REQUEST RECEIVED (createUser) [Admin] #####");
        try {
            UserView dataCreate = service.create(userView);
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", dataCreate
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (createUser) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<UserView>> updateUser(
            @PathVariable Long id,
            @RequestBody UserView userView) {
        logger.info("##### REQUEST RECEIVED (updateUser) [Admin] #####");
        try {
            UserView updateModel = service.update(id, userView);
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updateModel
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateUser) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteUser) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteUser) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<UserView>> findUserById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findUserById) [Admin] #####");
        try{
            UserView modelData = service.findById(id);
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<UserView> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findUserById) [Admin] #####");
        }
    }
}

