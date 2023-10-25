package ProjetJee.ProjetJee.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ProjetJee.ProjetJee.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}