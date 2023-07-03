package sample.cafekiosk.spring.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.unit.beverages.Americano;

public class CafeKioskTest {


    @Test
    @DisplayName("add Test")
    void t1() throws Exception {
        final CafeKiosk cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());

        System.out.println(cafeKiosk.getBeverages().size());
        System.out.println(cafeKiosk.getBeverages().get(0).getName());
    }
}
