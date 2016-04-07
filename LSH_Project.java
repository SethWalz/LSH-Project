/*
Michael Walz | 700609721
Tyler Gaskey | 700603897
Alyssa Ringhausen | 700578441
Marissa Blanton | Unknown

Write a Java program to implement LSH as given in section 3.4.3 of the textbook
*/
import java.io.*;
import java.util.*;

public class LSH_Project {

	public static void main(String[] args) throws Exception {

		// Create Token
		String test = "";
		String test2 = "";

		// first scanner
		Scanner in = new Scanner(new File("foo.txt"));
		while(in.hasNextLine()){
			test += in.nextLine();
		}

		// first scanner
		Scanner in2 = new Scanner(new File("foo2.txt"));
				while(in2.hasNextLine()){
					test2 += in2.nextLine();
		}

		// declaring hashsets
		HashSet<String> shingles = new HashSet<String>();
		HashSet<String> shingles2 = new HashSet<String>();
		int shingleLength = 5;

		// splitting each string
		String[] split = test.split("\\s+");
		String[] split2 = test2.split("\\s+");

		String temp = "";
		for(int i = 0; i < split.length - shingleLength + 1; i++){
			for(int j = i; j < shingleLength + i; j++){
				temp += split[j] + " ";
			}
			shingles.add(temp);

			temp = "";
		}
		// second hashset
		String temp2 = "";
		for(int i = 0; i < split2.length - shingleLength + 1; i++){
					for(int j = i; j < shingleLength + i; j++){
						temp2 += split2[j] + " ";
					}
					shingles2.add(temp2);

					temp2 = "";
		}

		// test output for shingles
		System.out.println("First Set of Shingles: \n");
		for (String s: shingles) {
			System.out.println(s);
		}
		System.out.println("\nSecond Set of Shingles: \n");
		for (String s: shingles2) {
					System.out.println(s);
		}



	}

}