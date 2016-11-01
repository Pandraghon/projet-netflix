/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.models.Film;
import project.models.Serie;
import project.repositories.FilmRepository;
import project.repositories.SerieRepository;


@Controller
public class WebController {
    
    private final String SUBFOLDER 	= "pages/";
	
    private final String PAGE_INDEX     = SUBFOLDER + "index";
   
    @Autowired
    private FilmRepository filmRep; 
    @Autowired
    private SerieRepository serieRep; 
    
    @GetMapping({"", "/", "/home"})
    public String index(Model model) {
        model.addAttribute("films", (Iterable<Film>) filmRep.findAll());
        model.addAttribute("series", (Iterable<Serie>) serieRep.findAll());
        return PAGE_INDEX;
    }
    
}
