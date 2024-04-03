import java.util.Arrays;

public class CRCgenerator {

    public static boolean XOR(boolean B1,boolean B2){
        return ((B1 && !B2) || (!B1 && B2));
    }

    public static boolean[] XORbooleanArrays(boolean[] arr1,boolean[] arr2){
        // the first bit from 2 arrays arent being used in XOR process

        //        index of arrays :  0 1 2 3
        //                   arr1 :  1 0 0 1    
        //                   arr2 :  1 1 0 1
        //                 output :    1 0 0 x
        //        index of output :    0 1 2 3
        // 
        // NOTE: x come from original data


        boolean[] output = new boolean[arr1.length];
        for(int i = 1;i < arr1.length;i++){ // XOR 2 bits except those 2 bit in the first position
            output[i - 1] = XOR(arr1[i],arr2[i]);
        }
        return output;
    }

    public static boolean[] XORarrayAndZeros(boolean[] arr1){
        boolean[] output = new boolean[arr1.length];
        for(int i = 1;i < arr1.length;i++){ // XOR 2 bits except those 2 bit in the first position
            output[i - 1] = XOR(arr1[i],false);
        }
        return output;
    }
    
    public static boolean[] generateCodeWord(boolean[] dataWord,boolean[] CRC){
        
        boolean[] divisor = CRC;

        int windowEndIndex = divisor.length - 1;


        // initialize dividendWindow
        boolean[] dividendWindow = new boolean[divisor.length];
        for(int i = 0;i < divisor.length;i++){
            if(i < dataWord.length){
                dividendWindow[i] = dataWord[i]; //bit from dataword
            }else{
                dividendWindow[i] = false; // extra 0
            }
        }
        // initialize dividendWindow

        // perform modulo-2 division using XOR operation
        while(windowEndIndex < dataWord.length + divisor.length - 1){

            if(windowEndIndex < dataWord.length){
                dividendWindow[divisor.length - 1] = dataWord[windowEndIndex];
            }else{
                dividendWindow[divisor.length - 1] = false;
            }
            
            if(dividendWindow[0]){
                dividendWindow = XORbooleanArrays(dividendWindow,divisor);
            }else{
                dividendWindow = XORarrayAndZeros(dividendWindow);
            }
            
            windowEndIndex += 1;
        }
        // perform modulo-2 division using XOR operation

        // dividendWindow is larger than CRC but contain CRC so use the code below to extract CRC
        boolean[] resultCRC = Arrays.copyOfRange(dividendWindow, 0, dividendWindow.length - 1);

        //merge dataWord with result CRC to get codeWord
        boolean[] codeWord = new boolean[dataWord.length + resultCRC.length];
        for(int  i =0 ;i < dataWord.length;i++){
            codeWord[i] = dataWord[i];
        }
        for(int  i =0 ;i < resultCRC.length;i++){
            codeWord[i + dataWord.length] = resultCRC[i];
        }
        return codeWord;
    }

    public static boolean[] calculateSyndrome(boolean[] codeWord,boolean[] CRC){

        boolean[] divisor = CRC;

        int windowEndIndex = divisor.length - 1;

        // initialize dividendWindow
        boolean[] dividendWindow = new boolean[divisor.length];
        for(int i = 0;i < divisor.length;i++){
            dividendWindow[i] = codeWord[i];
        }
        // initialize dividendWindow

        // perform modulo-2 division using XOR operation
        while(windowEndIndex < codeWord.length){

            dividendWindow[divisor.length - 1] = codeWord[windowEndIndex];
            
            
            if(dividendWindow[0]){
                dividendWindow = XORbooleanArrays(dividendWindow,divisor);
            }else{
                dividendWindow = XORarrayAndZeros(dividendWindow);
            }

            windowEndIndex += 1;
        }
        // perform modulo-2 division using XOR operation

        // dividendWindow is larger than CRC but contain CRC so use the code below to extract CRC and return
        return Arrays.copyOfRange(dividendWindow, 0, dividendWindow.length - 1);
    }

}
