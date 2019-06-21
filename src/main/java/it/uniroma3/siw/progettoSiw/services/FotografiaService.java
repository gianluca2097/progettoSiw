package it.uniroma3.siw.progettoSiw.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progettoSiw.model.Album;
import it.uniroma3.siw.progettoSiw.model.Fotografia;
import it.uniroma3.siw.progettoSiw.model.Richiesta;
import it.uniroma3.siw.progettoSiw.repository.FotografiaRepository;

@Service
public class FotografiaService {
	@Autowired
	private FotografiaRepository fotografiaRepository;
	
	@Transactional
	public Fotografia addFotografia(Fotografia f) {
		return this.fotografiaRepository.save(f);
	}
	@Transactional
	public List<Fotografia> getTutti(){
		return (List<Fotografia>)this.fotografiaRepository.findAll();
	}
	@Transactional
	public Fotografia getPerId(Long id) {
		return this.fotografiaRepository.findById(id).get();
	}
	@Transactional
	public Fotografia getPerTitolo(String titolo){
		return this.fotografiaRepository.findByTitolo(titolo);
	}
	@Transactional
	public boolean existsPerId(Long id) {
		return this.fotografiaRepository.existsById(id);
	}
	@Transactional
	public List<Fotografia> getPerAlbum(Album a){
		return this.fotografiaRepository.findByAlbum(a);
	}
	@Transactional
	public void setAlbum(Album a,Fotografia f) {
		f.setAlbum(a);
	}
	@Transactional
	public List<Fotografia> getPerRichiesta(Richiesta r){
		return this.fotografiaRepository.findByRichieste(r);
	}
	
	@Transactional
	public void addRichiesta(Richiesta r,Fotografia f) {
	        f.getRichieste().add(r);
		
	}

}

