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

package org.springframework.samples.petclinic.service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository	petRepository;

	private VetRepository	vetRepository;

	private OwnerRepository	ownerRepository;

	private VisitRepository	visitRepository;

	private HotelRepository hotelRepository;
	
	private CauseRepository  causeRepository;
	
	private DonationRepository  donationRepository;


	@Autowired
	public ClinicService(final PetRepository petRepository, final VetRepository vetRepository, final OwnerRepository ownerRepository, final VisitRepository visitRepository, final HotelRepository hotelRepository, final CauseRepository causeRepository, final  DonationRepository  donationRepository) {
		this.hotelRepository = hotelRepository;
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
		this.causeRepository = causeRepository;
		this.donationRepository = donationRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}
	
	@Transactional
	public void saveVet (Vet vet) throws DataAccessException{
		vetRepository.save(vet);
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) throws DataAccessException {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) throws DataAccessException {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.save(owner);
	}

	@Transactional
	public void deleteOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.delete(owner);
	}

	@Transactional
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	@Transactional
	public void savePet(final Pet pet) throws DataAccessException {
		this.petRepository.save(pet);
	}

	@Transactional
	public void deletePet(final Pet pet) throws DataAccessException {
		this.petRepository.delete(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return this.vetRepository.findAll();
	}

	@Transactional
	public void saveHotel( Hotel hotel) {
		this.hotelRepository.save(hotel);
	}

	public Collection<Hotel> findHotelsByPetId( int petId) {
		return this.hotelRepository.findByPetId(petId);
	}

	@Transactional
	public void deleteHotel(int hotelId)  {
		hotelRepository.delete(hotelId);
	}

	public Hotel findHotelById( int hotelId) {
		return this.hotelRepository.findByHotelId(hotelId);
	}

	@Transactional(readOnly = true)
	public Vet findVetById(final int id) throws DataAccessException {
		return this.vetRepository.findById(id);
	}

	@Transactional
	public void deleteVet(final Vet vet) throws DataAccessException {
		this.vetRepository.delete(vet);
	}

	public Collection<Visit> findVisitsByPetId(final int petId) {
		List<Visit> visits = this.visitRepository.findByPetId(petId);
		visits = visits.stream().filter(v -> (v.getDescription() == null)).collect(Collectors.toList());
		return visits;
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(final int id) throws DataAccessException {
		return this.visitRepository.findById(id);
	}

	@Transactional
	public void deleteVisit(final int visitId) throws DataAccessException {
		Visit visit = this.visitRepository.findById(visitId);

		this.visitRepository.delete(visit);
	}

	
	@Transactional(readOnly = true)
	public List<Specialty> findVetSpecialities()  {
		return vetRepository.findVetSpecialities();
	}
	
	
	@Transactional
	public Collection<Cause> findAllCauses() {
		return this.causeRepository.findAll();
	}
	@Transactional
	public Integer findCurrentAmountByCauseId(final int id) {
		return this.causeRepository.findCurrentAmountByCauseId(id) != null ? this.causeRepository.findCurrentAmountByCauseId(id) : 0;
	}
	@Transactional
	public void saveCause(@Valid final Cause cause) {
		this.causeRepository.save(cause);
	}
	@Transactional
	public Cause findCauseById(final int causeId) {
		return this.causeRepository.findById(causeId);
	}
	@Transactional
	public void saveDonation(@Valid final Donation donation) {
		this.donationRepository.save(donation);
	}
	
	@Transactional
	public List<Donation> findDonationByCauseId(int causeId){
		return donationRepository.findDonationByCauseId(causeId);
		
	}
	
	@Transactional
	public List<Owner> findAllOwners(){
		return ownerRepository.findAll();
	}
}
