import java.lang.Math;


public class HammingCodeGenerator {

    public static boolean XOR(boolean B1,boolean B2){

        // XOR method for 2 boolean input

        return ((B1 && !B2) || (!B1 && B2));
    }
    
    public static String translaterToBitString(char alphabet){

        // method for turning alphabet into bitString

        int value = (int) alphabet;

        String bitString = "";

        while(value != 0){ // get the bitstring
            bitString = (value % 2) + bitString;
            value = value/2;
        }

        return bitString;

    }

    public static String generateCodeWord(String dataWord){

        // find the number of parity bit
        // 2^p >= d + p + 1
        // p is the number of parity bit
        // d is length of dataword

        StringBuilder dataWordSB = new StringBuilder(dataWord);
        // since the data format is in D7, D6, D5, ... , D2, D1
        // the data has to be reversed in order to make it more easier to code the method
        dataWordSB.reverse();

        int paritySize = 4;
        int dataIndex = 0;
        StringBuilder codeWord = new StringBuilder("");
        boolean parity = false;

        // i is the index of parity bit
        for(int i = 0;i < paritySize + dataWordSB.length();i++){
            if(i == 0 || i == 1 || i == 3 || i == 7){
                codeWord.append("p");
            }else{
                codeWord.append(dataWordSB.charAt(dataIndex));
                dataIndex += 1;
            }
        }
        // now codeword is looking like this 1100p010p1pp

        // the next task is to put the parity bit into 'p'
        // k = the power index
        // power = 2 to the power of k
        // j = index for calculating the range of parity bit coverages of each power (2^k)
        // p is for looping though the parity bit coverage
        // (lower bound : upper bound) = (k : 2k - 1), (3k : 4k - 1), (7k : 8k - 1),...
        // (lower bound : upper bound) = ((2j - 1)k : (2jk - 1)) // with the variables
        for(int k = 1,j,power;k <= paritySize;k++){
            power = ((int) Math.pow(2,k - 1));
            parity = false;
            j = 1;
            while ((2*j - 1) * power  <= paritySize + dataWord.length()){
                for(int p = (2*j - 1) * power ;p <= (2*j) * power  - 1 && p <= codeWord.length();p++){
                    if(p == power){
                        continue;
                    }
                    parity = XOR(parity,codeWord.substring(p - 1, p).equals("1"));
                }
                j++;
            }
            codeWord.setCharAt(power - 1, parity?'1':'0');
        }
        

        return codeWord.reverse().toString();

    }




}
