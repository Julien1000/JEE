package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String sauvegarderCategoriePlace(@ModelAttribute CategoriePlace categoriePlace) {
        // Code pour sauvegarder la CategoriePlace dans la base de données
        // Assurez-vous de gérer les détailsProduit associés ici
        categoriePlaceRepository.save(categoriePlace);
        return "redirect:/ajouterCategoriePlace";
    }
}
