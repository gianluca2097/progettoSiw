package it.uniroma3.siw.progettoSiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettoSiw.model.Fotografo;

public interface FotografoRepository extends CrudRepository<Fotografo, Long>{

	public List<Fotografo> findByNome(String nome);
	public List<Fotografo> findByNomeAndCognome(String nome, String cognome);
}
