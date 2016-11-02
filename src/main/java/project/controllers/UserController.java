package project.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.models.Film;
import project.models.User;
import project.repositories.UserRepository;

@Controller
@RequestMapping("/users")
// @SessionAttributes({"idUser", "userName", "UserRole"})
public class UserController {

	private final String SUBFOLDER = "users/";

	private final String PAGE_INDEX = SUBFOLDER + "index";
	private final String PAGE_VIEW = SUBFOLDER + "view";
	private final String PAGE_EDIT = SUBFOLDER + "edit";
	private final String PAGE_ADD = SUBFOLDER + "signup";
	private final String PAGE_DELETE = SUBFOLDER + "delete";
	private final String PAGE_LOGIN = SUBFOLDER + "login";

	@Autowired
	private UserRepository userRep;

	@GetMapping({ "", "/" })
	public String index(Model model) {
		model.addAttribute("users", (List<User>) userRep.findAll());
		return PAGE_INDEX;
	}

	@GetMapping("/view/{id}")
	public String view(Model model, @RequestParam("id") Long id) {
		User user = userRep.findOne(id);
		model.addAttribute(user);
		return PAGE_VIEW;
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @RequestParam("id") Long id) {
		User user = userRep.findOne(id);
		model.addAttribute(user);
		return PAGE_EDIT;
	}

	@PostMapping("/edit/{id}")
	public String edit(Model model, @Valid User user, BindingResult bindingResult, @RequestParam("id") Long id) {
		if (bindingResult.hasErrors())
			return PAGE_EDIT;
		user.setId(id);
		userRep.save(user);
		return String.format("redirect:/users/view/%1", id);
	}

	@GetMapping("/signup")
	public String add(Model model) {
		model.addAttribute("user", new User());
		return PAGE_ADD;
	}

	@PostMapping("/signup")
	public String add(HttpSession session, @Valid User user, BindingResult bindingResult) {

		/*
		 * if(bindingResult.hasErrors()) { System.out.println("erreeeeur");
		 * return "PAGE_ADD"; }
		 */
		
		session.removeAttribute("msgerreuremail");
		session.removeAttribute("msgerreurusername");
		if (userRep.findByUsername(user.getUsername()) == null) {
			if (userRep.findByEmail(user.getEmail()) == null) {
	
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));

				User saveUser = userRep.save(user);
				System.out.println("NEW SAVED USER WITH ID : " + saveUser.getId());

				session.setAttribute("id", saveUser.getId());
				session.setAttribute("role", saveUser.getRole());
				session.setAttribute("username", saveUser.getUsername());

				session.removeAttribute("msgerreuremail");
				session.removeAttribute("msgerreurusername");
				System.out.println("ID de session : " + session.getAttribute("id") +
						" nom : " + session.getAttribute("username") +
						" role : " + session.getAttribute("role"));
				return "redirect:/";
			} else {
				System.out.println("Email déjà utilisé par un autre utilisateur !");
				session.setAttribute("msgerreuremail", "Email déjà utilisé par un autre utilisateur !");
				return PAGE_ADD;
			}
		} else {
			System.out.println("Le nom d'utilisateur choisi est déjà utilisé ! ");
			session.setAttribute("msgerreurusername", "Le nom d'utilisateur choisit est déjà utilisé !");

			return PAGE_ADD;
		}

	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return PAGE_LOGIN;
	}

	@PostMapping("/login")
	public String loginPost(HttpSession session, Model model, @Valid User user) {

		session.removeAttribute("msgerreurmdp");
		session.removeAttribute("msgerreurlogin");
		session.removeAttribute("login");
		
		if (userRep.findByUsername(user.getUsername()) != null) {

			if (BCrypt.checkpw(user.getPassword(), userRep.findByUsername(user.getUsername()).getPassword())) {

				long id = userRep.findByUsername(user.getUsername()).getId();

				boolean role = userRep.findByUsername(user.getUsername()).getRole();

				session.setAttribute("id", id);
				session.setAttribute("role", role);
				session.setAttribute("username", user.getUsername());

				session.removeAttribute("msgerreurmdp");
				session.removeAttribute("msgerreurlogin");
				System.out.println("ID de session : " + session.getAttribute("id") +
						" nom : " + session.getAttribute("username") +
						" role : " + session.getAttribute("role"));
				return "redirect:/";
			} else {

				System.out.println("Mot de passe érroné ");
				session.setAttribute("msgerreurmdp", "Le mot de passe saisi est incorrect !");
				return PAGE_LOGIN;
			}

		} else {
			System.out.println("login érroné ");
			session.setAttribute("msgerreurlogin", "Le login saisi est incorrect !");
			return PAGE_LOGIN;
		}

	}

	@GetMapping("/logout")
	public String utilisateurdeconneter(HttpSession session) {
		session.removeAttribute("id");
		session.removeAttribute("role");
		session.removeAttribute("username");
		System.out.println(session.getAttribute("id"));
		return "redirect:/";
	}
	
	
}
