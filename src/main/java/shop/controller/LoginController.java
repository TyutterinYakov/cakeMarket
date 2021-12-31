package shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import shop.global.GlobalData;
import shop.model.Role;
import shop.model.User;
import shop.repository.RoleRepository;
import shop.repository.UserRepository;
import shop.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginServ;

	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	@GetMapping("/register")
	public String registerGet() {
		
		return "register";
	}
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request, Model md) throws ServletException {
		boolean check = loginServ.register(user, request);
		if(!check) {
			md.addAttribute("error_register", check);
			return "/register";
		}

		return "redirect:/";
	}
}
