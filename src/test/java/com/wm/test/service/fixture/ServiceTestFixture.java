package com.wm.test.service.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.wm.test.domain.Reservation;
import com.wm.test.domain.ReservationStatus;
import com.wm.test.domain.Seat;
import com.wm.test.domain.VenueLevel;
import com.wm.test.dto.ReservationDetail;
import com.wm.test.dto.SeatHold;
import com.wm.test.dto.SeatsPerLevel;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;

/**
 * Test fixture for unit tests.
 * 
 * @author Sachin Garg
 */
public class ServiceTestFixture {

	/**
	 * Creates the seat list.
	 *
	 * @return the list
	 */
	public List<Seat> createSeatList(){
		List<Seat> seatList = new ArrayList<Seat>();
		
		Reservation r = new Reservation();
		r.setId(1L);
		for(int i=1; i <=5; i++){
			Seat s = new Seat();
			s.setId(Long.valueOf(i));
			s.setLevelId(1L);
			s.setRowId(1L);	
			s.setReservation(r);
			seatList.add(s);
		}

		return seatList;
	}
	
	/**
	 * Creates the seat list.
	 *
	 * @return the list
	 */
	public List<Seat> createAVSeatList(){
		List<Seat> seatList = new ArrayList<Seat>();
		
		Reservation r = null;
		for(int i=1; i <=5; i++){
			Seat s = new Seat();
			s.setId(Long.valueOf(i));
			s.setLevelId(1L);
			s.setRowId(1L);	
			s.setReservation(r);
			s.setNumber(i);
			seatList.add(s);
		}
				
		return seatList;
	}

	/**
	 * Creates the seat list.
	 *
	 * @return the list
	 */
	public List<Seat> createAVAndHoldSeatList(){
		List<Seat> seatList = new ArrayList<Seat>();
		
		Reservation r = null;
		for(int i=1; i <=5; i++){
			Seat s = new Seat();
			s.setId(Long.valueOf(i));
			s.setLevelId(1L);
			s.setRowId(1L);	
			s.setReservation(r);
			s.setNumber(i);
			seatList.add(s);
		}
		
		Reservation rr = new Reservation();
		
		for(int i=1; i <=5; i++){
			rr.setId(1L);
			Seat s = new Seat();
			s.setId(Long.valueOf(i));
			s.setLevelId(1L);
			s.setRowId(1L);	
			s.setReservation(rr);
			s.setNumber(i);
			seatList.add(s);
		}

		return seatList;
	}


	/**
	 * Creates the reservation.
	 *
	 * @return the reservation
	 */
	public Reservation createReservation(){
		Reservation res = new Reservation();
		res.setId(1L);
		res.setSeats(createSeatList());
		res.setStatus(ReservationStatus.HELD);
		res.setHeldTime(Calendar.getInstance().getTimeInMillis());
		return res;
	}

	public List<Reservation> createReservationList(){
		List<Reservation> resList = new ArrayList<Reservation>();
		
		for(int i=1 ; i<=2; i++){		
			Reservation res = new Reservation();
			res.setId(0L);
			res.setSeats(createSeatList());
			res.setStatus(ReservationStatus.HELD);
			res.setHeldTime(Calendar.getInstance().getTimeInMillis());
			resList.add(res);
		}

		return resList;
	}

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
		List<Long> held = Arrays.asList(1L,2L,3L,4L,5L);
		return new SeatHold(1L, held);
	}

	/**
	 * Creates the hold request.
	 *
	 * @return the hold request
	 */
	public HoldRequest createHoldRequest(){
		HoldRequest hr = new HoldRequest();
		hr.setNumberOfSeats(1);
		hr.setMinLevel(1);
		hr.setMaxLevel(2);
		hr.setEmail("sachin@sachin.com");
		return hr;
	}

	/**
	 * Creates the hold request.
	 *
	 * @return the hold request
	 */
	public HoldRequest createHoldRequestException(){
		HoldRequest hr = new HoldRequest();
		hr.setNumberOfSeats(10);
		hr.setMinLevel(2);
		hr.setMaxLevel(3);
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

	/**
	 * Creates the venue level.
	 *
	 * @return the venue level
	 */
	public VenueLevel createVenueLevel(){
		VenueLevel venueLevel = new VenueLevel("Orchestra", 1,new Double(100.00),25,50);
		venueLevel.setId(1L);
		return venueLevel;
	}


}
