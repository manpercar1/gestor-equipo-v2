package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.transaction.annotation.Transactional;

public interface HotelRepository {

	 	void save(Hotel hotel);

	    List<Hotel> findByPetId(Integer hotelId);
	    
	    @Transactional
	    @Modifying
	    @Query("DELETE FROM Hotel h where h.id=:hotelId")
	    void delete(@Param(value = "hotelId") int hotelId) ;
	        
	    @Query("SELECT v FROM Hotel v where v.id=:hotelId")
		Hotel findByHotelId(@Param(value = "hotelId") int hotelId) ;
	    
	    @Query("SELECT p.hotels FROM Pet p where p.id=:petId")
		Collection<Hotel> findHotelsByPetId(@Param(value = "petId") int petId) ;

	
}
