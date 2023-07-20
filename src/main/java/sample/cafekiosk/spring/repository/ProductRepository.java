package sample.cafekiosk.spring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sample.cafekiosk.spring.domain.Product;
import sample.cafekiosk.spring.domain.ProductSellingType;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingTypeIn(List<ProductSellingType> sellingTypes);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    List<Product> findLatestProducts(Pageable pageable);

}
