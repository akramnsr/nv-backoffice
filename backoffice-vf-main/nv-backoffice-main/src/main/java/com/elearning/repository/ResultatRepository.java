package com.elearning.repository;

import com.elearning.model.Resultat;
import com.elearning.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    @Query("""
    SELECT r
    FROM Resultat r
    JOIN FETCH r.etudiant
    JOIN FETCH r.quiz
    """)
    List<Resultat> findAllWithDetails();

    List<Resultat> findByEtudiant(User etudiant);

}

