import java.util.Random;


public class CRCtester{

    public static boolean[] generateDataWord(int maxWordSize){
        Random rand = new Random();
        int size = maxWordSize; // might edit
        boolean[] dataWord = new boolean[size];
        for(int i = 0;i < size;i++){
            dataWord[i] = (rand.nextInt(2) == 1?true:false);
        }
        return dataWord;
    }
    public static void main(String args[]){

        // boolean[] data = generateDataWord(24);
        // for(int i = 0;i < data.length;i++){

        //     System.out.print((data[i]?"1":"0") + " ");
        // }

        boolean[] data = new boolean[]{true,false,true,true,false,false,true,true};
        for(int i = 0;i < data.length;i++){
            System.out.print((data[i]?"1":"0") + " ");
        }
        System.out.println("\n\n");
        boolean[] output = CRCgenerator.generateCRCcodeWord(data,CRCgenerator.CRC_TYPE.CRC_4);
        for(int i = 0;i < output.length;i++){
            System.out.print((output[i]?"1":"0") + " ");
        }

        boolean[] codeWord = new boolean[data.length + output.length];
        for(int  i =0 ;i < data.length;i++){
            codeWord[i] = data[i];
        }
        for(int  i =0 ;i < output.length;i++){
            codeWord[i + data.length] = output[i];
        }


        System.out.println("\n\n");
        boolean[] syndrome = CRCgenerator.calculateSyndrome(codeWord,CRCgenerator.CRC_TYPE.CRC_4);
        for(int i = 0;i < syndrome.length;i++){
            System.out.print((syndrome[i]?"1":"0") + " ");
        }





    }


    
}