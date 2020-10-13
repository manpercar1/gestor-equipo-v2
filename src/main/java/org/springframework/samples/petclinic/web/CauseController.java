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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CauseController {

	private final ClinicService	clinicService;


	@InitBinder("cause")
	public void initCauseBinder(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public CauseController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@GetMapping(value = {
		"/causes/list"
	})
	public String causeList(final ModelMap model) {
		List<Cause> causes = new ArrayList<>();

		for (Cause cause : this.clinicService.findAllCauses()) {
			cause.setCurrentBudget(this.clinicService.findCurrentAmountByCauseId(cause.getId()));
			causes.add(cause);
		}
		model.put("causes", causes);

		return "causes/causeList";
	}

	@GetMapping(value = "/causes/new")
	public String initForm(final ModelMap model) {
		model.put("cause", new Cause());
		return "causes/createCuase";
	}


	@PostMapping(value = "/causes/new")
	public String processForm(@Valid final Cause cause, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			/*Como no sabía exactamente como resolver este problema 
			*del incidente I-000136 lo he resuelto de esta manera un poco enrevesada
			Controlo esta excepción en concreto y le creo una variable en el modelo. Compruebo esa variable en el tag de input*/
			if(containsConvertError(result)) {
				model.put("convertError", 1);
			}
			return "causes/createCuase";
		} else {
			cause.setCurrentBudget(0);
			this.clinicService.saveCause(cause);
			return "redirect:/causes/list";
		}
	}
	

	private boolean containsConvertError(BindingResult result) {
		boolean res = false;
		res = result.getAllErrors().stream()
				.anyMatch(x->x.getCode().equals("typeMismatch"));
		return res;		
	}
	
	@GetMapping("/causes/{causeId}")
	public String showCause(@PathVariable("causeId") final int causeId, final ModelMap model) {
		Cause cause = this.clinicService.findCauseById(causeId);
		cause.setCurrentBudget(this.clinicService.findCurrentAmountByCauseId(cause.getId()));
		model.put("cause", cause);
		return "causes/causeDetails";
	}

}
