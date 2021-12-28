package shop.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.model.Category;
import shop.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryDao;
	
	
	@Transactional
	public List<Category> getAllCategory(){
		return categoryDao.findAll();
	}
	
	@Transactional
	public void addCategory(Category ct) {
		categoryDao.save(ct);
	}
	
	@Transactional
	public void deleteCategory(int id) {
		categoryDao.deleteById(id);
		
	}
	
	@Transactional
	public Optional<Category> getByIdCategory(int id) {
		return categoryDao.findById(id);
		
	}
}
