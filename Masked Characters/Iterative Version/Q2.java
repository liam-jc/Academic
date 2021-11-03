/*	A program that takes as input a string of any length of random number of binary characters “0 “ and “1”
	and a masked “*” character at some positions, and finds all possible sequences of binary strings
	that can be constructed by replacing the masked ”*” character by either 0 or 1.
	Output is redirected to an out.txt file.
	Written by Liam Carlisle, September 28 2020.
	A programming assignment for the Data Structures and Algorithms (COMP 352) course at Concordia University Montreal.
*/

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Q2{
	static void RevealStr(String in){
		ArrayList<Character> inputCopy = new ArrayList<>();
		for (int i = 0; i<in.length(); i++) {
			inputCopy.add(in.charAt(i)); //copies input string into character array
		}
		int asteriskAmt = 0;
		ArrayList<Integer> asteriskIndexes = new ArrayList<Integer>();
		for (int i = 0; i < in.length(); i++){ //gets # of *s and indexes of *s
			if (in.charAt(i) == '*') {
				asteriskAmt++; //count number of *s in string
				asteriskIndexes.add(i); //record index of * in string
			}
		}
		int amtProperNewStrings = (int)(Math.pow(2, asteriskAmt)); //number of strings to output is 2^(number of asterisks)
		int amtNewStrings = 0; //total amount of strings that will be produced. sum of i=0 to #*s of 2^i:
		for (int i=0; i<=asteriskAmt; i++) {
			amtNewStrings+=(int)(Math.pow(2, i));
		}

		ArrayList<List<Character>> arraysForOutput = new ArrayList<>();//ArrayList of ArrayLists which contain copies of original string
		arraysForOutput.add((List<Character>) inputCopy.clone()); //set first element of arraysForOutput to original string
		//search input string for *s and branch 2 copies when found, one replacing * with 0 & one replacing * with 1
		int arraysForOutputIndex = 1; //starts at 1 because the first array you want to access is the first copied/altered array
		for (int j = 0; j<amtNewStrings; j++) {
			for (int i=0; i<in.length(); i++) {
				if (arraysForOutput.get(j).get(i) == '*') {
					arraysForOutput.add((List<Character>) ((ArrayList) arraysForOutput.get(j)).clone()); //clone input "string" [inputCopy] to arraysForOutput
					arraysForOutput.get(arraysForOutputIndex).set(i, '0'); //sets '0' in copied arrayList
					arraysForOutputIndex++; //will now be used to reference second clone, which will set '1'
					arraysForOutput.add((List<Character>) ((ArrayList) arraysForOutput.get(j)).clone()); //clone input "string" [inputCopy] to arraysForOutput
					arraysForOutput.get(arraysForOutputIndex).set(i, '1'); //sets '1' in copied arrayList
					arraysForOutputIndex++;
					break;
				}
			}
		}

		System.out.println("\n-------------\nOriginal string was: \n"+in+"\n");
		System.out.println("Number of * is "+asteriskAmt+" at indexes "+asteriskIndexes+".\n");
		System.out.println("Output strings are:");
		for (int i=(arraysForOutput.size() - amtProperNewStrings); i<arraysForOutput.size(); i++) {
			for (int j=0; j<in.length(); j++) {
				System.out.print(arraysForOutput.get(i).get(j));
			}
			System.out.println("");
		}

		try { //print to out.txt file
			FileWriter output = new FileWriter("out.txt", false);
			PrintWriter print_line = new PrintWriter(output);
			

			for (int i=(arraysForOutput.size() - amtProperNewStrings); i<arraysForOutput.size(); i++) {
				for (int j=0; j<in.length(); j++) {
					print_line.printf( "%s" ,arraysForOutput.get(i).get(j));
				}
				print_line.printf( "%s" + "%n" ,"");
			}
			print_line.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in FileWriter.");
		}
	}


	public static void main (String args[]) {
	//this part is to generate test string:
		StringBuilder stringToTest = new StringBuilder();
		int numberOfAsterisksForTest = 2; //change this to test different number of *s. Must be < or == testStringActualLength.
		int testStringActualLength = 4;
	//to specify string length, activate above and deactivate below.
	//to randomly generate string length change above line to following:
	/*--
		int testStringMaxLength = 100; //change max length of test string here
		int testStringActualLength = numberOfAsterisksForTest + (int)(Math.random()*(testStringMaxLength-numberOfAsterisksForTest)); //random number for length of test string
	*/
		System.out.println("String length will be "+testStringActualLength);
		for (int i=0; i<testStringActualLength; i++) {// creates the test string of random length, populates with 0s and 1s randomly
			double chooser = Math.random();
			if (chooser <.5) {
				stringToTest.append("0");
			}
			else stringToTest.append("1");
		}
		int asteriskPlacement;
		int[] allAsteriskPlacements = new int[numberOfAsterisksForTest];
		for (int i=0; i<numberOfAsterisksForTest; i++) { //populate asterisk location array
			allAsteriskPlacements[i] = 0;
		}
		int[] indexesToChooseFrom = new int[testStringActualLength];
		boolean[] indexHasBeenChosen = new boolean[testStringActualLength];
		for (int i=0; i<testStringActualLength; i++) { //fill indexesToChooseFrom with incrementing ints
			indexesToChooseFrom[i] = i;
			indexHasBeenChosen[i] = false; //prepares array for testing duplicates
		}
		for (int i=0; i<numberOfAsterisksForTest; i++) {
			boolean isDuplicate = true;
			while (isDuplicate) {
				asteriskPlacement = (int)(Math.random()*testStringActualLength); //generates potential index for asterisk in string
				if (indexHasBeenChosen[asteriskPlacement] == false) {
					allAsteriskPlacements[i] = asteriskPlacement;
					indexHasBeenChosen[asteriskPlacement] = true;
					isDuplicate = false;
				}
			}
		}

/*		//used for debugging
 		System.out.println("number of asterisks chosen is "+numberOfAsterisksForTest);
		System.out.print("asterisks will be at indexes ");
		for (int i=0; i<numberOfAsterisksForTest; i++) {
			System.out.print(allAsteriskPlacements[i]+" ");
		}
*/
		for (int i=0; i<numberOfAsterisksForTest; i++) {//replace 0s and 1s in stringToTest with *s at random indexes
			stringToTest.replace(allAsteriskPlacements[i], allAsteriskPlacements[i]+1, "*");
		}


	//this part is to run test:
		long startTime = System.currentTimeMillis();
		RevealStr(stringToTest.toString());
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		System.out.println("\nTime taken to execute algorithm was "+elapsed+" milliseconds.");
	}
}
