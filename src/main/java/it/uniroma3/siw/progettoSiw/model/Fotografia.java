package it.uniroma3.siw.progettoSiw.model;


import java.util.List;

import javax.persistence.*;

@Entity
public class Fotografia {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titolo;
	
	@ManyToOne
	private Album album;
	
	@ManyToMany
	private List<Richiesta> richieste;
	
	
	public List<Richiesta> getRichieste() {
		return richieste;
	}
	public void setRichieste(List<Richiesta> richieste) {
		this.richieste = richieste;
	}
	public Fotografia() {
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
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
}

