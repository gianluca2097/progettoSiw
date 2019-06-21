package it.uniroma3.siw.progettoSiw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.progettoSiw.model.Richiesta;
import it.uniroma3.siw.progettoSiw.model.Album;
import it.uniroma3.siw.progettoSiw.model.Fotografia;
import it.uniroma3.siw.progettoSiw.model.Fotografo;
import it.uniroma3.siw.progettoSiw.services.AlbumService;
import it.uniroma3.siw.progettoSiw.services.AlbumValidator;
import it.uniroma3.siw.progettoSiw.services.FotografiaService;
import it.uniroma3.siw.progettoSiw.services.FotografiaValidator;
import it.uniroma3.siw.progettoSiw.services.FotografoService;
import it.uniroma3.siw.progettoSiw.services.FotografoValidator;
import it.uniroma3.siw.progettoSiw.services.RichiestaService;


@Controller
public class MainController {

	@Autowired
	private FotografoService fotografoService;
	
	@Autowired
	private FotografiaService fotografiaService;

	@Autowired
	private FotografoValidator fotografoValidator;
	
	@Autowired
	private FotografiaValidator fotografiaValidator;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private AlbumValidator albumValidator;
	
	@Autowired
	private RichiestaService richiestaService;
	

	
	//Gestione funzionario(AreaRiservata, FotografoRiservato, albumForm, Album, fotografiaForm) 
	
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String admin(Model model) {
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String role = details.getAuthorities().iterator().next().getAuthority(); // get first authority
		model.addAttribute("username", details.getUsername());
		model.addAttribute("role", role);
		return "admin";
	}
	

	@RequestMapping("/AreaRiservata")
	public String addFotografo(Model model) {
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", details.getUsername());
		model.addAttribute("fotografo", new Fotografo());
		model.addAttribute("fotografi", this.fotografoService.tutti());
		return "AreaRiservata.html";
	}
	

	@RequestMapping(value = "/AreaRiservata", method = RequestMethod.POST)
	public String newFotografo(@Valid @ModelAttribute("fotografo") Fotografo fotografo, Model model,
			BindingResult bindingResult) {
		//aggiunge l'utente sempre in sessione quando viene inserito un fotografo
		UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", details.getUsername());
		this.fotografoValidator.validate(fotografo, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.fotografoService.inserisci(fotografo);
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "AreaRiservata.html";
		} else {
			//ritorna alla stessa pagina perchè la lista dei fotografi e la form sono sulla stessa pagina
			return "AreaRiservata.html";
		}
	}
	

	@RequestMapping(value = "/FotografoRiservato/{id}", method = RequestMethod.GET)
	public String getFotografoRis(@PathVariable("id") Long id, Model model) {
		if (this.fotografoService.existsPerId(id)) {
			Fotografo f = this.fotografoService.fotografoPerId(id);
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			//ritorna la pagina dedicata al funzionario con i dati del fotografo e i propri album 
			return "FotografoRiservato.html";
		} else {
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "AreaRiservata";
		}

	}
	

	@RequestMapping("/ForografoRiservato/{id}/addAlbum")
	public String albumForm(@Valid @ModelAttribute("album") Album album, @PathVariable("id") Long id, Model model) {
		Fotografo f = this.fotografoService.fotografoPerId(id);
		model.addAttribute("fotografo", f);
		//form per procedere con l'inserimento dei dati dell'album
		return "albumForm";
	}
	

	@RequestMapping(value = "/FotografoRiservato/{idF}/album/{idA}", method = RequestMethod.GET)
	public String stampaAlbum(@Valid @ModelAttribute("fotografia") Fotografia ph, @PathVariable("idF") Long idF,
			@PathVariable("idA") Long idA, Model model) {
		Fotografo f = this.fotografoService.fotografoPerId(idF);
		model.addAttribute("fotografo", f);
		Album a = this.albumService.getPerId(idA);
		if (this.albumService.existsPerId(idA)) {
			model.addAttribute("album", this.albumService.getPerId(idA));
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			//pagina che contiene l'elenco delle fotografie
			return "Album";
		} else {
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			return "FotografoRiservato";
		}
	}
	

	@RequestMapping(value = "/FotografoRiservato/{id}/album", method = { RequestMethod.POST })
	public String inserisciAlbum(@Valid @ModelAttribute("album") Album album, @PathVariable("id") Long id,
			Model model, String titolo, BindingResult bindingResultAlbum) {
		Fotografo f = this.fotografoService.fotografoPerId(id);
		model.addAttribute("fotografo", f);
		this.albumValidator.validate(album, bindingResultAlbum);
		if (!bindingResultAlbum.hasErrors()) {
			this.albumService.setFotografo(album, f);
			this.fotografoService.addAlbum(album, f);
			model.addAttribute("album", album);
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			//se inserito correttamente ritorna alla pagina con tutti gli album e lo aggiunge
			return "FotografoRiservato";
		} else {			
			return "albumForm";
		}
	}
	

	@RequestMapping(value = "/FotografoRis/{idF}/album/{idA}", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String inserisciFotografia(@Valid @ModelAttribute("fotografia") Fotografia fotografia,
			@PathVariable("idF") Long idF, @PathVariable("idA") Long idA, Model model,
			BindingResult bindingResult, String titolo) {
		Fotografo f = this.fotografoService.fotografoPerId(idF);
		Album a = this.albumService.getPerId(idA);
		this.fotografiaValidator.validate(fotografia, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.albumService.addFotgrafia(fotografia, a);
			this.fotografiaService.setAlbum(a, fotografia);
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			model.addAttribute("album", a);
			model.addAttribute("fotografo", f);
			//se fotografia inserita correttamente ritorna alla pagina dell'album
			//che contiene l'elenco di foto
			return "Album";
		}
		else {
			model.addAttribute("album", a);
			model.addAttribute("fotografo", f);
			return "fotografiaForm";
		}
	}
	
	
	@RequestMapping(value = "/FotografoRiservato/{idF}/album/{idA}/addFotografia")
	public String fotografiaForm(@Valid @ModelAttribute("fotografia") Fotografia fotografia,
			@PathVariable("idF") Long idF, @PathVariable("idA") Long idA, Model model) {
		model.addAttribute("fotografo", this.fotografoService.fotografoPerId(idF));
		model.addAttribute("album", this.albumService.getPerId(idA));
		//form per l'nserimento della fotografia
		return "fotografiaForm";
	}
	

	@RequestMapping(value = "/FotografoRiservato/{idF}/album/{idA}/fotografia/{idPh}", method = RequestMethod.GET)
	public String visualizzaFotografia(@Valid @ModelAttribute("fotografia") Fotografia fotografia,
			@PathVariable("idF") Long idF, @PathVariable("idA") Long idA,
			@PathVariable("idPh") Long idPh, Model model) {
		Fotografo f = this.fotografoService.fotografoPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		if (this.fotografiaService.existsPerId(idPh)) {
			model.addAttribute("fotografia", this.fotografiaService.getPerId(idPh));
			//la fotografia ritornerà a una pagina con..
			return "Fotografia";
		}
		else {
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			return "Album";
		}
	}
	
	@RequestMapping("/funzionario/richieste")
	public String visualizzaRichieste(Model model) {
		model.addAttribute("richieste", this.richiestaService.getTutte());
		return "listaRichieste";
	}
	
	

	// Gestione visitatore( Home, AreaFotografi, Fotografo, AlbumUser, AreaFunzionari, Contatti)
	//gestione link e gestione parte dedicata ai visitatori
	//duplicazione dei metodi perchè i visitatori non possono accedere alle stesse pagine dei funzionari

	@RequestMapping("/AreaFotografi")
	public String getListaFotografi(@Valid @ModelAttribute("fotografo") Fotografo fotografo, Model model) {
		model.addAttribute("fotografi", this.fotografoService.tutti());
		//nella pagina areaFotografi viene visualizzato l'elenco dei fotografi inseriti dal funzionario
		return "AreaFotografi.html";

	}

	
	@RequestMapping(value = "/Fotografo/{id}", method = RequestMethod.GET)
	public String getFotografo(@PathVariable("id") Long id, Model model) {
		if (this.fotografoService.existsPerId(id)) {
			Fotografo f = this.fotografoService.fotografoPerId(id);
			model.addAttribute("fotografo", f);
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			//ritorna alla pagina con i dati del fotografo e l'elenco dei suoi album aggiunti dal funzionario
			return "Fotografo";
		} else {
			model.addAttribute("fotografi", this.fotografoService.tutti());
			return "AreaFotografi";
		}

	}

	
	@RequestMapping(value = "/Fotografo/{idF}/album/{idA}", method = RequestMethod.GET)
	public String visualizzaAlbumUser(@Valid @ModelAttribute("fotografia") Fotografia ph, @PathVariable("idF") Long idF,
			@PathVariable("idA") Long idA, Model model) {
		Fotografo f = this.fotografoService.fotografoPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		if (this.albumService.existsPerId(idA)) {
			model.addAttribute("album", this.albumService.getPerId(idA));
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			//ritorna alla pagina con le info dell'album e il suo elenco di foto aggiunte dal funzionario
			return "AlbumUser";
		} else {
			model.addAttribute("albums", this.albumService.getAlbumPerFotografo(f));
			return "Fotografo";
		}
	}
	
	@RequestMapping(value = "/Fotografo/{idF}/album/{idA}/fotografia/{idPh}", method = RequestMethod.GET)
	public String visualizzaFotografiaUs(@Valid @ModelAttribute("fotografia") Fotografia fotografia,
			@PathVariable("idF") Long idF, @PathVariable("idA") Long idA,
			@PathVariable("idPh") Long idPh, Model model) {
		Fotografo f = this.fotografoService.fotografoPerId(idF);
		Album a = this.albumService.getPerId(idA);
		model.addAttribute("fotografo", f);
		model.addAttribute("album", a);
		if (this.fotografiaService.existsPerId(idPh)) {
			model.addAttribute("fotografia", this.fotografiaService.getPerId(idPh));
			//la fotografia ritorna ad una pagina con..
			return "FotografiaUser";
		}
		else {
			model.addAttribute("fotografie", this.fotografiaService.getPerAlbum(a));
			return "AlbumUser";
		}
	}
	
	@RequestMapping("/IniziaRichiesta")
	public String iniziaRichiesta(Model model) {
		Richiesta r = new Richiesta();
		this.richiestaService.inserisciRichiesta(r);
		model.addAttribute("richiesta", r);
		return "Richiesta";
	}

	@RequestMapping("/RichiediFotografie/{idR}")
	public String visualizzaGallery(@PathVariable("idR")Long idR, Model model) {
		model.addAttribute("richiesta", this.richiestaService.getPerId(idR));
		model.addAttribute("listaFotografie", this.fotografiaService.getTutti());
		return "RichiediFotografie";
	}
	
	@RequestMapping(value = "/RichiediFotografie/{idR}/putInRichiesta/{idPh}", method = RequestMethod.GET)
	public String aggiungiFotografiaInRichiesta(@PathVariable("idR")Long idR,
			@PathVariable("idPh")Long idPh,
			Model model) {
		Richiesta r = this.richiestaService.getPerId(idR);
		Fotografia ph = this.fotografiaService.getPerId(idPh);
		this.richiestaService.addFotografia(r, ph);
		this.fotografiaService.addRichiesta(r, ph);
		model.addAttribute("listaFotografie", this.fotografiaService.getTutti());
		model.addAttribute("richiesta", r);
		return "RichiediFotografie";
	}
	@RequestMapping("/riepilogo/richiesta/{idR}")
	public String visualizzaRiepilogoRichiesta(@PathVariable("idR")Long idR, Model model) {
		Richiesta r = this.richiestaService.getPerId(idR);
		model.addAttribute("listaFotografie", this.fotografiaService.getPerRichiesta(r));
		model.addAttribute("richiesta", r);
		return "CompilaRichiesta";
	}
	@RequestMapping("/conferma/richiesta/{idR}")
	public String confermaRichiesta(Model model, @PathVariable("idR")Long idR) {
		
			return "confermaRichiesta";
		}
			
	
                      
		
	
	@RequestMapping("/confermaRichiesta")
	public String richiestaAccettata( Model model) {

		return "/Home";
	}
	


	@RequestMapping(value = { "/", "/Home" })
	public String Home() {
		return "Home.html"; 
	}

	@RequestMapping("/Contatti")
	public String Contatti() {
		return "Contatti.html";
	}

	@RequestMapping("/AreaFunzionari")
	public String AreaFunzionari() {
		return "AreaFunzionari.html"; 
	}
	

}
