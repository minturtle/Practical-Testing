package sample.cafekiosk.spring.controller;


import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
import sample.cafekiosk.spring.dto.ProductCreationDto;
import sample.cafekiosk.spring.dto.response.ProductResponse;
import sample.cafekiosk.spring.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/selling")
    public List<ProductResponse> getSellingProducts(){
        return productService.getSellingProduct();
    }


    @PostMapping("/new")
    public ResponseEntity addNewProduct(@RequestBody  NewProductRequest productRequest){

        final ProductCreationDto creationDto = ProductCreationDto.builder()
                .productName(productRequest.getProductName())
                .sellingType(productRequest.getSellingType())
                .productType(productRequest.getProductType())
                .price(productRequest.getPrice())
                .build();

        productService.createProduct(creationDto);

        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NewProductRequest{
        private String productName;
        private ProductSellingType sellingType;
        private ProductType productType;
        private Integer price;
    }
}
