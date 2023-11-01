package ProjetJee.ProjetJee.Controller;

import java.io.IOException;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.DetailProduit;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.DetailProduitRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;

@Controller
public class DetailProduitController {
	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private DetailProduitRepository detailProduitRepository;

	@GetMapping(path = "/addDetailProduit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showForm(Model model, Authentication authentication) {
		List<Produit> produits = (List<Produit>) produitRepository.findAll();
		model.addAttribute("produit", produits);
		model.addAttribute("detailProduit", new DetailProduit());
		boolean isAdmin = false;
		boolean isUserLoggedIn = false;
		// Vérifier si l'utilisateur est authentifié
		if (authentication != null && authentication.isAuthenticated()) {
			// Ajouter le nom de l'utilisateur au modèle
			model.addAttribute("currentUser", authentication.getName());
			isUserLoggedIn = true;

			// Vérifier si l'utilisateur a le rôle "ROLE_ADMIN"
			isAdmin = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.anyMatch("ROLE_ADMIN"::equals);
		}

		// Ajouter la variable isAdmin au modèle
		model.addAttribute("isUserLoggedIn", isUserLoggedIn);
		model.addAttribute("isAdmin", isAdmin);
		Long produitID = produits.get(0).getId();
		model.addAttribute("idProduit", produitID);
		String produitName = produits.get(0).getName();
		model.addAttribute("nameProduit", produitName);
		return "detailProduitForm";
	}

	@PostMapping("/saveDetailProduit")
	public String saveProduct(@RequestParam(value = "id", required = false) Long id, @RequestParam("date") Date date,
			@RequestParam("adresse") String adresse, @RequestParam("produit") Long produitId,
			@RequestParam("image") MultipartFile file) throws IOException {

		DetailProduit detailProduit;

		// Si un ID est fourni, tentez de récupérer le produit existant.
		// Sinon, créez un nouveau produit.
		if (id != null) {
			detailProduit = detailProduitRepository.findById(id).orElse(new DetailProduit());
		} else {
			detailProduit = new DetailProduit();
		}

//	    // Mettez à jour les attributs du produit.
		detailProduit.setDate(date);
		detailProduit.setAdresse(adresse);

		// Gérer l'affectation de la catégorie.
		Produit produit = produitRepository.findById(produitId).orElse(null);
		if (produit == null) {
			// Vous pouvez gérer l'erreur ici si la catégorie n'est pas trouvée.
			// Par exemple, rediriger vers un message d'erreur ou une page spécifique.
			return "errorPage"; // Assurez-vous d'avoir une vue ou une page pour gérer cette erreur.
		} else {
			detailProduit.setProduit(produit);
		}

		// Traitez l'image si elle est fournie.
		if (file != null && !file.isEmpty()) {
			// Compression de l'image
			byte[] bytes = file.getBytes();

			// Assignation de l'image compressée au produit.
			detailProduit.setImageLieu(bytes);
		}

		// Sauvegardez le produit, qu'il soit nouveau ou modifié.
		detailProduitRepository.save(detailProduit);

		return "redirect:/addDetailProduit";
	}

	@GetMapping(path = "/getDetailsByProduct/{produitId}")
	public ResponseEntity<List<DetailProduit>> getDetailsByProduct(@PathVariable Long produitId) {
	    List<DetailProduit> details = detailProduitRepository.findByProduitId(produitId);
	    return ResponseEntity.ok(details);
	}
}
