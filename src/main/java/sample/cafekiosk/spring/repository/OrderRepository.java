package sample.cafekiosk.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
}
