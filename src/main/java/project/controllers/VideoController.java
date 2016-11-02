/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.server.PathParam;
import org.apache.commons.io.FilenameUtils;
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
        Map<String, String> type = new HashMap<>();
        type.put("avi", "video/x-msvideo");
        type.put("mp4", "video/mp4");
        type.put("webm", "video/webm");
        type.put("ogv", "video/ogg");
        type.put("mov", "video/quicktime");
        
        Video video = videoRepository.findOne(id);
        if(video == null) return "redirect:/";
        
        model.addAttribute(video);
        model.addAttribute("type", type.get(FilenameUtils.getExtension(video.getContent())));
        
        return PAGE_VIEW;
    }
    
}
