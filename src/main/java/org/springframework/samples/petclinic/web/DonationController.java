
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {

	private static final String	CAUSE_CLOSED							= "cause completed";
	private final ClinicService	clinicService;


	@Autowired
	public DonationController(final ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@GetMapping("/donations/{causeId}")
	public String listadoDonation(ModelMap modelMap, @PathVariable("causeId") int causeId) {
		String vista ="donations/donationList";
		List<Donation> donation = new ArrayList<Donation>();
		donation.addAll(clinicService.findDonationByCauseId(causeId));
		modelMap.addAttribute("donations",donation);
		return vista;
	}

	@ModelAttribute("cause")
	public Cause initCause(@PathVariable("causeId") final int causeId) {
		Cause cause = this.clinicService.findCauseById(causeId);
		cause.setCurrentBudget(this.clinicService.findCurrentAmountByCauseId(cause.getId()));
		return cause;
	}
	
	@ModelAttribute("ownersName")
	public List<String> getOwnersName(){
		List<String> names = this.clinicService.findAllOwners().stream()
				.map(x->x.getFirstName()+" "+x.getLastName())
				.collect(Collectors.toList());
		return names;
	}


	@InitBinder("cause")
	public void initBinderCause(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/donations/new")
	public String initForm(final Cause cause, final ModelMap model) {
		Donation donation = new Donation();
		donation.setDonationDate(LocalDate.now());
		model.put("donation", donation);
		return "donations/createDonation";
	}

	@PostMapping(value = "/donations/new")
	public String processForm(@ModelAttribute final Cause cause, @Valid final Donation donation, final BindingResult result, final ModelMap model) {
		if (cause.getBudget() <= cause.getCurrentBudget()) {
			result.rejectValue("client", DonationController.CAUSE_CLOSED, DonationController.CAUSE_CLOSED);
			result.rejectValue("amount", DonationController.CAUSE_CLOSED, DonationController.CAUSE_CLOSED);
		}
		Integer err = cause.getBudget() - cause.getCurrentBudget();
		if (donation.getAmount() != null && err < donation.getAmount()) {
			result.rejectValue("amount", "must be less than " + err, "must be less than " + err);
		}
		if (result.hasErrors()) {
			return "donations/createDonation";
		} else {
			donation.setDonationDate(LocalDate.now());
			cause.getDonations().add(donation);
			this.clinicService.saveDonation(donation);
			cause.setCurrentBudget(this.clinicService.findCurrentAmountByCauseId(cause.getId()));
			this.clinicService.saveCause(cause);

			return "redirect:/causes/{causeId}/donations/{causeId}";
		}
	}

}
