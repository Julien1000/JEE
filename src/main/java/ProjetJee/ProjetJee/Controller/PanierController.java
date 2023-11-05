package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ProjetJee.ProjetJee.Entity.*;
import ProjetJee.ProjetJee.Repository.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PanierController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PanierRepository panierRepository;
    @Autowired
    private CategoriePlaceRepository categoriePlaceRepository;
    @Autowired
    private DetailCommandeRepository detailCommandeRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired 
    private CategorieRepository categorieRepository;
    
    @Autowired 
    private DetailProduitRepository detailProduitRepository;
    
    @Autowired 
    private ProduitRepository produitRepository;

    @PostMapping("/ajouterPanier")
    public String ajouterElementAuPanier(Model model, @RequestParam(name = "idCategoriePlace", required=false) Long idCategoriePlace,
                                         @RequestParam(name = "quantite", required=false) String quantiteStr, 
                                         @RequestParam(name = "idProduit") Long idProduit,
                                         Authentication authentication,RedirectAttributes redirectAttributes) {
        if (idCategoriePlace == null) {
            // Le paramètre idCategoriePlace est manquant, affichez la popup d'erreur.
        	redirectAttributes.addFlashAttribute("erreur", "Veuillez sélectionner une catégorie.");
            return "redirect:/produit/perso/"+idProduit+"?erreur=erreurSelection"; // Remplacez par la page de produit appropriée
        }
    	Integer quantite = null;
        if (quantiteStr != null && !quantiteStr.isEmpty() && quantiteStr != "") {
            try {
                quantite = Integer.parseInt(quantiteStr);
                if (quantite <= 0) {
                    // La quantité est invalide, affichez la popup d'erreur.
                	redirectAttributes.addFlashAttribute("erreur", "Quantité non valide.");
                    return "redirect:/produit/perso/"+idProduit+"?erreur=erreurQuantite"; // Remplacez par la page de produit appropriée
                }
            } catch (NumberFormatException e) {
                // La chaîne n'est pas un entier valide, affichez la popup d'erreur.
            	redirectAttributes.addFlashAttribute("erreur", "Quantité non valide.");
                return "redirect:/produit/perso/"+idProduit+"?erreur=erreurQuantite"; // Remplacez par la page de produit appropriée
            }
        }else {
        	redirectAttributes.addFlashAttribute("erreur", "Quantité non valide.");
            return "redirect:/produit/perso/"+idProduit+"?erreur=erreurQuantite"; // Remplacez par la page de produit appropriée

        }

    	if (quantite < 1) {
    		redirectAttributes.addFlashAttribute("erreur", "Quantité non conforme");
            return "redirect:/produit/perso/"+idProduit +"?erreur=erreurQuantite"; // Remplacez par la page de produit appropriée
        }

    	User user = userRepository.findByUsernameOrEmail(authentication.getName(), authentication.getName());
        CategoriePlace categoriePlace = categoriePlaceRepository.findById(idCategoriePlace)
                .orElseThrow(() -> new RuntimeException("CategoriePlace non trouvée"));

        // Vérifier si la quantité demandée est disponible
        if (categoriePlace.getStock() < quantite) {
        	redirectAttributes.addFlashAttribute("erreur", "Stock insuffisant");
            return "redirect:/produit/perso/"+idProduit + "?erreur=erreurStock"; // Remplacez par la page de produit appropriée
        }

        // Mettre à jour le stock
        categoriePlace.setStock(categoriePlace.getStock() - quantite);
        
        categoriePlaceRepository.save(categoriePlace);

        DetailCommande detailCommande = new DetailCommande();
        detailCommande.setCategoriePlace(categoriePlace);
        detailCommande.setQuantite(quantite);
        Panier panier = panierRepository.findByUserId(user.getId());
        if (panier == null) {
            panier = new Panier();
            panier.setUser(user);
            panier.setDetailCommande(new ArrayList<>());
            
        }
        
        panier.getDetailCommande().add(detailCommande);
        detailCommande.setPanier(panier);
        panierRepository.save(panier);
        
        detailCommandeRepository.save(detailCommande);
        System.out.println(user.getId());
    	redirectAttributes.addFlashAttribute("successMessage", "Produit ajouté au panier");
        return "redirect:/produit/perso/"+idProduit;
    }


    @PostMapping("/enregistrerPanier")

    public String enregistrerPanier(Authentication authentication,RedirectAttributes redirectAttributes) {
    	User user = userRepository.findByUsernameOrEmail(authentication.getName(), authentication.getName());

        Panier panier = panierRepository.findByUserId(user.getId());

        if (panier == null || panier.getDetailCommande() == null || panier.getDetailCommande().isEmpty()) {
        	redirectAttributes.addFlashAttribute("erreurMessage", "Panier vide");
            return "redirect:/monPanier";
        }

        Commande commande = new Commande();
        commande.setStatus(1);
        commande.setUser(user);
        commande.setDetailCommande(new ArrayList<>(panier.getDetailCommande()));

        commandeRepository.save(commande);

        for (DetailCommande detailCommande : commande.getDetailCommande()) {
            detailCommande.setCommande(commande);
            detailCommande.setPanier(null);
            detailCommandeRepository.save(detailCommande);
        }

        panierRepository.delete(panier);

    	redirectAttributes.addFlashAttribute("successMessage", "Commande validée");
        return "redirect:/monPanier";
    }

    @GetMapping("/monPanier")
    public String monPanier(Model model, Authentication authentication) {
        User user = userRepository.findByUsernameOrEmail(authentication.getName(), authentication.getName());
        Panier panier = panierRepository.findByUserId(user.getId());
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
            List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
    		model.addAttribute("categories", categorie);
    		List<Produit> produits = (List<Produit>) produitRepository.findAll();
    	    model.addAttribute("produits", produits);
        }

        if (panier != null && panier.getDetailCommande() != null) {
            List<Map<String, Object>> detailPanierList = new ArrayList<>();
            
            for (DetailCommande detailCommande : panier.getDetailCommande()) {
                Map<String, Object> detailPanier = new HashMap<>();
                detailPanier.put("quantite", detailCommande.getQuantite());
                detailPanier.put("id", detailCommande.getId());
                detailPanier.put("categoriePlace", categoriePlaceRepository.findById(detailCommande.getCategoriePlace().getId()).orElse(null));
                DetailProduit detailProduit = detailProduitRepository.findById(detailCommande.getCategoriePlace().getDetailProduit().getId()).orElse(null);
                detailPanier.put("detailProduit", detailProduit);
                detailPanier.put("produit", produitRepository.findById(detailProduit.getProduit().getId()).orElse(null));
                
                detailPanierList.add(detailPanier);
            }
            
            model.addAttribute("detailPanierList", detailPanierList);
        }
        List<Commande> commandes = commandeRepository.findByUser(user);
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("commandes", commandes);
        model.addAttribute("statusList", Arrays.asList(1, 2, 3, 4));
        
        model.addAttribute("panier", panier);
        return "monPanier";
    }
    @PostMapping("/supprimerPanier")
    public String supprimerPanier(Authentication authentication) {
    	User user = userRepository.findByUsernameOrEmail(authentication.getName(), authentication.getName());
        Panier panier = panierRepository.findByUserId(user.getId());

        if (panier != null && panier.getDetailCommande() != null) {
            for (DetailCommande detail : panier.getDetailCommande()) {
                CategoriePlace categoriePlace = detail.getCategoriePlace();
                categoriePlace.setStock(categoriePlace.getStock() + detail.getQuantite());
                categoriePlaceRepository.save(categoriePlace);
                detailCommandeRepository.delete(detail);
            }
            panierRepository.delete(panier);
        }

        return "redirect:/monPanier";  
    }
    @Transactional
    @PostMapping("/supprimerElementDuPanier")
    public String supprimerElementDuPanier(@RequestParam(name = "detailCommandeId") Long detailCommandeId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(authentication.getName());
        Panier panier = panierRepository.findByUserId(user.getId());

        if (panier != null && panier.getDetailCommande() != null) {
            // Trouver le DetailCommande à supprimer
            DetailCommande detailToRemove = panier.getDetailCommande().stream()
                    .filter(detail -> detail.getId().equals(detailCommandeId))
                    .findFirst()
                    .orElse(null);

            if (detailToRemove != null) {
                // Mettre à jour le stock
                CategoriePlace categoriePlace = detailToRemove.getCategoriePlace();
                categoriePlace.setStock(categoriePlace.getStock() + detailToRemove.getQuantite());
                categoriePlaceRepository.save(categoriePlace);

                // Supprimer le DetailCommande du panier et de la base de données
                panier.getDetailCommande().remove(detailToRemove);
                detailCommandeRepository.delete(detailToRemove);
                panierRepository.save(panier);  // Enregistrer le panier mis à jour
            } else {
                redirectAttributes.addFlashAttribute("erreur", "L'élément à supprimer n'a pas été trouvé dans le panier.");
            }
        } else {
            redirectAttributes.addFlashAttribute("erreur", "Le panier est vide ou n'existe pas.");
        }

        return "redirect:/monPanier";  // Redirection vers la page du panier
    }



}
