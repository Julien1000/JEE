package ProjetJee.ProjetJee.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ProjetJee.ProjetJee.Entity.Categorie;
import ProjetJee.ProjetJee.Entity.Contact;
import ProjetJee.ProjetJee.Entity.Produit;
import ProjetJee.ProjetJee.Repository.CategorieRepository;
import ProjetJee.ProjetJee.Repository.ContactRepository;
import ProjetJee.ProjetJee.Repository.ProduitRepository;


@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private ProduitRepository produitRepository;

    @GetMapping("/contact")
    public String showContactForm(Model model, Authentication authentication) {
        model.addAttribute("contact", new Contact());
        boolean isAdmin = false;
	    boolean isUserLoggedIn = false;
        // Vérifier si l'utilisateur est authentifié
        if (authentication != null && authentication.isAuthenticated()) {
            // Ajouter le nom de l'utilisateur au modèle
            model.addAttribute("currentUser", authentication.getName());
            isUserLoggedIn = true;
            
            // Vérifier si l'utilisateur a le rôle "ROLE_ADMIN"
            isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
            List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
    		model.addAttribute("categories", categorie);
    		List<Produit> produits = (List<Produit>) produitRepository.findAll();
    	    model.addAttribute("produits", produits);
        }

        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        return "formulaireDeContact";
    }

    @PostMapping("/submit-contact")
    public String submitContactForm(Contact contact, RedirectAttributes redirectAttributes) {
        try {
            contactRepository.save(contact);
            // Message de succès en cas de soumission réussie
            redirectAttributes.addFlashAttribute("successMessage", "Formulaire de contact envoyé avec succès !");
        } catch (Exception e) {
            // Message d'erreur en cas d'échec de la soumission
            redirectAttributes.addFlashAttribute("errorMessage", "Une erreur s'est produite lors de l'envoi du formulaire.");
        }
        return "redirect:/contact"; // Redirige vers la page du formulaire de contact
    }
    
    @GetMapping("/contactList")
    public String showContactList(Model model, Authentication authentication) {
        Iterable<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        boolean isAdmin = false;
	    boolean isUserLoggedIn = false;
        // Vérifier si l'utilisateur est authentifié
        if (authentication != null && authentication.isAuthenticated()) {
            // Ajouter le nom de l'utilisateur au modèle
            model.addAttribute("currentUser", authentication.getName());
            isUserLoggedIn = true;
            
            // Vérifier si l'utilisateur a le rôle "ROLE_ADMIN"
            isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
            List<Categorie> categorie = (List<Categorie>) categorieRepository.findAll();
    		model.addAttribute("categories", categorie);
    		List<Produit> produits = (List<Produit>) produitRepository.findAll();
    	    model.addAttribute("produits", produits);
        }

        // Ajouter la variable isAdmin au modèle
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
        return "contactList";
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
        return "redirect:/contactList";
    }
}
