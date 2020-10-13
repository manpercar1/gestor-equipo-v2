package org.springframework.samples.petclinic.model;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyFormatter implements Formatter<Specialty> {
	
	private final ClinicService clinicService;

	@Autowired
	public SpecialtyFormatter(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	public String print(Specialty petType, Locale locale) {
		return petType.getName();
	}

	@Override
	public Specialty parse(String text, Locale locale) throws ParseException {
		Collection<Specialty> findSpecialties = this.clinicService.findVetSpecialities();
		for(Specialty type : findSpecialties) {
			if(type.getName().equals(text)) {
				return type;
			}
		}
		throw new ParseException("type not found "+text, 0);
	}

}
