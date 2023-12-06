package com.jiochem.spring.exam.Controllers;

import com.jiochem.spring.exam.Models.Category;
import com.jiochem.spring.exam.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("categories/list");
        List<Category> categories = categoryService.getAllCategories();
        mv.addObject("categories", categories);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewCategory(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("categories/detail");
        Category category = categoryService.getCategoryById(id);
        mv.addObject("category", category);
        return mv;
    }

    @RequestMapping(value = "/ajouter", method = RequestMethod.GET)
    public ModelAndView showAddForm() {
        ModelAndView mv = new ModelAndView("categories/ajouter");
        mv.addObject("category", new Category());
        return mv;
    }

    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public ModelAndView addCategory(@ModelAttribute("category") Category category) {
        ModelAndView mv = new ModelAndView("redirect:/categories/");
        return mv;
    }
}
