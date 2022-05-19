package fr.lgpl.annuaire;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;
    @Column(name = "login", nullable = false, length = 50)
    private String login;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Lob
    @Column(name = "mdp", nullable = false)
    private String mdp;
    @Column(name = "date_arrivee", nullable = false)
    private Date date_arrivee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Date getDate_arrivee() {
        return date_arrivee;
    }

    public void setDate_arrivee(Date date_arrivee) {
        this.date_arrivee = date_arrivee;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", date_arrivee=" + date_arrivee +
                '}';
    }
}
