package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Category;
import datn.be.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PaginatedResponse<Category> getAllCategories(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {

        logger.info("##### REQUEST RECEIVED (getAllCategories) #####");
        try {
            Page<Category> categoryPage = this.categoryService.getListCategories(name, status, page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", categoryPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", null);

        } finally {
            logger.info("##### REQUEST FINISHED (getAllCategories) [Admin] #####");
        }
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }
}