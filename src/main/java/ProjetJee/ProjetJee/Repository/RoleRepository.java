package ProjetJee.ProjetJee.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ProjetJee.ProjetJee.Entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}