package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Slide;
import datn.be.model.Tag;
import datn.be.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/tags")
public class AdminTagController {

    private static final Logger logger = LoggerFactory.getLogger(AdminTagController.class);

    @Autowired
    private TagService service;


    @GetMapping
    public PaginatedResponse<Tag> getListsTag(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page_size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getListsTag) [Admin] #####");
        try {
            Page<Tag> lists = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", lists);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsTag) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Tag>> createTag(@RequestBody Tag modelData) {
        logger.info("##### REQUEST RECEIVED (createTag) [Admin] #####");
        try {
            Tag dataCreate = service.create(modelData);
            PaginatedResponse.SingleResponse<Tag> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", dataCreate
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (createTag) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Tag>> updateTag(
            @PathVariable Long id,
            @RequestBody Tag tag) {
        logger.info("##### REQUEST RECEIVED (updateTag) [Admin] #####");
        try {
            Tag updateModel = service.update(id, tag);
            PaginatedResponse.SingleResponse<Tag> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updateModel
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Tag> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateTag) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteTag) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteTag) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Tag>> findTagById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findTagById) [Admin] #####");
        try{
            Tag modelData = service.findById(id);
            PaginatedResponse.SingleResponse<Tag> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<Tag> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findTagById) [Admin] #####");
        }
    }
}
