package ProjetJee.ProjetJee.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ProjetJee.ProjetJee.Entity.Role;
import ProjetJee.ProjetJee.Entity.User;
import ProjetJee.ProjetJee.Repository.RoleRepository;
import ProjetJee.ProjetJee.Repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class Default {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void createInitialRole() {

		Role existe = roleRepository.findByName("ROLE_ADMIN");

		if (existe == null) {
			Role role = new Role();
			role.setName("ROLE_ADMIN");

			roleRepository.save(role);
		}

		Role existe2 = roleRepository.findByName("ROLE_USER");

		if (existe2 == null) {
			Role role2 = new Role();
			role2.setName("ROLE_USER");

			roleRepository.save(role2);
		}
	}

	@PostConstruct
	public void createInitialUser() {

		User existe = userRepository.findByUsernameOrEmail("admin", "admin@gmail.com");

		// L'utilisateur n'existe pas
		if (existe == null) {
			User utilisateur = new User();
			utilisateur.setName("admin");
			utilisateur.setUsername("admin");
			utilisateur.setEmail("admin@gmail.com");
			// Récupérer le Role depuis la base de données
			Role role = roleRepository.findByName("ROLE_ADMIN");

			// Affecter le rôle à l'utilisateur
			utilisateur.setRole(role);
			String motDePasse = "admin";
			String encodedPassword = passwordEncoder.encode(motDePasse);

			utilisateur.setPassword(encodedPassword);

			userRepository.save(utilisateur);
		}

		User existe2 = userRepository.findByUsernameOrEmail("test", "test@gmail.com");

		// L'utilisateur n'existe pas
		if (existe2 == null) {
			User utilisateur2 = new User();
			utilisateur2.setName("test");
			utilisateur2.setUsername("test");
			utilisateur2.setEmail("test@gmail.com");
			// Récupérer le Role depuis la base de données
			Role role2 = roleRepository.findByName("ROLE_USER");

			// Affecter le rôle à l'utilisateur
			utilisateur2.setRole(role2);
			String motDePasse = "test";
			String encodedPassword = passwordEncoder.encode(motDePasse);

			utilisateur2.setPassword(encodedPassword);

			userRepository.save(utilisateur2);
		}
	}
}
