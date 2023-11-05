package ProjetJee.ProjetJee.Entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String name;
	
	private String description;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	
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
	
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categorie, id, name);
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
				&& Objects.equals(name, other.name);
	}
	
	
}
