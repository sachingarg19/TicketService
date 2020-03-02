package com.wm.test.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// TODO: Auto-generated Javadoc
/**
 * The Class Seat.
 * 
 * @author Sachin Garg
 */
@Entity
public class Seat {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The level id. */
	private long levelId;

	/** The row id. */
	private long rowId;

	/** The reservation. */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reservationId")
	private Reservation reservation;

	/** The number. */
	private int number;

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number
	 *            the new number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Instantiates a new seat.
	 */
	public Seat() {
	}

	/**
	 * Instantiates a new seat.
	 *
	 * @param levelId
	 *            the level id
	 * @param rowId
	 *            the row id
	 * @param reservation
	 *            the reservation
	 */
	public Seat(long levelId, long rowId, Reservation reservation) {
		super();
		this.levelId = levelId;
		this.rowId = rowId;
		this.reservation = reservation;
	}

	/**
	 * Instantiates a new seat.
	 *
	 * @param levelId
	 *            the level id
	 * @param rowId
	 *            the row id
	 */
	public Seat(long levelId, long rowId) {
		super();
		this.levelId = levelId;
		this.rowId = rowId;
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
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the level id.
	 *
	 * @return the level id
	 */
	public long getLevelId() {
		return levelId;
	}

	/**
	 * Sets the level id.
	 *
	 * @param levelId
	 *            the new level id
	 */
	public void setLevelId(long levelId) {
		this.levelId = levelId;
	}

	/**
	 * Gets the row id.
	 *
	 * @return the row id
	 */
	public long getRowId() {
		return rowId;
	}

	/**
	 * Sets the row id.
	 *
	 * @param rowId
	 *            the new row id
	 */
	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	/**
	 * Gets the reservation.
	 *
	 * @return the reservation
	 */
	public Reservation getReservation() {
		return reservation;
	}

	/**
	 * Sets the reservation.
	 *
	 * @param reservation
	 *            the new reservation
	 */
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Seats [id=" + id + ", levelId=" + levelId + ", rowId=" + rowId + ", reservation=" + reservation.getId()
				+ "]";
	}

}
