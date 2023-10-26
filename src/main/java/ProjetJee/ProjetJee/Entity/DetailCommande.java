package ProjetJee.ProjetJee.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	@JoinColumn(name = "id_commande") // Assurez-vous que le nom de la colonne est correct
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

	public Produit getProduit() {

		return this.produit;

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

	public void setCommande(Commande commande) {
		// TODO Auto-generated method stub
		this.commande = commande;
	}



}

