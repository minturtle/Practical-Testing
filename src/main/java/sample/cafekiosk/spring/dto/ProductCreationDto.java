package sample.cafekiosk.spring.dto;

import lombok.Builder;
import lombok.Data;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;


@Builder
@Data
public class ProductCreationDto {
    private String productName;
    private ProductSellingType sellingType;
    private ProductType productType;
    private Integer price;
}
