package datn.be.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images/";

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        logger.info("##### REQUEST RECEIVED (uploadImage) #####");
        try {
            String fileUrl = uploadService.uploadFile(file);
            return ResponseEntity.ok().body(new UploadResponse("success", 0, "Upload successful", fileUrl));
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponse("error", 1, "Upload failed", null));
        } finally {
            logger.info("##### REQUEST FINISHED (uploadImage) #####");
        }
    }

    @PostMapping("/uploads/image")
    public ResponseEntity<?> uploadsImage(@RequestParam("file") MultipartFile file) {
        logger.info("##### REQUEST RECEIVED (uploadsImage) #####");
        try {
            String fileUrl = uploadService.uploadFile(file);
            return ResponseEntity.ok().body(new UploadResponse("success", 0, "Upload successful", fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponse("error", 1, "Upload failed", e.getMessage()));
        } finally {
            logger.info("##### REQUEST FINISHED (uploadsImage) #####");
        }
    }
}
