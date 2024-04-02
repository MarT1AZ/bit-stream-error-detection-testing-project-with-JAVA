import java.lang.Math;


public class HammingCodeGenerator {

    public static boolean XOR(char c1,char c2){
        boolean B1 = (c1 == 1);
        boolean B2 = (c2 == 1);
        return ((B1 && !B2) || (!B1 && B2));
    }

    public static boolean XOR(boolean B1,boolean B2){
        return ((B1 && !B2) || (!B1 && B2));
    }
    
    public static String translaterToBitString(char alphabet){

        int value = (int) alphabet;

        String bitString = "";



        while(value != 0){ // get the bitstring
            bitString = (value % 2) + bitString;
            value = value/2;
        }

        // while (bitString.length() != 8) { //padding on the front with 0 if the length of the string is less than 8
        //     bitString = "0" + bitString;
        // }

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




        for(int i = 0;i < paritySize + dataWordSB.length();i++){
            if(i == 0 || i == 1 || i == 3 || i == 7){
                codeWord.append("p");
            }else{
                codeWord.append(dataWordSB.charAt(dataIndex));
                dataIndex += 1;
            }
        }

        // System.out.println("\n\n" + codeWord.reverse() + "\n\n");

        for(int i = 1,j,power;i <= paritySize;i++){
            power = ((int) Math.pow(2,i - 1));
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
