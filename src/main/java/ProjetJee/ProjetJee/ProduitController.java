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
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private CategorieRepository categorieRepository;

	@GetMapping(path = "/addProduit")
	public String showForm(Model model) {
		List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
	    model.addAttribute("categories", categories);
		model.addAttribute("produit", new Produit());
		return "produitForm";
	}

	@PostMapping("/saveProduit")
	public String saveProduct(
	    @RequestParam(value="id", required=false) Long id,
	    @RequestParam("name") String name,
	    @RequestParam("prix") String prix,
	    @RequestParam("stock") String stock,
	    @RequestParam("numeroPlace") String numeroPlace,
	    @RequestParam("categorie") Long categorieId,   // Modification ici: Utilisation de "categorie" comme nom du paramètre
	    @RequestParam("image") MultipartFile file
	) throws IOException {

	    Produit produit;

	    // Si un ID est fourni, tentez de récupérer le produit existant.
	    // Sinon, créez un nouveau produit.
	    if (id != null) {
	        produit = produitRepository.findById(id).orElse(new Produit());
	    } else {
	        produit = new Produit();
	    }

//	    // Mettez à jour les attributs du produit.
	    produit.setName(name);
	    produit.setPrix(Double.parseDouble(prix));
	    produit.setStock(Integer.parseInt(stock));
	    produit.setNumeroPlace(numeroPlace);

	    // Gérer l'affectation de la catégorie.
	    Categorie cat = categorieRepository.findById(categorieId).orElse(null);
	    if (cat == null) {
	        // Vous pouvez gérer l'erreur ici si la catégorie n'est pas trouvée.
	        // Par exemple, rediriger vers un message d'erreur ou une page spécifique.
	        return "errorPage";  // Assurez-vous d'avoir une vue ou une page pour gérer cette erreur.
	    } else {
	        produit.setCategorie(cat);
	    }

	    // Traitez l'image si elle est fournie.
	    if (file != null && !file.isEmpty()) {
	        byte[] bytes = file.getBytes();
	        produit.setImage(bytes);
	    }

	    // Sauvegardez le produit, qu'il soit nouveau ou modifié.
	    produitRepository.save(produit);

	    return "redirect:/produit";
	}



	@GetMapping(path = "/produit")
	public String listProduit(Model model) {
	    List<Produit> allProducts = (List<Produit>) produitRepository.findAll();
	    model.addAttribute("produits", allProducts);
	    return "produitList";
	}
	@GetMapping("/displayImage/{id}")
	public ResponseEntity<byte[]> displayImage(@PathVariable Long id) {
	    java.util.Optional<Produit> produit = produitRepository.findById(id);
	    
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
	    produitRepository.deleteById(id);
	    return "redirect:/produits";
	}
	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable Long id, Model model) {
	    java.util.Optional<Produit> produit = produitRepository.findById(id);
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
	    List<Produit> produits = produitRepository.findByCategorieId(idCategorie);
	    model.addAttribute("produits", produits);
	    return "productsByCategory";  // Name of the Thymeleaf template
	}

}
