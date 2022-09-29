package org.treinchauffeur.roosterbot.obj;

import java.util.ArrayList;

/**
 * 
 * @author Leonk
 * Defines a driver as an object
 */

public class Driver {
	
	private String driverName;
	private String driverNumber;
	private ArrayList<Shift> shifts;

	public Driver(String driverName, String driverNumber, ArrayList<Shift> shifts) {
		super();
		this.driverName = driverName;
		this.driverNumber = driverNumber;
		this.shifts = shifts;
	}
	
	public Driver() {
		this.driverName = "";
		this.driverNumber = "";
		this.shifts = null;
	}
	

}
