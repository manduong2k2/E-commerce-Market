package com.e_com.OrderService.Order.Application.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.OrderService.Order.Application.DTO.Responses.OrderResponse;
import com.e_com.OrderService.Order.Application.Services.OrderService;
import com.e_com.OrderService.Shared.Application.Annotation.Auth.Authenticated;

@RestController
@RequestMapping("/api/orders")
@Authenticated
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> list() {
        return ResponseEntity.ok(orderService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}
