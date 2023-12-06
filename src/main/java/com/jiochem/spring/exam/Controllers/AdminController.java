package com.jiochem.spring.exam.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping()
    ModelAndView adminHome(){
        ModelAndView mv = new ModelAndView("admin");
        return mv;
    }
}
