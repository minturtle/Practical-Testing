package sample.cafekiosk.spring.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.domain.ProductType;
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
        //product Number 생성 전략 : DB에서 마지막에 저장된 productNumber의 상품 번호를 읽어와서 +1

        return new ResponseEntity(HttpStatus.OK);
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewProductRequest{
        private String productName;
        private ProductSellingType sellingType;
        private ProductType productType;
        private Integer price;
    }
}
