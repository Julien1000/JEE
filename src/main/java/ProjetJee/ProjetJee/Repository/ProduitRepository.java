package ProjetJee.ProjetJee.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import ProjetJee.ProjetJee.Entity.Produit;

public interface ProduitRepository extends CrudRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long idCategorie);
    Produit findByName(String name);
    List<Produit> findTop5ByNameContaining(String name);
}

