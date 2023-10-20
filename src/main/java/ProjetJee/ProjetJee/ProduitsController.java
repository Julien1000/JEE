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



@Controller
public class ProduitsController {

	@Autowired
	private ProduitsRepository produitsRepository;
	@Autowired
	private CategorieRepository categorieRepository;

	@GetMapping(path = "/addProduits")
	public String showForm(Model model) {
		List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
	    model.addAttribute("categories", categories);
		model.addAttribute("produits", new Produits());
		return "produitsForm";
	}

	@PostMapping("/saveProduits")
	public String saveProduct(
	    @RequestParam(value="id", required=false) Long id,
	    @RequestParam("name") String name,
	    @RequestParam("prix") String prix,
	    @RequestParam("stock") String stock,
	    @RequestParam("numeroPlace") String numeroPlace,
	    @RequestParam("categorie") Long categorieId,   // Modification ici: Utilisation de "categorie" comme nom du paramètre
	    @RequestParam("image") MultipartFile file
	) throws IOException {

	    Produits produits;

	    // Si un ID est fourni, tentez de récupérer le produit existant.
	    // Sinon, créez un nouveau produit.
	    if (id != null) {
	        produits = produitsRepository.findById(id).orElse(new Produits());
	    } else {
	        produits = new Produits();
	    }

//	    // Mettez à jour les attributs du produit.
	    produits.setName(name);
	    produits.setPrix(Double.parseDouble(prix));
	    produits.setStock(Integer.parseInt(stock));
	    produits.setNumeroPlace(numeroPlace);

	    // Gérer l'affectation de la catégorie.
	    Categorie cat = categorieRepository.findById(categorieId).orElse(null);
	    if (cat == null) {
	        // Vous pouvez gérer l'erreur ici si la catégorie n'est pas trouvée.
	        // Par exemple, rediriger vers un message d'erreur ou une page spécifique.
	        return "errorPage";  // Assurez-vous d'avoir une vue ou une page pour gérer cette erreur.
	    } else {
	        produits.setCategorie(cat);
	    }

	    // Traitez l'image si elle est fournie.
	    if (file != null && !file.isEmpty()) {
	        byte[] bytes = file.getBytes();
	        produits.setImage(bytes);
	    }

	    // Sauvegardez le produit, qu'il soit nouveau ou modifié.
	    produitsRepository.save(produits);

	    return "redirect:/produits";
	}



	@GetMapping(path = "/produits")
	public String listProduits(Model model) {
	    List<Produits> allProducts = (List<Produits>) produitsRepository.findAll();
	    model.addAttribute("produits", allProducts);
	    return "produitsList";
	}
	@GetMapping("/displayImage/{id}")
	public ResponseEntity<byte[]> displayImage(@PathVariable Long id) {
	    java.util.Optional<Produits> produit = produitsRepository.findById(id);
	    
	    if (produit.isPresent() && produit.get().getImage() != null) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) // Assurez-vous de définir le type de contenu approprié
	                .body(produit.get().getImage());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Long id) {
	    produitsRepository.deleteById(id);
	    return "redirect:/produits";
	}
	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable Long id, Model model) {
	    java.util.Optional<Produits> produit = produitsRepository.findById(id);
	    if (produit.isPresent()) {
	        model.addAttribute("produits", produit.get());
	        model.addAttribute("categories", categorieRepository.findAll());
	        return "produitsForm"; // utilisez le même formulaire que pour ajouter un produit, mais avec les données pré-remplies.
	    } else {
	        return "redirect:/produits"; // ou redirigez vers une page d'erreur si vous le souhaitez.
	    }
	}
	
	@GetMapping(path = "/produits/{idCategorie}")
	public String listProductsByCategory(@PathVariable("idCategorie") Long idCategorie, Model model) {
	    List<Produits> produits = produitsRepository.findByCategorieId(idCategorie);
	    model.addAttribute("produits", produits);
	    return "productsByCategory";  // Name of the Thymeleaf template
	}

}
