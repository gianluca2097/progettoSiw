package it.uniroma3.siw.progettoSiw.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettoSiw.model.Richiesta;
import it.uniroma3.siw.progettoSiw.model.Album;
import it.uniroma3.siw.progettoSiw.model.Fotografia;


public interface FotografiaRepository extends CrudRepository<Fotografia, Long>{

	public Fotografia findByTitolo(String Titolo);
	public List<Fotografia> findByAlbum(Album a);
	public List<Fotografia> findByRichieste(Richiesta r);
}


