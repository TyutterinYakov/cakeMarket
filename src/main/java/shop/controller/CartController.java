package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.global.GlobalData;
import shop.model.Product;
import shop.service.ProductService;

@Controller
public class CartController {
	@Autowired
	private ProductService productDao;
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable Long id) {
		GlobalData.cart.add(productDao.getProductById(id).get());
		return "redirect:/shop";
	}
	@GetMapping("/cart")
	public String cartGet(Model md) {
		md.addAttribute("cartCount", GlobalData.cart.size());
		md.addAttribute("cart", GlobalData.cart);
		//md.addAttribute("total", productDao.totalAmount(GlobalData.cart));
		md.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		
		return "cart";
	}
	@GetMapping("/cart/removeItem/{id}")
	public String cartDeleteItem(@PathVariable int id) {
		if(GlobalData.cart.size()>id) {
		GlobalData.cart.remove(id);
		}
		
		return "redirect:/cart";
	}
	@GetMapping("/checkout")
	public String getCheckOut(Model md) {
		md.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		
		
		return "checkout";
	}
	
}
