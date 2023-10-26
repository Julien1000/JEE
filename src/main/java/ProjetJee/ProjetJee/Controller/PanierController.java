package ProjetJee.ProjetJee.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ProjetJee.ProjetJee.Entity.Panier;
import ProjetJee.ProjetJee.Entity.User;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.DetailCommandeRepository;
import ProjetJee.ProjetJee.Repository.PanierRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Repository.UserRepository;
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

	@PostMapping("/ajouterPanier")
	public String ajouterElementAuPanier(Model model, @RequestParam(value = "id", required = false) Long id,
			@RequestParam("idProduit") Long idProduit, @RequestParam("quantite") int quantite,
			Authentication authentication) {
		if (quantite < 1) {
			model.addAttribute("erreurQuantite", "Quantité non conforme");
			return "redirect:/produit/perso/" + idProduit;
		}
		DetailCommande detailCommande = new DetailCommande();
		detailCommande.setQuantite(quantite);
		Produit produit = produitRepository.findById(idProduit).orElse(null);
		detailCommande.setProduit(produit);
		detailCommandeRepository.save(detailCommande);
		User user = userRepository.findByUsername(authentication.getName());

		Panier panier = panierRepository.findByUserId(user.getId());
		if (panier == null) {
			panier = new Panier();
			panier.setUser(user);
		}

		// Assurez-vous que le panier et le détail de la commande existent

		if (panier != null && detailCommande != null) {
			if (panier.getDetailCommande() == null) {
				// Si la liste detailCommande est null, créez une nouvelle liste.
				panier.setDetailCommande(new ArrayList<DetailCommande>());
			}
			panier.getDetailCommande().add(detailCommande);
			panierRepository.save(panier);
			return "redirect:/produit";
		} else {
			// Gérer les cas d'erreur, par exemple, en renvoyant une réponse d'erreur
			// ou en lançant une exception appropriée.
			return "redirect:/produit/perso/" + idProduit;
		}
	}
}
