package com.wm.test.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;



// TODO: Auto-generated Javadoc
/**
 * The Class Reservation.
 * @author Sachin Garg
 */
@Entity
public class Reservation {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The email. */
	private String email;

	/** The status. */
	private ReservationStatus status;

	/** The held time. */
	private Long heldTime;

	/**
	 * Gets the held time.
	 *
	 * @return the held time
	 */
	public Long getHeldTime() {
		return heldTime;
	}



	/**
	 * Sets the held time.
	 *
	 * @param heldTime the new held time
	 */
	public void setHeldTime(Long heldTime) {
		this.heldTime = heldTime;
	}



	/** The seats. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "reservation")
	private List<Seat> seats;


	/**
	 * Instantiates a new reservation.
	 */
	public Reservation() {}



	/**
	 * Instantiates a new reservation.
	 *
	 * @param email the email
	 * @param status the status
	 * @param seats the seats
	 */
	public Reservation(String email, ReservationStatus status, List<Seat> seats) {
		super();
		this.email = email;
		this.status = status;
		this.seats = seats;
	}



	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public ReservationStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}



	/**
	 * Gets the seats.
	 *
	 * @return the seats
	 */
	public List<Seat> getSeats() {
		return seats;
	}



	/**
	 * Sets the seats.
	 *
	 * @param seats the new seats
	 */
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", email=" + email + ", status="
				+ status + ", seats=" + seats + "]";
	}





}
