package sample.cafekiosk.spring.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ProductSellingType {

    SELLING("판매중"),
    HOLD("판매 보류"),
    STOP_SELLING("판매 중지");

    private final String text;


    public static List<ProductSellingType> forDisplayProducts(){
        return List.of(SELLING, HOLD);
    }
}
