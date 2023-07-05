package sample.cafekiosk.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.dto.response.OrderResponse;
import sample.cafekiosk.spring.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest reqBody) {
        reqBody.getProductNumberList();

        return null;
    }
}
