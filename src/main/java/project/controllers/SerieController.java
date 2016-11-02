
package project.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
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

import project.models.Category;
import project.models.Episode;
import project.models.Media;
import project.models.Serie;
import project.models.Video;
import project.repositories.CategoryRepository;
import project.repositories.EpisodeRepository;
import project.repositories.MediaRepository;
import project.repositories.SerieRepository;
import project.repositories.VideoRepository;
import project.storage.StorageService;

@Controller
@RequestMapping("/series")
public class SerieController {

	
	
	private final String SUBFOLDER 		= "series/";
	
	private final String PAGE_INDEX 	= SUBFOLDER + "index";
	private final String PAGE_VIEW 		= SUBFOLDER + "view";
	private final String PAGE_VIEW_EPISODE 		= SUBFOLDER + "viewEpisode";
	private final String PAGE_LIST 		= SUBFOLDER + "list";
	private final String PAGE_EDIT 		= SUBFOLDER + "edit";
	private final String PAGE_ADD 		= SUBFOLDER + "add";
	private final String PAGE_ADD_EPISODE 		= SUBFOLDER + "addEpisode";
	private final String PAGE_LIST_EPISODE 		= SUBFOLDER + "listEpisode";
	private final String PAGE_DELETE 	= SUBFOLDER + "delete";
	private final String PAGE_ADMIN	= SUBFOLDER + "admin";
	
	private final StorageService storageService;
	
	@Autowired
	private SerieRepository SerieRepository;

	@Autowired
	private MediaRepository MediaRepository;
	
	
	@Autowired
	private EpisodeRepository EpisodeRepository;
	
	
	@Autowired
	private CategoryRepository CategoryRepository;
	
	
	@Autowired
	private VideoRepository VideoRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	  @Autowired
	   public SerieController(StorageService storageService) {
	        this.storageService = storageService;
	  }
	  
	  
	@GetMapping({"","/"})
	public String listSeries(Model model, @RequestParam(value="category", required=false) String category)
	{
		 if(category != null) {
             Category cat = CategoryRepository.findByNameIgnoreCase(category);
             if(cat == null) {
                 model.addAttribute("series", (Iterable<Serie>) SerieRepository.findAll());
             } else {
                 model.addAttribute("series", (Iterable<Serie>) SerieRepository.findByMedia_Categories(cat));
             }
         }
		 else
		 {
			 Iterable<Serie> list = SerieRepository.findAll();

				model.addAttribute("series",list);
		 }
		
		  model.addAttribute("categories", (Iterable<Category>) CategoryRepository.findAll());
		

		return PAGE_LIST;
		
	}
	@GetMapping("/admin")
	public String listAdmin(Model model)
	{
		Iterable<Serie> list = SerieRepository.findAll();

		model.addAttribute("serie",list);

		return PAGE_ADMIN;
		
	}
	
	@GetMapping("/view/{id}")
	public String viewSerieInformations(@PathVariable("id") Long id,Model model) {
		
		Serie serie = SerieRepository.findOne(id);
		model.addAttribute("serie", serie);
		Media media = serie.getMedia();
		model.addAttribute("media", media);
		
		List<Long> saisons = serie.listeSaisons();
		
		model.addAttribute("saisons", saisons);
		
		return PAGE_VIEW;
	}
	
	
	@GetMapping("/viewEpisode/{id}")
	public String viewEpisodeInformations(@PathVariable("id") Long id,Model model) {
		
		Video video = VideoRepository.findOne(id);
		Episode episode = EpisodeRepository.findByVideo(video);
		
		model.addAttribute("video", video);
		model.addAttribute("episode", episode);
		
		return PAGE_VIEW_EPISODE;
	}
	
	@GetMapping("/view/{id:[1-9]+}/{saison_number:[1-9]+}/listEpisode")
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
	public String addEpisode(@Valid Episode episode, BindingResult bindingresult, @RequestParam("file") MultipartFile video,RedirectAttributes redirectAttributes) {
		
		System.out.println("DEBUT POST ADD EPISODE");

		if(bindingresult.hasErrors())
		{
			System.out.println("ADD ERRORS : "+ bindingresult.toString());
			return PAGE_ADD_EPISODE;
		}
		
		Episode saveEpisode = EpisodeRepository.save(episode);
		
        String filePath = "";
        if (!video.isEmpty()) {
            try {
                String uploadsDir = "/vid/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                filePath = "episode" + Long.toString(saveEpisode.getId()) + "." + FilenameUtils.getExtension(video.getOriginalFilename());
                String path = realPathtoUploads + filePath;
                File dest = new File(path);
                video.transferTo(dest);
                Video newvideo = VideoRepository.save(new Video(filePath));
                saveEpisode.setVideo(newvideo);
                System.out.println(filePath);
            } catch (IOException | IllegalStateException e) {

            }
        }
		
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
    public String addSerieForm(Model model) {
        model.addAttribute("serie", new Serie());
        model.addAttribute("media", new Media());
        model.addAttribute("categories", (Iterable<Category>) CategoryRepository.findAll());
        return PAGE_ADD;
    }

	
    @PostMapping("/add")
    public String addSerie(@Valid Media media, BindingResult bindingresult, @RequestParam("file") MultipartFile image,@RequestParam("categ") String[] categories,
            RedirectAttributes redirectAttributes) {

        if (bindingresult.hasErrors()) {
            System.out.println("ADD ERRORS : " + bindingresult.toString());
            return PAGE_ADD;
        }

        if(categories != null && categories.length != 0) {
            for(String categ : categories) {
                if(categ.trim().isEmpty()) continue;
                Category category = CategoryRepository.findByNameIgnoreCase(categ);
                if(category == null) {
                    category = CategoryRepository.save(new Category(categ));
                }
                if(!media.getCategories().contains(category))
                    media.getCategories().add(category);
            }
        }
        
        Media saveMedia = MediaRepository.save(media);
        String filePath = "";
        if (!image.isEmpty()) {
            try {
                String uploadsDir = "/img/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                filePath = Long.toString(saveMedia.getId()) + "." + FilenameUtils.getExtension(image.getOriginalFilename());
                String path = realPathtoUploads + filePath;
                File dest = new File(path);
                image.transferTo(dest);
            } catch (IOException | IllegalStateException e) {

            }
        }

        saveMedia.setImage(filePath);
        saveMedia = MediaRepository.save(saveMedia);

        Serie serie = new Serie(media);
        serie.setMedia(media);
        Serie saveSerie = SerieRepository.save(serie);


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
		
		
		
		Media saveMedia = MediaRepository.save(media);
		Serie serie = SerieRepository.findOne(media.getId());
		serie.setMedia(saveMedia);
		Serie saveSerie = SerieRepository.save(serie);
		
        System.out.println("NEW SAVED MEDIA WITH ID : " + saveMedia.getId() + " NAME = " + saveMedia.getName() + " IMAGE = " + saveMedia.getImage());
        System.out.println("NEW SAVED SERIE WITH ID : " + saveSerie.getMedia().getId() + " NAME = " + saveSerie.getMedia().getName() + " IMAGE = " + saveSerie.getMedia().getImage());

		
		return "redirect:/series/admin"; 
		
	}
	

	
	




}
