package com.wm.test.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// TODO: Auto-generated Javadoc
/**
 * The Class VenueLevel.
 */
@Entity
public class VenueLevel {

	/** The id. */
	@Id
	@GeneratedValue
	private Long id;

	/** The name. */
	private String name;

	/** The number. */
	private int number;

	/** The price. */
	private Double price;

	/** The rows. */
	private Integer rows;

	/** The seats in row. */
	private Integer seatsInRow;

	/**
	 * Instantiates a new venue level.
	 */
	public VenueLevel() {
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price
	 *            the new price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows
	 *            the new rows
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * Gets the seats in row.
	 *
	 * @return the seats in row
	 */
	public Integer getSeatsInRow() {
		return seatsInRow;
	}

	/**
	 * Sets the seats in row.
	 *
	 * @param seatsInRow
	 *            the new seats in row
	 */
	public void setSeatsInRow(Integer seatsInRow) {
		this.seatsInRow = seatsInRow;
	}

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
	 * Instantiates a new venue level.
	 *
	 * @param name
	 *            the name
	 * @param number
	 *            the number
	 * @param price
	 *            the price
	 * @param rows
	 *            the rows
	 * @param seatsInRow
	 *            the seats in row
	 */
	public VenueLevel(String name, int number, Double price, Integer rows, Integer seatsInRow) {
		super();
		this.name = name;
		this.price = price;
		this.rows = rows;
		this.seatsInRow = seatsInRow;
		this.number = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VenueLevel [id=" + id + ", name=" + name + ", number=" + number + ", price=" + price + ", rows=" + rows
				+ ", seatsInRow=" + seatsInRow + "]";
	}

}
