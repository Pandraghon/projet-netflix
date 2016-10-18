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

import models.Media;
import models.Serie;
import repositories.MediaRepository;
import repositories.SerieRepository;

@Controller
@RequestMapping("/series")
public class SerieController {

	
	
	private final String SUBFOLDER 		= "series/";
	
	private final String PAGE_INDEX 	= SUBFOLDER + "index";
	private final String PAGE_VIEW 		= SUBFOLDER + "view";
	private final String PAGE_LIST 		= SUBFOLDER + "list";
	private final String PAGE_EDIT 		= SUBFOLDER + "edit";
	private final String PAGE_ADD 		= SUBFOLDER + "add";
	private final String PAGE_DELETE 	= SUBFOLDER + "delete";
	
	
	@Autowired
	private SerieRepository SerieRepository;

	@Autowired
	private MediaRepository MediaRepository;
	
	@GetMapping({"","/"})
	public String listSeries(Model model)
	{
		
		/*Serie serie = new Serie("Blindspot",new Date(), "qlqch", "trailer",null);
		SerieRepository.save(serie);*/
		Iterable<Serie> list = SerieRepository.findAll();
		for(Serie serie : list)
		{
			System.out.println("LIST SERIE ID : "+ serie.getId() + " WITH NAME " + serie.getMedia().getName());
		}
		System.out.println("AFTER LIST SERIE  ");
		model.addAttribute("serie",list);
		return PAGE_LIST;
		
	}
	
	@GetMapping("/view/{id}")
	public String viewSerieInformations(Model model, @RequestParam("id") Long id) {
		
		return PAGE_VIEW;
	}
	
	@GetMapping("/add")
	public String addSerieForm(Model model)
	{
		model.addAttribute("serie",new Serie());
		model.addAttribute("media", new Media());
		return PAGE_ADD;
	}
	
	@PostMapping("/add")
	public String addSerie(@Valid Media media, BindingResult bindingresult){
		
		if(bindingresult.hasErrors())
		{
			System.out.println("ADD ERRORS : "+ bindingresult.toString());
			return PAGE_ADD;
		}
		
		Media savemedia = MediaRepository.save(media);
		Serie serie = new Serie(media);
		serie.setMedia(media);
		Serie saveSerie = SerieRepository.save(serie);
		System.out.println("NEW SAVED MEDIA WITH ID : "+ savemedia.getId() + " NAME = " + savemedia.getName());
		System.out.println("NEW SAVED SERIE WITH ID : "+ saveSerie.getMedia().getId()+ " NAME = " + saveSerie.getMedia().getName());
		Iterable<Serie> list = SerieRepository.findAll();
		for(Serie sserie : list)
		{
			System.out.println("LIST SERIE ID : "+ sserie.getId() + " WITH NAME " + sserie.getMedia().getName());
		}
		System.out.println("AFTER LIST SERIE  ");
		
		return "redirect:/series"; 
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSerie(@PathVariable("id") Long id){
		
		SerieRepository.delete(id);
		return PAGE_LIST;
	}
	
	
	@GetMapping("/edit/{id}")
	public String editSerieForm(@PathVariable("id") Long id,Model model){
		
		Serie serie= SerieRepository.findOne(id);
		model.addAttribute("serie", serie);
		
		return PAGE_EDIT;
	}
	

	@PostMapping("/edit")
	public String editSerie(@Valid Serie serie){
		
		System.out.println("SAVED EDIT SERIE WITH ID : "+ serie.getId());
		SerieRepository.save(serie);
		return PAGE_LIST;
		
	}
	
	
	
	
	
	
}
