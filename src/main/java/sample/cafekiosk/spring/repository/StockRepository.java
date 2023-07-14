package sample.cafekiosk.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.domain.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
