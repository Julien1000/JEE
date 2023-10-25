package ProjetJee.ProjetJee;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

//@Controller
//public class IndexController {
	
//	@GetMapping(path = "/index")
//	public String index() {
//		return "redirect:/categoriesList.html";
//	}
	
//}


@Controller
public class IndexController {
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProduitRepository produitRepository;
    @GetMapping("/index")
    public String afficherMaPage(Model model,Authentication authentication) {
    	List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categorie);
		List<Produit> produits = (List<Produit>) produitRepository.findAll();
	    model.addAttribute("produits", produits);
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
        System.out.println("Is Admin: " + isAdmin);
        System.out.println("Authorities: " + authentication.getAuthorities());

        return "index"; // Le nom de votre template HTML
    }
}
