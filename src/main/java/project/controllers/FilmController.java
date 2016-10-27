package project.controllers;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.models.Film;
import project.models.Media;
import project.repositories.FilmRepository;
import project.repositories.MediaRepository;

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
	
	@Autowired
	private MediaRepository mediaRep;
	
	@GetMapping({"", "/"})
	public String listFilms(Model model) {
		model.addAttribute("film", (Iterable<Film>) filmRep.findAll());
		return PAGE_LIST;
	}
	
	@GetMapping("/view/{id}")
	public String viewFilm(Model model, @PathParam("id") Long id) {
		Film film = filmRep.findOne(id);
		model.addAttribute(film);
		return PAGE_VIEW;
	}
	
	@GetMapping("/edit/{id}")
	public String editFilmForm(Model model, @PathParam("id") Long id) {
		Film film= filmRep.findOne(id);
		model.addAttribute("film", film);
		return PAGE_EDIT;
	}
	
	@PostMapping("/edit/{id}")
	public String editFilm(Model model, @Valid Film film, BindingResult bindingResult, @PathParam("id") Long id) {		
		return "redirect:/" + SUBFOLDER;
	}
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("film",new Film());
		model.addAttribute("media",new Media());
		return PAGE_ADD;
	}
	
	@PostMapping("/add")
	public String add(Model model, @Valid Film film, @Valid Media media, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
		{
			System.out.println("erreeeeur");
			return "PAGE_ADD";
		}
		Media saveMedia = mediaRep.save(media);
		System.out.println("NEW SAVED MEDIA WITH ID : "+ saveMedia.getId());
		
		film.setMedia(saveMedia);
		Film saveFilm = filmRep.save(film);
		
		System.out.println("NEW SAVED FILM WITH ID : "+ saveFilm.getId());
		return "redirect:/" + SUBFOLDER;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id){
		
		filmRep.delete(id);
		return "redirect:/" + SUBFOLDER;
	}
}

