package com.jiochem.spring.exam.Models;

import com.jiochem.spring.exam.Constraints.FieldMatch;

import com.jiochem.spring.exam.GroupValidation.RegisterGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Utilisateur")
@FieldMatch.List({
        @FieldMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "les mots de passent sont différents :( ",
                groups = {RegisterGroup.class}
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un prénom", groups = {RegisterGroup.class, Default.class})
    private String firstname;

    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un nom", groups = {RegisterGroup.class, Default.class})
    private String lastname;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Veuillez saisir un nom d'utilisateur ", groups = {RegisterGroup.class, Default.class})
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un mot de passe")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Le mot de passe doit contenir au moins une lettre, un chiffre et 8 caractères min.",
            groups = RegisterGroup.class)
    private String password;

    @Transient
    private String confirmPassword;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(){

        this.roles = new ArrayList<Role>();
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
    }

    public List<Role> getRoles(){
        return this.roles;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
