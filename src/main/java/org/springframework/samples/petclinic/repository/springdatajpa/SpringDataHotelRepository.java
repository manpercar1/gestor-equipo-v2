
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.repository.HotelRepository;


public interface SpringDataHotelRepository extends HotelRepository, Repository<Hotel, Integer> {

	
}
