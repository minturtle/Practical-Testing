package sample.cafekiosk.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.Order;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.dto.response.OrderResponse;
import sample.cafekiosk.spring.dto.response.ProductResponse;
import sample.cafekiosk.spring.repository.OrderRepository;
import sample.cafekiosk.spring.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest reqBody, LocalDateTime orderTime) {

        // product number에 해당하는 product들을 모두 찾아온다.
        List<String> productNumbers = reqBody.getProductNumberList();
        List<Product> products = findProducts(productNumbers);

        //Order 객체를 생성한다.
        Order order = Order.of(products, orderTime);


        // Order 객체를 저장한다.
        orderRepository.save(order);

        // 해당 product의 재고를 감소시킨다.
        for(Product product : products){
            if(product.getStock() == null){ continue; }

            product.getStock().deductStock(1);
        }


        // product -> productResponse로 객체를 변환한다.
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::of).collect(Collectors.toList());



        return OrderResponse.builder()
                .id(order.getId())
                .orderDateTime(order.getOrderTime())
                .products(productResponses)
                .totalPrice(order.getTotalPrice())
                .build();
    }


    // 중복된 Products Number이 들어와도, 중복해서 같은 객체가 리스트에 담김
    private List<Product> findProducts(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        // 중복 처리
        final Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        // products의 중복 제거 문제를 해결한 List
        List<Product> duplicateProducts = productNumbers.stream().map(productMap::get).toList();
        return duplicateProducts;
    }
}
