package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import models.Film;
import models.User;
import repositories.FilmRepository;
import repositories.UserRepository;

@Controller
@RequestMapping("/films")
public class FilmController {

	private final String SUBFOLDER 		= "films/";
	
	private final String PAGE_LIST 	= SUBFOLDER + "index";
	private final String PAGE_VIEW 	= SUBFOLDER + "view";
	private final String PAGE_EDIT 	= SUBFOLDER + "edit";
	private final String PAGE_ADD 	= SUBFOLDER + "add";
	private final String PAGE_DELETE = SUBFOLDER + "delete";
	
	@Autowired
	private FilmRepository filmRep; 
	
	@GetMapping({"", "/"})
	public String listFilms(Model model) {
		model.addAttribute("films", (Iterable<Film>) filmRep.findAll());
		return PAGE_LIST;
	}
	
	@GetMapping("/view/{id}")
	public String viewFilm(Model model, @RequestParam("id") Long id) {		
		return PAGE_VIEW;
	}
	
	@GetMapping("/edit/{id}")
	public String editFilmForm(Model model, @RequestParam("id") Long id) {
		return PAGE_EDIT;
	}
	
	@PostMapping("/edit/{id}")
	public String editFilm(Model model, @Valid Film film, BindingResult bindingResult, @RequestParam("id") Long id) {		
		return PAGE_EDIT;
	}
	
	@GetMapping("create")
	public String add(Model model) {
		
		return PAGE_ADD;
	}
	
	@PostMapping("/create")
	public String add(Model model, @Valid Film film, BindingResult bindingResult) {
		
		return PAGE_ADD;
	}
	
	@GetMapping("/delete/{id}")//on se base sur l'id du produit a supprimer, les chemins donc peuvent aussi etre dynamiques
	public String deleteProduct(@PathVariable("id") Long id){
		
		filmRep.delete(id);
		return "redirect:/";
	}
}
