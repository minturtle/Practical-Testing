package sample.cafekiosk.spring.unit;

import sample.cafekiosk.spring.unit.beverages.Beverage;
import sample.cafekiosk.spring.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage){
        beverages.add(beverage);
    }

    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }

    public void clear(){
        beverages.clear();
    }

    public int calculateTotalPractice() {
        int totalPrice =
                beverages.stream().mapToInt(Beverage::getPrice).sum();
        return totalPrice;
    }

    public Order createOrder(){
        return new Order(LocalDateTime.now(), beverages);
    }
}
