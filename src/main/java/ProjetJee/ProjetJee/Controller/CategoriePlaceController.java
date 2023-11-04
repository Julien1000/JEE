package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Repository.CategoriePlaceRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;

@Controller
public class CategoriePlaceController {

    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private CategoriePlaceRepository categoriePlaceRepository;

    @GetMapping("/ajouterCategoriePlace")
    public String ajouterCategoriePlace(Model model) {
        model.addAttribute("categoriePlace", new CategoriePlace());
        model.addAttribute("produit", produitRepository.findAll());
        return "ajouterCategoriePlace";
    }

    @PostMapping("/ajouterCategoriePlace")
    public String sauvegarderCategoriePlace(@ModelAttribute CategoriePlace categoriePlace, RedirectAttributes redirectAttributes) {
        // Code pour sauvegarder la CategoriePlace dans la base de données
        // Assurez-vous de gérer les détailsProduit associés ici
//		try {
	        categoriePlaceRepository.save(categoriePlace);
			// Message de succès en cas de soumission réussie
			redirectAttributes.addFlashAttribute("successMessage", "Catégorie ajouté avec succès !");
//		} catch (Exception e) {
//			// Message d'erreur en cas d'échec de la soumission
//			redirectAttributes.addFlashAttribute("errorMessage",
//					"Une erreur s'est produite lors de l'ajout de la catégorie.");
//		}
        return "redirect:/ajouterCategoriePlace";
    }
}
