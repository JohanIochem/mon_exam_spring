package com.jiochem.spring.exam.Controllers;

import com.jiochem.spring.exam.GroupValidation.RegisterGroup;
import com.jiochem.spring.exam.Models.User;
import com.jiochem.spring.exam.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping()
public class SecurityController {

    @Autowired
    UserService userService;


    @RequestMapping(value ="/login", method = RequestMethod.GET)
    ModelAndView login(){
        ModelAndView mv = new ModelAndView("security/login");
        return mv;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    ModelAndView logout(){
        ModelAndView mv = new ModelAndView("security/logout");
        return mv;
    }

}
