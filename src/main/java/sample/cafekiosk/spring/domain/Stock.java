package sample.cafekiosk.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Builder
    private Stock(Integer stockQuantity, Product product) {

        if(product.getType() == ProductType.HANDMADE){
            throw new IllegalArgumentException("HANDMADE는 수량을 측정할 수 없습니다.");
        }

        this.stockQuantity = stockQuantity;
        this.product = product;
    }

    @Id @GeneratedValue
    private Long id;


    private Integer stockQuantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
