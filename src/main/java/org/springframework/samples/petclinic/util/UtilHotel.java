
package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.samples.petclinic.model.Hotel;

public class UtilHotel {

	private UtilHotel() {
		new UtilHotel();
	}

	public static boolean bookingByPet(final Hotel hotel, final Collection<Hotel> hotels) {

		boolean ocupado;
		List<Hotel> hotelsOfAPet;

		ocupado = false;
		hotelsOfAPet = new ArrayList<>();

		hotelsOfAPet.addAll(hotels);

		for (int i = 0; i < hotels.size(); i++) {

			LocalDate actualStartDate = hotelsOfAPet.get(i).getStartDate();
			LocalDate actualEndDate = hotelsOfAPet.get(i).getEndDate();
			LocalDate newStartDate = hotel.getStartDate();
			LocalDate newEndDate = hotel.getEndDate();

			boolean actualStartDateBeforeNewStartDate = actualStartDate.isBefore(newStartDate);
			boolean actualEndDateAfterNewStartDate = actualEndDate.isAfter(newStartDate);
			boolean actualStartDateBeforeNewEndDate = actualStartDate.isBefore(newEndDate);
			boolean actualEndDateAfterNewEndDate = actualEndDate.isAfter(newEndDate);
			boolean actualStartDateEqualsNewStartDate = actualStartDate.equals(newStartDate);
			boolean actualEndDateEqualsNewEndDate = actualEndDate.equals(newEndDate);
			boolean actualStartDateAfterNewStartDate = actualStartDate.isAfter(newStartDate);
			boolean actualEndDateBeforeNewEndDate = actualEndDate.isBefore(newEndDate);

			if ((actualStartDateBeforeNewStartDate || actualStartDateEqualsNewStartDate) && actualEndDateAfterNewStartDate || actualStartDateBeforeNewEndDate && (actualEndDateAfterNewEndDate || actualEndDateEqualsNewEndDate)
				|| actualStartDateEqualsNewStartDate && actualEndDateEqualsNewEndDate || actualStartDateAfterNewStartDate && actualEndDateBeforeNewEndDate) {
				ocupado = true;
			}

		}

		return ocupado;
	}

	public static boolean validateDates(final Hotel hotel) {

		boolean date;

		date = false;

		if (hotel.getStartDate().isAfter(hotel.getEndDate())) {
			date = true;
		}

		return date;
	}
	
	public static boolean validateDatesToday(final Hotel hotel) {
		
		boolean date;
		
		date = false;
		
		if (hotel.getStartDate().isBefore(LocalDate.now())) {
			date = true;
		}
		
		return date;
	}
}
