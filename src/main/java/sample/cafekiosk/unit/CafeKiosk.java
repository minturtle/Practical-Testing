package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {


    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 00);

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
        return beverages.stream()
                .mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder(LocalTime now){

        if(now.isBefore(SHOP_OPEN_TIME) || now.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 가능 시간이 아닙니다.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}
