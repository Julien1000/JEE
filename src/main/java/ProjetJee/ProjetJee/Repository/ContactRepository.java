package ProjetJee.ProjetJee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Contact;
@Repository
public interface ContactRepository extends JpaRepository<Contact,Long>{

}
