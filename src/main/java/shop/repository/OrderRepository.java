package shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.Order;
import shop.model.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Product> findProductByOrderId(Long id); 
}
