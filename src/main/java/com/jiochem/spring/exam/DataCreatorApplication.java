package com.jiochem.spring.exam;

import com.jiochem.spring.exam.Models.Annonce;
import com.jiochem.spring.exam.Models.Category;
import com.jiochem.spring.exam.Models.Role;
import com.jiochem.spring.exam.Models.User;
import com.jiochem.spring.exam.Services.AnnonceService;
import com.jiochem.spring.exam.Services.CategoryService;
import com.jiochem.spring.exam.Services.RoleService;
import com.jiochem.spring.exam.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class DataCreatorApplication {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DataCreatorApplication.class);
    }

    @Bean
    public CommandLineRunner dataLoader(UserService userService, RoleService roleService, CategoryService categoryService, AnnonceService annonceService) {
        return args -> {
            // Création des rôles
            createRoleIfNotExists("admin", roleService);
            createRoleIfNotExists("user", roleService);
            createRoleIfNotExists("journaliste", roleService);

            // Création des catégories
            createCategoryIfNotExists("Voitures", categoryService);
            createCategoryIfNotExists("High_Tech", categoryService);

            // Création des utilisateurs
            createUserIfNotExists("sabrina_yaaas", "user", "Sabrina", "Yassss", "journaliste", userService, roleService);
            createUserIfNotExists("jean_dubois", "user", "Jean", "Dubois", "user", userService, roleService);
            createUserIfNotExists("aurelien_lechef", "admin", "Aurélien", "Lechef", "admin", userService, roleService);

            // Création des annonces
            createAnnonceIfNotExists("Peugeot 308 GT", "/upload/cequiaruaipuetremavoiture.JPG", "Compacte sportive française au tempérament dynamique. 205ch", "Voitures", annonceService, categoryService);
            createAnnonceIfNotExists("Renault Clio 4 RS", "/upload/clio.jpg", "Excellente voiture de la part de Renault Sport 200ch.", "Voitures", annonceService, categoryService);
            createAnnonceIfNotExists("Vente d'ordinateur Asus", "/upload/pc_hb.jpg", "A Vendre Asus prété par Human Booster, 8go de Ram seulement", "High_Tech", annonceService, categoryService);
            createAnnonceIfNotExists("Tele 8K", "/upload/tele_8k.jpg", "A vendre télé 8K. Qualité d'image sensationnelle. J'ai pas posté de photo de la télé installé chez moi, car en vrai de vrai, à la FNAC elle rend trop bien ! ", "High_Tech", annonceService, categoryService);
        };
    }

    private void createRoleIfNotExists(String roleName, RoleService roleService) {
        if (roleService.findByName(roleName) == null) {
            roleService.saveRole(new Role(roleName));
        }
    }

    private void createCategoryIfNotExists(String categoryName, CategoryService categoryService) {
        if (categoryService.getCategoryByName(categoryName) == null) {
            categoryService.addCategory(new Category(categoryName));
        }
    }

    private void createUserIfNotExists(String username, String password, String firstname, String lastname, String roleName, UserService userService, RoleService roleService) {
        if (userService.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.addRole(roleService.findByName(roleName));
            userService.registerUser(user);
        }
    }

    private void createAnnonceIfNotExists(String title, String image, String content, String categoryName, AnnonceService annonceService, CategoryService categoryService) {
        if (annonceService.getAnnonceByTitle(title) == null) {
            Annonce annonce = new Annonce();
            annonce.setTitle(title);
            annonce.setImage(image);
            annonce.setContent(content);
            annonce.setPublicationDate(new Date());
            Category category = categoryService.getCategoryByName(categoryName);
            annonce.setCategory(category);
            annonceService.addAnnonce(annonce);
        }
    }
}
