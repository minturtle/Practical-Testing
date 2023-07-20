package sample.cafekiosk.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 상품상태를 가진 상품들을 조회하여 받아볼 수 있다.")
    void t1() throws Exception {
        //given
        List<ProductSellingType> sellingStatuses = List.of(ProductSellingType.SELLING, ProductSellingType.HOLD);
        productRepository.saveAll(getDummyData());

        //when
        List<Product> products =
                productRepository.findAllBySellingTypeIn(sellingStatuses);

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingType")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingType.SELLING),
                        tuple("002", "크로와상", ProductSellingType.HOLD)
                );
    }

    @Test
    @DisplayName("Product Number 리스트로 여러개의 Product를 한번에 조회할 수 있다.")
    void t2() throws Exception {
        //given
        productRepository.saveAll(getDummyData());

        List<String> productNumbers = List.of("001", "002");

        //when
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        //then
        assertThat(products).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder("아메리카노", "크로와상");

    }

    @Test
    @DisplayName("가장 최근에 저장된 데이터를 가져올 수 있다.")
    void t3() throws Exception {
        //given
        List<Product> dummyData = getDummyData();
        productRepository.saveAll(dummyData);

        Product expected = dummyData.get(2);
        //when
        final Product actual = productRepository.findTopByOrderByIdDesc();
        //then
        assertThat(actual).isEqualTo(expected);
    }


    private List<Product> getDummyData(){
        Product product1 = Product.builder()
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .sellingType(ProductSellingType.SELLING)
                .name("아메리카노")
                .price(3500)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .type(ProductType.BAKERY)
                .sellingType(ProductSellingType.HOLD)
                .name("크로와상")
                .price(3000)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(ProductType.BOTTLE)
                .sellingType(ProductSellingType.STOP_SELLING)
                .name("카페 라떼")
                .price(4500)
                .build();
        return List.of(product1, product2, product3);
    }

}