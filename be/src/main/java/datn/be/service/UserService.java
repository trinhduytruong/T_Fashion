package datn.be.service;

import datn.be.model.UserView;
import datn.be.repository.UserViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserViewRepository repository;

    public Page<UserView> getLists(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<UserView> listUserView = repository.getLists(pageable);
            logger.info("List UserView: " + listUserView);
            return listUserView;
        } catch (Exception e){
            logger.error("UserService.getLists() ", e);
            throw new RuntimeException(e);
        }
    }

    public UserView create(UserView userView) {
        try {
            logger.info("Create UserView: " + userView);
            return repository.save(userView);
        } catch (Exception e){
            logger.error("UserService.create() ", e);
            throw new RuntimeException(e);
        }
    }

    public UserView update(Long id, UserView user) {
        try {
            Optional<UserView> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                UserView existingUpdate = existingOpt.get();
                existingUpdate.setName(user.getName());

                logger.info("Update user with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("UserService.update() ", e);
            throw new RuntimeException(e);
        }
    }

    public UserView updateProfile(Long id, UserView user) {
        try {
            Optional<UserView> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                UserView existingUpdate = existingOpt.get();
                if(user.getAvatar() != null) {
                    existingUpdate.setAvatar(user.getAvatar());
                }
                if(user.getName() != null) {
                    existingUpdate.setName(user.getName());
                }
                if(user.getEmail() != null) {
                    existingUpdate.setEmail(user.getEmail());
                }
                if(user.getPhone() != null) {
                    existingUpdate.setPhone(user.getPhone());
                }

                logger.info("Update user with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("UserService.updateProfile() ", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("Delete user with ID: " + id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("UserService.delete() ", e);
            throw new RuntimeException(e);
        }
    }

    public UserView findById(Long id) {
        logger.info("Get user with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }
}

