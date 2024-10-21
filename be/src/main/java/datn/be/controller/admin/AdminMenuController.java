package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Menu;
import datn.be.model.Tag;
import datn.be.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/menus")
public class AdminMenuController {

    private static final Logger logger = LoggerFactory.getLogger(AdminMenuController.class);

    @Autowired
    private MenuService service;


    @GetMapping
    public PaginatedResponse<Menu> getListsMenu(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getListsMenu) [Admin] #####");
        try {
            Page<Menu> lists = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", lists);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsMenu) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Menu>> createMenu(@RequestBody Menu modelData) {
        logger.info("##### REQUEST RECEIVED (createMenu) [Admin] #####");
        try {
            Menu dataCreate = service.create(modelData);
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", dataCreate
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (createMenu) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Menu>> updateMenu(
            @PathVariable Long id,
            @RequestBody Menu menu) {
        logger.info("##### REQUEST RECEIVED (updateMenu) [Admin] #####");
        try {
            Menu updateModel = service.update(id, menu);
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updateModel
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateMenu) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteMenu) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteMenu) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Menu>> findMenuById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findMenuById) [Admin] #####");
        try{
            Menu modelData = service.findById(id);
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<Menu> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findMenuById) [Admin] #####");
        }
    }
}

