package com.jiochem.spring.exam.Repository;

import com.jiochem.spring.exam.Models.Annonce;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Long> {

    Annonce findByTitle(String title);

}
