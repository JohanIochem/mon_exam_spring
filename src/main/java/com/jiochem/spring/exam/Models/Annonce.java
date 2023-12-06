package com.jiochem.spring.exam.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;


import java.util.Date;

@Entity
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Veuillez saisir un titre pour l'annonce")
    private String title;

    @Column
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Veuillez saisir le contenu de l'annonce")
    private String content;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Veuillez spécifier une date de publication")
    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Annonce() {
        this.publicationDate = new Date();
    }

    public Annonce(String title, String image, String content, Date publicationDate, Category category, User journaliste) {
        this.title = title;
        this.image = image;
        this.content = content;
        this.publicationDate = new Date();
        this.category = category;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }


    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}