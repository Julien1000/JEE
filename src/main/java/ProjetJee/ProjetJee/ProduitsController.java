package ProjetJee.ProjetJee;

import java.io.File;
import java.io.IOException;

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
	

	@GetMapping(path = "/addProduits")
	public String showForm(Model model) {
		model.addAttribute("produits", new Produits());
		return "produitsForm";
	}

	@PostMapping("/saveProduits")
	public String saveProduct(@RequestParam(value="id", required=false) Long id, 
	                          @RequestParam("name") String name, 
	                          @RequestParam("prix") double prix, 
	                          @RequestParam("stock") int stock, 
	                          @RequestParam("numeroPlace") String numeroPlace, 
	                          @RequestParam("image") MultipartFile file) throws IOException {

	    Produits produit;

	    // Si un ID est fourni, tentez de récupérer le produit existant.
	    // Sinon, créez un nouveau produit.
	    if (id != null) {
	        produit = produitsRepository.findById(id).orElse(new Produits());
	    } else {
	        produit = new Produits();
	    }

	    // Mettez à jour les attributs du produit.
	    produit.setName(name);
	    produit.setPrix(prix);
	    produit.setStock(stock);
	    produit.setNumeroPlace(numeroPlace);

	    // Traitez l'image si elle est fournie.
	    if (file != null && !file.isEmpty()) {
	        byte[] bytes = file.getBytes();
	        produit.setImage(bytes);
	    }

	    // Sauvegardez le produit, qu'il soit nouveau ou modifié.
	    produitsRepository.save(produit);

	    return "redirect:/produits";
	}


	@GetMapping(path = "/produits")
	public String listProduits(Model model) {
		model.addAttribute("produits", produitsRepository.findAll());

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
	        return "produitsForm"; // utilisez le même formulaire que pour ajouter un produit, mais avec les données pré-remplies.
	    } else {
	        return "redirect:/produits"; // ou redirigez vers une page d'erreur si vous le souhaitez.
	    }
	}

}
