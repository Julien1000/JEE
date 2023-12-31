package ProjetJee.ProjetJee.Controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.Commande;
import ProjetJee.ProjetJee.Entity.DetailCommande;
import ProjetJee.ProjetJee.Entity.Produit;

import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Repository.CommandeRepository;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired 
    private ProduitRepository produitRepository;
    
    
    @Autowired 
    private CategorieRepository categorieRepository;

    @GetMapping("/afficher")
    public String afficherCommandes(Model model,Authentication authentication) {
        List<Commande> commandes = commandeRepository.findAll();
        model.addAttribute("commandes", commandes);
        model.addAttribute("statusList", Arrays.asList(1, 2, 3, 4));
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
        List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
		model.addAttribute("categories", categories);
        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);

        return "afficherCommandes";
    }
    
    @GetMapping("/creer")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String creerFormulaire(Model model) {
        List<Produit> produits = (List<Produit>) produitRepository.findAll();
        model.addAttribute("produits", produits);
        return "creerCommande";
    }

    @PostMapping("/changerStatut2/{idCommande}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changerStatut2(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(2);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut3/{idCommande}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changerStatut3(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(3);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut4/{idCommande}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changerStatut4(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(4);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut5/{idCommande}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String changerStatut5(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(5);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut6")
    public String remettreCommandesAuStatut4() {
        List<Commande> commandes = commandeRepository.findByStatus(5);
        for (Commande commande : commandes) {
            commande.setStatus(4);
            commandeRepository.save(commande);
        }
        return "redirect:/commande/afficher";
    }
    
    @GetMapping("/afficherStats")
    public String afficherStats(Model model, Authentication authentication) {
        List<Commande> commandes = commandeRepository.findAll();

        int nombreCommandesStatus1 = 0;
        int nombreCommandesStatus2 = 0;
        int nombreCommandesStatus3 = 0;
        Map<String, Integer> produitsParCategorie = new HashMap<>();
        Map<Produit, Integer> produitsVendus = new HashMap<>();

        for (Commande commande : commandes) {
            if (commande.getDetailCommande() != null) {
                for (DetailCommande detail : commande.getDetailCommande()) {
                    Produit produit = detail.getCategoriePlace().getDetailProduit().getProduit();
                    int quantite = detail.getQuantite();

                    // Compter les produits par catégorie
                    String nomCategorie = produit.getCategorie().getName();
                    produitsParCategorie.put(nomCategorie, produitsParCategorie.getOrDefault(nomCategorie, 0) + quantite);

                    // Compter les produits vendus
                    produitsVendus.put(produit, produitsVendus.getOrDefault(produit, 0) + quantite);
                }
            }

            // Compter le nombre de commandes pour chaque statut
            int status = commande.getStatus();
            if (status == 1) {
                nombreCommandesStatus1++;
            } else if (status == 2) {
                nombreCommandesStatus2++;
            } else if (status == 3) {
                nombreCommandesStatus3++;
            }
        }

        // Trier les produits vendus par quantité, en ordre décroissant, et prendre le top 5
        List<Map.Entry<Produit, Integer>> top5ProduitsList = produitsVendus.entrySet().stream()
                .sorted(Map.Entry.<Produit, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

        // Convertir les résultats en deux listes pour les noms et les quantités
        List<String> top5ProduitsNoms = top5ProduitsList.stream()
                .map(entry -> entry.getKey().getName())
                .collect(Collectors.toList());

        List<Integer> top5ProduitsQuantites = top5ProduitsList.stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        // Ajouter toutes les données nécessaires au modèle
        model.addAttribute("nombreCommandesStatus1", nombreCommandesStatus1);
        model.addAttribute("nombreCommandesStatus2", nombreCommandesStatus2);
        model.addAttribute("nombreCommandesStatus3", nombreCommandesStatus3);
        model.addAttribute("produitsParCategorie", produitsParCategorie);
        model.addAttribute("commandes", commandes);
        model.addAttribute("totalProduitsVendus", produitsVendus.values().stream().mapToInt(Integer::intValue).sum());
        model.addAttribute("top5ProduitsNoms", top5ProduitsNoms);
        model.addAttribute("top5ProduitsQuantites", top5ProduitsQuantites);
        
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
        List<Categorie> categories = (List<Categorie>) categorieRepository.findAll();
		    model.addAttribute("categories", categories);
        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);

        return "afficherStats";
    }




}