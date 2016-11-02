package project.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.apache.commons.io.FileUtils;
import project.models.Category;

import project.models.Film;
import project.models.Media;
import project.models.Video;
import project.repositories.CategoryRepository;
import project.repositories.FilmRepository;
import project.repositories.MediaRepository;
import project.repositories.VideoRepository;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final String SUBFOLDER = "films/";

    private final String PAGE_LIST = SUBFOLDER + "index";
    private final String PAGE_VIEW = SUBFOLDER + "view";
    private final String PAGE_EDIT = SUBFOLDER + "edit";
    private final String PAGE_ADD = SUBFOLDER + "add";
    private final String PAGE_DELETE = SUBFOLDER + "delete";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FilmRepository filmRep;
    
    @Autowired
    private VideoRepository videoRep;

    @Autowired
    private MediaRepository mediaRep;

    @Autowired
    private CategoryRepository categRep;

    @GetMapping({"", "/"})
    public String listFilms(Model model, @RequestParam(value = "category", required = false) String category) {
        if (category != null) {
            Category cat = categRep.findByNameIgnoreCase(category);
            if (cat == null) {
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
    public String viewFilm(HttpSession session, Model model, @PathVariable("id") Long id) {
        if ((session.getAttribute("id") == null) || (!(boolean) session.getAttribute("role"))) {
            session.setAttribute("login", "Vous devez d'abord vous connecter");
            return "redirect:/users/login";
        }
        Film film = filmRep.findOne(id);
        if(film == null) return "redirect:/" + SUBFOLDER;
        model.addAttribute(film);
        return PAGE_VIEW;
    }

    @GetMapping("/edit/{id}")
    public String editFilmForm(Model model, @PathVariable("id") Long id) {
        Film film = filmRep.findOne(id);
        if(film == null) return "redirect:/" + SUBFOLDER;
        model.addAttribute("film", film);
        model.addAttribute("media", film.getMedia());
        model.addAttribute("categories", film.getMedia().getCategories());
        model.addAttribute("listCategories", (Iterable<Category>) categRep.findAll());

        return PAGE_EDIT;
    }

    @PostMapping("/edit/{id}")
    public String editFilm(Model model, @Valid Media media, BindingResult bindingResult, @RequestParam("file") MultipartFile image, @RequestParam(value = "categ", required = false) String[] categories,
            RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {

        Film film = filmRep.findOne(id);
        if(film == null) return "redirect:/" + SUBFOLDER;
        System.out.println("id media : " + media.getId());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("film", film);
            model.addAttribute("categories", media.getCategories());
            model.addAttribute("listCategories", (Iterable<Category>) categRep.findAll());
            return PAGE_EDIT;
        }

        if (categories.length != 0) {
            for (String categ : categories) {
                if (categ.trim().isEmpty()) {
                    continue;
                }
                Category category = categRep.findByNameIgnoreCase(categ);
                if (category == null) {
                    category = categRep.save(new Category(categ));
                }
                if (!media.getCategories().contains(category)) {
                    media.getCategories().add(category);
                }
            }
        }

        String filePath = "";
        if (!image.isEmpty()) {
            try {
                String uploadsDir = "/img/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                filePath = Long.toString(media.getId()) + "." + FilenameUtils.getExtension(image.getOriginalFilename());
                String path = realPathtoUploads + filePath;
                File dest = new File(path);
                image.transferTo(dest);
                media.setImage(filePath);
            } catch (IOException | IllegalStateException e) {

            }
        } else {
            media.setImage(film.getMedia().getImage());
        }
            
        mediaRep.save(media);
        Film saveFilm = filmRep.save(film);

        System.out.println("NEW SAVED FILM WITH ID : " + saveFilm.getId());
        return "redirect:/" + SUBFOLDER;
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("media", new Media());
        model.addAttribute("listCategories", (Iterable<Category>) categRep.findAll());

        return PAGE_ADD;
    }

    @PostMapping("/add")
    public String add(Model model, @Valid Media media, BindingResult bindingResult, @RequestParam("file") MultipartFile image, @RequestParam("video") MultipartFile video, @RequestParam(value = "categ", required = false) String[] categories,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listCategories", (Iterable<Category>) categRep.findAll());
            return PAGE_ADD;
        }

        if (categories.length != 0) {
            for (String categ : categories) {
                if (categ.trim().isEmpty()) {
                    continue;
                }
                Category category = categRep.findByNameIgnoreCase(categ);
                if (category == null) {
                    category = categRep.save(new Category(categ));
                }
                if (!media.getCategories().contains(category)) {
                    media.getCategories().add(category);
                }
            }
        }

        Media saveMedia = mediaRep.save(media);

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
                saveMedia.setImage(filePath);
            } catch (IOException | IllegalStateException e) {

            }
        }

        saveMedia = mediaRep.save(saveMedia);
        System.out.println("NEW SAVED MEDIA WITH ID : " + saveMedia.getId() + " NAME = " + saveMedia.getImage());

        filePath = "";
        Video vid = null;
        if (!video.isEmpty()) {
            try {
                String uploadsDir = "/vid/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                filePath = "film" + Long.toString(saveMedia.getId()) + "." + FilenameUtils.getExtension(video.getOriginalFilename());
                String path = realPathtoUploads + filePath;
                File dest = new File(path);
                video.transferTo(dest);
                System.out.println(dest.length());
                vid = videoRep.save(new Video(filePath));
                System.out.println(filePath);
            } catch (IOException | IllegalStateException e) {

            }
        }
        
        Film film = new Film();
        film.setMedia(media);
        film.setVideo(vid);
        Film saveFilm = filmRep.save(film);

        System.out.println("NEW SAVED FILM WITH ID : " + saveFilm.getId());
        return "redirect:/" + SUBFOLDER;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {

        filmRep.delete(id);
        return "redirect:/" + SUBFOLDER;
    }

}
