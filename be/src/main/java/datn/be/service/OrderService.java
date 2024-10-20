package datn.be.service;

import datn.be.model.Order;
import datn.be.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository repository;

    public Page<Order> getLists(String code, String status, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Order> orderList = repository.getLists(code, status, pageable);
            logger.info("orderList: " + orderList);
            return orderList;
        } catch (Exception e){
            logger.error("OrderService.getLists() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    public Order create(Order order) {
        try {
            logger.info("create order: " + order);
            return repository.save(order);
        } catch (Exception e){
            logger.error("OrderService.create() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    public Order update(Long id, Order order) {
        try {
            Optional<Order> existingOpt = repository.findById(id);

            if (existingOpt.isPresent()) {
                Order existingUpdate = existingOpt.get();
                existingUpdate.setUpdated_at(order.getUpdated_at());

                logger.info("update order with ID: " + id);
                return repository.save(existingUpdate);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("OrderService.update() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                logger.info("delete order with ID: " + id);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("OrderService.delete() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    public Order findById(Long id) {
        logger.info("get order with ID: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id " + id));
    }
}
