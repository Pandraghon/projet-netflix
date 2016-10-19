package controllers;


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
import repositories.FilmRepository;

@Controller
@RequestMapping("/films")
public class FilmController {

	private final String SUBFOLDER 		= "films/";
	
	private final String PAGE_LIST 	= SUBFOLDER + "list";
	private final String PAGE_VIEW 	= SUBFOLDER + "view";
	private final String PAGE_EDIT 	= SUBFOLDER + "edit";
	private final String PAGE_ADD 	= SUBFOLDER + "add";
	private final String PAGE_DELETE = SUBFOLDER + "delete";
	
	@Autowired
	private FilmRepository filmRep; 
	
	@GetMapping({"", "/"})
	public String listFilms(Model model) {
		model.addAttribute("film", (Iterable<Film>) filmRep.findAll());
		return PAGE_LIST;
	}
	
	@GetMapping("/view/{id}")
	public String viewFilm(Model model, @RequestParam("id") Long id) {
		Film film = filmRep.findOne(id);
		model.addAttribute(film);
		return PAGE_VIEW;
	}
	
	@GetMapping("/edit/{id}")
	public String editFilmForm(Model model, @RequestParam("id") Long id) {
		Film film= filmRep.findOne(id);
		model.addAttribute("film", film);
		return PAGE_EDIT;
	}
	
	@PostMapping("/edit/{id}")
	public String editFilm(Model model, @Valid Film film, BindingResult bindingResult, @RequestParam("id") Long id) {		
		return PAGE_EDIT;
	}
	
	@GetMapping("create")
	public String add(Model model) {
		model.addAttribute("film",new Film());
		return PAGE_ADD;
	}
	
	@PostMapping("/create")
	public String add(Model model, @Valid Film film, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
		{
			return "PAGE_ADD";
		}
		Film saveFilm = filmRep.save(film);
		System.out.println("NEW SAVED FILM WITH ID : "+ saveFilm.getId());
		return PAGE_ADD;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id){
		
		filmRep.delete(id);
		return PAGE_DELETE;
	}
}
