package sample.cafekiosk.spring.unit;

import sample.cafekiosk.spring.unit.beverages.Americano;
import sample.cafekiosk.spring.unit.beverages.Latte;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();

        cafeKiosk.add(new Americano());
        System.out.println("add Americano");


        cafeKiosk.add(new Latte());
        System.out.println("add Latte");

        final int totalPrice =
                cafeKiosk.calculateTotalPractice();

        System.out.println(totalPrice);



    }


}
