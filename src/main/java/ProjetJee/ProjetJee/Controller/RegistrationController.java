package ProjetJee.ProjetJee.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProjetJee.ProjetJee.Repository.UserRepository;
import ProjetJee.ProjetJee.Repository.RoleRepository;
import ProjetJee.ProjetJee.Entity.Role;
import ProjetJee.ProjetJee.Entity.User;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String showRegistrationForm() {
        return "registerForm";
    }

    @PostMapping
    public String registerUser(Model model,@RequestParam String name, @RequestParam String username, @RequestParam String email, @RequestParam String password) {
        // Créer un nouveau User
    	
        User existingUser = userRepository.findByUsernameOrEmail(username, email);
        if (existingUser != null) {
            model.addAttribute("error", "Le nom d'utilisateur ou l'adresse email est déjà utilisé");
            return "registerForm";
        }
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

     // Récupérer le Role depuis la base de données
        Role role = roleRepository.findByName("ROLE_USER");

        // Affecter le rôle à l'utilisateur
        user.setRole(role);


        // Enregistrer l'utilisateur dans la base de données
        userRepository.save(user);

        // Rediriger l'utilisateur vers le login
        return "redirect:/login";
    }
}
