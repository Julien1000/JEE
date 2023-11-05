package ProjetJee.ProjetJee.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Produit;
@Repository
public interface ProduitRepository extends CrudRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long idCategorie);
    Produit findByName(String name);
    List<Produit> findTop5ByNameContaining(String name);
}

