/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.SpecialtyFormatter;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private static final String VIEWS_VET_CREATE_OR_UPDATE_FORM = "vets/createOrUpdateVetForm";
	private final ClinicService clinicService;


	@Autowired
	public VetController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@ModelAttribute("specialties")
	public Collection<Specialty> populateSpecialities() {
		return this.clinicService.findVetSpecialities();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result,
		@RequestParam(value = "specialties", required = false) String[] specialties) throws ParseException {

		SpecialtyFormatter formatter = new SpecialtyFormatter(clinicService);
		Locale locale = new Locale.Builder().setRegion("US").build();
		List<Specialty> res = new ArrayList<Specialty>();

		if (specialties == null) {
			Specialty specialty = new Specialty();
			specialty = formatter.parse("none", locale);
			res.add(specialty);
		} else {
			List<String> list = Arrays.asList(specialties);
			for (String s : list) {
				if (s.equals("none") && list.size()>1) {

					result.rejectValue("specialties", "Especialidad none y alguna mas", "No es posible crear un veterinario con especialidad o especialidades y que una de ellas sea none");
				}
				Specialty specialty = new Specialty();
				specialty = formatter.parse(s, locale);
				res.add(specialty);
			}
		}
		vet.setSpecialties(res);

		if (result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		} else {
			this.clinicService.saveVet(vet);
			return "redirect:/vets/" + vet.getId();
		}
	}

	@GetMapping(value = "/vets/{vetId}/edit")
	public String initUpdateVetForm(@PathVariable("vetId")final int vetId, Model model) {
		Vet vet = this.clinicService.findVetById(vetId);
		model.addAttribute(vet);
		return VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/vets/{vetId}/edit")
	public String processUpdateVetForm(@Valid final Vet vet,
		final BindingResult result, @PathVariable("vetId") final int vetId,
		@RequestParam(value = "specialties", required = false)
	final String[] specialties) throws ParseException {

		SpecialtyFormatter formatter = new SpecialtyFormatter(this.clinicService);
		Locale locale = new Locale.Builder().setRegion("US").build();
		List<Specialty> spec = new ArrayList<Specialty>();

		if (specialties == null) {
			Specialty specialty = new Specialty();
			specialty = formatter.parse("none", locale);
			spec.add(specialty);
		} else {
			List<String> list = Arrays.asList(specialties);
			for (String s : list) {
				if (s.equals("none") && list.size()>1) {
					ObjectError error = new ObjectError("errorNone",
						"No es posible crear un veterinario con especialidad o especialidades y que una de ellas sea none");
					result.addError(error);
				}
				Specialty sp = new Specialty();
				sp = formatter.parse(s, locale);
				spec.add(sp);
			}
		}

		if (result.hasErrors()) {
			return VIEWS_VET_CREATE_OR_UPDATE_FORM;
		} else {
			vet.setId(vetId);
			this.clinicService.saveVet(vet);
			return "redirect:/vets";
		}

	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml" })
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of
		// Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		return vets;
	}

	@GetMapping("/vets/{vetId}")
	public ModelAndView showvet(@PathVariable("vetId") int vetId) {
		ModelAndView mav = new ModelAndView("vets/vetDetails");
		mav.addObject(this.clinicService.findVetById(vetId));
		return mav;
	}

	@GetMapping(value = "/vets/{vetId}/delete")
	public String initDeleteVetForm(@PathVariable("vetId") final int vetId, final Model model) {
		Vet vet = this.clinicService.findVetById(vetId);
		this.clinicService.deleteVet(vet);

		return "redirect:/vets";
	}

}
