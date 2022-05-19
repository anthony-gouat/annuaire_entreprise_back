package fr.lgpl.annuaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

    @Autowired
    UtilisateurRepository ur;
    /**
     * Apelle de la liste des Utilisateurs stockées en base de données
     * @return la liste de tous les utilisateurs
     */
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> resultat =  (List<Utilisateur>) ur.findAll();
        for (Utilisateur u : resultat) {
            u.setMdp("");
        }
        return resultat;
    }

    @PostMapping
    public Utilisateur ajoutUtilisateur(@Validated @RequestBody Utilisateur utilisateur, HttpServletResponse response) throws IOException {
        if(findByLogin(utilisateur.getLogin())==null)
            return ur.save(utilisateur);
        response.setStatus(400);
        response.getWriter().write("login deja utilise");
        response.getWriter().close();
        return null;
    }

    @DeleteMapping("/{id}")
    public void suppressionUtilisateur(@PathVariable(value="id")Long id) {
        ur.deleteById(id);
    }

    @PutMapping("/{id}")
    public Utilisateur modifieUtilisateur(@Validated @RequestBody Utilisateur newUtilisateur,@PathVariable(value="id")Long id) {
        Utilisateur utilisateur = null;
        Optional<Utilisateur> optionalUtilisateur = ur.findById(id);
        utilisateur = (optionalUtilisateur.isPresent())?optionalUtilisateur.get():null;
        if (utilisateur!=null){
            if(newUtilisateur.getNom()!=null)utilisateur.setNom(newUtilisateur.getNom());
            if(newUtilisateur.getPrenom()!=null)utilisateur.setPrenom(newUtilisateur.getPrenom());
            if(newUtilisateur.getLogin()!=null)utilisateur.setLogin(newUtilisateur.getLogin());
            if(newUtilisateur.getEmail()!=null)utilisateur.setMdp(newUtilisateur.getMdp());
            if(newUtilisateur.getMdp().length()>0)utilisateur.setEmail(newUtilisateur.getEmail());
            if(newUtilisateur.getDate_arrivee()!=null)utilisateur.setDate_arrivee(newUtilisateur.getDate_arrivee());
            return ur.save(utilisateur);
        }
        return null;
    }

    @PostMapping("/authentification")
    public Utilisateur authentification(@RequestBody Utilisateur user, HttpServletResponse response) throws IOException {
        Utilisateur utilisateur = findByLogin(user.getLogin());
        if(utilisateur!=null){
            if(utilisateur.getMdp().equals(user.getMdp())) {
                utilisateur.setMdp("");
                return utilisateur;
            }
            response.setStatus(403);
            response.getWriter().write("mot de passe incorrect");
            response.getWriter().close();

            return null;
        }
        response.setStatus(403);
        response.getWriter().write("login inconnu");
        response.getWriter().close();
        return null;
    }

    private Utilisateur findByLogin(String login){
        Iterable<Utilisateur> allUsers = ur.findAll();
        for (Utilisateur user:allUsers) {
            if(user.getLogin().equals(login)) return user;
        }
        return null;
    }
}
