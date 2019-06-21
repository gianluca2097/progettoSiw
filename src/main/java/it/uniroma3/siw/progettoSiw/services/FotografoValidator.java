package it.uniroma3.siw.progettoSiw.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.progettoSiw.model.Fotografo;


@Component 
public class FotografoValidator implements Validator{

	@Override
	public boolean supports(Class<?> aClass) {
		return Fotografo.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		

	}
	
}
