package com.example.backend.services;

import com.example.backend.dto.OrderDetailsDTO;
import com.example.backend.enums.OrderStatus;
import com.example.backend.models.Order;
import com.example.backend.repositorys.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service

public class OrderServices {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductServices productServices;


    public List<Order> findOrdersByDate(LocalDate date) {
        return repository.findOrdersByDate(date);

    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> getOrderByCustomerId(long id) {
        return repository.findOrderByCustomerId(id);
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = repository.findById(id).orElseThrow(() -> new IllegalStateException("this order dose not exist"));
        if (status == OrderStatus.CANCELLED) {
            order.getOrderItems().forEach(orderItem -> {
                productServices.editStockQuantity(orderItem.getProductId(), orderItem.getQuantity(), true);
            });
        }
        order.setStatus(status);
    }


    public Long getOrdersCount(OrderStatus status) {
        return repository.countOrdersByStatus(status);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return repository.findOrdersByStatus(status);
    }

    public OrderDetailsDTO getOrderDetailsById(Long id) {
        return repository.findOrderDetails(id).orElseThrow(() -> new IllegalStateException("this order dose not exist"));
    }

    public List<OrderDetailsDTO> getOrderDetailsByCustomerId(Long id) {
        return repository.findOrderDetailsByCustomerId(id);
    }
}
