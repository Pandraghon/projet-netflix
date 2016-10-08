/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    private final String SUBFOLDER 	= "pages/";
	
    private final String PAGE_INDEX     = SUBFOLDER + "index";
    
    
    
    @GetMapping({"", "/", "/home"})
    public String index(Model model) {
        return PAGE_INDEX;
    }
    
}
