package ProjetJee.ProjetJee.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanier;

    @OneToOne
    @JoinColumn(name = "id_User")
    private User user;
    
	
    @OneToMany(mappedBy = "panier")
    private List<DetailCommande> detailCommande;

	public Long getIdPanier() {
		return idPanier;
	}

	public void setIdPanier(Long idPanier) {
		this.idPanier = idPanier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<DetailCommande> getDetailCommande() {
		return detailCommande;
	}

	public void setDetailCommande(List<DetailCommande> detailCommande) {
		this.detailCommande = detailCommande;
	}
    


}
