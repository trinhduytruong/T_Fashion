package datn.be.service;

import datn.be.model.Slide;
import datn.be.repository.SlideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlideService {

    private static final Logger logger = LoggerFactory.getLogger(SlideService.class);

    @Autowired
    private SlideRepository repository;

    public Page<Slide> getLists(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Slide> slideList = repository.getLists(pageable);
            logger.info("slideList: " + slideList);
            return slideList;
        } catch (Exception e) {
            logger.error("SlideService.getLists(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Slide create(Slide slide) {
        try {
            logger.info("create slide: " + slide);
            return repository.save(slide);
        } catch (Exception e) {
            logger.error("SlideService.create(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Slide update(Long id, Slide slide) {
        try {
            Optional<Slide> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Slide existingUpdate = existingOpt.get();
                existingUpdate.setName(slide.getName());
                existingUpdate.setAvatar(slide.getAvatar());
                existingUpdate.setDescription(slide.getDescription());
                existingUpdate.setPosition(slide.getPosition());
                existingUpdate.setLink(slide.getLink());
                existingUpdate.setPage(slide.getPage());
                existingUpdate.setUpdated_at(slide.getUpdated_at());

                logger.info("update slide with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Slide not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("SlideService.update(): " + e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete slide with ID: " + id);
            } else {
                throw new RuntimeException("Slide not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("SlideService.delete(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Slide findById(Long id) {
        logger.info("get slide with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slide not found with id " + id));
    }
}
