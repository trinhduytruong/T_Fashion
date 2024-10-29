package datn.be.controller.guest;

import datn.be.common.PaginatedResponse;
import datn.be.common.ResponseHelper;
import datn.be.model.Slide;
import datn.be.service.CategoryService;
import datn.be.service.SlideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/slides")

public class SlideController {

    private static final Logger logger = LoggerFactory.getLogger(SlideController.class);

    @Autowired
    private SlideService service;

    @GetMapping
    public PaginatedResponse<Slide> getListsSlide(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        logger.info("##### REQUEST RECEIVED (getListsSlide) #####");
        try {
            Page<Slide> productPage = this.service.getLists(page, size);
            return ResponseHelper.createPaginatedResponse("success", 0, "successfully", productPage);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseHelper.createPaginatedResponse("errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại",null);
        } finally {
            logger.info("##### REQUEST FINISHED (getListsSlide) #####");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginatedResponse.SingleResponse<Slide>> findSlideById(@PathVariable Long id) {
        logger.info("##### REQUEST RECEIVED (findSlideById) #####");
        try{
            Slide modelData = service.findById(id);
            PaginatedResponse.SingleResponse<Slide> response = ResponseHelper.createSingleResponse(
                    "success", 0, "successfully", modelData
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.info("Exception: " + e.getMessage(), e);
            PaginatedResponse.SingleResponse<Slide> response = ResponseHelper.createSingleResponse(
                    "errors", 0, "Có lỗi xẩy ra, xin vui lòng thử lại", null
            );
            return ResponseEntity.ok(response);
        } finally {
            logger.info("##### REQUEST FINISHED (findSlideById) #####");
        }
    }
}
