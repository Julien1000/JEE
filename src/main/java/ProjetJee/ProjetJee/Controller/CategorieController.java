package ProjetJee.ProjetJee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Entity.Categorie;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class CategorieController {

	@Autowired
	private CategorieRepository categorieRepository;
	

	@GetMapping(path = "/addCategorie")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showForm(Model model,Authentication authentication) {
		model.addAttribute("categorie", new Categorie());
		List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
        model.addAttribute("categories", categories);
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
        
		return "categorieForm";
	}

	@PostMapping(path = "/saveCategorie")
	public String saveCategorie(@ModelAttribute Categorie categorie) {
		categorieRepository.save(categorie);
		return "redirect:/categorie";
	}

	@GetMapping(path = "/categorie")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String listCategories(Model model, Authentication authentication) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
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
        if (isAdmin) {
        	return "categoriesList";
        }else {
        	return "index";
        }
	}
	@GetMapping("/editCategorie/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editCategorieForm(@PathVariable("id") Long id, Model model,Authentication authentication) {
        Categorie existingCategorie = categorieRepository.findById(id).orElse(null);
        model.addAttribute("categorie", existingCategorie);
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
        
        return "categorieForm";  // Reuse the same form for editing
    }

    // Handler to handle the POST request for editing a category
    @PostMapping("/editCategorie/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateCategorie(@PathVariable("id") Long id, @ModelAttribute Categorie categorie) {
        // Ideally, you'd check if the category exists first, but for simplicity, we're directly saving
        categorieRepository.save(categorie);
        return "redirect:/categorie";
    }

    // Handler to handle the deletion of a category
    @GetMapping("/deleteCategorie/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategorie(@PathVariable("id") Long id) {
        categorieRepository.deleteById(id);
        return "redirect:/categorie";
    }

}
