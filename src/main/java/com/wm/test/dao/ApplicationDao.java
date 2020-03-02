package com.wm.test.dao;

import java.util.List;
import java.util.Optional;

import com.wm.test.domain.Reservation;
import com.wm.test.domain.Seat;
import com.wm.test.domain.VenueLevel;


/**
 * The Interface ApplicationDao.
 * @author Sachin Garg
 */
public interface ApplicationDao {

	
	/**
	 * Gets the available seats.
	 *
	 * @param venueLevel the venue level
	 * @return the available seats
	 */
	List<Seat> getAvailableSeats(Optional<Integer> venueLevel);

	/**
	 * Gets the seats.
	 *
	 * @param seatIds the seat ids
	 * @return the seats
	 */
	public List<Seat> getSeats(List<Long> seatIds);
	
	/**
	 * Gets the all seats.
	 *
	 * @return the all seats
	 */
	public List<Seat> getAllSeats();
	
	/**
	 * Reserve.
	 *
	 * @param reservation the reservation
	 * @return the long
	 */
	public long reserve(Reservation reservation);
	
	/**
	 * Gets the registration.
	 *
	 * @param id the id
	 * @param customerEmail the customer email
	 * @return the registration
	 */
	public Reservation getRegistration(Long id, String customerEmail);
	
			
	/**
	 * Gets the held registration.
	 *
	 * @param olddate the olddate
	 * @return the held registration
	 */
	public List<Reservation> getHeldRegistration(Long olddate);
	
	/**
	 * Removes the reservations.
	 *
	 * @param reservations the reservations
	 */
	public void removeReservations(List<Reservation> reservations) ;
	
	/**
	 * Save or update.
	 *
	 * @param entity the entity
	 */
	public void  saveOrUpdate( Seat entity);
	
	/**
	 * Save or update.
	 *
	 * @param entity the entity
	 */
	public void  saveOrUpdate( VenueLevel entity);


	
}