package com.example.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/")
@Controller
public class MainController {
    @GetMapping
    public ModelAndView showHome(){
        return new ModelAndView("index");
    }
}
