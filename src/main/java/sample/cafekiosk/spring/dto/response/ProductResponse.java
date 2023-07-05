package sample.cafekiosk.spring.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;



@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {

    public static ProductResponse of(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.id = product.getId();
        productResponse.productNumber = product.getProductNumber();
        productResponse.type = product.getType();
        productResponse.sellingType = product.getSellingType();
        productResponse.name = product.getName();
        productResponse.price = product.getPrice();

        return productResponse;
    }

    private Long id;

    private String productNumber;

    private ProductType type;

    private ProductSellingType sellingType;

    private String name;

    private int price;


}
