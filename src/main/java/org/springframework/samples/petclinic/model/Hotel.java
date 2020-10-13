
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {

	@NotBlank
	@Column(name = "details")
	private String		details;

	@NotNull
	@Column(name = "start_date_book")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	startDate;

	@NotNull
	@Column(name = "end_date_book")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	endDate;


	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final LocalDate endtDate) {
		this.endDate = endtDate;
	}


	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;


	public String getDetails() {
		return this.details;
	}

	public void setDetails(final String details) {
		this.details = details;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}

}
