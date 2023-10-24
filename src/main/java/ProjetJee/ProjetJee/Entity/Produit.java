package ProjetJee.ProjetJee.Entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double prix;
	@Lob
	private byte[] image;
	private int stock;
	private String numeroPlace;
	@ManyToOne
    @JoinColumn(name = "idCategories")
    private Categorie categorie;
	public Categorie getCategorie() {
        return categorie;
    }
	public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
	public Long getId() {
	    return id;
	}
	


	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public void setId(Long id) {
	    this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getNumeroPlace() {
		return numeroPlace;
	}
	public void setNumeroPlace(String numeroPlace) {
		this.numeroPlace = numeroPlace;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categorie, id, name, numeroPlace, prix, stock);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		return Objects.equals(categorie, other.categorie) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(numeroPlace, other.numeroPlace)
				&& Double.doubleToLongBits(prix) == Double.doubleToLongBits(other.prix) && stock == other.stock;
	}
	
	
}
