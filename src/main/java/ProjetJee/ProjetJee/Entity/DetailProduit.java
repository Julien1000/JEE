	package ProjetJee.ProjetJee.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

public class DetailProduit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String adresse;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "idProduit")
	private Produit produit;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] imageLieu;
	

	public Long getId() {
		return id;
	}


	public Produit getProduit() {
		return produit;
	}


	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte[] getImageLieu() {
		return imageLieu;
	}

	public void setImageLieu(byte[] imageLieu) {
		this.imageLieu = imageLieu;
	}

	
	

}
