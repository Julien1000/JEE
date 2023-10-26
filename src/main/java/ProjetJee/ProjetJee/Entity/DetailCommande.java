package ProjetJee.ProjetJee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DetailCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_Produit")
    private Produit produit;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "id_commande")
    private ValiderPanier validerPanier;

    // getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public ValiderPanier getValiderPanier() {
        return validerPanier;
    }

    public void setValiderPanier(ValiderPanier validerPanier) {
        this.validerPanier = validerPanier;
    }
}
