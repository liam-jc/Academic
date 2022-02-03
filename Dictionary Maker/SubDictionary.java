/*	A program that takes as input a .txt file and generates an alphabetized list of the words it contains.
 *      It will ignore punctuation, numbers, and duplicate words. An example input PersonOfTheCentury.txt is included in folder.
 * 	Output is redirected to the file SubDictionary.txt.
 *	Written by Liam Carlisle, April 19 2020.
 *	A programming assignment for the OOP II (COMP 249) course at Concordia University Montreal.
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
public class SubDictionary {
	public static void main (String[] args) {
		String[] headerLetters = {"A", "B", "C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		String outputFilename = "SubDictionary.txt";
		
	//get name of txt
		Scanner fileNameInputScanner = new Scanner(System.in);
		System.out.println("Welcome to the dictionary generator!\nPlease enter input file name: ");
		String x = fileNameInputScanner.next();
		fileNameInputScanner.close();
		ArrayList<String> dictionary = new ArrayList<String>();
		String[] bannedPunctuation = {",", "?", ":", "'", "’", "�", ",", "=", ";", "!", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		
	//read from txt
		try {
			Scanner txtInputScanner = new Scanner(new FileInputStream(x));
			while (txtInputScanner.hasNext()){
				String inputWord = (txtInputScanner.next()).toUpperCase();
				for (int i =0; i<bannedPunctuation.length; i++) { //iterate through array of banned punctuation to remove any from inputWord
					if (inputWord.contains(bannedPunctuation[i])) {
						int punctuationAt = inputWord.indexOf(bannedPunctuation[i]);
						inputWord = inputWord.substring(0, punctuationAt);
					}
				}	
				//check for duplicates
				boolean isDuplicate = false;
				for (int i = 0; i<dictionary.size(); i++) {
					if (inputWord.matches(dictionary.get(i))){
						isDuplicate = true;
					}
				}
				//if word is found suitable, add to dictionary
				if (!isDuplicate && ((inputWord.length() > 1) || inputWord.contentEquals("I") || inputWord.contentEquals("A"))) {
					dictionary.add(inputWord);
				}
			}
			txtInputScanner.close();
		}
		catch(Exception e) {
			System.out.println("Error in txt read scanner: " + e);
		}
		Collections.sort(dictionary); //sort alphabetically
		try { //write to file
			PrintWriter output = new PrintWriter(new FileOutputStream(outputFilename));
			output.println("The document produced this sub-dictionary, which includes "+dictionary.size()+" entries.");				
			for (int i=0; i<headerLetters.length; i++) {	
				output.println("\n"+headerLetters[i]+"\n***");
				for (int j = 0; j<dictionary.size(); j++) {
					if (dictionary.get(j).startsWith(headerLetters[i])) {
						output.println(dictionary.get(j));
					}
				}
			}
			output.close();
		}
		catch(Exception e) {
			System.out.println("Error txt write: "+e);
		}
		System.out.println("Dictionary generated from input file \""+x+"\" may now be found in "+outputFilename+"."
				+"\nProgram has finished and will now terminate normally.");
	}
}
