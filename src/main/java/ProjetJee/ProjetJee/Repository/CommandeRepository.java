package ProjetJee.ProjetJee.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ProjetJee.ProjetJee.Entity.Commande;
import ProjetJee.ProjetJee.Entity.User;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByStatus(int status);
    List<Commande> findByUser(User user);
}
