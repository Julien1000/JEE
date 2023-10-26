package ProjetJee.ProjetJee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ProjetJee.ProjetJee.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
User findByUsernameOrEmail(String username, String email);
User findByUsername(String username);

}