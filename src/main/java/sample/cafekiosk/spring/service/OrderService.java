package sample.cafekiosk.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.domain.Order;
import sample.cafekiosk.spring.domain.OrderStatus;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.dto.response.OrderResponse;
import sample.cafekiosk.spring.dto.response.ProductResponse;
import sample.cafekiosk.spring.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest reqBody) {

        // product number에 해당하는 product들을 모두 찾아온다.
        List<String> productNumbers = reqBody.getProductNumberList();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);


        //Order 객체를 생성한다.
        Order order = Order.of(products);

        // product -> productResponse로 객체를 변환한다.
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::of).collect(Collectors.toList());


        // 총 금액을 계산한다.
        final int totalPrice = products.stream()
                .mapToInt(Product::getPrice).sum();


        return OrderResponse.builder()
                .id(order.getId())
                .localDateTime(LocalDateTime.now())
                .products(productResponses)
                .totalPrice(totalPrice)
                .build();
    }
}
