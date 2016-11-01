package project.controllers;


import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.io.FilenameUtils;
import project.models.Category;

import project.models.Film;
import project.models.Media;
import project.repositories.CategoryRepository;
import project.repositories.FilmRepository;
import project.repositories.MediaRepository;

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
        private HttpServletRequest request;
	
	@Autowired
	private FilmRepository filmRep; 
	
	@Autowired
	private MediaRepository mediaRep;
	
	@Autowired
	private CategoryRepository categRep;

	@GetMapping({ "", "/" })
	public String listFilms(Model model, @RequestParam(value="category", required=false) String category) {
                if(category != null) {
                    Category cat = categRep.findByNameIgnoreCase(category);
                    if(cat == null) {
                        model.addAttribute("films", (Iterable<Film>) filmRep.findAll());
                    } else {
                        model.addAttribute("films", (Iterable<Film>) filmRep.findByMedia_Categories(cat));
                    }
                } else {
                    model.addAttribute("films", (Iterable<Film>) filmRep.findAll());
                }
                model.addAttribute("categories", (Iterable<Category>) categRep.findAll());
		return PAGE_LIST;
	}
	
	@GetMapping("/view/{id}")
	public String viewFilm(Model model, @PathVariable("id") Long id) {
		Film film = filmRep.findOne(id);
		model.addAttribute(film);
		return PAGE_VIEW;
	}
	
	@GetMapping("/edit/{id}")
	public String editFilmForm(Model model, @PathVariable("id") Long id) {
		Film film= filmRep.findOne(id);
		model.addAttribute("film", film);
		return PAGE_EDIT;
	}
	
	@PostMapping("/edit/{id}")
	public String editFilm(Model model, @Valid Film film, BindingResult bindingResult, @PathVariable("id") Long id) {		
		return "redirect:/" + SUBFOLDER;
	}
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("film",new Film());
		model.addAttribute("media",new Media());
		model.addAttribute("categories", (Iterable<Category>) categRep.findAll());
			
		return PAGE_ADD;
	}
	
	@PostMapping("/add")
	public String add( @Valid Media media, BindingResult bindingResult, @RequestParam("file") MultipartFile image, @RequestParam("categ") String[] categories,
            RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors())
		{
			System.out.println("erreeeeur");
			return PAGE_ADD;
		}
                
                for(String categ : categories) {
                    Category category = categRep.findByName(categ);
                    if(category == null) {
                        category = categRep.save(new Category(categ));
                    }
                    media.getCategories().add(category);
                }
		
		Media saveMedia = mediaRep.save(media);
                
                String filePath = "";
                if (!image.isEmpty()) {
                    try {
                        String uploadsDir = "/img/";
                        String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                        if(! new File(realPathtoUploads).exists())
                            new File(realPathtoUploads).mkdir();

                        filePath = Long.toString(saveMedia.getId()) + "." + FilenameUtils.getExtension(image.getOriginalFilename());
                        String path = realPathtoUploads + filePath;
                        File dest = new File(path);
                        image.transferTo(dest);
                    } catch(IOException | IllegalStateException e) {
                        
                    }
                }
		
		saveMedia.setImage(filePath);
                saveMedia = mediaRep.save(saveMedia);
		System.out.println("NEW SAVED MEDIA WITH ID : "+ saveMedia.getId() + " NAME = " + saveMedia.getImage() );
		
		Film film = new Film(media);
		film.setMedia(media);
		Film saveFilm = filmRep.save(film);
		
		System.out.println("NEW SAVED FILM WITH ID : "+ saveFilm.getId());
		return "redirect:/" + SUBFOLDER;
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id){
		
		filmRep.delete(id);
		return "redirect:/" + SUBFOLDER;
	}
	
    
}

