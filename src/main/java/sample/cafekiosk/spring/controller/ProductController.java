package sample.cafekiosk.spring.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    public SellingProductListResponse getSellingProducts(){

        final List<ProductResponse> sellingProducts = productService.getSellingProduct();
        return new SellingProductListResponse(sellingProducts);
    }


    @PostMapping("/new")
    public ResponseEntity addNewProduct(@RequestBody  @Valid  NewProductRequest productRequest){

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
        @NotBlank(message = "상품의 이름은 빈 값일 수 없습니다.")
        private String productName;

        @NotNull(message = "상품 판매 타입은 빈 값일 수 없습니다.")
        private ProductSellingType sellingType;

        @NotNull(message = "상품 타입은 빈 값일 수 없습니다.")
        private ProductType productType;

        @Positive(message = "가격은 음수일 수 없습니다.")
        private Integer price;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SellingProductListResponse{
        List<ProductResponse> list;
    }
}
