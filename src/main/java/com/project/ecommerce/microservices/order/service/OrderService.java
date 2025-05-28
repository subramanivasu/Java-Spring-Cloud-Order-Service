package com.project.ecommerce.microservices.order.service;

import com.project.ecommerce.microservices.order.dto.OrderRequest;
import com.project.ecommerce.microservices.order.model.Order;
import com.project.ecommerce.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);

        return order.getOrderNumber();


    }
}
