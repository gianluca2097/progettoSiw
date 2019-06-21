package it.uniroma3.siw.progettoSiw.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.progettoSiw.model.Album;
import it.uniroma3.siw.progettoSiw.model.Fotografo;
import it.uniroma3.siw.progettoSiw.repository.FotografoRepository;


@Service
public class FotografoService {
	
	@Autowired 
	private FotografoRepository fotografoRepository;
	
	@Autowired
	private AlbumService albumService;
	
	
	@Transactional 
	public Fotografo inserisci(Fotografo fotografo) {
		return fotografoRepository.save(fotografo);
	}
	
	@Transactional
	public List<Fotografo> tutti(){
		return (List<Fotografo>) fotografoRepository.findAll();
	}

	public Fotografo fotografoPerId(Long id) {
		return this.fotografoRepository.findById(id).get();
	}
	
	@Transactional
	public void addAlbum(Album a, Fotografo f) {
		this.albumService.addAlbum(a);
		
	}
	
	@Transactional
	public List<Fotografo> getPerNome(String nome){
		return this.fotografoRepository.findByNome(nome);
	}
	@Transactional
	public List<Fotografo> getPerNomeAndCognome(String nome, String cognome){
		return this.fotografoRepository.findByNomeAndCognome(nome, cognome);
	}
	@Transactional
	public boolean existsPerId(Long id) {
		return this.fotografoRepository.existsById(id);
	}
	
	
	
}
