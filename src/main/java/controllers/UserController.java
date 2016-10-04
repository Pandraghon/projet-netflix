package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import models.User;
import repositories.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	private final String SUBFOLDER 		= "users/";
	
	private final String PAGE_INDEX 	= SUBFOLDER + "index";
	private final String PAGE_VIEW 		= SUBFOLDER + "view";
	private final String PAGE_EDIT 		= SUBFOLDER + "edit";
	private final String PAGE_ADD 		= SUBFOLDER + "add";
	private final String PAGE_DELETE 	= SUBFOLDER + "delete";
	private final String PAGE_LOGIN 	= SUBFOLDER + "login";

	@Autowired
	private UserRepository repository; 
	
	@GetMapping({"", "/"})
	public String index(Model model) {
		model.addAttribute("users", (List<User>) repository.findAll());
		return PAGE_INDEX;
	}
	
	@GetMapping("/view/{id}")
	public String view(Model model, @RequestParam("id") Long id) {
		
		return PAGE_VIEW;
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @RequestParam("id") Long id) {
		
		return PAGE_EDIT;
	}
	
	@PostMapping("/edit/{id}")
	public String edit(Model model, @Valid User user, BindingResult bindingResult, @RequestParam("id") Long id) {
		
		return PAGE_EDIT;
	}
	
	@GetMapping("/signup")
	public String add(Model model) {
		
		return PAGE_ADD;
	}
	
	@PostMapping("/signup")
	public String add(Model model, @Valid User user, BindingResult bindingResult) {
		
		return PAGE_ADD;
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return PAGE_LOGIN;
	}
	
	@PostMapping("/login")
	public String loginPost(Model model) {
		
		return PAGE_LOGIN;
	}
	
}
