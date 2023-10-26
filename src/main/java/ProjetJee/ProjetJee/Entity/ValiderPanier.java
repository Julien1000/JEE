package ProjetJee.ProjetJee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ValiderPanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    private Long idUtilisateur;
    private int Status;
    @OneToMany(mappedBy = "validerPanier")
    private List<DetailCommande> detailCommande;

    public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public List<DetailCommande> getDetailCommande() {
        return detailCommande;
    }

    public void setDetailCommande(List<DetailCommande> detailCommande) {
        this.detailCommande = detailCommande;
    }
}