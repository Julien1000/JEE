package ProjetJee.ProjetJee.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjetJee.ProjetJee.Entity.Commande;
import ProjetJee.ProjetJee.Entity.DetailCommande;
import ProjetJee.ProjetJee.Entity.Produit;

import ProjetJee.ProjetJee.Repository.DetailCommandeRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Repository.CommandeRepository;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired 
    private ProduitRepository produitRepository;
    
    @Autowired
    private DetailCommandeRepository detailCommandeRepository;

    @GetMapping("/afficher")
    public String afficherCommandes(Model model) {
        List<Commande> commandes = commandeRepository.findAll();
        model.addAttribute("commandes", commandes);
        model.addAttribute("statusList", Arrays.asList(1, 2, 3, 4));

        return "afficherCommandes";
    }
    
    @GetMapping("/creer")
    public String creerFormulaire(Model model) {
        List<Produit> produits = (List<Produit>) produitRepository.findAll();
        model.addAttribute("produits", produits);
        return "creerCommande";
    }
//    @PostMapping("/saveCommande")
//    public String saveCommande(
//        @RequestParam("idUtilisateur") Long idUtilisateur,
//        @RequestParam("idProduits") List<Long> idProduits,
//        @RequestParam("quantites") List<Integer> quantites
//    ) {
//
//        Commande commande = new Commande();
//        commande.setIdUtilisateur(idUtilisateur);
//        commande.setStatus(1); // ou une autre valeur selon votre logique métier
//        commandeRepository.save(commande);
//
//        List<DetailCommande> detailsCommande = new ArrayList<>();
//        for (int i = 0; i < idProduits.size(); i++) {
//            DetailCommande detailCommande = new DetailCommande();
//            Produit produit = produitRepository.findById(idProduits.get(i))
//                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
//            detailCommande.setProduit(produit);
//            detailCommande.setQuantite(quantites.get(i));
//            detailCommande.setValiderPanier(commande); // Définir la commande dans DetailCommande
//            detailCommandeRepository.save(detailCommande);
//            detailsCommande.add(detailCommande);
//        }
//        commande.setDetailCommande(detailsCommande);
//        commandeRepository.save(commande);
//
//        return "redirect:/validerPanier/afficher"; // ajustez selon vos besoins
//    }
    @PostMapping("/changerStatut2/{idCommande}")
    public String changerStatut2(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(2);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut3/{idCommande}")
    public String changerStatut3(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(3);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut4/{idCommande}")
    public String changerStatut4(@PathVariable Long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setStatus(4);
        commandeRepository.save(commande);
        return "redirect:/commande/afficher";
    }
    @PostMapping("/changerStatut5/{idCommande}")
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
    public String afficherStats(Model model) {
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

        return "afficherStats";
    }




}