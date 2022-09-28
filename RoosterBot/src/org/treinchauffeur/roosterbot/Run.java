package org.treinchauffeur.roosterbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.treinchauffeur.roosterbot.misc.FileTools;
import org.treinchauffeur.roosterbot.misc.Logger;

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
	 * Line indexing: 0=days in text, 1=date in 31-12 format, 2=shift number, 3=starttime formatted to 24:60, 4=endtime formatted to 24:60, 5=profession, 6=location
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
			
			//TODO load more data than just modifier. Was a pain in the back-side though.
			
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if(!f.renameTo(new File("input/#USED DW"+weekNumber+" "+yearNumber+" "+staffNumber+".txt"))) 
			f.delete();*/
	}
}
