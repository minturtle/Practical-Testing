package sample.cafekiosk.spring.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

    public static Order of(List<Product> products, LocalDateTime orderTime){
        // create Order
        Order order = new Order();

        //set Values
        order.orderStatus = OrderStatus.INIT;
        order.totalPrice = calculateTotalPrice(products);
        order.orderTime = orderTime;
        order.orderProducts = products.stream().map(product ->
            OrderProduct.builder()
                    .product(product)
                    .order(order)
                    .build()
        ).collect(Collectors.toList());


        return order;
    }

    private static int calculateTotalPrice(List<Product> products) {
        return products.stream().mapToInt(Product::getPrice).sum();
    }


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime orderTime;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

}
