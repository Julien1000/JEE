package ProjetJee.ProjetJee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.DetailCommande;

@Repository
public interface DetailCommandeRepository extends JpaRepository<DetailCommande, Long> {

}
