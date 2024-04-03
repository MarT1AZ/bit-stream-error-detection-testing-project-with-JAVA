import java.util.Random;


public class CRCtester{

    public static enum CRC_TYPE {CRC_4,CRC_8,CRC_16,CRC_24,CRC_32};

    
    public static boolean[] getCRC(CRC_TYPE CT){
        // return CRC based on CRC_TYPE
        boolean[] CRC = new boolean[0];
        switch (CT) {
            case CRC_4:
                CRC = new boolean[5];
                CRC[0] = true;
                CRC[1] = true;
                CRC[2] = true;
                CRC[3] = true;
                CRC[4] = true;
                break;
            case CRC_8:
                CRC = new boolean[9];
                CRC[0] = true;
                CRC[2] = true;
                CRC[4] = true;
                CRC[6] = true;
                CRC[8] = true;
                break;
            case CRC_16:
                CRC = new boolean[17];
                CRC[0] = true;
                CRC[2] = true;
                CRC[15] = true;
                CRC[16] = true;
                break;
            case CRC_24:
                CRC = new boolean[25];
                CRC[0] = true;
                CRC[8] = true;
                CRC[12] = true;
                CRC[14] = true;
                CRC[23] = true;
                CRC[24] = true;
                break;
            case CRC_32:
                CRC = new boolean[33];
                CRC[0] = true;
                CRC[1] = true;
                CRC[2] = true;
                CRC[4] = true;
                CRC[5] = true;
                CRC[7] = true;
                CRC[8] = true;
                CRC[10] = true;
                CRC[11] = true;
                CRC[12] = true;
                CRC[16] = true;
                CRC[22] = true;
                CRC[23] = true;
                CRC[26] = true;
                CRC[32] = true;
                break;
        
        }
        return CRC;
    }

    // public static boolean[] generateDataWord(int maxWordSize){
    //     Random rand = new Random();
    //     int size = maxWordSize; // might edit
    //     boolean[] dataWord = new boolean[size];
    //     for(int i = 0;i < size;i++){
    //         dataWord[i] = (rand.nextInt(2) == 1?true:false);
    //     }
    //     return dataWord;
    // }

    public static void customTrailCRC(int trialNumber,CRC_TYPE CRCTYPE,boolean[] dataWord,boolean[] isErrorArray){
        // helper method to generate customize CRC trial
        // 
        // user can directly input dataword and the location of errors and CRC type
        //     
    
        trialCRC(trialNumber,CRCTYPE,false,false,false,dataWord,-1,isErrorArray);
    }

    public static void RandomTrailCRC(int trialNumber,CRC_TYPE CRCTYPE,int dataWordLength){
        // helper method to generate randomized CRC trial
        // 
        // user can randomize dataword, dataword length and the location of errors but the CRC type is selected by the user
        // 
        trialCRC(trialNumber,CRCTYPE,true,true,false,null,dataWordLength,null);
    }

    public static void trialCRC(int trialNumber,
                                CRC_TYPE CRCTYPE,
                                boolean randomizedDataword,
                                boolean randomizedError,
                                boolean isErrorLongerThanCRClength,
                                boolean[] dataWord,
                                int dataWordLength,
                                boolean[] isErrorArray){

        // core method for showing the CRC encoding and decoding output

        

        System.out.print("Trial number : " + trialNumber + "\n\n");

        Random rand = new Random();
        
        if(randomizedDataword){
            dataWord = new boolean[dataWordLength];
            for(int i = 0;i < dataWordLength;i++){
                dataWord[i] = (rand.nextInt(2) % 2 == 0);
            }
        }
        

        System.out.print("dataWord : ");
        for(int i = 0;i < dataWord.length;i++){
            System.out.print((dataWord[i]?"1":"0") + " ");
        }
        System.out.print(" length of  : " + dataWord.length);
        System.out.print("\n\n");

        boolean[] CRC = getCRC(CRCTYPE);
        System.out.print("CRC used : ");
        for(int i = 0;i < CRC.length;i++){
            System.out.print((CRC[i]?"1":"0") + " ");
        }
        System.out.print(" CRC used : " + CRCTYPE);
        System.out.print("\n\n");

        System.out.print("codeWord : ");
        boolean[] codeWord = CRCgenerator.generateCodeWord(dataWord,CRC);
        for(int i = 0;i < codeWord.length;i++){
            System.out.print((codeWord[i]?"1":"0") + " ");
        }
        System.out.print(" length of  : " + codeWord.length);
        System.out.print("\n\n");

        int burstErrorLength = 0;
        int additionalBurstLength = 0;
        int startErrorIndex = 0;
        int endErrorIndex = 0;
        // boolean[] isError
        if(randomizedError){
            burstErrorLength = rand.nextInt(CRC.length - 1) + 1;
            additionalBurstLength = 0;
            if(isErrorLongerThanCRClength){
                additionalBurstLength = rand.nextInt(codeWord.length - burstErrorLength);
            }
            burstErrorLength += additionalBurstLength;



            startErrorIndex = rand.nextInt(codeWord.length - burstErrorLength);
            endErrorIndex = startErrorIndex + burstErrorLength - 1;
            
            
            isErrorArray = new boolean[codeWord.length];
            isErrorArray[startErrorIndex] = true;
            codeWord[startErrorIndex] = !codeWord[startErrorIndex];
            if(startErrorIndex != endErrorIndex){
                isErrorArray[endErrorIndex] = true;
                codeWord[endErrorIndex] = !codeWord[endErrorIndex];    
            }
            
            for(int i = startErrorIndex + 1;i < endErrorIndex;i++){
                if(rand.nextInt(2) % 2 == 1){
                    isErrorArray[i] = true;
                    codeWord[i] = !codeWord[i];
                }
            }
        }else{
            for(int i = 0;i < isErrorArray.length;i++){
                if(isErrorArray[i]){
                    startErrorIndex = i;
                    break;
                }
            }
            for(int i = isErrorArray.length - 1;i >= 0;i--){
                if(isErrorArray[i]){
                    endErrorIndex = i;
                    break;
                }
            }
            burstErrorLength = endErrorIndex - startErrorIndex + 1;
        }
        

        System.out.print("applying error of length " + burstErrorLength + "\n\n");
        System.out.print("start : " + startErrorIndex + " end : " + endErrorIndex);
        System.out.print("\n\n");

        System.out.print("codeWord : ");
        for(int i = 0;i < codeWord.length;i++){
            System.out.print((codeWord[i]?"1":"0") + " ");
        }
        System.out.print("\n\n");

        System.out.print("error at : ");
        for(int i = 0;i < codeWord.length;i++){
            System.out.print(isErrorArray[i]?"X ":"  ");
        }
        System.out.print("\n\n");

        System.out.print("syndrome : ");
        boolean[] syndrome = CRCgenerator.calculateSyndrome(codeWord,CRC);
        for(int i = 0;i < syndrome.length;i++){
            System.out.print((syndrome[i]?"1":"0") + " ");
        }
        System.out.print("\n\n");

        boolean isErrorDetected = false;
        for(int i = 0;i < syndrome.length;i++){
            if(syndrome[i]){
                isErrorDetected = true;
                break;
            }
        }

        if(isErrorDetected){
            System.out.print("error detected\n\n");
        }else{
            System.out.print("no error detected\n\n");
        }


    }

    public static boolean[] toBooleanArray(String s){
        // use for to transform customized dataword string into boolean array
        boolean[] res = new boolean[s.length()];
        for(int i = 0 ;i < s.length();i++){
            res[i] = s.charAt(i) == '1';
        }
        return res;
    }

    public static void main(String args[]){
        
        int trialNumber = 1;
        String seperator = "=======================================================================\n\n";
        System.out.print("\n\n");
        for(int i = 0; i < 2;i++){ // CRC4 with dataword length of 4 (2 cases)
            RandomTrailCRC(trialNumber,CRC_TYPE.CRC_4, 4);
            trialNumber++;
            System.out.print(seperator);
        }

        for(int i = 0; i < 2;i++){ // CRC8 with dataword length of 6 (2 cases)
            RandomTrailCRC(trialNumber,CRC_TYPE.CRC_8, 6);
            trialNumber++;
            System.out.print(seperator);
        }

        for(int i = 0; i < 2;i++){ // CRC16 with dataword length of 8 (2 cases)
            RandomTrailCRC(trialNumber,CRC_TYPE.CRC_16, 8);
            trialNumber++;
            System.out.print(seperator);
        }

        for(int i = 0; i < 2;i++){ // CRC4 with dataword length of 6 (2 cases)
            RandomTrailCRC(trialNumber,CRC_TYPE.CRC_24, 6);
            trialNumber++;
            System.out.print(seperator);
        }

        for(int i = 0; i < 2;i++){ // CRC32 with dataword length of 4 (2 cases)
            RandomTrailCRC(trialNumber,CRC_TYPE.CRC_32, 4);
            trialNumber++;
            System.out.print(seperator);
        }


        // errorIndexString '1' indicate codeword error position 0 to codeword.length - 1 (left to right)
        // show cases that fail to detect error
        String dataWord1 = "10110011";
        String errorIndexString1 = "010100101000"; // the length is dataWord.length + CRC_4.length - 1
        System.out.print("case where CRC fail to detect error\n\n");
        customTrailCRC(trialNumber,CRC_TYPE.CRC_4, toBooleanArray(dataWord1), toBooleanArray(errorIndexString1));
        trialNumber++;
        System.out.print(seperator);
        String dataWord2 = "10110011";
        String errorIndexString2 = "0100000000010000"; // the length is dataWord.length + CRC_8.length - 1
        System.out.print("case where CRC fail to detect error\n\n");
        customTrailCRC(trialNumber,CRC_TYPE.CRC_8, toBooleanArray(dataWord2), toBooleanArray(errorIndexString2));

    }

    
}