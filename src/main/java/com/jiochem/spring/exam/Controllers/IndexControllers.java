package com.jiochem.spring.exam.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexControllers {

    @RequestMapping("")
    ModelAndView home(){
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }
}

