package ProjetJee.ProjetJee.Controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Service.ProduitService;
import ProjetJee.ProjetJee.Repository.CategoriePlaceRepository;
import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Repository.DetailProduitRepository;
import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Entity.DetailProduit;
import ProjetJee.ProjetJee.Entity.Produit;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

@Controller
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
	@Autowired
	private CategoriePlaceRepository categoriePlaceRepository;

	@Autowired
	private DetailProduitRepository detailProduitRepository;

	@GetMapping(path = "/addProduit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String showForm(Model model, Authentication authentication) {
		List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("produit", new Produit());
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
		Long categorieId1 = categories.get(0).getId();
		model.addAttribute("idCategorie1", categorieId1);
		String categorieName1 = categories.get(0).getName();
		model.addAttribute("nameCategorie1", categorieName1);
		return "produitForm";
	}

	@PostMapping("/saveProduit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String saveProduct(
	    @RequestParam(value="id", required=false) Long id,
	    @RequestParam("name") String name,
	    @RequestParam("categorie") Long categorieId,
	    @RequestParam("image") MultipartFile file,
		@RequestParam("description") String description,
		RedirectAttributes redirectAttributes

	) throws IOException {

		Produit produit;

		// Si un ID est fourni, tentez de récupérer le produit existant.
		// Sinon, créez un nouveau produit.
		if (id != null) {
			produit = produitRepository.findById(id).orElse(new Produit());
		} else {
			produit = new Produit();
		}

		// // Mettez à jour les attributs du produit.
		produit.setName(name);

		// Gérer l'affectation de la catégorie.
		Categorie cat = categorieRepository.findById(categorieId).orElse(null);
		if (cat == null) {
			// Vous pouvez gérer l'erreur ici si la catégorie n'est pas trouvée.
			// Par exemple, rediriger vers un message d'erreur ou une page spécifique.
			return "errorPage"; // Assurez-vous d'avoir une vue ou une page pour gérer cette erreur.
		} else {
			produit.setCategorie(cat);
		}

		// Traitez l'image si elle est fournie.
		if (file != null && !file.isEmpty()) {
			// Compression de l'image
			byte[] bytes = file.getBytes();

			// Assignation de l'image compressée au produit.
			produit.setImage(bytes);
		}

		produit.setDescription(description);

	    // Sauvegardez le produit, qu'il soit nouveau ou modifié.
		try {
		    produitRepository.save(produit);
			// Message de succès en cas de soumission réussie
			redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté avec succès !");
		} catch (Exception e) {
			// Message d'erreur en cas d'échec de la soumission
			redirectAttributes.addFlashAttribute("errorMessage",
					"Une erreur s'est produite lors de l'ajout du produit.");
		}


		return "redirect:/produit";
	}

	@GetMapping(path = "/produit")
	public String listProduit(Model model, Authentication authentication) {
		List<Produit> allProducts = (List<Produit>) produitRepository.findAll();
		model.addAttribute("produits", allProducts);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        produitService.deleteProduitAndRelatedEntities(id);
        return "redirect:/produit";
    }

	@GetMapping("/produit/perso/{id}")
	public String persoProduct(@PathVariable Long id, Model model, Authentication authentication) {
		java.util.Optional<Produit> produitOptional = produitRepository.findById(id);
		List<Produit> allProducts = (List<Produit>) produitRepository.findAll();
		model.addAttribute("produits", allProducts);
		List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categorie);
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

		if (produitOptional.isPresent()) {
			Produit produit = produitOptional.get();
			model.addAttribute("produit", produit);

			List<DetailProduit> allDetailProduits = (List<DetailProduit>) detailProduitRepository.findAll();
			// for (DetailProduit detailProduit : allDetailProduits) {
			// if(detailProduit.getProduit().getId() != produit.getId()) {
			// allDetailProduits.remove(detailProduit);
			// }
			// }
			allDetailProduits = allDetailProduits.stream()
					.filter(detailProduit -> detailProduit.getProduit().getId() == produit.getId())
					.collect(Collectors.toList());
			model.addAttribute("detailProduit", allDetailProduits);
			model.addAttribute("detailProduit", allDetailProduits);

			// DetailProduit detailProduit = detailProduitRepository.findByProduit(produit);
			// model.addAttribute("detailProduit", detailProduit);

			// Récupérer les CategoriePlace associées
			// List<CategoriePlace> categoriePlaces =
			// categoriePlaceRepository.findByDetailProduit(detailProduit);
			// model.addAttribute("categoriePlaces", categoriePlaces);
			Long categorieId = produit.getCategorie().getId();
			model.addAttribute("idCategorie", categorieId);
		} else {
			// Gérer le cas où le produit n'est pas trouvé, par exemple, rediriger vers une
			// page d'erreur
			return "produitNotFound";
		}
		return "produitPerso";
	}

	@GetMapping("/editProduct/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editProduct(@PathVariable Long id, Model model, Authentication authentication) {
		java.util.Optional<Produit> produit = produitRepository.findById(id);
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
		if (produit.isPresent()) {
			Produit produitPresent = produit.get();
			model.addAttribute("produit", produitPresent);
			model.addAttribute("categories", categorieRepository.findAll());

			Long categorieId1 = produitPresent.getCategorie().getId();
			model.addAttribute("idCategorie1", categorieId1);
			String categorieName1 = produitPresent.getCategorie().getName();
			model.addAttribute("nameCategorie1", categorieName1);
			return "produitForm"; // utilisez le même formulaire que pour ajouter un produit, mais avec les
									// données pré-remplies.
		} else {
			return "redirect:/produit"; // ou redirigez vers une page d'erreur si vous le souhaitez.
		}
	}

	@GetMapping(path = "/produit/{idCategorie}")
	public String listProductsByCategory(@PathVariable("idCategorie") Long idCategorie, Model model,
			Authentication authentication) {
		List<Produit> produits = produitRepository.findByCategorieId(idCategorie);
		model.addAttribute("produits", produits);
		List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categorie);
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
		return "productsByCategory"; // Name of the Thymeleaf template
	}

	@GetMapping(path = "/getCategoryByDetail/{detailProduitId}")
	public ResponseEntity<List<CategoriePlace>> getDetailsByProduct(@PathVariable Long detailProduitId) {
		List<CategoriePlace> categories = categoriePlaceRepository.findByDetailProduitId(detailProduitId);
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/displayImageLieu/{id}")
	public ResponseEntity<byte[]> displayImageLieu(@PathVariable Long id) {
		java.util.Optional<DetailProduit> detailProduit = detailProduitRepository.findById(id);

		if (detailProduit.isPresent() && detailProduit.get().getImageLieu() != null) {
			return ResponseEntity.ok()
					.contentType(MediaType.IMAGE_JPEG) // Assurez-vous de définir le type de contenu approprié
					.body(detailProduit.get().getImageLieu());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
