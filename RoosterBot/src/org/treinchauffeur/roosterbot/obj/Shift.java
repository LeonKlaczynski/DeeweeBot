package org.treinchauffeur.roosterbot.obj;

import java.util.Date;

/**
 * 
 * @author Leonk Defines a shift as an object
 */

public class Shift {
	/** TODO Known modifiers:
	 * 
	 * # Guaranteed
	 * > Pupil
	 * E Extra
	 * *
	 * !
	 * @
	 */

	private Driver driver;
	private int weekNumber;
	private int yearNumber;
	private Date baseDate; //used for R (rest / day off) days
	private Date startTime;
	private Date endTime;
	private String shiftNumber;
	private String shiftNumberModifier;
	private String profession;
	private String location;
	
	public Shift(Driver driver, int weekNumber, int yearNumber, Date baseDate, Date startTime, Date endTime, String shiftNumber,
			String shiftNumberModifier, String profession, String location) {
		super();
		this.driver = driver;
		this.weekNumber = weekNumber;
		this.yearNumber = yearNumber;
		this.baseDate = baseDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.shiftNumber = shiftNumber;
		this.shiftNumberModifier = shiftNumberModifier;
		this.profession = profession;
		this.location = location;
	}
	
	public Shift() {
		super();
		this.driver = null;
		this.weekNumber = -1;
		this.yearNumber = -1;
		this.baseDate = new Date();
		this.startTime = new Date();
		this.endTime = new Date();
		this.shiftNumber = "!!!";
		this.shiftNumberModifier = "!!!";
		this.profession = "!!!";
		this.location = "!!!";
	}

	/**
	 * @return the weekNumber
	 */
	public int getWeekNumber() {
		return weekNumber;
	}

	/**
	 * @param weekNumber the weekNumber to set
	 */
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	/**
	 * @return the yearNumber
	 */
	public int getYearNumber() {
		return yearNumber;
	}

	/**
	 * @param yearNumber the yearNumber to set
	 */
	public void setYearNumber(int yearNumber) {
		this.yearNumber = yearNumber;
	}

	/**
	 * @return the baseDate
	 */
	public Date getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate the baseDate to set
	 */
	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the shiftNumber
	 */
	public String getShiftNumber() {
		return shiftNumber;
	}

	/**
	 * @param shiftNumber the shiftNumber to set
	 */
	public void setShiftNumber(String shiftNumber) {
		this.shiftNumber = shiftNumber;
	}

	/**
	 * @return the shiftNumberModifier
	 */
	public String getShiftNumberModifier() {
		return shiftNumberModifier;
	}

	/**
	 * @param shiftNumberModifier the shiftNumberModifier to set
	 */
	public void setShiftNumberModifier(String shiftNumberModifier) {
		this.shiftNumberModifier = shiftNumberModifier;
	}

	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}

	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}
