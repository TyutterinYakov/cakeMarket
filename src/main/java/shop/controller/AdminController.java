package shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import shop.dto.ProductDTO;
import shop.model.Category;
import shop.model.Product;
import shop.service.CategoryService;
import shop.service.ProductService;

@Controller
public class AdminController {
	
	@Autowired
	private CategoryService categoryService; 
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome() {
		
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCategories(Model md) {
		md.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	@GetMapping("admin/categories/add")
	public String getAddCategories(Model mod) {
		mod.addAttribute("category", new Category());
		
		return "categoriesAdd";
	}
	@PostMapping("admin/categories/add")
	public String postAddCategories(@ModelAttribute("category") Category ct) {
		categoryService.addCategory(ct);
		return "redirect:/admin/categories";
	}
	@GetMapping("admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		categoryService.deleteCategory(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model md) {
		Optional<Category> ct = categoryService.getByIdCategory(id);
		if(ct.isPresent()) {
			md.addAttribute("category", ct.get());
			return "categoriesAdd";
		} else {
			return "404";
		}
	}
	
	//PRODUCT
	
	@GetMapping("/admin/products")
	public String getProducts(Model md) {
		md.addAttribute("products", productService.findAllProducts());
		return "products";
	}
	@GetMapping("/admin/products/add")
	public String getAddProducts(Model md) {
		md.addAttribute("productDTO", new ProductDTO());
		md.addAttribute("categories", categoryService.getAllCategory());
		
		return "productsAdd";
	}
	@PostMapping("/admin/products/add")
	public String postAddProduct(@ModelAttribute("productDTO") ProductDTO prd,
			@RequestParam("productImage")MultipartFile file,
			@RequestParam("imgName")String imgName) throws IOException {
		Product pr = new Product();
		pr.setName(prd.getName());
		pr.setId(prd.getId());
		pr.setCategory((categoryService.getByIdCategory(prd.getCategoryId()).orElseGet(null)));
		pr.setPrice(prd.getPrice());
		pr.setWeight(prd.getWeight());
		pr.setDescription(prd.getDescription());
		
		String imageUUID = productService.buildImage(imgName, file);
		pr.setImageName(imageUUID);
		productService.addProduct(pr);
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/delete/{id}")
	public String removeProduct(@PathVariable Long id) {
		productService.removeProduct(id);
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable Long id, Model md) {
			
			md.addAttribute("categories", categoryService.getAllCategory());
			md.addAttribute("productDTO",  productService.buildUpdateProduct(id));
			return "productsAdd";

	}
}
