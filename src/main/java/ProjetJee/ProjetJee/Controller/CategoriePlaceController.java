package ProjetJee.ProjetJee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.CategoriePlaceRepository;
import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;

@Controller
public class CategoriePlaceController {

    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private CategoriePlaceRepository categoriePlaceRepository;
    
    @Autowired
	private CategorieRepository categorieRepository;

    @GetMapping("/ajouterCategoriePlace")
    public String ajouterCategoriePlace(Model model, Authentication authentication) {
        model.addAttribute("categoriePlace", new CategoriePlace());
        model.addAttribute("produit", produitRepository.findAll());
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
        return "ajouterCategoriePlace";
    }

    @PostMapping("/ajouterCategoriePlace")
    public String sauvegarderCategoriePlace(@ModelAttribute CategoriePlace categoriePlace) {
        // Code pour sauvegarder la CategoriePlace dans la base de données
        // Assurez-vous de gérer les détailsProduit associés ici
        categoriePlaceRepository.save(categoriePlace);
        return "redirect:/ajouterCategoriePlace";
    }
}
