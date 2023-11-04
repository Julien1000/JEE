package ProjetJee.ProjetJee.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class CategoriePlace {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private long prix;
	@NotNull
	private int stock;
	@NotNull
	private String nomCategoriePlace;
	
	@ManyToOne
	@JoinColumn(name = "idDetailProduit")
	private DetailProduit detailProduit;

	public Long getId() {
		return id;
	}

	public long getPrix() {
		return prix;
	}

	public void setPrix(long prix) {
		this.prix = prix;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNomCategoriePlace() {
		return nomCategoriePlace;
	}

	public void setNomCategoriePlace(String nomCategoriePlace) {
		this.nomCategoriePlace = nomCategoriePlace;
	}

	public DetailProduit getDetailProduit() {
		return detailProduit;
	}

	public void setDetailProduit(DetailProduit detailProduit) {
		this.detailProduit = detailProduit;
	}
	
	
	
	
}
