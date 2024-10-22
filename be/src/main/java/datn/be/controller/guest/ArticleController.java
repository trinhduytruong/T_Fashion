package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Article;
import datn.be.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")

public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService service;

    @GetMapping
    public PaginatedResponse<Article> getListsArticle(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsArticle) #####");
        try {
            Page<Article> dataResponse = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", dataResponse);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsArticle) #####");
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Article>> findArticleBySlug(@PathVariable String slug) {
        logger.info("##### REQUEST RECEIVED (findArticleBySlug) #####");
        try{
            Article modelData = service.findBySlug(slug);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findArticleBySlug) #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Article>> findArticleById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findArticleById) #####");
        try{
            Article modelData = service.findById(id);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Article> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findArticleById) #####");
        }
    }
}

