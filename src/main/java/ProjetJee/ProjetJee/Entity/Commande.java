package ProjetJee.ProjetJee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;
    @ManyToOne
    @JoinColumn(name="id_user")
	@NotNull
    private User user;

	@NotNull
    private int status;
    @OneToMany(mappedBy = "commande")
    private List<DetailCommande> detailCommande;



    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getIdCommande() {
        return idCommande;
    }
	

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    

    public List<DetailCommande> getDetailCommande() {
        return detailCommande;
    }

    public void setDetailCommande(List<DetailCommande> detailCommande) {
        this.detailCommande = detailCommande;
    }
}