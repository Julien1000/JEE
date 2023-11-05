package ProjetJee.ProjetJee.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;


import org.springframework.ui.Model;


@Controller
public class IndexController {
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProduitRepository produitRepository;
    @GetMapping("/index")
    public String afficherMaPage(Model model, Authentication authentication) {
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
            List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
    		model.addAttribute("categories", categorie);
    		List<Produit> produits = (List<Produit>) produitRepository.findAll();
    	    model.addAttribute("produits", produits);
        }

        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        

        return "index"; 
    }
    
    
}
