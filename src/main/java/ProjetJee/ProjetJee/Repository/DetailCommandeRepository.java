package ProjetJee.ProjetJee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Entity.DetailCommande;

@Repository
public interface DetailCommandeRepository extends JpaRepository<DetailCommande, Long> {
    List<DetailCommande> findByCategoriePlace(CategoriePlace categoriePlace);
}
