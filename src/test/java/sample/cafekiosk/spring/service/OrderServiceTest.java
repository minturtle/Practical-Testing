package sample.cafekiosk.spring.service;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
import sample.cafekiosk.spring.domain.Stock;
import sample.cafekiosk.spring.repository.OrderProductRepository;
import sample.cafekiosk.spring.repository.OrderRepository;
import sample.cafekiosk.spring.repository.ProductRepository;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.dto.response.OrderResponse;
import sample.cafekiosk.spring.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAll();
        stockRepository.deleteAll();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAll();

    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void t1() throws Exception {
        //given
        final List<Product> dummyData = getDummyData();
        final LocalDateTime orderTime = LocalDateTime.now();
        productRepository.saveAll(dummyData);

        OrderCreateRequest reqBody = OrderCreateRequest.builder()
                .productNumberList(List.of("001", "002"))
                .build();

        //when
        OrderResponse orderResponse = orderService.createOrder(reqBody, orderTime);
        //then

        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse).extracting("totalPrice", "orderDateTime")
                        .containsExactly(6500, orderTime);

        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber")
                .containsExactlyInAnyOrder("001", "002");
    }

    @Test
    @DisplayName("중복해서 같은 상품을 여러개 주문할 수 있다.")
    void t2() throws Exception {
        //given
        List<Product> dummyData = getDummyData();
        productRepository.saveAll(dummyData);
        OrderCreateRequest reqBody = OrderCreateRequest.builder()
                .productNumberList(List.of("001", "001"))
                .build();

        LocalDateTime testOrderTime = LocalDateTime.of(2023, 7, 7, 00, 00);
        //when
        OrderResponse order = orderService.createOrder(reqBody, testOrderTime);
        //then
        assertThat(order.getId()).isNotNull();
        assertThat(order).extracting("totalPrice", "orderDateTime")
                .containsExactly(7000, testOrderTime);
        assertThat(order.getProducts()).extracting("productNumber")
                .containsExactly("001", "001");

    }

    @Test
    @DisplayName("병 음료와 베이커리는 재고 정보를 가진다.")
    void t3() throws Exception {
        //given

        List<Product> dummyData = getDummyData();
        Stock stock = Stock.builder()
                .stockQuantity(5)
                .product(dummyData.get(1))
                .build();
        Stock stock2 = Stock.builder()
                .stockQuantity(3)
                .product(dummyData.get(2))
                .build();

        productRepository.saveAll(dummyData);
        stockRepository.saveAll(List.of(stock, stock2));

        //when
        List<Product> bottleBakeryProducts = productRepository.findAllByProductNumberIn(List.of("002", "003"));
        List<Stock> stocks = bottleBakeryProducts.stream().map(Product::getStock).toList();

        //then
        assertThat(stocks).extracting("stockQuantity", "product")
                .containsExactly(tuple(5, dummyData.get(1)), tuple(3, dummyData.get(2)));

    }

    @Test
    @DisplayName("HANDMADE 타입의 상품은 재고를 가질 수 없다.")
    void t4() throws Exception {
        //given
        List<Product> dummyData = getDummyData();

        //when
        assertThatThrownBy(()->{
            Stock stock = Stock.builder()
                    .stockQuantity(5)
                    .product(dummyData.get(0))
                    .build();
        }).isInstanceOf(IllegalArgumentException.class);


        //then
    }



    private List<Product> getDummyData(){
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SELLING)
                .name("아메리카노")
                .price(3500)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.BAKERY)
                .sellingType(ProductSellingType.HOLD)
                .name("크로와상")
                .price(3000)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.BOTTLE)
                .sellingType(ProductSellingType.STOP_SELLING)
                .name("카페 라떼")
                .price(4500)
                .build();
        return List.of(product1, product2, product3);
    }

}