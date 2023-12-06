package com.jiochem.spring.exam.Controllers;

import com.jiochem.spring.exam.Exception.WrongFileTypeException;
import com.jiochem.spring.exam.Models.Annonce;

import com.jiochem.spring.exam.Models.Category;

import com.jiochem.spring.exam.Services.AnnonceService;

import com.jiochem.spring.exam.Services.CategoryService;

import com.jiochem.spring.exam.Services.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/annonces")
public class AnnonceController {

    @Autowired
    AnnonceService annonceService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StorageService storageService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(name = "categoryFilter", required = false) Long categoryFilter) {
        ModelAndView mv = new ModelAndView("annonces/list");

        List<Annonce> annonces;

        if (categoryFilter != null) {

            annonces = annonceService.getAnnoncesByCategoryId(categoryFilter);
        } else {

            annonces = annonceService.getAllAnnonces();
        }


        List<Category> categories = categoryService.getAllCategories();

        mv.addObject("annonces", annonces);
        mv.addObject("categories", categories);


        mv.addObject("categoryId", categoryFilter);

        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView viewAnnonce(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("annonces/detail");
        Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
        Annonce annonce = annonceOptional.orElse(null);
        mv.addObject("annonce", annonce);
        return mv;
    }


    @RequestMapping(value = "/ajouter", method = RequestMethod.GET)
    public ModelAndView showAddForm(Principal principal) {

        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));


        if (isJournalisteOrAdmin) {
            ModelAndView mv = new ModelAndView("annonces/ajouter");
            mv.addObject("annonce", new Annonce());


            List<Category> categories = categoryService.getAllCategories();
            mv.addObject("categories", categories);

            return mv;
        } else {

            return new ModelAndView("redirect:/annonces/list");
        }
    }

    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public String addAnnonce(@ModelAttribute("annonce") @Valid Annonce annonce,
                             Principal principal, BindingResult bindingResult,
                             @RequestParam("imageAnnonce") MultipartFile imageAnnonce, Model model) throws IOException {
        if (annonce.getPublicationDate() == null) {
            annonce = new Annonce();
        }

        if (bindingResult.hasErrors()) {
            return "annonce/ajouter";
        } else {
            try {
                annonce.setImage(storageService.store(imageAnnonce));

                this.annonceService.addAnnonce(annonce);
                return "redirect:/annonces/list";
            } catch (WrongFileTypeException e) {
                model.addAttribute("uploadError", "Veuillez ajouter une image");
                return "annonces/ajouter";
            }
        }
    }


    @RequestMapping(value = "/modifier/{id}", method = RequestMethod.GET)
    public ModelAndView showEditForm(@PathVariable Long id, Principal principal) {

        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));


        if (isJournalisteOrAdmin) {
            ModelAndView mv = new ModelAndView("annonces/modifier");
            Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
            Annonce annonce = annonceOptional.orElse(null);
            mv.addObject("annonce", annonce);

            // Ajouter  liste catégories au modèle
            List<Category> categories = categoryService.getAllCategories();
            mv.addObject("categories", categories);

            return mv;
        } else {
            // Sinon-> redirection vers la liste ou une page d'erreur d'accès refusé
            return new ModelAndView("redirect:/annonces/list");
        }
    }

    @RequestMapping(value = "/modifier/{id}", method = RequestMethod.POST)
    public ModelAndView editAnnonce(@PathVariable Long id, @ModelAttribute("annonce") Annonce annonce, @RequestParam("imageAnnonce") MultipartFile imageAnnonce, Principal principal) {
        // Récupérer l'annonce existante de la base de données
        Optional<Annonce> existingAnnonceOptional = annonceService.getAnnonceById(id);
        if (existingAnnonceOptional.isPresent()) {
            Annonce existingAnnonce = existingAnnonceOptional.get();


            if (!imageAnnonce.isEmpty()) {

                try {
                    existingAnnonce.setImage(storageService.store(imageAnnonce));
                } catch (WrongFileTypeException | IOException e) {


                }
            }

            existingAnnonce.setTitle(annonce.getTitle());
            existingAnnonce.setContent(annonce.getContent());
            existingAnnonce.setPublicationDate(new Date());
            existingAnnonce.setCategory(annonce.getCategory());

            annonceService.updateAnnonce(existingAnnonce);

            ModelAndView mv = new ModelAndView("redirect:/annonces/list");
            return mv;
        }

        // Je renvoi vers list si l'annonce n'existe pas
        return new ModelAndView("redirect:/annonces/list");
    }


    @RequestMapping(value = "/supprimer/{id}", method = RequestMethod.GET)
    public ModelAndView deleteAnnonce(@PathVariable Long id, Principal principal) {

        boolean isJournalisteOrAdmin = principal != null &&
                (principal instanceof Authentication &&
                        (((Authentication) principal).getAuthorities().stream()
                                .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals("journaliste") ||
                                                grantedAuthority.getAuthority().equals("admin"))));


        if (isJournalisteOrAdmin) {

            Optional<Annonce> annonceOptional = annonceService.getAnnonceById(id);
            if (annonceOptional.isPresent()) {

                annonceService.deleteAnnonce(id);

                ModelAndView mv = new ModelAndView("redirect:/annonces/list");
                return mv;
            } else {

                return new ModelAndView("redirect:/annonces/list");
            }
        }


        return new ModelAndView("redirect:/annonces/list");
    }





}

