package ProjetJee.ProjetJee;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/index")
    public String afficherMaPage(Model model) {
    	List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categorie);
        return "index"; // Le nom de votre template HTML
    }
}
