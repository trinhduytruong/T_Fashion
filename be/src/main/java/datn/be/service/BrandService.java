package datn.be.service;

import datn.be.model.Brand;
import datn.be.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Autowired
    private BrandRepository repository;

    public Page<Brand> getLists(String name, String status, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Brand> brandList = repository.getLists(name, status, pageable);
            logger.info("brandList: " + brandList);
            return brandList;
        } catch (Exception e) {
            logger.error("BrandRepository.getLists(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Brand create(Brand brand) {
        try {
            logger.info("create brand: " + brand);
            return repository.save(brand);
        } catch (Exception e) {
            logger.error("BrandRepository.create(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Brand update(Long id, Brand brand) {
        try {
            Optional<Brand> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Brand existingUpdate = existingOpt.get();
                existingUpdate.setName(brand.getName());
                existingUpdate.setSlug(brand.getSlug());
                existingUpdate.setAvatar(brand.getAvatar());
                existingUpdate.setStatus(brand.getStatus());
                existingUpdate.setUpdated_at(brand.getUpdated_at());

                logger.info("update brand with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Brand not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("BrandRepository.update(): " + e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete brand with ID: " + id);
            } else {
                throw new RuntimeException("Brand not found with id " + id);
            }
        } catch (Exception e) {
            logger.error("BrandRepository.delete(): " + e);
            throw new RuntimeException(e);
        }
    }

    public Brand findById(Long id) {
        logger.info("get brand with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id " + id));
    }
}

