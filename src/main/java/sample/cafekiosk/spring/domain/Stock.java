package sample.cafekiosk.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Builder
    private Stock(Integer stockQuantity, Product product) {
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
