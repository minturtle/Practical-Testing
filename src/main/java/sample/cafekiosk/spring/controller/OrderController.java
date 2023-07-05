package sample.cafekiosk.spring.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.service.OrderService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/new")
    public void createOrder(@RequestBody OrderCreateRequest reqBody){
        orderService.createOrder(reqBody, LocalDateTime.now());

    }


}
