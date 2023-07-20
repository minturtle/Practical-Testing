package sample.cafekiosk.spring.domain;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class StockTest {

    @Test
    @DisplayName("재고를 뺄 수 있다")
    void t1() throws Exception {
        //given
        Stock dummyStock = getDummyStock(5);
        //when
        dummyStock.deductStock(3);

        //then
        assertThat(dummyStock.getStockQuantity()).isEqualTo(2);
    }


    @Test
    @DisplayName("재고를 뺄 때, 요청 갯수가 재고의 수보다 클 수 없다(예외가 발생한다)")
    void t2() throws Exception {
        //given
        Stock dummyStock = getDummyStock(5);

        //when
        ThrowableAssert.ThrowingCallable throwableMethod = ()->dummyStock.deductStock(10);
        //then
        assertThatThrownBy(throwableMethod).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"5,3,false", "1,3,true", "3,3,false"})
    @DisplayName("현재 남아있는 수량이 요청받은 수량보다 작은지 확인할 수 있다.")
    void t3(int stockQuantity, int requestCount, boolean expected) throws Exception {
        //given
        final Stock stock = getDummyStock(stockQuantity);

        //when
        boolean actual = stock.isQuantityLessThan(requestCount);
        //then
        assertThat(actual).isEqualTo(expected);
    }


    private Stock getDummyStock(int stockQuantity){
        final Product product = Product.builder()
                .productNumber("001")
                .name("아메리카노")
                .type(ProductType.BAKERY)
                .build();


        return Stock.builder()
                .product(product)
                .stockQuantity(stockQuantity)
                .build();
    }



}