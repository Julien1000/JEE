package ProjetJee.ProjetJee.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}