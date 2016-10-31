package project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.models.Episode;
import project.models.Media;
import project.models.Serie;
import project.repositories.EpisodeRepository;
import project.repositories.MediaRepository;
import project.repositories.SerieRepository;
import project.storage.StorageFileNotFoundException;
import project.storage.StorageService;

@Controller
@RequestMapping("/series")
public class SerieController {

	
	
	private final String SUBFOLDER 		= "series/";
	
	private final String PAGE_INDEX 	= SUBFOLDER + "index";
	private final String PAGE_VIEW 		= SUBFOLDER + "view";
	private final String PAGE_LIST 		= SUBFOLDER + "list";
	private final String PAGE_EDIT 		= SUBFOLDER + "edit";
	private final String PAGE_ADD 		= SUBFOLDER + "add";
	private final String PAGE_ADD_EPISODE 		= SUBFOLDER + "addEpisode";
	private final String PAGE_LIST_EPISODE 		= SUBFOLDER + "listEpisode";
	private final String PAGE_DELETE 	= SUBFOLDER + "delete";
	
	private final StorageService storageService;
	
	@Autowired
	private SerieRepository SerieRepository;

	@Autowired
	private MediaRepository MediaRepository;
	
	
	@Autowired
	private EpisodeRepository EpisodeRepository;
	
	
	  @Autowired
	   public SerieController(StorageService storageService) {
	        this.storageService = storageService;
	  }
	  
	  
	@GetMapping({"","/"})
	public String listSeries(Model model)
	{
		Iterable<Serie> list = SerieRepository.findAll();

		model.addAttribute("serie",list);

		return PAGE_LIST;
		
	}
	
	@GetMapping("/view/{id}")
	public String viewSerieInformations(@PathVariable("id") Long id,Model model) {
		
		Serie serie = SerieRepository.findOne(id);
		model.addAttribute("serie", serie);
		Media media = serie.getMedia();
		model.addAttribute("media", media);
		
		List<Long> saisons = serie.listeSaisons();
		/*List<Episode> episodes = serie.getEpisodes();
		model.addAttribute("episode", episodes);*/
		
		model.addAttribute("saisons", saisons);
		
		return PAGE_VIEW;
	}
	
	@GetMapping("/view/{id}/{saison_number}/listEpisode")
	public String listEpisodes(@PathVariable("id") Long id,@PathVariable("saison_number") Long saison_number, Model model) {
		
		Serie serie = SerieRepository.findOne(id);
		model.addAttribute("serie", serie);
		Media media = serie.getMedia();
		model.addAttribute("media", media);
		
		List<Episode> all = serie.getEpisodes();
		List<Episode> episodes = new ArrayList<>();
		for(Episode episode : all)
		{
			if(episode.getSaison_number()==saison_number)
			{
				episodes.add(episode);
			}
		}

		model.addAttribute("episodes", episodes);
		
		return PAGE_LIST_EPISODE;
	}
	
	@GetMapping("/addEpisode")
	public String addEpisodeGet(Model model) {
		
		
		model.addAttribute("episode", new Episode());
		List<Serie> listserie = (List<Serie>) SerieRepository.findAll();
		model.addAttribute("serie",listserie);
		
		return PAGE_ADD_EPISODE;
	}
	
	@PostMapping("/addEpisode")
	public String addEpisode(@Valid Episode episode, BindingResult bindingresult) {
		
		

		if(bindingresult.hasErrors())
		{
			System.out.println("ADD ERRORS : "+ bindingresult.toString());
			return PAGE_ADD_EPISODE;
		}
		
		EpisodeRepository.save(episode);
		
		
		Serie serie = episode.getSerie();
		List<Episode> listEpisodes = serie.getEpisodes();
		listEpisodes.add(episode);
		//Serie serie = episode.getSerie();
		//serie.ajouterEpisode(episode);
		 
		SerieRepository.save(serie);
		System.out.println("NEW SAVED EPISODE WITH ID : "+ episode.getId() + " NAME = " + episode.getTitre() + " SERIE = " + episode.getSerie().getMedia().getName()) ;
		System.out.println("NEW SAVED SERIE WITH ID : "+ serie.getId() + " NAME = " + serie.getMedia().getName() + " SAISON = " + listEpisodes.size());
		return "redirect:/series/view/"+serie.getId(); 
	}
	
	@GetMapping("/add")
	public String addSerieForm(Model model)
	{
		model.addAttribute("serie",new Serie());
		model.addAttribute("media", new Media());
		
		model.addAttribute("images", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(SerieController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

		return PAGE_ADD;
	}
	
	@PostMapping("/add")
	public String addSerie(@Valid Media media, BindingResult bindingresult,@RequestParam("file") MultipartFile image,
            RedirectAttributes redirectAttributes){

		if(bindingresult.hasErrors())
		{
			System.out.println("ADD ERRORS : "+ bindingresult.toString());
			return PAGE_ADD;
		}
		
		
		
		storageService.store(image);
		
		media.setImage(image.getOriginalFilename());
		Media savemedia = MediaRepository.save(media);
		
		Serie serie = new Serie(media);
		serie.setMedia(media);
		Serie saveSerie = SerieRepository.save(serie);
		
		
		System.out.println("NEW SAVED MEDIA WITH ID : "+ savemedia.getId() + " NAME = " + savemedia.getName() + " IMAGE = " + savemedia.getImage()) ;
		System.out.println("NEW SAVED SERIE WITH ID : "+ saveSerie.getMedia().getId()+ " NAME = " + saveSerie.getMedia().getName() + " IMAGE = " + saveSerie.getMedia().getImage());

		
		return "redirect:/series"; 
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSerie(@PathVariable("id") Long id){
		
		Serie serie = SerieRepository.findOne(id);
		//serie.getMedia()
		SerieRepository.delete(id);

		return "redirect:/series";
	}
	
	
	@GetMapping("/edit/{id}")
	public String editSerieForm(@PathVariable("id") Long id,Model model){
		
		Serie serie= SerieRepository.findOne(id);
		model.addAttribute("serie", serie);
		Media media = serie.getMedia();
		model.addAttribute("media", media);
		return PAGE_EDIT;
	}
	

	@PostMapping("/edit")
	public String editSerie(@Valid Media media,BindingResult bindingresult){
		
		if(bindingresult.hasErrors())
		{
			System.out.println("ADD ERRORS : "+ bindingresult.toString());
			return PAGE_EDIT;
		}
		
		
		
		Media savemedia = MediaRepository.save(media);
		Serie serie = SerieRepository.findOne(media.getId());
		serie.setMedia(savemedia);
		SerieRepository.save(serie);
		
		return "redirect:/series"; 
		
	}
	
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
	
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
	
	
}
