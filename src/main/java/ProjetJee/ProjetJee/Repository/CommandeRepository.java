	package ProjetJee.ProjetJee.Repository;
	
	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}
