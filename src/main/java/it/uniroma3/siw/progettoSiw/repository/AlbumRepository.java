package it.uniroma3.siw.progettoSiw.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettoSiw.model.Album;
import it.uniroma3.siw.progettoSiw.model.Fotografo;


public interface AlbumRepository extends CrudRepository<Album, Long>{

	public Album findByTitolo(String titolo);

	public List<Album> findByFotografo(Fotografo f);
	
	
}

