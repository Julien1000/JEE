package ProjetJee.ProjetJee.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ProjetJee.ProjetJee.Repository.ProduitRepository;
import ProjetJee.ProjetJee.Entity.Produit;

@RestController
@RequestMapping("/api/produits")
public class SearchController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Produit>> searchProduits(@RequestParam String query) {
        if (query != null && query.length() >= 3) {
            List<Produit> produits = produitRepository.findTop5ByNameContaining(query);
            return ResponseEntity.ok(produits);
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
