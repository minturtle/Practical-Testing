package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class CafeKioskTest {


    @Test
    @DisplayName("키오스크에 음료를 1개 추가하면 주문목록에 담긴다.")
    void t1() throws Exception {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();

        //when
        cafeKiosk.add(new Americano());

        //then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    @Test
    @DisplayName("키오스크에 추가한 음료를 삭제하면 주문 목록에서 제거된다.")
    void t2() throws Exception {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano beverage = new Americano();
        cafeKiosk.add(beverage);

        //when
        cafeKiosk.remove(beverage);

        //then
        assertThat(cafeKiosk.getBeverages()).isEmpty();

    }


    @Test
    @DisplayName("키오스크에 지금까지 담은 음료들을 모두 지우면 주문 목록이 초기화 된다.")
    void t3() throws Exception {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Latte latte = new Latte();
        Americano americano = new Americano();

        cafeKiosk.add(latte);
        cafeKiosk.add(americano);

        //when
        cafeKiosk.clear();

        //then
        assertThat(cafeKiosk.getBeverages()).isEmpty();

    }

    @Test
    @DisplayName("하나의 음료에 대해 여러 잔을 추가해 주문 목록에 여러잔을 한번에 추가할 수 있다.")
    void t4() throws Exception {
        //given
        final CafeKiosk cafeKiosk = new CafeKiosk();
        final Americano americano = new Americano();

        //when
        cafeKiosk.add(americano, 2);

        //then
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    @DisplayName("하나의 음료에 대해 0 이하의 잔은 추가할 수 없다.")
    void t5() throws Exception {
        //given
        final CafeKiosk cafeKiosk = new CafeKiosk();

        //when & then
        assertThatThrownBy(()->cafeKiosk.add(new Americano(), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Count는 0이하일 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"10,00", "22,00"}, delimiter=',')
    @DisplayName("영업시간내에 주문을 해 주문을 생성할 수 있다.")
    void t6(int hour, int min) throws Exception {
        //given
        // + hour, min
        final CafeKiosk cafeKiosk = new CafeKiosk();
        final Americano americano = new Americano();

        cafeKiosk.add(americano);

        //when
        final Order order = cafeKiosk.createOrder(LocalTime.of(hour, min));
        //then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages()).containsExactly(americano);
    }


    @ParameterizedTest
    @CsvSource(value = {"9,59", "22,01"})
    @DisplayName("영업시간 외에는 주문을 할 수 없다.")
    void t7(int hour, int min) throws Exception {
        //given
        //++ hour, min
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        //when & then
        assertThatThrownBy(()->cafeKiosk.createOrder(LocalTime.of(hour, min)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 가능 시간이 아닙니다.");
    }

    @Test
    @DisplayName("지금까지 담은 음료의 총 가격을 계산해 알 수 있다.")
    void t8() throws Exception {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int expected = americano.getPrice() + latte.getPrice();
        //when
        int totalPrice = cafeKiosk.calculateTotalPractice();


        //then
        assertThat(totalPrice).isEqualTo(expected);
    }

}


