package datn.be.service;

import datn.be.dto.request.OrderRequest;
import datn.be.model.Order;
import datn.be.model.Product;
import datn.be.model.Transaction;
import datn.be.model.UserView;
import datn.be.repository.OrderRepository;
import datn.be.repository.ProductRepository;
import datn.be.repository.TransactionRepository;
import datn.be.repository.UserViewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserViewRepository userViewRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private static final String PREFIX = "OD";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 10 - PREFIX.length();

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

    @Transactional
    public Order create(OrderRequest orderRequest) {
        try {
            Order order = this.buildOrder(orderRequest, null);
            order = repository.save(order);
            if(!orderRequest.getProducts().isEmpty()) {
                // Save product details into transactions
                this.buildTransaction(order, orderRequest.getProducts());
            }
            Optional<Order> orderOptional = repository.findById(order.getId());
            return orderOptional.isPresent() ? orderOptional.get() : null;
        } catch (Exception e){
            logger.error("OrderService.create() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    @Transactional
    public Order update(Long id, OrderRequest orderRequest) {
        try {
            Order order = repository.getById(id);
            if (order != null) {
                Order newOrder = this.buildOrder(orderRequest, order);
                return repository.save(newOrder);
            } else {
                throw new RuntimeException("Data not found with id " + id);
            }
        } catch (Exception e){
            logger.error("OrderService.update() ", e);
            throw new RuntimeException("Failed to fetch order", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                transactionRepository.deleteByOrderId(id);
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

    private Order buildOrder(OrderRequest orderData, Order order) {
        if(order == null) {
            order = new Order();
            order.setCreated_at(new Date());

            if(orderData.getUser_id() != null) {
                Optional<UserView> user = userViewRepository.findById(orderData.getUser_id());
                if(user.isPresent()) {
                    order.setUser(user.get());
                }
            }
        } else {
            if(orderData.getUser_id() != null) {
                Optional<UserView> user = userViewRepository.findById(orderData.getUser_id());
                if(user.isPresent()) {
                    order.setUser(user.get());
                }
            }
        }

        BigDecimal subTotal = orderData.getTotal_amount().add(orderData.getShipping_fee() != null ? orderData.getShipping_fee() : BigDecimal.ZERO);

        order.setPayment_status(orderData.getPayment_status());
        order.setCode(generateOrderCode(10));
        order.setTotal_shipping_fee(orderData.getShipping_fee() != null ? orderData.getShipping_fee() :
                (order.getShipping_amount() != null ? order.getShipping_amount() : BigDecimal.ZERO));

        order.setPayment_method_id(orderData.getPayment_method_id() != null ? new BigDecimal(orderData.getPayment_method_id()) :
                (order.getPayment_method_id() != null ? order.getPayment_method_id() : new BigDecimal(1)));

        order.setPayment_status(orderData.getPayment_status() != null ? orderData.getPayment_status() : "pending");
        order.setStatus(orderData.getStatus()!= null ? orderData.getStatus() : "pending");
        order.setCoupon_code(orderData.getCoupon_code());
        order.setAmount(orderData.getTotal_amount() != null ? orderData.getTotal_amount() : BigDecimal.ZERO);
        order.setShipping_amount(orderData.getShipping_fee() != null ? orderData.getShipping_fee() : BigDecimal.ZERO);
        order.setTax_amount(orderData.getTax_amount() != null ? orderData.getTax_amount() : BigDecimal.ZERO);
        order.setDiscount_amount(orderData.getDiscount_amount() != null ? orderData.getDiscount_amount() : BigDecimal.ZERO);
        order.setSub_total(subTotal);
        order.setCompleted_at(orderData.getCompleted_at() != null ? new Date(orderData.getCompleted_at()) : new Date());
        order.setNotes(orderData.getNotes());
        order.setUpdated_at(new Date());

        return order;
    }


    private void buildTransaction(Order order, List<OrderRequest.ProductData> products) {
        if(!products.isEmpty()) {
            // Save product details into transactions
            for (OrderRequest.ProductData productData : products) {
                Product product = productRepository.getById(productData.getId());
                if(product != null) {
                    BigDecimal totalProductPrice = new BigDecimal(product.getPrice()).multiply(new BigDecimal(productData.getQuantity()));
                    Transaction transaction = new Transaction();
                    transaction.setOrder(order);
                    transaction.setPrice(product.getPrice());
                    transaction.setQty(productData.getQuantity());
                    transaction.setProduct(product);
                    transaction.setTotal_price(totalProductPrice);
                    transaction.setStatus(order.getStatus() != null ? order.getStatus() : "pending");
                    transactionRepository.save(transaction);
                }

            }
        }
    }

    public String generateOrderCode(int length) {

        SecureRandom random = new SecureRandom();
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomCode.append(CHARACTERS.charAt(index));
        }

        return PREFIX + randomCode.toString();
    }
}
