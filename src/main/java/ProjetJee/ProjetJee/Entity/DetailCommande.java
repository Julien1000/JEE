package ProjetJee.ProjetJee.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;


import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class DetailCommande {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_CategoriePlace")
	@NotNull
	private CategoriePlace categoriePlace;
	@NotNull
	private int quantite;
	
	@ManyToOne
	@JoinColumn(name = "id_commande") 
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name = "id_panier", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Panier panier;


	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}


	public CategoriePlace getCategoriePlace() {
		return categoriePlace;
	}

	public void setCategoriePlace(CategoriePlace categoriePlace) {
		this.categoriePlace = categoriePlace;
	}

	public Commande getCommande() {
		return commande;
	}

	public int getQuantite() {

		return quantite;

	}

	public void setQuantite(int quantite) {

		this.quantite = quantite;

	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}



}

