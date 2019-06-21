package it.uniroma3.siw.progettoSiw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.progettoSiw.model.Funzionario;
import it.uniroma3.siw.progettoSiw.repository.FunzionarioRepository;

@Component
public class DBPopulation implements ApplicationRunner {
	
	@Autowired
	private FunzionarioRepository funzionarioRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.deleteAll();
		this.addAll();
	}
	
	private void deleteAll() {
		funzionarioRepository.deleteAll();
	}
	
	//aggiunge un funzionario e lo setta come admin, al momento è l'unico presente nell'azienda 
	private void addAll() {		
		 Funzionario admin = new Funzionario(1L, "Mario", "Rossi", "mariorossi", null, "ADMIN");
	        String adminPassword = new BCryptPasswordEncoder().encode("mrpass");
	        admin.setPassword(adminPassword);
	        admin = this.funzionarioRepository.save(admin);		
	}

}
