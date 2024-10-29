package datn.be.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/images";
    @Value("${app.base-url}")
    private String baseUrl;

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID() + fileExtension;
            String filePath = UPLOAD_DIR + File.separator + fileName;

            Files.copy(file.getInputStream(), Paths.get(filePath));
            String fileUrl = baseUrl + "/uploads/images/" + fileName;
            logger.info("fileUrl: {}", fileUrl);
            return fileUrl;
        } catch (Exception e){
            logger.error("UploadService.uploadFile() ", e);
            throw new RuntimeException(e);
        }
    }
}
