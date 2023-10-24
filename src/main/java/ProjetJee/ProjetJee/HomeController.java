package ProjetJee.ProjetJee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String greeting() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String admin() {
        return "home";
    }
    
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }
    
    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}