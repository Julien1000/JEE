package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String showForm(Model model) {
		model.addAttribute("categorie", new Categorie());
		return "categorieForm";
	}

	@PostMapping(path = "/saveCategorie")
	public String saveCategorie(@ModelAttribute Categorie categorie) {
		categorieRepository.save(categorie);
		return "redirect:/categories";
	}

	@GetMapping(path = "/categories")
	public String listCategories(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		return "categoriesList";
	}
	@GetMapping("/editCategorie/{id}")
    public String editCategorieForm(@PathVariable("id") Long id, Model model) {
        Categorie existingCategorie = categorieRepository.findById(id).orElse(null);
        model.addAttribute("categorie", existingCategorie);
        return "categorieForm";  // Reuse the same form for editing
    }

    // Handler to handle the POST request for editing a category
    @PostMapping("/editCategorie/{id}")
    public String updateCategorie(@PathVariable("id") Long id, @ModelAttribute Categorie categorie) {
        // Ideally, you'd check if the category exists first, but for simplicity, we're directly saving
        categorieRepository.save(categorie);
        return "redirect:/categories";
    }

    // Handler to handle the deletion of a category
    @GetMapping("/deleteCategorie/{id}")
    public String deleteCategorie(@PathVariable("id") Long id) {
        categorieRepository.deleteById(id);
        return "redirect:/categories";
    }

}
