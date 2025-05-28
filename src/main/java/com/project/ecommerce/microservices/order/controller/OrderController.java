package com.project.ecommerce.microservices.order.controller;

import com.project.ecommerce.microservices.order.dto.OrderRequest;
import com.project.ecommerce.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){

        String orderNumber = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order placed successfully for Order Number: " + orderNumber);

    }
}
