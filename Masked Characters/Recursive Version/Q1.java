/*	A program that takes as input a string of any length of random number of binary characters “0 “ and “1”
	and a masked “*” character at some positions, and find all possible sequences of binary strings
	that can be construct by replacing the masked ”*” character by either 0 or 1.
	Written by Liam Carlisle, September 28 2020.
	A programming assignment for the Data Structures and Algorithms (COMP 352) course at Concordia University Montreal.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Q1 {
	static void RevealStr(String in){
		StringBuilder inB = new StringBuilder(in); //StringBuilder has useful .indexOf method
		StringBuilder sb0 = new StringBuilder(in); //1st new string copy if input has *
		StringBuilder sb1 = new StringBuilder(in); //2nd new string copy if input has *
			if (sb0.indexOf("*")==-1 && sb1.indexOf("*")==-1) { //if string enters without *s, print
				System.out.println(sb0.toString());
				try { //print to out.txt file
					FileWriter output = new FileWriter("out.txt", true);
					PrintWriter print_line = new PrintWriter(output);
					print_line.printf( "%s" + "%n" ,sb0.toString());
					print_line.close();
								
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error in FileWriter.");
				}
			}
			if (!(inB.indexOf("*")==-1)) { //if string enters with *s
				int asteriskAt = inB.indexOf("*"); //location of * in string		
				sb0.deleteCharAt(asteriskAt); //replace * with 0 in 1st new string
				sb0.insert(asteriskAt, '0');		
				sb1.deleteCharAt(asteriskAt); //replace * with 1 in 2nd new string
				sb1.insert(asteriskAt, '1');
				RevealStr(sb0.toString()); //run function recursively on first string to check
								//for further *s and replace if they exist.
				RevealStr(sb1.toString()); //run function recursively on first string to check
								//for further *s and replace if they exist.
			}	
	}
	
	public static void main(String args[]) {

		

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
				System.out.println("It will have "+numberOfAsterisksForTest+" asterisks.");
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
				System.out.println("String to test is: \n"+stringToTest.toString()+".\n");
				System.out.println("Output is:");
				long startTime = System.currentTimeMillis();
				RevealStr(stringToTest.toString());
				long endTime = System.currentTimeMillis();
				long elapsed = endTime - startTime;
				System.out.println("\nTime taken to execute algorithm was "+elapsed+" milliseconds.");
			}
	}
	

	
	

