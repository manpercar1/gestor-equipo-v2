
package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Range;


@Entity
@Table(name = "causes")
public class Cause extends NamedEntity {

	@NotBlank
	@Column(name = "description")
	private String			description;

	@NotNull
	@Column(name = "budget")
	@Range(min = 1, message = "Please select positive numbers only")	
	private Integer			budget;

	@NotBlank
	@Column(name = "organization")
	private String			organization;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cause_id")
	private Set<Donation>	donations;

	@Transient
	private Integer			currentBudget;


	public Integer getCurrentBudget() {
		return this.currentBudget;
	}

	public void setCurrentBudget(final Integer currentBudget) {
		this.currentBudget = currentBudget;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String descripcion) {
		this.description = descripcion;
	}

	public Integer getBudget() {
		return this.budget;
	}

	public void setBudget(final Integer budget) {
		this.budget = budget;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(final String organization) {
		this.organization = organization;
	}

	public Set<Donation> getDonations() {
		return this.donations;
	}

	public void setDonations(final Set<Donation> donations) {
		this.donations = donations;
	}

}
