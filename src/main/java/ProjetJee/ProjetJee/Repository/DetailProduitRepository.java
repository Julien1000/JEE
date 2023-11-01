package ProjetJee.ProjetJee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ProjetJee.ProjetJee.Entity.DetailProduit;


public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long>  {
List<DetailProduit> findByProduitId(long idProduit);
}
