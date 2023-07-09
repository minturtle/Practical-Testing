package sample.cafekiosk.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.domain.OrderProduct;


@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
