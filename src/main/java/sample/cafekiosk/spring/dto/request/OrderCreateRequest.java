package sample.cafekiosk.spring.dto.request;

import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
@Builder
public class OrderCreateRequest {

    private List<String> productNumberList;

}
