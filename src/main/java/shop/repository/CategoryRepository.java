package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
