package sample.cafekiosk.spring.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;



public class OrderTest {

    @Test
    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다.")
    void t1() throws Exception {
        //given
        final List<Product> dummyData = getDummyData();
        final LocalDateTime orderTime = LocalDateTime.now();
        //when
        Order order = Order.of(dummyData,orderTime);
        //then
        assertThat(order.getTotalPrice()).isEqualTo(11000);
    }


    @Test
    @DisplayName("상품 리스트과 주문 날짜를 기반으로 주문을 생성할 수 있다. 생성된 주문의 status는 INIT이다.")
    void t2() throws Exception {
        //given
        final List<Product> dummyData = getDummyData();
        final LocalDateTime orderTime = LocalDateTime.now();
        //when
        Order order = Order.of(dummyData,orderTime);
        //then
        assertThat(order).extracting("totalPrice", "orderTime","orderStatus")
                .containsExactlyInAnyOrder(11000, orderTime, OrderStatus.INIT);
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
