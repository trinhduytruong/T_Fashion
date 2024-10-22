package datn.be.controller.admin;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.dto.ArticleRequest;
import datn.be.model.Article;
import datn.be.model.Menu;
import datn.be.service.ArticleService;
import datn.be.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/articles")
public class AdminArticleController {

    private static final Logger logger = LoggerFactory.getLogger(AdminArticleController.class);

    @Autowired
    private ArticleService service;

    @Autowired
    private MenuService menuService;

    @GetMapping
    public PaginatedResponse<Article> getListsArticle(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsArticle) [Admin] #####");
        try {
            Page<Article> lists = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", lists);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "successfully", null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsArticle) [Admin] #####");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse.SingleResponse<Article>> createArticle(@RequestBody ArticleRequest articleRequest) {
        logger.info("##### REQUEST RECEIVED (createArticle) [Admin] #####");
        try {
            Menu menu = menuService.findById(articleRequest.getMenuId());
            Article article = new Article();
            article.setName(articleRequest.getName());
            article.setSlug(articleRequest.getSlug());
            article.setStatus(articleRequest.getStatus());
            article.setDescription(articleRequest.getDescription());
            article.setIs_featured(articleRequest.getIsFeatured());
            article.setViews(articleRequest.getViews());
            article.setAvatar(articleRequest.getAvatar());
            article.setContent(articleRequest.getContent());
            article.setMenu(menu);

            Article createdArticle = service.create(article, articleRequest.getTags());
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", createdArticle
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (createArticle) [Admin] #####");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Article>> updateArticle(
            @PathVariable Long id,
            @RequestBody Article article) {
        logger.info("##### REQUEST RECEIVED (updateArticle) [Admin] #####");
        try {
            Article updateModel = service.update(id, article);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", updateModel
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (updateArticle) [Admin] #####");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (deleteArticle) [Admin] #####");
        try {
            service.delete(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } finally {
            logger.info("##### REQUEST FINISHED (deleteArticle) [Admin] #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Article>> findArticleById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findArticleById) [Admin] #####");
        try{
            Article modelData = service.findById(id);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findArticleById) [Admin] #####");
        }
    }
}
