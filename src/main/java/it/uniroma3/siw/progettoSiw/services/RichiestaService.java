package it.uniroma3.siw.progettoSiw.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.progettoSiw.model.Fotografia;
import it.uniroma3.siw.progettoSiw.model.Richiesta;
import it.uniroma3.siw.progettoSiw.repository.RichiestaRepository;

@Service
public class RichiestaService {
	@Autowired
	private RichiestaRepository richiestaRepository;
	@Transactional
	public List<Richiesta> getTutte(){
		return (List<Richiesta>)this.richiestaRepository.findAll();
	}
	@Transactional
	public Richiesta inserisciRichiesta(Richiesta r) {
		return this.richiestaRepository.save(r);
	}
	@Transactional
	public void addFotografia(Richiesta r, Fotografia f) {
		r.getFotografie().add(f);
	}
	@Transactional
	public List<Fotografia> getFotografie(Richiesta r){
		return r.getFotografie();
	}
	@Transactional
	public Richiesta getPerId(Long id) {
		return this.richiestaRepository.findById(id).get();
	}
	@Transactional
	public void deleteRichiesta(Richiesta r) {
		this.richiestaRepository.delete(r);
	}


}
