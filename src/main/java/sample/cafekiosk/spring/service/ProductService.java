package sample.cafekiosk.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.dto.ProductCreationDto;
import sample.cafekiosk.spring.dto.response.ProductResponse;
import sample.cafekiosk.spring.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProduct(){
        List<Product> products = productRepository.findAllBySellingTypeIn(ProductSellingType.forDisplayProducts());

        return products.stream().map(ProductResponse::of).collect(Collectors.toList());
    }


    public Product findLatestProduct() {
        return productRepository.findTopByOrderByIdDesc().orElse(null);
    }


    public void createProduct(ProductCreationDto request){
        // 먼저 상품 번호부터 지정해 준다. latestProduct가 있다면 그 상품의 상품 번호 +1, 없다면 001로 지정한다.
        String newNumber = createProductNumber();

        Product newProduct = Product.builder()
                .productNumber(newNumber)
                .type(request.getProductType())
                .sellingType(request.getSellingType())
                .name(request.getProductName())
                .price(request.getPrice())
                .build();

        productRepository.save(newProduct);

    }


    /**
    * methodName : createProductNumber
    * Author : Minseok Kim
    * description : 새로운 Product가 갖게될 ProductNumber을 생성한다.
    *
    * @return 새로운 Product가 갖게될 ProductNumber
     */
    private String createProductNumber() {
        Product latestProduct = findLatestProduct();
        String creationProductNumber = null;
        if(latestProduct == null){
            return creationProductNumber = "001";
        }

        final int latestProductNumber = Integer.parseInt(latestProduct.getProductNumber());
        return String.format("%03d", latestProductNumber + 1);

    }


}
