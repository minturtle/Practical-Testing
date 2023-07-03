package sample.cafekiosk.spring.unit;

import lombok.Getter;
import sample.cafekiosk.spring.unit.beverages.Beverage;
import sample.cafekiosk.spring.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage){
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count){
        if(count <= 0){
            throw new IllegalArgumentException("Count는 0이하일 수 없습니다.");
        }

        for(int i =0 ; i < count; i++){
            beverages.add(beverage);
        }
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
