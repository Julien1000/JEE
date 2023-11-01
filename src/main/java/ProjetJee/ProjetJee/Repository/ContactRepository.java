package ProjetJee.ProjetJee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ProjetJee.ProjetJee.Entity.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long>{

}
