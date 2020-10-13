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

package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

	@NotBlank
	@Column(name = "client")
	private String		client;

	@NotNull
	@Column(name = "donation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	donationDate;

	@NotNull
	@Column(name = "amount")
	@Min(value = 1)
	private Integer		amount;
	
	 @ManyToOne
	 @JoinColumn(name = "cause_id")
	 private Cause cause;


	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public String getClient() {
		return this.client;
	}

	public void setClient(final String client) {
		this.client = client;
	}

	public LocalDate getDonationDate() {
		return this.donationDate;
	}

	public void setDonationDate(final LocalDate date) {
		this.donationDate = date;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(final Integer amount) {
		this.amount = amount;
	}

}
