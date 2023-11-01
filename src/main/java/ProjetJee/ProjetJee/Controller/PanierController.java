package ProjetJee.ProjetJee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ProjetJee.ProjetJee.Entity.*;
import ProjetJee.ProjetJee.Repository.*;

import java.util.ArrayList;
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
    private DetailProduitRepository detailProduitRepository;
    
    @Autowired 
    private ProduitRepository produitRepository;

    @PostMapping("/ajouterPanier")
    public String ajouterElementAuPanier(Model model, @RequestParam("idCategoriePlace") Long idCategoriePlace,
                                         @RequestParam("quantite") int quantite, Authentication authentication) {
        if (quantite < 1) {
            model.addAttribute("erreurQuantite", "Quantité non conforme");
            return "redirect:/produit";  // Remplacez par la page de produit appropriée
        }

        User user = userRepository.findByUsername(authentication.getName());
        CategoriePlace categoriePlace = categoriePlaceRepository.findById(idCategoriePlace)
                .orElseThrow(() -> new RuntimeException("CategoriePlace non trouvée"));

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

        return "redirect:/produit";
    }

    @PostMapping("/enregistrerPanier")
    public String enregistrerPanier(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Panier panier = panierRepository.findByUserId(user.getId());

        if (panier == null || panier.getDetailCommande() == null || panier.getDetailCommande().isEmpty()) {
            System.out.println("Le panier est vide ou n'existe pas.");
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

        System.out.println("Commande enregistrée avec succès et panier vidé.");

        return "redirect:/index";
    }

    @GetMapping("/monPanier")
    public String monPanier(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Panier panier = panierRepository.findByUserId(user.getId());

        if (panier != null && panier.getDetailCommande() != null) {
            List<Map<String, Object>> detailPanierList = new ArrayList<>();
            
            for (DetailCommande detailCommande : panier.getDetailCommande()) {
                Map<String, Object> detailPanier = new HashMap<>();
                detailPanier.put("quantite", detailCommande.getQuantite());
                detailPanier.put("categoriePlace", categoriePlaceRepository.findById(detailCommande.getCategoriePlace().getId()).orElse(null));
                DetailProduit detailProduit = detailProduitRepository.findById(detailCommande.getCategoriePlace().getDetailProduit().getId()).orElse(null);
                detailPanier.put("detailProduit", detailProduit);
                detailPanier.put("produit", produitRepository.findById(detailProduit.getProduit().getId()).orElse(null));
                
                detailPanierList.add(detailPanier);
            }
            
            model.addAttribute("detailPanierList", detailPanierList);
        }
        
        model.addAttribute("panier", panier);
        return "monPanier";
    }

}
