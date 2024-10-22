package datn.be.service;

import datn.be.model.Menu;
import datn.be.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository repository;

    public Page<Menu> getLists(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            return this.repository.getLists(pageable);
        } catch (Exception e){
            logger.error("MenuService.getLists() ", e);
            throw new RuntimeException("Failed to fetch menus", e);
        }
    }

    public Menu create(Menu menu) {
        try {
            logger.info("create menu: " + menu);
            return repository.save(menu);
        } catch (Exception e){
            logger.error("MenuService.create() ", e);
            throw new RuntimeException("Failed to fetch menus", e);
        }
    }

    public Menu update(Long id, Menu menu) {
        try {
            Optional<Menu> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Menu existingUpdate = existingOpt.get();
                existingUpdate.setName(menu.getName());
                existingUpdate.setSlug(menu.getSlug());
                existingUpdate.setStatus(menu.getStatus());
                existingUpdate.setDescription(menu.getDescription());
                existingUpdate.setUpdated_at(menu.getUpdated_at());

                logger.info("update menu with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("MenuService.update() ", e);
            throw new RuntimeException("Failed to fetch menus", e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete menu with ID: " + id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("MenuService.delete() ", e);
            throw new RuntimeException("Failed to fetch menus", e);
        }
    }

    public Menu findById(Long id) {
        logger.info("get menu with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }

    public Menu findBySlug(String slug) {
        logger.info("get menu by slug " + slug);
        return repository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Data not found with slug " + slug));
    }
}
