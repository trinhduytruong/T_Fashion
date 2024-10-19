package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.controller.guest.CategoryController;
import datn.be.model.Category;
import datn.be.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public PaginatedResponse<Category> getAllCategories(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getAllCategories) [Admin] #####");
        try {
            Page<Category> categoryPage = this.categoryService.getListCategories(name, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", categoryPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getAllCategories) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Category>> createCategory(@RequestBody Category category) {
        logger.info("##### REQUEST RECEIVED (createCategory) [Admin] #####");
        try {
            Category createdCategory = categoryService.createCategory(category);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", createdCategory
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (createCategory) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Category>> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {
        logger.info("##### REQUEST RECEIVED (updateCategory) [Admin] #####");
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updatedCategory
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateCategory) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Category>> deleteCategory(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteCategory) [Admin] #####");
        try {
            categoryService.deleteCategory(id);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", null
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteCategory) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Category>> getCategoryById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (getCategoryById) [Admin] #####");
        try{
            Category category = categoryService.getCategoryById(id);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", category
            );
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Category> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (getCategoryById) [Admin] #####");
        }
    }
}
