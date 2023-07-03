package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sample.cafekiosk.spring.unit.beverages.Americano;
import sample.cafekiosk.spring.unit.beverages.Latte;
import sample.cafekiosk.spring.unit.order.Order;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class CafeKioskTest {


    @Test
    @DisplayName("add Test")
    void t1() throws Exception {
        CafeKiosk cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");

    }

    @Test
    @DisplayName("remove test")
    void t2() throws Exception {
        CafeKiosk cafeKiosk = new CafeKiosk();

        Americano beverage = new Americano();
        cafeKiosk.add(beverage);

        cafeKiosk.remove(beverage);

        assertThat(cafeKiosk.getBeverages()).isEmpty();

    }


    @Test
    @DisplayName("clear test")
    void t3() throws Exception {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Latte latte = new Latte();
        Americano americano = new Americano();

        cafeKiosk.add(latte);
        cafeKiosk.add(americano);

        cafeKiosk.clear();

        assertThat(cafeKiosk.getBeverages()).isEmpty();

    }

    @Test
    @DisplayName("add test: 하나의 음료 여러잔")
    void t4() throws Exception {
        final CafeKiosk cafeKiosk = new CafeKiosk();

        final Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    @DisplayName("add test: 하나의 음료 여러잔 : 0이하인 경우")
    void t5() throws Exception {
        final CafeKiosk cafeKiosk = new CafeKiosk();

        assertThatThrownBy(()->cafeKiosk.add(new Americano(), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Count는 0이하일 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"10,00", "22,00"}, delimiter=',')
    @DisplayName("CreateOrder Test")
    void t6(int hour, int min) throws Exception {

        final CafeKiosk cafeKiosk = new CafeKiosk();
        final Americano americano = new Americano();

        cafeKiosk.add(americano);

        final Order order = cafeKiosk.createOrder(LocalTime.of(hour, min));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages()).containsExactly(americano);
    }


    @ParameterizedTest
    @CsvSource(value = {"9,59", "22,01"})
    @DisplayName("Exception Case::Create Order test Out Of Time")
    void t7(int hour, int min) throws Exception {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(()->cafeKiosk.createOrder(LocalTime.of(hour, min)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 가능 시간이 아닙니다.");
    }

}


