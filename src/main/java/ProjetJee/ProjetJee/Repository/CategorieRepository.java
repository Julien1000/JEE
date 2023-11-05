package ProjetJee.ProjetJee.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Categorie;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {}

