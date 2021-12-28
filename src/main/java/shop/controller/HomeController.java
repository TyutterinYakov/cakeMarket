package shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.service.CategoryService;
import shop.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryDao;
	@Autowired
	private ProductService productDao;
	
	
	
	@GetMapping({"/home", "/"})
	public String home(Model md) {
		
		return "index";
	}
	@GetMapping({"/shop"})
	public String shop(Model md) {
		md.addAttribute("categories", categoryDao.getAllCategory());
		md.addAttribute("products", productDao.findAllProducts());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(@PathVariable int id,Model md) {
		md.addAttribute("categories", categoryDao.getAllCategory());
		md.addAttribute("products", productDao.getProductByCategoryId(id));
		return "shop";
	}
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(@PathVariable Long id, Model md) {
		md.addAttribute("product", productDao.getProductById(id).get());
		return "viewProduct";
	}
}
