package datn.be.service;

import datn.be.model.Tag;
import datn.be.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagRepository repository;

    public Page<Tag> getLists(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Tag> tagList = repository.getLists(pageable);
            logger.info("tagList: " + tagList);
            return tagList;
        } catch (Exception e) {
            logger.error("TagService.getLists(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Tag create(Tag tag) {
        try {
            logger.info("create tag: " + tag);
            return repository.save(tag);
        } catch (Exception e) {
            logger.error("TagService.create(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Tag update(Long id, Tag tag) {
        try {
            Optional<Tag> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Tag existingUpdate = existingOpt.get();
                existingUpdate.setName(tag.getName());
                existingUpdate.setSlug(tag.getSlug());
                existingUpdate.setStatus(tag.getStatus());
                existingUpdate.setDescription(tag.getDescription());
                existingUpdate.setUpdated_at(tag.getUpdated_at());

                logger.info("update tag with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("TagService.update(): " + e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete tag with ID: " + id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("TagService.delete(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Tag findById(Long id) {
        logger.info("get tag with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }
}
