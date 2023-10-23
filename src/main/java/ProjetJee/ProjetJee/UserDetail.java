package ProjetJee.ProjetJee;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetail implements UserDetailsService {
@Autowired    
UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        User user = userRepo.findByUsernameOrEmail(username, username);
          if(user==null){
                throw new UsernameNotFoundException("User not exists by Username");
               }
          Set<GrantedAuthority> authorities = new HashSet<>();

       // Récupérez le rôle unique
       Role role = user.getRole();

       // Ajoutez le rôle au set des autorisations
       authorities.add(new SimpleGrantedAuthority(role.getName()));

       // Retournez le set des autorisations

        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
    }
}