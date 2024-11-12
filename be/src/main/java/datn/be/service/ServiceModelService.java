package datn.be.service;

import datn.be.dto.request.ServiceRequestDto;
import datn.be.model.ServiceModel;
import datn.be.repository.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ServiceModelService {

    @Autowired
    private ServiceRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(ServiceModelService.class);

    public Page<ServiceModel> getLists(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<ServiceModel> listServiceModel = repository.getLists(pageable);
            logger.info("listServiceModel: " + listServiceModel);
            return listServiceModel;

        } catch (Exception e){
            logger.error("ServiceRepository.getLists() ", e);
            throw new RuntimeException("Failed to fetch service", e);
        }
    }


    @Transactional
    public ServiceModel create(ServiceRequestDto serviceRequestDto) {
        try {
            ServiceModel serviceModel = new ServiceModel();
            serviceModel.setPrice(serviceRequestDto.getPrice());
            serviceModel.setDescription(serviceRequestDto.getDescription());
            serviceModel.setName(serviceRequestDto.getName());
            serviceModel.setSlug(serviceRequestDto.getSlug());
            serviceModel.setIs_home_service(serviceRequestDto.getIs_home_service());
            serviceModel.setCreated_at(new Date());
            serviceModel.setUpdated_at(new Date());

            logger.info("Create service model: " + serviceModel);
            return repository.save(serviceModel);
        } catch (Exception e){
            logger.error("ServiceRepository.create() ", e);
            throw new RuntimeException("Failed to fetch service", e);
        }
    }

    public ServiceModel update(Long id, ServiceRequestDto serviceRequestDto) {
        try {
            Optional<ServiceModel> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                ServiceModel serviceModel = existingOpt.get();
                serviceModel.setName(serviceRequestDto.getName());
                serviceModel.setSlug(serviceRequestDto.getSlug());
                serviceModel.setPrice(serviceRequestDto.getPrice());
                serviceModel.setDescription(serviceRequestDto.getDescription());
                serviceModel.setUpdated_at(new Date());
                serviceModel.setIs_home_service(serviceRequestDto.getIs_home_service());

                logger.info("Update service model with ID: " + id);
                return repository.save(serviceModel);
            } else {
                throw new RuntimeException("Service not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ServiceRepository.update() ", e);
            throw new RuntimeException("Failed to fetch service", e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                logger.info("Delete service model with ID: " + id);
                repository.deleteById(id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("ServiceRepository.delete() ", e);
            throw new RuntimeException("Failed to fetch service", e);
        }
    }

    public ServiceModel findById(Long id) {
        logger.info("Get service model with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }

//    public ServiceModel findBySlug(String slug) {
//        return repository.findBySlug(slug)
//                .orElseThrow(() -> new RuntimeException("Data not found with slug " + slug));
//    }
}
