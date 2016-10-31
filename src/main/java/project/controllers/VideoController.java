/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controllers;

import javax.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pandr
 */
@Controller
@RequestMapping("/videos")
public class VideoController {
    
    private final String SUBFOLDER 		= "videos/";
	
    private final String PAGE_VIEW              = SUBFOLDER + "view";
    
    @GetMapping("/view/{id}")
    public String viewFilm(Model model, @PathParam("id") Long id) {
            
            return PAGE_VIEW;
    }
    
}
