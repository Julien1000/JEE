package ProjetJee.ProjetJee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    Panier findByUserId(Integer integer); 
}
