package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Repository.CategorieRepository;

import java.util.List;

@Controller
public class NavbarController {
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/navbar")
    public String afficherMaPage(Model model, Authentication authentication) {
        // Récupérer la liste des catégories depuis la base de données
        System.out.println("Le contrôleur NavbarController est appelé");
        List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
        model.addAttribute("categories", categories);
        
        // Initialiser la variable isAdmin à false
        boolean isAdmin = false;
	    boolean isUserLoggedIn = false;
        // Vérifier si l'utilisateur est authentifié
        if (authentication != null && authentication.isAuthenticated()) {
            // Ajouter le nom de l'utilisateur au modèle
            model.addAttribute("currentUser", authentication.getName());
            isUserLoggedIn = true;
            
            // Vérifier si l'utilisateur a le rôle "ROLE_ADMIN"
            isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
        }

        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);

        
        return "navbar"; // Le nom de votre template HTML
    }
}

