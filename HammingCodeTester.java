

public class HammingCodeTester {
    
    public static void main(String args[]){
        
        char B = 'i';
        String BitString = HammingCodeGenerator.translaterToBitString(B);
        BitString = "1100";
        System.out.println(BitString);
        System.out.println(HammingCodeGenerator.generateCodeWord(BitString));


    }



}
