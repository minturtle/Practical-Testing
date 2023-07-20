package sample.cafekiosk.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
import sample.cafekiosk.spring.dto.ProductCreationDto;
import sample.cafekiosk.spring.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("가장 최근에 저장된 Product를 조회할 수 있다.")
    void t1() throws Exception {
        //given
        List<Product> dummyData = getDummyData();
        Product expected = dummyData.get(2);
        productRepository.saveAll(dummyData);
        //when
        Product actual = productService.findLatestProduct();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이미 등록된 상품이 있을때, 등록된 가장 최근의 상품의 상품번호 +1된 신규 상품을 등록할 수 있다.")
    void t2() throws Exception {
        //given
        final List<Product> dummyData = getDummyData();
        productRepository.saveAll(dummyData);


        final ProductCreationDto productCreationDto = ProductCreationDto.builder()
                .productName("테스트 상품")
                .productType(ProductType.BAKERY)
                .sellingType(ProductSellingType.SELLING)
                .price(10000)
                .build();
        //when
        productService.createProduct(productCreationDto);
        //then
        Product latestSavedProduct = productRepository.findTopByOrderByIdDesc()
                .orElseThrow(()->new RuntimeException("Product 저장에 실패했습니다."));

        assertThat(latestSavedProduct).extracting("productNumber", "name" , "type", "sellingType", "price")
                .containsExactly("004", "테스트 상품", ProductType.BAKERY, ProductSellingType.SELLING, 10000);
    }

    @Test
    @DisplayName("이미 등록된 상품이 없을때, 상품번호가 001인 신규 상품을 등록할 수 있다.")
    void t3() throws Exception {
        //given

        final ProductCreationDto productCreationDto = ProductCreationDto.builder()
                .productName("테스트 상품")
                .productType(ProductType.BAKERY)
                .sellingType(ProductSellingType.SELLING)
                .price(10000)
                .build();
        //when
        productService.createProduct(productCreationDto);
        //then
        Product latestSavedProduct = productRepository.findTopByOrderByIdDesc()
                .orElseThrow(()->new RuntimeException("Product 저장에 실패했습니다."));

        assertThat(latestSavedProduct).extracting("productNumber", "name" , "type", "sellingType", "price")
                .containsExactly("001", "테스트 상품", ProductType.BAKERY, ProductSellingType.SELLING, 10000);
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