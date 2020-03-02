package com.wm.test.dto;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SeatHold.
 * @author Sachin Garg
 */
public class SeatHold {
	
	/** The id. */
	Long id;
	
	/** The level. */
	String level;
	
	/** The seats. */
	List<Long> seats;
	
	
	
	/** The message. */
	String message;
	
	/**
	 * Instantiates a new seat hold.
	 *
	 * @param id the id
	 * @param seats the seats
	 */
	public SeatHold(Long id, List<Long> seats) {
		super();
		this.id = id;
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
	 * @param id
	 * @param level
	 * @param seats
	 * @param message
	 */
	public SeatHold(Long id, String level, List<Long> seats, String message) {
		super();
		this.id = id;
		this.level = level;
		this.seats = seats;
		this.message = message;
	}

	public SeatHold() {
		// TODO Auto-generated constructor stub
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
	 * Gets the seats.
	 *
	 * @return the seats
	 */
	public List<Long> getSeats() {
		return seats;
	}
	
	/**
	 * Sets the seats.
	 *
	 * @param seats the new seats
	 */
	public void setSeats(List<Long> seats) {
		this.seats = seats;
	}
	
	
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SeatHold [id=" + id + ", level=" + level + ", seats=" + seats + ", message=" + message + "]";
	}
	

}
