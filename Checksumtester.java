import java.util.ArrayList;

public class Checksumtester {
    public static void main(String[] args) {
//    	System.out.println("Hello world");
    	System.out.println("|---------------- @ GENERATOR ---------------|");
    	ArrayList<String> codeword =  ChecksumGenerator.Checksum_gen("File1.txt");
    	System.out.println("|------------- END OF GENERATOR -------------|");
    	
    	System.out.println();
    	
    	String checksum = codeword.get(codeword.size()-1);
    	System.out.println("|---------------- @ CHECKER ---------------|");
    	boolean validity =  ChecksumGenerator.Checksum_check("File2.txt", checksum);
    	
    	System.out.println("|------------- END OF CHECKER -------------|");
    	
    	System.out.println();
    	
    	System.out.println("| | | | | | | | | | | | | | | |");
    	if(validity) {
    		System.out.println("The received data is correct.");
    	}
    	else {
    		System.out.println("The received data is incorrect!");
    	}
    	System.out.println("| | | | | | | | | | | | | | | |");
    }
}
