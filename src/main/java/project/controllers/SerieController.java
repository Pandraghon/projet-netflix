package project.controllers;

import java.io.File;
import java.io.IOException;
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

import project.models.Media;
import project.models.Serie;
import project.repositories.MediaRepository;
import project.repositories.SerieRepository;

@Controller
@RequestMapping("/series")
public class SerieController {

    private final String SUBFOLDER = "series/";

    private final String PAGE_INDEX = SUBFOLDER + "index";
    private final String PAGE_VIEW = SUBFOLDER + "view";
    private final String PAGE_LIST = SUBFOLDER + "list";
    private final String PAGE_EDIT = SUBFOLDER + "edit";
    private final String PAGE_ADD = SUBFOLDER + "add";
    private final String PAGE_DELETE = SUBFOLDER + "delete";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SerieRepository SerieRepository;

    @Autowired
    private MediaRepository MediaRepository;

    @GetMapping({"", "/"})
    public String listSeries(Model model) {
        Iterable<Serie> list = SerieRepository.findAll();

        model.addAttribute("serie", list);

        return PAGE_LIST;

    }

    @GetMapping("/view/{id}")
    public String viewSerieInformations(Model model, @PathVariable("id") Long id) {

        return PAGE_VIEW;
    }

    @GetMapping("/add")
    public String addSerieForm(Model model) {
        model.addAttribute("serie", new Serie());
        model.addAttribute("media", new Media());

        return PAGE_ADD;
    }

    @PostMapping("/add")
    public String addSerie(@Valid Media media, BindingResult bindingresult, @RequestParam("file") MultipartFile image,
            RedirectAttributes redirectAttributes) {

        if (bindingresult.hasErrors()) {
            System.out.println("ADD ERRORS : " + bindingresult.toString());
            return PAGE_ADD;
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

        System.out.println("NEW SAVED MEDIA WITH ID : " + saveMedia.getId() + " NAME = " + saveMedia.getName() + " IMAGE = " + saveMedia.getImage());
        System.out.println("NEW SAVED SERIE WITH ID : " + saveSerie.getMedia().getId() + " NAME = " + saveSerie.getMedia().getName() + " IMAGE = " + saveSerie.getMedia().getImage());

        return "redirect:/series";
    }

    @GetMapping("/delete/{id}")
    public String deleteSerie(@PathVariable("id") Long id) {

        SerieRepository.delete(id);
        return PAGE_LIST;
    }

    @GetMapping("/edit/{id}")
    public String editSerieForm(@PathVariable("id") Long id, Model model) {

        Serie serie = SerieRepository.findOne(id);
        model.addAttribute("serie", serie);

        return PAGE_EDIT;
    }

    @PostMapping("/edit")
    public String editSerie(@Valid Serie serie) {

        System.out.println("SAVED EDIT SERIE WITH ID : " + serie.getId());
        SerieRepository.save(serie);
        return "redirect:/series";

    }

}
