package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import shop.model.Role;
import shop.model.User;
import shop.repository.RoleRepository;
import shop.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	private BCryptPasswordEncoder cryptPass;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/register")
	public String registerGet() {
		
		return "register";
	}
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		String password = user.getPassword();
		user.setPassword(cryptPass.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findById(2).get());
		user.setRoles(roles);
		userRepo.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
}
