package sample.cafekiosk.spring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;
import sample.cafekiosk.spring.dto.ProductResponse;
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

}
