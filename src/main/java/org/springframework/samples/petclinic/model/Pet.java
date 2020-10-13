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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

	@Column(name = "birth_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	birthDate;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType		type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner		owner;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Visit>	visits;


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
	private Set<Hotel>	hotels;


	public void setBirthDate( LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetType getType() {
		return this.type;
	}


	public void setType( PetType type) {
		this.type = type;
	}

	public Owner getOwner() {
		return this.owner;
	}


	protected void setOwner( Owner owner) {
		this.owner = owner;
	}

	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}


	protected void setVisitsInternal( Set<Visit> visits) {
		this.visits = visits;
	}

	protected Set<Hotel> getHotelsInternal() {
		if (this.hotels == null) {
			this.hotels = new HashSet<>();
		}
		return this.hotels;
	}

	protected void setHotelsInternal( Set<Hotel> hotels) {
		this.hotels = hotels;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(this.getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}


	public void addVisit( Visit visit) {
		this.getVisitsInternal().add(visit);
		visit.setPet(this);
	}


	public List<Hotel> getHotels() {
		List<Hotel> sortedHotels = new ArrayList<>(this.getHotelsInternal());
		PropertyComparator.sort(sortedHotels, new MutableSortDefinition("startDate", false, false));
		return Collections.unmodifiableList(sortedHotels);
	}

	public void addHotel( Hotel hotel) {
		this.getHotelsInternal().add(hotel);
		hotel.setPet(this);
	}

	public void deleteHotel( Hotel hotel) {
		List<Hotel> hotels = this.getHotels();
		for(Hotel h : hotels) {
			if(h.getDetails() == null) {
				this.hotels.remove(h);
			}
		}
		
		this.hotels.remove(hotel);
		
		hotel.setPet(this);
  }

	public void deleteVisit(final Visit visit) {
		//Al principio aparece una visita nula
		List<Visit> visitas = this.getVisits();
		for (Visit v : visitas) {
			if (v.getDescription() == null) {
				this.visits.remove(v);
			}
		}

		this.visits.remove(visit);
	}

}
