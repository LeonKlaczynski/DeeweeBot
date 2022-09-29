package org.treinchauffeur.roosterbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

import org.treinchauffeur.roosterbot.misc.FileTools;
import org.treinchauffeur.roosterbot.misc.Logger;
import org.treinchauffeur.roosterbot.obj.Shift;

import net.fortuna.ical4j.model.Date;

/**
 * 
 * @author Leonk
 * A basic bot to load workdays from a DW (donderdagse week) weekly planning .txt file into something that's actually useful like Google Calendar.
 * ACTUALLY just for now we're gonna generate a .ics file for manual importing.
 */

public class Run {
	private static String TAG = "Run";
	private static FileTools fileTools = new FileTools();
	public static File toRead;
	
	public static void main(String[] args) {
		fileTools.loadFilesInFolder(new File("input/"));
		
		for(File f : fileTools.files) {
			if(f.getName().startsWith("DW")) {
				toRead = f;
			}
		}
		
		if(toRead == null) {
			Logger.log(TAG, "No valid DW files were present to read.");
			return;
		} else {
			Logger.log(TAG, "Using file: "+toRead.getAbsolutePath());
			readDWFile(toRead);
		}		
		
	}
	
	/**
	 * Read the DW file & save to raw data
	 * @param f
	 * Line indexing: 0=days in text, 1=date in 31-12 format, 2=shift number/letters, 3=starttime formatted to 24:60, 4=endtime formatted to 24:60, 5=profession, 6=location
	 */
	
	private static void readDWFile(File f) {
		int staffNumber = -1, weekNumber = -1, yearNumber = -1;
		try {
			// Defining scanner
			Scanner scanner = new Scanner(f);

			// First line - weeknumber + year
			String startingLine = scanner.nextLine().replaceAll("\\s+", " ");
			String[] dateYear = startingLine.split("-");
			weekNumber = Integer.parseInt(dateYear[0].substring(Math.max(dateYear[0].length() - 2, 0)));
			yearNumber = Integer.parseInt(dateYear[1]);

			// Second line - empty for formatting purposes
			String emptyLineOne = scanner.nextLine().replaceAll("\\s+", " ");

			// Third line - staff number
			String staffNumberLine = scanner.nextLine().replaceAll("\\s+", " ");
			staffNumber = Integer.parseInt(staffNumberLine.split(" ")[0]);

			// Fourth line - empty for formatting purposes
			String emptyLineTwo = scanner.nextLine().replaceAll("\\s+", " ");

			// Fifth line - headers for formatting
			String headersLine = scanner.nextLine().replaceAll("\\s+", " ");

			// Sixth line - lines underneath headers, also worthless
			String underscoresLine = scanner.nextLine().replaceAll("\\s+", " ");
			
			Logger.debug(TAG, "DW FOR "+staffNumberLine+":");			
			Logger.debug(TAG, "//////////// START OF WEEK ////////////");

			// Seventh line - first actual day listing
			String mondayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String mondayModifier = "-1";
			String[] mondayArray = mondayLine.split(" ");
			if (mondayArray[2].equals("!") || mondayArray[2].equals("@") || mondayArray[2].equals(">") || mondayArray[2].equals("<")
					|| mondayArray[2].equals("*") || mondayArray[2].equals("?") || mondayArray[2].equals("E") || mondayArray[2].equals("#") || mondayArray[2].equals("$")) { //Check if we have modifiers
				mondayModifier = mondayArray[2];
				for (int i = 2; i < mondayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					mondayArray[i] = mondayArray[i + 1];
				}
			}
			
			Shift mondayShift = new Shift(); //TODO everything..
			Calendar monday = Calendar.getInstance();
			monday.set(Calendar.YEAR, yearNumber);
			monday.set(Calendar.MONTH, Integer.parseInt(mondayArray[1].split("-")[1]));
			monday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(mondayArray[1].split("-")[0]));
			String mondayProfession = "";
			String mondayLocation = "";
			if(mondayArray.length > 5) { // Things like CURS don't have a function etc listed
			mondayProfession = mondayArray[5].substring(0, 1).toUpperCase() + mondayArray[5].substring(1).toLowerCase();
			mondayLocation = mondayArray[6].substring(0, 1).toUpperCase() + mondayArray[6].substring(1).toLowerCase();
			}
			String mondayShiftNumber = mondayArray[2];
			String mondayStartTime = "";
			String mondayEndTime = "";
			if(!mondayShiftNumber.equalsIgnoreCase("R") && !mondayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			mondayStartTime = mondayArray[3];
			mondayEndTime = mondayArray[4];
			}
			
			String mondayTitle = "";
			
			if(mondayArray.length == 3)
				mondayTitle = mondayShiftNumber;
			else if(mondayArray.length == 5)
				mondayTitle = mondayShiftNumber + " " + mondayStartTime + "-" + mondayEndTime;
			else if(mondayModifier != "-1")
				mondayTitle = mondayProfession + " " + mondayLocation + " " + mondayModifier + "" + mondayShiftNumber + " " + mondayStartTime + "-" + mondayEndTime;
			else 
				mondayTitle = mondayProfession + " " + mondayLocation + " " + mondayShiftNumber + " " + mondayStartTime + "-" + mondayEndTime;
			
			Logger.debug(TAG, mondayTitle);
			
			// Eighth line - second day
			
			String tuesdayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String tuesdayModifier = "-1";
			String[] tuesdayArray = tuesdayLine.split(" ");
			if (tuesdayArray[2].equals("!") || tuesdayArray[2].equals("@") || tuesdayArray[2].equals(">") || tuesdayArray[2].equals("<")
					|| tuesdayArray[2].equals("*") || tuesdayArray[2].equals("?") || tuesdayArray[2].equals("E") || tuesdayArray[2].equals("#") || tuesdayArray[2].equals("$")) { //Check if we have modifiers
				tuesdayModifier = tuesdayArray[2];
				for (int i = 2; i < tuesdayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					tuesdayArray[i] = tuesdayArray[i + 1];
				}
			}
			
			Shift tuesdayShift = new Shift(); //TODO everything..
			Calendar tuesday = Calendar.getInstance();
			tuesday.set(Calendar.YEAR, yearNumber);
			tuesday.set(Calendar.MONTH, Integer.parseInt(tuesdayArray[1].split("-")[1]));
			tuesday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tuesdayArray[1].split("-")[0]));
			String tuesdayProfession = "";
			String tuesdayLocation = "";
			if(tuesdayArray.length > 5) { // Things like CURS don't have a function etc listed
			tuesdayProfession = tuesdayArray[5].substring(0, 1).toUpperCase() + tuesdayArray[5].substring(1).toLowerCase();
			tuesdayLocation = tuesdayArray[6].substring(0, 1).toUpperCase() + tuesdayArray[6].substring(1).toLowerCase();
			}
			String tuesdayShiftNumber = tuesdayArray[2];
			String tuesdayStartTime = "";
			String tuesdayEndTime = "";
			if(!tuesdayShiftNumber.equalsIgnoreCase("R") && !tuesdayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			tuesdayStartTime = tuesdayArray[3];
			tuesdayEndTime = tuesdayArray[4];
			}
			
			String tuesdayTitle = "";
			
			if(tuesdayArray.length == 3)
				tuesdayTitle = tuesdayShiftNumber;
			else if(tuesdayArray.length == 5)
				tuesdayTitle = tuesdayShiftNumber + " " + tuesdayStartTime + "-" + tuesdayEndTime;
			else if(tuesdayModifier != "-1")
				tuesdayTitle = tuesdayProfession + " " + tuesdayLocation + " " + tuesdayModifier + "" + tuesdayShiftNumber + " " + tuesdayStartTime + "-" + tuesdayEndTime;
			else 
				tuesdayTitle = tuesdayProfession + " " + tuesdayLocation + " " + tuesdayShiftNumber + " " + tuesdayStartTime + "-" + tuesdayEndTime;

			Logger.debug(TAG, tuesdayTitle);
			
			// Ninth line - third day
			
			String wednesdayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String wednesdayModifier = "-1";
			String[] wednesdayArray = wednesdayLine.split(" ");
			if (wednesdayArray[2].equals("!") || wednesdayArray[2].equals("@") || wednesdayArray[2].equals(">") || wednesdayArray[2].equals("<")
					|| wednesdayArray[2].equals("*") || wednesdayArray[2].equals("?") || wednesdayArray[2].equals("E") || wednesdayArray[2].equals("#") || wednesdayArray[2].equals("$")) { //Check if we have modifiers
				wednesdayModifier = wednesdayArray[2];
				for (int i = 2; i < wednesdayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					wednesdayArray[i] = wednesdayArray[i + 1];
				}
			}
			
			Shift wednesdayShift = new Shift(); //TODO everything..
			Calendar wednesday = Calendar.getInstance();
			wednesday.set(Calendar.YEAR, yearNumber);
			wednesday.set(Calendar.MONTH, Integer.parseInt(wednesdayArray[1].split("-")[1]));
			wednesday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(wednesdayArray[1].split("-")[0]));
			String wednesdayProfession = "";
			String wednesdayLocation = "";
			if(wednesdayArray.length > 5) { // Things like CURS don't have a function etc listed
			wednesdayProfession = wednesdayArray[5].substring(0, 1).toUpperCase() + wednesdayArray[5].substring(1).toLowerCase();
			wednesdayLocation = wednesdayArray[6].substring(0, 1).toUpperCase() + wednesdayArray[6].substring(1).toLowerCase();
			}
			String wednesdayShiftNumber = wednesdayArray[2];
			String wednesdayStartTime = "";
			String wednesdayEndTime = "";
			if(!wednesdayShiftNumber.equalsIgnoreCase("R") && !wednesdayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			wednesdayStartTime = wednesdayArray[3];
			wednesdayEndTime = wednesdayArray[4];
			}
			
			String wednesdayTitle = "";
			
			if(wednesdayArray.length == 3)
				wednesdayTitle = wednesdayShiftNumber;
			else if(wednesdayArray.length == 5)
				wednesdayTitle = wednesdayShiftNumber + " " + wednesdayStartTime + "-" + wednesdayEndTime;
			else if(wednesdayModifier != "-1")
				wednesdayTitle = wednesdayProfession + " " + wednesdayLocation + " " + wednesdayModifier + "" + wednesdayShiftNumber + " " + wednesdayStartTime + "-" + wednesdayEndTime;
			else 
				wednesdayTitle = wednesdayProfession + " " + wednesdayLocation + " " + wednesdayShiftNumber + " " + wednesdayStartTime + "-" + wednesdayEndTime;

			Logger.debug(TAG, wednesdayTitle);
			
			// Tenth line - fourth day
			
			String thursdayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String thursdayModifier = "-1";
			String[] thursdayArray = thursdayLine.split(" ");
			if (thursdayArray[2].equals("!") || thursdayArray[2].equals("@") || thursdayArray[2].equals(">") || thursdayArray[2].equals("<")
					|| thursdayArray[2].equals("*") || thursdayArray[2].equals("?") || thursdayArray[2].equals("E") || thursdayArray[2].equals("#") || thursdayArray[2].equals("$")) { //Check if we have modifiers
				thursdayModifier = thursdayArray[2];
				for (int i = 2; i < thursdayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					thursdayArray[i] = thursdayArray[i + 1];
				}
			}
			
			Shift thursdayShift = new Shift(); //TODO everything..
			Calendar thursday = Calendar.getInstance();
			thursday.set(Calendar.YEAR, yearNumber);
			thursday.set(Calendar.MONTH, Integer.parseInt(thursdayArray[1].split("-")[1]));
			thursday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(thursdayArray[1].split("-")[0]));
			String thursdayProfession = "";
			String thursdayLocation = "";
			if(thursdayArray.length > 5) { // Things like CURS don't have a function etc listed
			thursdayProfession = thursdayArray[5].substring(0, 1).toUpperCase() + thursdayArray[5].substring(1).toLowerCase();
			thursdayLocation = thursdayArray[6].substring(0, 1).toUpperCase() + thursdayArray[6].substring(1).toLowerCase();
			}
			String thursdayShiftNumber = thursdayArray[2];
			String thursdayStartTime = "";
			String thursdayEndTime = "";
			if(!thursdayShiftNumber.equalsIgnoreCase("R") && !thursdayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			thursdayStartTime = thursdayArray[3];
			thursdayEndTime = thursdayArray[4];
			}
			
			String thursdayTitle = "";
			
			if(thursdayArray.length == 3)
				thursdayTitle = thursdayShiftNumber;
			else if(thursdayArray.length == 5)
				thursdayTitle = thursdayShiftNumber + " " + thursdayStartTime + "-" + thursdayEndTime;
			else if(thursdayModifier != "-1")
				thursdayTitle = thursdayProfession + " " + thursdayLocation + " " + thursdayModifier + "" + thursdayShiftNumber + " " + thursdayStartTime + "-" + thursdayEndTime;
			else 
				thursdayTitle = thursdayProfession + " " + thursdayLocation + " " + thursdayShiftNumber + " " + thursdayStartTime + "-" + thursdayEndTime;

			Logger.debug(TAG, thursdayTitle);
			
			// Eleventh line - fifth day
			
			String fridayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String fridayModifier = "-1";
			String[] fridayArray = fridayLine.split(" ");
			if (fridayArray[2].equals("!") || fridayArray[2].equals("@") || fridayArray[2].equals(">") || fridayArray[2].equals("<")
					|| fridayArray[2].equals("*") || fridayArray[2].equals("?") || fridayArray[2].equals("E") || fridayArray[2].equals("#") || fridayArray[2].equals("$")) { //Check if we have modifiers
				fridayModifier = fridayArray[2];
				for (int i = 2; i < fridayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					fridayArray[i] = fridayArray[i + 1];
				}
			}
			
			Shift fridayShift = new Shift(); //TODO everything..
			Calendar friday = Calendar.getInstance();
			friday.set(Calendar.YEAR, yearNumber);
			friday.set(Calendar.MONTH, Integer.parseInt(fridayArray[1].split("-")[1]));
			friday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fridayArray[1].split("-")[0]));
			String fridayProfession = "";
			String fridayLocation = "";
			if(fridayArray.length > 5) { // Things like CURS don't have a function etc listed
			fridayProfession = fridayArray[5].substring(0, 1).toUpperCase() + fridayArray[5].substring(1).toLowerCase();
			fridayLocation = fridayArray[6].substring(0, 1).toUpperCase() + fridayArray[6].substring(1).toLowerCase();
			}
			String fridayShiftNumber = fridayArray[2];
			String fridayStartTime = "";
			String fridayEndTime = "";
			if(!fridayShiftNumber.equalsIgnoreCase("R") && !fridayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			fridayStartTime = fridayArray[3];
			fridayEndTime = fridayArray[4];
			}
			
			String fridayTitle = "";
			
			if(fridayArray.length == 3)
				fridayTitle = fridayShiftNumber;
			else if(fridayArray.length == 5)
				fridayTitle = fridayShiftNumber + " " + fridayStartTime + "-" + fridayEndTime;
			else if(fridayModifier != "-1")
				fridayTitle = fridayProfession + " " + fridayLocation + " " + fridayModifier + "" + fridayShiftNumber + " " + fridayStartTime + "-" + fridayEndTime;
			else 
				fridayTitle = fridayProfession + " " + fridayLocation + " " + fridayShiftNumber + " " + fridayStartTime + "-" + fridayEndTime;

			Logger.debug(TAG, fridayTitle);
			
			// Twelveth line - sixth day
			
			String saturdayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String saturdayModifier = "-1";
			String[] saturdayArray = saturdayLine.split(" ");
			if (saturdayArray[2].equals("!") || saturdayArray[2].equals("@") || saturdayArray[2].equals(">") || saturdayArray[2].equals("<")
					|| saturdayArray[2].equals("*") || saturdayArray[2].equals("?") || saturdayArray[2].equals("E") || saturdayArray[2].equals("#") || saturdayArray[2].equals("$")) { //Check if we have modifiers
				saturdayModifier = saturdayArray[2];
				for (int i = 2; i < saturdayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					saturdayArray[i] = saturdayArray[i + 1];
				}
			}
			
			Shift saturdayShift = new Shift(); //TODO everything..
			Calendar saturday = Calendar.getInstance();
			saturday.set(Calendar.YEAR, yearNumber);
			saturday.set(Calendar.MONTH, Integer.parseInt(saturdayArray[1].split("-")[1]));
			saturday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(saturdayArray[1].split("-")[0]));
			String saturdayProfession = "";
			String saturdayLocation = "";
			if(saturdayArray.length > 5) { // Things like CURS don't have a function etc listed
			saturdayProfession = saturdayArray[5].substring(0, 1).toUpperCase() + saturdayArray[5].substring(1).toLowerCase();
			saturdayLocation = saturdayArray[6].substring(0, 1).toUpperCase() + saturdayArray[6].substring(1).toLowerCase();
			}
			String saturdayShiftNumber = saturdayArray[2];
			String saturdayStartTime = "";
			String saturdayEndTime = "";
			if(!saturdayShiftNumber.equalsIgnoreCase("R") && !saturdayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			saturdayStartTime = saturdayArray[3];
			saturdayEndTime = saturdayArray[4];
			}
			
			String saturdayTitle = "";
			
			if(saturdayArray.length == 3)
				saturdayTitle = saturdayShiftNumber;
			else if(saturdayArray.length == 5)
				saturdayTitle = saturdayShiftNumber + " " + saturdayStartTime + "-" + saturdayEndTime;
			else if(saturdayModifier != "-1")
				saturdayTitle = saturdayProfession + " " + saturdayLocation + " " + saturdayModifier + "" + saturdayShiftNumber + " " + saturdayStartTime + "-" + saturdayEndTime;
			else 
				saturdayTitle = saturdayProfession + " " + saturdayLocation + " " + saturdayShiftNumber + " " + saturdayStartTime + "-" + saturdayEndTime;

			Logger.debug(TAG, saturdayTitle);
			
			// Thirteenth line - seventh day
			
			String sundayLine = scanner.nextLine().replaceAll("\\s+", " ");
			String sundayModifier = "-1";
			String[] sundayArray = sundayLine.split(" ");
			if (sundayArray[2].equals("!") || sundayArray[2].equals("@") || sundayArray[2].equals(">") || sundayArray[2].equals("<")
					|| sundayArray[2].equals("*") || sundayArray[2].equals("?") || sundayArray[2].equals("E") || sundayArray[2].equals("#") || sundayArray[2].equals("$")) { //Check if we have modifiers
				sundayModifier = sundayArray[2];
				for (int i = 2; i < sundayArray.length - 1; i++) { //If so, get rid of them but save them. They mess things up later on
					sundayArray[i] = sundayArray[i + 1];
				}
			}
			
			Shift sundayShift = new Shift(); //TODO everything..
			Calendar sunday = Calendar.getInstance();
			sunday.set(Calendar.YEAR, yearNumber);
			sunday.set(Calendar.MONTH, Integer.parseInt(sundayArray[1].split("-")[1]));
			sunday.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sundayArray[1].split("-")[0]));
			String sundayProfession = "";
			String sundayLocation = "";
			if(sundayArray.length > 5) { // Things like CURS don't have a function etc listed
			sundayProfession = sundayArray[5].substring(0, 1).toUpperCase() + sundayArray[5].substring(1).toLowerCase();
			sundayLocation = sundayArray[6].substring(0, 1).toUpperCase() + sundayArray[6].substring(1).toLowerCase();
			}
			String sundayShiftNumber = sundayArray[2];
			String sundayStartTime = "";
			String sundayEndTime = "";
			if(!sundayShiftNumber.equalsIgnoreCase("R") && !sundayShiftNumber.equalsIgnoreCase("streepjesdag")) {
			sundayStartTime = sundayArray[3];
			sundayEndTime = sundayArray[4];
			}
			
			String sundayTitle = "";
			
			if(sundayArray.length == 3)
				sundayTitle = sundayShiftNumber;
			else if(sundayArray.length == 5)
				sundayTitle = sundayShiftNumber + " " + sundayStartTime + "-" + sundayEndTime;
			else if(sundayModifier != "-1")
				sundayTitle = sundayProfession + " " + sundayLocation + " " + sundayModifier + "" + sundayShiftNumber + " " + sundayStartTime + "-" + sundayEndTime;
			else 
				sundayTitle = sundayProfession + " " + sundayLocation + " " + sundayShiftNumber + " " + sundayStartTime + "-" + sundayEndTime;

			Logger.debug(TAG, sundayTitle);
			Logger.debug(TAG, "//////////// END OF WEEK ////////////");
			
			Logger.log(TAG, "Finished scanning "+toRead.getAbsolutePath());
			
			
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if(!f.renameTo(new File("input/#USED DW"+weekNumber+" "+yearNumber+" "+staffNumber+".txt"))) 
			f.delete();*/
	}
}
