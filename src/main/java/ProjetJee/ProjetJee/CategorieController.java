package ProjetJee.ProjetJee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;



@Controller
public class CategorieController {

	@Autowired
	private CategorieRepository categorieRepository;
	

	@GetMapping(path = "/addCategorie")
	public String showForm(Model model) {
		System.out.println("CDCSDCDSCSDCDCDCDSCD");
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

}
