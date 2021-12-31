package shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.model.Role;
import shop.model.User;
import shop.repository.RoleRepository;
import shop.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	private BCryptPasswordEncoder cryptPass;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	
	
	@Transactional
	public boolean register(User user, HttpServletRequest request) throws ServletException {
		if(userRepo.findUserByEmail(user.getEmail()).isPresent()) {
			return false;
		}
		String password = user.getPassword();
		user.setPassword(cryptPass.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findById(2).get());
		user.setRoles(roles);
		userRepo.save(user);
		request.login(user.getEmail(), password);
		return true;
	}
}
