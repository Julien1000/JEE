package ProjetJee.ProjetJee.Repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.CategoriePlace;
import ProjetJee.ProjetJee.Entity.DetailProduit;

import java.util.List;

@Repository
public interface CategoriePlaceRepository extends CrudRepository<CategoriePlace, Long> {
    List<CategoriePlace> findByDetailProduit(DetailProduit detailProduit);
}
