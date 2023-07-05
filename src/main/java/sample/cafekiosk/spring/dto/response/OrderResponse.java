package sample.cafekiosk.spring.dto.response;

import lombok.Builder;
import lombok.Data;
import sample.cafekiosk.spring.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime localDateTime;
    private List<ProductResponse> products;

}
