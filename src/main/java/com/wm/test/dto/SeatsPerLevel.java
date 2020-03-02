/**
 * 
 */
package com.wm.test.dto;

/**
 * @author Sachin Garg
 *
 */
public class SeatsPerLevel {

	private String levelName;
	private int numOfSeats;
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * @return the numOfSeats
	 */
	public int getNumOfSeats() {
		return numOfSeats;
	}
	/**
	 * @param numOfSeats the numOfSeats to set
	 */
	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SeatsPerLevel [levelName=" + levelName + ", numOfSeats=" + numOfSeats + "]";
	}

}
