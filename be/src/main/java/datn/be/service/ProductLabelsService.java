package datn.be.service;

import datn.be.model.ProductLabels;
import datn.be.repository.ProductLabelsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductLabelsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductLabelsService.class);

    @Autowired
    private ProductLabelsRepository repository;

    public Page<ProductLabels> getLists(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<ProductLabels> productLabelsList = repository.getLists(pageable);
            logger.info("productLabelsList: " + productLabelsList);
            return productLabelsList;
        } catch (Exception e){
            logger.error("ProductLabelsService.getLists(): ", e);
            throw new RuntimeException("Failed to fetch ProductLabels", e);
        }
    }

    public ProductLabels create(ProductLabels productLabels) {
        try {
            logger.info("create ProductLabels: " + productLabels);
            return repository.save(productLabels);
        } catch (Exception e){
            logger.error("ProductLabelsService.create(): ", e);
            throw new RuntimeException("Failed to fetch ProductLabels", e);
        }
    }

    public ProductLabels update(Long id, ProductLabels productLabels) {
        try {
            Optional<ProductLabels> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                ProductLabels existingUpdate = existingOpt.get();
                existingUpdate.setName(productLabels.getName());
                existingUpdate.setSlug(productLabels.getSlug());
                existingUpdate.setStatus(productLabels.getStatus());
                existingUpdate.setDescription(productLabels.getDescription());
                existingUpdate.setUpdated_at(productLabels.getUpdated_at());

                logger.info("update ProductLabels with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ProductLabelsService.update(): ", e);
            throw new RuntimeException("Failed to fetch ProductLabels", e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete ProductLabels with ID: " + id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ProductLabelsService.delete(): ", e);
            throw new RuntimeException("Failed to fetch ProductLabels", e);
        }
    }

    public ProductLabels findById(Long id) {
        logger.info("get ProductLabels with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }
}
