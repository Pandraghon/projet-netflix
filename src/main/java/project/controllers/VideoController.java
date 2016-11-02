/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controllers;

import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.repositories.VideoRepository;
import project.models.Video;

@Controller
@RequestMapping("/videos")
public class VideoController {
    
    @Autowired
    private VideoRepository videoRepository; 
    
    private final String SUBFOLDER 		= "videos/";
	
    private final String PAGE_VIEW              = SUBFOLDER + "view";
    
    @GetMapping("/view/{id}")
    public String viewFilm(Model model, @PathVariable("id") Long id) {
        Video video = videoRepository.findOne(id);
        if(video == null) return "redirect:/";
        
        model.addAttribute(video);
        
            return PAGE_VIEW;
    }
    
}
