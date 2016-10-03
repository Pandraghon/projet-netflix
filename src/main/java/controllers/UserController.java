package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import models.User;
import repositories.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private final String SUBFOLDER = "users/";

	@Autowired
	private UserRepository repository; 
	
	@GetMapping({"", "/"})
	public String index(Model model) {
		model.addAttribute("users", (List<User>) repository.findAll());
		return SUBFOLDER + "index";
	}
	
	@GetMapping("/view/{id}")
	public String view(Model model, @RequestParam("id") Long id) {
		
		return SUBFOLDER + "view";
	}
	
}
