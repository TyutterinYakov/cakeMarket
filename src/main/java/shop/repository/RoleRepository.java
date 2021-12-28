package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	
}
