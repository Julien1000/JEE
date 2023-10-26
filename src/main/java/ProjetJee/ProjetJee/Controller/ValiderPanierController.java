package ProjetJee.ProjetJee.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjetJee.ProjetJee.Entity.DetailCommande;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Entity.ValiderPanier;
import ProjetJee.ProjetJee.Repository.DetailCommandeRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Repository.ValiderPanierRepository;

@Controller
@RequestMapping("/validerPanier")
public class ValiderPanierController {

    @Autowired
    private ValiderPanierRepository validerPanierRepository;
    
    @Autowired 
    private ProduitRepository produitRepository;
    
    @Autowired
    private DetailCommandeRepository detailCommandeRepository;

    @GetMapping("/afficher")
    public String afficherCommandes(Model model) {
    	List<ValiderPanier> commandes = validerPanierRepository.findAll();
        for (ValiderPanier commande : commandes) {
            System.out.println("Commande ID: " + commande.getIdCommande());
            if (commande.getDetailCommande() != null) {
                for (DetailCommande detail : commande.getDetailCommande()) {
                    System.out.println("Produit: " + detail.getProduit().getName() + ", Quantité: " + detail.getQuantite());
                }
            } else {
                System.out.println("Pas de détails pour cette commande");
            }
        }
        model.addAttribute("commandes", commandes);
        return "afficherCommandes";
    }
    
    @GetMapping("/creer")
    public String creerFormulaire(Model model) {
        List<Produit> produits = (List<Produit>) produitRepository.findAll();
        model.addAttribute("produits", produits);
        return "creerCommande";
    }
    @PostMapping("/saveCommande")
    public String saveCommande(
        @RequestParam("idUtilisateur") Long idUtilisateur,
        @RequestParam("idProduits") List<Long> idProduits,
        @RequestParam("quantites") List<Integer> quantites
    ) {

        ValiderPanier commande = new ValiderPanier();
        commande.setIdUtilisateur(idUtilisateur);
        commande.setStatus(1); // ou une autre valeur selon votre logique métier
        validerPanierRepository.save(commande);

        List<DetailCommande> detailsCommande = new ArrayList<>();
        for (int i = 0; i < idProduits.size(); i++) {
            DetailCommande detailCommande = new DetailCommande();
            Produit produit = produitRepository.findById(idProduits.get(i))
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
            detailCommande.setProduit(produit);
            detailCommande.setQuantite(quantites.get(i));
            detailCommande.setValiderPanier(commande); // Définir la commande dans DetailCommande
            detailCommandeRepository.save(detailCommande);
            detailsCommande.add(detailCommande);
        }
        commande.setDetailCommande(detailsCommande);
        validerPanierRepository.save(commande);

        return "redirect:/validerPanier/afficher"; // ajustez selon vos besoins
    }
    @PostMapping("/changerStatut2/{idCommande}")
    public String changerStatut2(@PathVariable Long idCommande) {
        ValiderPanier commande = validerPanierRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(2);
        validerPanierRepository.save(commande);
        return "redirect:/validerPanier/afficher";
    }
    @PostMapping("/changerStatut3/{idCommande}")
    public String changerStatut3(@PathVariable Long idCommande) {
        ValiderPanier commande = validerPanierRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(3);
        validerPanierRepository.save(commande);
        return "redirect:/validerPanier/afficher";
    }
    @PostMapping("/changerStatut4/{idCommande}")
    public String changerStatut4(@PathVariable Long idCommande) {
        ValiderPanier commande = validerPanierRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(4);
        validerPanierRepository.save(commande);
        return "redirect:/validerPanier/afficher";
    }
    @GetMapping("/afficherStats")
    public String afficherStats(Model model) {
        List<ValiderPanier> commandes = validerPanierRepository.findAll();

        int nombreCommandesStatus1 = 0;
        int nombreCommandesStatus2 = 0;
        int nombreCommandesStatus3 = 0;
        Map<String, Integer> produitsParCategorie = new HashMap<>();

        for (ValiderPanier commande : commandes) {
            System.out.println("Commande ID: " + commande.getIdCommande());
            if (commande.getDetailCommande() != null) {
                for (DetailCommande detail : commande.getDetailCommande()) {
                    Produit produit = detail.getProduit();
                    System.out.println("Produit: " + produit.getName() + ", Quantité: " + detail.getQuantite());

                    // Compter les produits par catégorie
                    String nomCategorie = produit.getCategorie().getName();
                    produitsParCategorie.put(nomCategorie, produitsParCategorie.getOrDefault(nomCategorie, 0) + detail.getQuantite());
                }
            } else {
                System.out.println("Pas de détails pour cette commande");
            }

            // Compter le nombre de commandes pour chaque statut
            if (commande.getStatus() == 1) {
                nombreCommandesStatus1++;
            } else if (commande.getStatus() == 2) {
                nombreCommandesStatus2++;
            } else if (commande.getStatus() == 3) {
                nombreCommandesStatus3++;
            }
        }

        model.addAttribute("nombreCommandesStatus1", nombreCommandesStatus1);
        model.addAttribute("nombreCommandesStatus2", nombreCommandesStatus2);
        model.addAttribute("nombreCommandesStatus3", nombreCommandesStatus3);
        model.addAttribute("produitsParCategorie", produitsParCategorie);
        model.addAttribute("commandes", commandes);

        return "afficherStats";
    }



}