package ProjetJee.ProjetJee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Entity.DetailCommande;
import ProjetJee.ProjetJee.Entity.DetailProduit;
import ProjetJee.ProjetJee.Repository.CategoriePlaceRepository;
import ProjetJee.ProjetJee.Repository.DetailCommandeRepository;
import ProjetJee.ProjetJee.Repository.DetailProduitRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final DetailProduitRepository detailProduitRepository;
    private final CategoriePlaceRepository categoriePlaceRepository;
    private final DetailCommandeRepository detailCommandeRepository;

    @Autowired
    public ProduitService(ProduitRepository produitRepository,
                          DetailProduitRepository detailProduitRepository,
                          CategoriePlaceRepository categoriePlaceRepository,
                          DetailCommandeRepository detailCommandeRepository) {
        this.produitRepository = produitRepository;
        this.detailProduitRepository = detailProduitRepository;
        this.categoriePlaceRepository = categoriePlaceRepository;
        this.detailCommandeRepository = detailCommandeRepository;
    }

    @Transactional
    public void deleteProduitAndRelatedEntities(Long produitId) {
        // Trouver tous les DetailProduit liés à ce produit
        List<DetailProduit> detailProduits = detailProduitRepository.findByProduitId(produitId);

        // Pour chaque DetailProduit, supprimer les CategoriePlace et les DetailCommande liées
        detailProduits.forEach(detailProduit -> {
            List<CategoriePlace> categoriePlaces = categoriePlaceRepository.findByDetailProduit(detailProduit);
            categoriePlaces.forEach(categoriePlace -> {
                List<DetailCommande> detailCommandes = detailCommandeRepository.findByCategoriePlace(categoriePlace);
                detailCommandeRepository.deleteAll(detailCommandes);
            });
            categoriePlaceRepository.deleteAll(categoriePlaces);
        });

        // Une fois les CategoriePlace et DetailCommande supprimées, nous pouvons supprimer les DetailProduit
        detailProduitRepository.deleteAll(detailProduits);

        // Et enfin, supprimer le produit lui-même
        produitRepository.deleteById(produitId);
    }
}