package it.uniroma3.siw.progettoSiw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.progettoSiw.model.Funzionario;

//avremo a disposizione tutti i metodi crud
public interface FunzionarioRepository extends CrudRepository<Funzionario, Long>{
	
}
