package ProjetJee.ProjetJee.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.DetailProduit;
import ProjetJee.ProjetJee.Entity.Produit;

@Repository
public interface DetailProduitRepository extends CrudRepository<DetailProduit, Long> {
    DetailProduit findByProduit(Produit produit);
    List<DetailProduit> findByProduitId(long idProduit);

}
