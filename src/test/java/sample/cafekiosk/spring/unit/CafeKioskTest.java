package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.unit.beverages.Americano;

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

}
