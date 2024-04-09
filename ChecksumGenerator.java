import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class ChecksumGenerator {
	
	public static StringBuilder calculate(int carry, int maxLength, String binary1, String binary2) {
		StringBuilder result = new StringBuilder();
		for(int i = maxLength-1 ; i>=0 ; i--) {
	    	 int sum = 0;
	    	 int bit1 = (binary1.charAt(i) - '0');
	    	 int bit2 = (binary2.charAt(i) - '0');
	    	 sum = sum + bit1 + bit2 + carry;
	    	 carry = sum/2;
	    	 result.insert(0, sum%2);

	     } 
		 if(carry == 1) {
	    	 StringBuilder buffer;     
			 String carry8bit = eightBit(Integer.toString(carry));
			 carry = 0;
			 buffer = calculate(carry, maxLength, result.toString(), carry8bit);
			 result = buffer;

	     }
		return result;
	}
	
	public static String binaryAdder(String binary1, String binary2) {

		 StringBuilder result = new StringBuilder();
	     int carry = 0;
	     //The instruction assume that the length of every binary bit us 8 bits
	     int maxLength = binary1.length();
	     result = calculate(carry, maxLength, binary1, binary2); 

	     return result.toString();

	}
	

	public static String eightBit(String binary) {
		binary = "0".repeat(8 - binary.length()) + binary;
		return binary;
	}
	
	public static String generateComplement(String binaryNumber) {
	    StringBuilder complement = new StringBuilder();
	    for (int i = 0; i < binaryNumber.length(); i++) {
	        char digit = binaryNumber.charAt(i);
	        if (digit == '0') {
	            complement.append('1');
	        } else if (digit == '1') {
	            complement.append('0');
	        } else {
	            // Handle invalid input
	            throw new IllegalArgumentException("Input must be a binary number (0s and 1s)");
	        }
	    }
	    return complement.toString();
	}
	
	public static String readFile(String fileName) {
		String text = new String();
    	File file = new File(fileName);
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine())
			{	
                text += scan.nextLine();
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return text;	
	}
	/* @GENERATOR PART */
    public static ArrayList<String> Checksum_gen(String fileName)  {
    	ArrayList<String> binaryStrings = new ArrayList<String>();
    	System.out.println("-- Welcome to Checksum Generator function --");
    	
        /* START of Read Text File */
    	String text = readFile(fileName);
		/* END of Read Text File */
		
		/* START of Transform Character to Binary string */
		String binary ="";
		for(int i=0 ; i< text.length()  ; i++) {
			binary = Integer.toBinaryString(text.charAt(i));
			if(binary.length() != 8){
			    binary = eightBit(binary);
			}
			if(text.charAt(i) == ' ') {
				System.out.println("\n" + "SPACE" + " : " + binary);
			}
			else {
				System.out.print(text.charAt(i) + ": " + binary + " | ");
			}			
			binaryStrings.add(binary);
		}
		/* END of Transform Character to Binary string */
		
		String sum = binaryStrings.get(0);
		 // Perform binary addition for each remaining binary string
		System.out.println();
        for (int i = 1; i < binaryStrings.size(); i++) {
            sum = binaryAdder(sum, binaryStrings.get(i));
        }       
        System.out.println("*******************************");
        System.out.println("Text: " + text);
        System.out.println("Sum: " + sum);
        System.out.println("Complement (Checksum): " + generateComplement(sum));
        System.out.println("*******************************");
        binaryStrings.add(generateComplement(sum));
        return binaryStrings;
    }
    /**/
    
    /* @CHECKER PART */
	public static int Checksum_check(String filename, String checksum_value) {
		ArrayList<String> binaryStrings = new ArrayList<String>();
    	System.out.println("-- Welcome to Checksum Checker function --");
    	
        /* START of Read Text File */
    	String text = readFile(filename);
		/* END of Read Text File */

		String binary ="";
		for(int i=0 ; i< text.length()  ; i++) {
			binary = Integer.toBinaryString(text.charAt(i));
			if(binary.length() != 8){
			    binary = eightBit(binary);
			}
			binaryStrings.add(binary);
		}

		String sum = binaryStrings.get(0);
		for (int i = 1; i < binaryStrings.size(); i++) {
            sum = binaryAdder(sum, binaryStrings.get(i));
        } 
		sum = binaryAdder(sum, checksum_value);
		// String sum = binaryStrings.get(0);
		 // Perform binary addition for each remaining binary string
		System.out.println();
   
       System.out.println("**************************************************");
       System.out.println("* Text: " + text);
       System.out.println("* Sum: " + sum);
       System.out.println("* Complement (Checksum): " + generateComplement(sum));
       System.out.println("**************************************************");

       if(generateComplement(sum).equals("00000000")) {
    	   return 0;
       }
       return -1;		
	}
    /**/
}