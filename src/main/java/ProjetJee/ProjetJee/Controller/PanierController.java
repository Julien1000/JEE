package ProjetJee.ProjetJee.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ProjetJee.ProjetJee.Entity.Panier;
import ProjetJee.ProjetJee.Entity.User;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.CommandeRepository;
import ProjetJee.ProjetJee.Repository.DetailCommandeRepository;
import ProjetJee.ProjetJee.Repository.PanierRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Repository.UserRepository;

import ProjetJee.ProjetJee.Entity.Commande;
import ProjetJee.ProjetJee.Entity.DetailCommande;

@Controller
public class PanierController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PanierRepository panierRepository;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private DetailCommandeRepository detailCommandeRepository;
	@Autowired 
	private CommandeRepository commandeRepository;

	@PostMapping("/ajouterPanier")
	public String ajouterElementAuPanier(Model model, @RequestParam("idProduit") Long idProduit,
	                                     @RequestParam("quantite") int quantite, Authentication authentication) {
	    // Vérifier si la quantité est valide
	    if (quantite < 1) {
	        model.addAttribute("erreurQuantite", "Quantité non conforme");
	        return "redirect:/produit/perso/" + idProduit;
	    }
	    
	    // Récupérer l'utilisateur connecté
	    User user = userRepository.findByUsername(authentication.getName());
	    
	    // Récupérer le produit par son ID
	    Produit produit = produitRepository.findById(idProduit)
	                                       .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
	    
	    // Créer un nouvel élément dans le panier (DetailCommande)
	    DetailCommande detailCommande = new DetailCommande();
	    
	    // A MODIFIER
	    detailCommande.setCategoriePlace(null);
	    detailCommande.setQuantite(quantite);
	    
	    // Récupérer le panier de l'utilisateur, ou en créer un nouveau s'il n'en a pas
	    Panier panier = panierRepository.findByUserId(user.getId());
	    if (panier == null) {
	        panier = new Panier();
	        panier.setUser(user);
	        panier.setDetailCommande(new ArrayList<>());
	    }
	    
	    // Ajouter le DetailCommande au panier et sauvegarder
	    panier.getDetailCommande().add(detailCommande);
	    detailCommande.setPanier(panier);
	    panierRepository.save(panier);
	    detailCommandeRepository.save(detailCommande);

	    return "redirect:/produit";
	}

	@PostMapping("/enregistrerPanier")
	public String enregistrerPanier(Authentication authentication) {
	    User user = userRepository.findByUsername(authentication.getName());
	    Panier panier = panierRepository.findByUserId(user.getId());

	    if (panier == null || panier.getDetailCommande() == null || panier.getDetailCommande().isEmpty()) {
	        System.out.println("Le panier est vide ou n'existe pas.");
	        return "redirect:/monPanier"; // Redirigez vers une page appropriée
	    }

	    Commande commande = new Commande();
	    commande.setStatus(1);
	    commande.setUser(user);
	    commande.setDetailCommande(new ArrayList<>(panier.getDetailCommande()));

	    System.out.println("Enregistrement de la commande avec " + commande.getDetailCommande().size() + " produits.");

	    commandeRepository.save(commande);

	    // Mettez à jour les relations inverses (si nécessaire)
	    for (DetailCommande detailCommande : commande.getDetailCommande()) {
	        detailCommande.setCommande(commande);
	        detailCommande.setPanier(null);
	        detailCommandeRepository.save(detailCommande);
	    }

	    // Videz le panier
	 // Videz le panier
	    
	    panierRepository.delete(panier);


	    System.out.println("Commande enregistrée avec succès et panier vidé.");

	    return "redirect:/index"; // Redirigez vers une page de confirmation
	}
	@GetMapping("/monPanier")
	public String monPanier(Model model, Authentication authentication) {
	    User user = userRepository.findByUsername(authentication.getName());
	    Panier panier = panierRepository.findByUserId(user.getId());
	    model.addAttribute("panier", panier);
	    return "monPanier";
	}



}
