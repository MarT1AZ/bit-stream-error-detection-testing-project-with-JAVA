import java.util.ArrayList;

public class Checksumtester {
    public static void main(String[] args) {
//    	System.out.println("Hello world");
    	System.out.println("|---------------- @ GENERATOR ---------------|");
    	ArrayList<String> codeword =  ChecksumGenerator.Checksum_gen("File1 (sent).txt ");
    	System.out.println("|------------- END OF GENERATOR -------------|");
    	
    	System.out.println();
    	
    	String checksum = codeword.get(codeword.size()-1);
    	System.out.println("|---------------- @ CHECKER ---------------|");
    	int validity =  ChecksumGenerator.Checksum_check("File1 (recieve).txt", checksum);
    	
    	System.out.println("|------------- END OF CHECKER -------------|");
    	
    	System.out.println();
    	
    	System.out.println("| | | | | | | | | | | | | | | |");
    	if(validity == 0) {
    		System.out.println("The received data is correct.");
    	}
    	else {
    		System.out.println("The received data is incorrect!");
    	}
    	System.out.println("| | | | | | | | | | | | | | | |\n\n\n");


		System.out.println("|---------------- @ GENERATOR ---------------|");
    	codeword =  ChecksumGenerator.Checksum_gen("File2 (sent).txt");
    	System.out.println("|------------- END OF GENERATOR -------------|");
    	
    	System.out.println();
    	
    	checksum = codeword.get(codeword.size()-1);
    	System.out.println("|---------------- @ CHECKER ---------------|");
    	validity =  ChecksumGenerator.Checksum_check("File2 (recieve).txt", checksum);
    	
    	System.out.println("|------------- END OF CHECKER -------------|");
    	
    	System.out.println();
    	
    	System.out.println("| | | | | | | | | | | | | | | |");
    	if(validity == 0) {
    		System.out.println("The received data is correct.");
    	}
    	else {
    		System.out.println("The received data is incorrect!");
    	}
    	System.out.println("| | | | | | | | | | | | | | | |\n\n\n");

		System.out.println("|---------------- @ GENERATOR ---------------|");
    	codeword =  ChecksumGenerator.Checksum_gen("File3 (sent).txt");
    	System.out.println("|------------- END OF GENERATOR -------------|");
    	
    	System.out.println();
    	
    	checksum = codeword.get(codeword.size()-1);
    	System.out.println("|---------------- @ CHECKER ---------------|");
    	validity =  ChecksumGenerator.Checksum_check("File3 (recieve).txt", checksum);
    	
    	System.out.println("|------------- END OF CHECKER -------------|");
    	
    	System.out.println();
    	
    	System.out.println("| | | | | | | | | | | | | | | |");
    	if(validity == 0) {
    		System.out.println("The received data is correct.");
    	}
    	else {
    		System.out.println("The received data is incorrect!");
    	}
    	System.out.println("| | | | | | | | | | | | | | | |\n\n\n");
    }
}
