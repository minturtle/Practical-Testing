package sample.cafekiosk.spring.service;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
import sample.cafekiosk.spring.repository.ProductRepository;
import sample.cafekiosk.spring.dto.request.OrderCreateRequest;
import sample.cafekiosk.spring.dto.response.OrderResponse;

import java.util.List;


@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;




    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다")
    void t1() throws Exception {
        //given
        final List<Product> dummyData = getDummyData();
        productRepository.saveAll(dummyData);

        OrderCreateRequest reqBody = OrderCreateRequest.builder()
                .productNumberList(List.of("001", "002"))
                .build();

        //when
        OrderResponse orderResponse = orderService.createOrder(reqBody);
        //then

        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse.getTotalPrice()).isEqualTo(6500);

        assertThat(orderResponse.getLocalDateTime()).isNotNull();

        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber")
                .containsExactlyInAnyOrder("001", "002");
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