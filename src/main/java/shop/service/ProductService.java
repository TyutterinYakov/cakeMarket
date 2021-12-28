package shop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import shop.dto.ProductDTO;
import shop.model.Product;
import shop.repository.ProductRepository;

@Service
public class ProductService {
	private static final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	@Autowired
	private ProductRepository productDao;
	
	@Transactional
	public List<Product> findAllProducts() {
		return productDao.findAll();
	}
	
	@Transactional
	public void addProduct(Product pr) {
		productDao.save(pr);
	}
	
	@Transactional
	public void removeProduct(Long id) {
		productDao.deleteById(id);
	}
	@Transactional
	public Optional<Product> getProductById(Long id){
		return productDao.findById(id);
	}
	
	@Transactional
	public List<Product> getProductByCategoryId(int id){
		return productDao.findAllByCategory_Id(id);
	}

	public String buildImage(String imgName, MultipartFile file) throws IOException {
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename(); 
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = "noimage.png";
		}
		return imageUUID;
		
	}

	public ProductDTO buildUpdateProduct(Long id) {
		Product product = getProductById(id).get();
		ProductDTO prd = new ProductDTO();
		prd.setId(id);
		prd.setName(product.getName());
		prd.setCategoryId(product.getCategory().getId());
		prd.setImageName(product.getImageName());
		prd.setPrice(product.getPrice());
		prd.setWeight(product.getWeight());
		prd.setDescription(product.getDescription());
		return prd;
	}


	
}
