package sample.cafekiosk.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    INIT("주문 생성"),
    CANCELED("주문 취소"),
    PAYMENT_COMPLETE("결제 완료"),
    PAYMENT_FAILED("결제 실패"),
    RECIEVED("주문접수"),
    COMPLETLE("처리완료");

    private final String text;
}
