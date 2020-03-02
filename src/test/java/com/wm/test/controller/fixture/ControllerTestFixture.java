package com.wm.test.controller.fixture;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.wm.test.dto.ReservationDetail;
import com.wm.test.dto.SeatHold;
import com.wm.test.dto.SeatsPerLevel;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;

// TODO: Auto-generated Javadoc
/**
 *	Test fixture for unit tests.
 *  @author Sachin Garg
 *
 */
public class ControllerTestFixture {

	/**
	 * Creates the seats per level.
	 *
	 * @return the seats per level
	 */
	public SeatsPerLevel createSeatsPerLevel(){
		SeatsPerLevel seatsPerLevel = new SeatsPerLevel();
		seatsPerLevel.setLevelName("Venue Level 1");
		seatsPerLevel.setNumOfSeats(1250);
		return seatsPerLevel;
	}

	
	/**
	 * Creates the venue level.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<Integer> createVenueLevel(Long id){
		Optional<Integer> venueLevel = Optional.empty();
		if(id !=0L){
			venueLevel = Optional.of(id.intValue());
		}
		return venueLevel;
	}

	/**
	 * Creates the seat hold.
	 *
	 * @return the seat hold
	 */
	public SeatHold createSeatHold(){
		List<Long> held = Arrays.asList(1L,2L,3L);
		return new SeatHold(1L, held);
	}

	/**
	 * Creates the hold request.
	 *
	 * @return the hold request
	 */
	public HoldRequest createHoldRequest(){
		HoldRequest hr = new HoldRequest();
		hr.setNumberOfSeats(3);
		hr.setMinLevel(1);
		hr.setMaxLevel(1);
		hr.setEmail("sachin@sachin.com");
		return hr;
	}

	/**
	 * Creates the reserve request.
	 *
	 * @return the reserve request
	 */
	public ReserveRequest createReserveRequest(){
		ReserveRequest rr = new ReserveRequest();
		rr.setId(1);
		rr.setEmail("sachin@sachin.com");
		return rr;
	}

	/**
	 * Creates the reservation detail.
	 *
	 * @return the reservation detail
	 */
	public ReservationDetail createReservationDetail(){
		ReservationDetail rr = new ReservationDetail();
		rr.setReservationID("1");
		rr.setEmailID("sachin@sachin.com");
		rr.setMessage("Either Already reserved or Reservation ID doesn't exists:-1");
		return rr;
	}
}
