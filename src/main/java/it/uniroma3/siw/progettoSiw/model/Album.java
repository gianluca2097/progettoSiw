package it.uniroma3.siw.progettoSiw.model;



import javax.persistence.GenerationType;
import java.util.*;
import javax.persistence.*;

@Entity
public class Album {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titolo;

	@ManyToOne
	private Fotografo fotografo;
	
	@OneToMany(mappedBy = "album")
	private Map<Long,Fotografia> fotografie;
	
	public Album() {
		this.fotografie = new HashMap<Long,Fotografia>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Fotografo getFotografo() {
		return fotografo;
	}

	public void setFotografo(Fotografo fotografo) {
		this.fotografo = fotografo;
	}
	public void addFotografia(Fotografia fotografia) {
		this.fotografie.put(fotografia.getId(), fotografia);
	}

	public Map<Long, Fotografia> getFotografie() {
		return fotografie;
	}

	public void setFotografie(Map<Long, Fotografia> fotografie) {
		this.fotografie = fotografie;
	}

}
