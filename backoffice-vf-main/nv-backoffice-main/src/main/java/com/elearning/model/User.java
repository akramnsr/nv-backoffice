package com.elearning.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** relation ManyToMany vers Formation (inchangée) */
    @ManyToMany
    @JoinTable(
            name = "user_formation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "formation_id")
    )
    private List<Formation> formations = new ArrayList<>();

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInscription;

    /** rôle (inchangé) */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Role role;

    /**
     * Relation OneToMany vers Resultat.
     * CascadeType.ALL + orphanRemoval=true garantissent
     * qu’on supprime d’abord les Resultat de ce User.
     */
    @OneToMany(
            mappedBy = "etudiant",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Resultat> resultats = new ArrayList<>();

    public User() {}

    public User(String nom, String prenom, String email, String motDePasse, Date dateInscription) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateInscription = dateInscription;
    }

    // --- getters & setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public List<Formation> getFormations() {
        return formations;
    }
    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Date getDateInscription() {
        return dateInscription;
    }
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public List<Resultat> getResultats() {
        return resultats;
    }
    public void setResultats(List<Resultat> resultats) {
        this.resultats = resultats;
    }
}
