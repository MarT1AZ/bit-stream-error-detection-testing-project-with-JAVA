import java.util.Arrays;

public class CRCgenerator {

    public static enum CRC_TYPE {CRC_4,CRC_8,CRC_16,RVS_CRC_16,CRC_24,CRC_32};
    public static boolean[] CRC4 = new boolean[]{true,false,false,true,true};
    public static boolean[] CRC8 = new boolean[]{true,true};
    public static boolean[] CRC16 = new boolean[]{true,true};
    public static boolean[] RVS_CRC16 = new boolean[]{true,true};
    public static boolean[] CRC24 = new boolean[]{true,true};
    public static boolean[] CRC32 = new boolean[]{true,true};
    
    public static boolean[] getCRC(CRC_TYPE CT){
        boolean[] CRC = new boolean[0];
        switch (CT) {
            case CRC_4:
                CRC = CRC4;
                break;
            case CRC_8:
            CRC = CRC8;
                break;
            case CRC_16:
            CRC = CRC16;
                break;
            case RVS_CRC_16:
            CRC = RVS_CRC16;
                break;
            case CRC_24:
            CRC = CRC24;
                break;
            case CRC_32:
            CRC =  CRC32;
                break;
        
        }
        return CRC;
    }

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
    
    public static boolean[] generateCRCcodeWord(boolean[] dataWord,CRC_TYPE CRC){
        
        boolean[] divisor = getCRC(CRC);

        int windowEndIndex = divisor.length - 1;

        boolean[] dividendWindow = new boolean[divisor.length];
        // initialize dividendWindow

        for(int i = 0;i < divisor.length;i++){
            if(i < dataWord.length){
                dividendWindow[i] = dataWord[i]; //bit from dataword
            }else{
                dividendWindow[i] = false; // extra 0
            }
        }

    

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

        return Arrays.copyOfRange(dividendWindow, 0, dividendWindow.length - 1);
    }

    public static boolean[] calculateSyndrome(boolean[] codeWord,CRC_TYPE CRC){

        boolean[] divisor = getCRC(CRC);

        int windowEndIndex = divisor.length - 1;

        boolean[] dividendWindow = new boolean[divisor.length];
        // initialize dividendWindow

        for(int i = 0;i < divisor.length;i++){
            dividendWindow[i] = codeWord[i];
        }

        while(windowEndIndex < codeWord.length){

            dividendWindow[divisor.length - 1] = codeWord[windowEndIndex];
            
            
            if(dividendWindow[0]){
                dividendWindow = XORbooleanArrays(dividendWindow,divisor);
            }else{
                dividendWindow = XORarrayAndZeros(dividendWindow);
            }

            windowEndIndex += 1;
        }

        return Arrays.copyOfRange(dividendWindow, 0, dividendWindow.length - 1);
    }

}
