package sample.cafekiosk.spring.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
