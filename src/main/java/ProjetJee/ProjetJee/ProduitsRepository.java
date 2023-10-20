package ProjetJee.ProjetJee;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProduitsRepository extends CrudRepository<Produits, Long> {
	List<Produits> findByCategorieId(Long idCategorie);

}
