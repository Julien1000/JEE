	package ProjetJee.ProjetJee.Repository;
	
	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.ValiderPanier;

@Repository
public interface ValiderPanierRepository extends JpaRepository<ValiderPanier, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}
