package shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.global.GlobalData;
import shop.model.Order;
import shop.model.Product;
import shop.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Transactional
	public Long saveOrder(Order or) {
		or.setProducts(GlobalData.cart);
		or = orderRepo.saveAndFlush(or);
		return or.getOrderId();
	}
	
	@Transactional
	public List<Product> getProductsOrder(Long id){
		List<Product> products = new ArrayList<>();
		products = orderRepo.findProductByOrderId(id);
		
		return products;
	}
	@Transactional
	public List<Order> findAllOrders(){
		
		return orderRepo.findAll();
	}

	public void removeOrder(Long id) {
		orderRepo.deleteById(id);
		
	}
	

}
