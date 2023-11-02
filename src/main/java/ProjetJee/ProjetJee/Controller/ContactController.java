package ProjetJee.ProjetJee.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ProjetJee.ProjetJee.Entity.Contact;
import ProjetJee.ProjetJee.Repository.ContactRepository;


@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
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
    public String showContactList(Model model) {
        Iterable<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contactList";
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
        return "redirect:/contactList";
    }
}
