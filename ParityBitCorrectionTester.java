
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class ParityBitCorrectionTester{

    public static void showOriginalData(boolean[][] dataWord){
        // show dataword
        String buffer = "";
        for(int j = 0;j < dataWord.length;j++){
            for(int i = 0;i < dataWord[j].length;i++){
                
               if(dataWord[j][i]){
                buffer += " 1";
               }else{
                buffer += " 0";
               }
            }
            buffer =  "[" + buffer + " ] ";
            System.out.print(buffer);
            buffer = "";
        }

    }

    public static int[] randomizeSingleBitErrorPosition(boolean[][] codeword){
        // method to show codeblock generate bitflip based on x and y index of 2d array codeword
        Random rand = new Random();
        int randX = rand.nextInt(codeword[0].length);
        int randY = rand.nextInt(codeword.length);
        return new int[]{randX,randY};
        // showParityProcess(codeword,randX,randY);

    }

    public static void showCodeBlock(boolean[][] codeWord){
        // method to show codeblock
        StringBuilder buffer = new StringBuilder();

        for(int j = 0;j < codeWord.length;j++){
            buffer.append("[ ");
            for(int i = 0;i < codeWord[j].length;i++){

                buffer.append(codeWord[j][i]?"1 ":"0 ");

            }
            buffer.append("] ");
        }
        System.out.print(buffer);

    }

    public static void showVerticalSyndrome(boolean[][] syndrome){
        // method to show vertical syndrome
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for(int i = 0;i < syndrome[1].length;i++){
            buffer.append(syndrome[1][i] ? " 1":" 0");
        }
        buffer.append(" ]");
        System.out.print(buffer);
    }

    public static void showHorizontalSyndrome(boolean[][] syndrome){
        // method to show horizontal syndrome
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        for(int i = 0;i < syndrome[0].length;i++){
            buffer.append(syndrome[0][i] ? " 1":" 0");
        }
        buffer.append(" ]");
        System.out.print(buffer);
    }

    public static void showErrorLocation(int[][] errorPositions,boolean[][] codeWord){
        // method to show error location
        StringBuilder buffer = new StringBuilder();
        boolean isError = false;
        for(int j = 0; j < codeWord.length;j++){
            buffer.append("[ ");
            for(int i = 0;i < codeWord[0].length;i++){
                for(int e = 0;e < errorPositions.length;e++){
                    if(j == errorPositions[e][1] && i == errorPositions[e][0]){
                        buffer.append("X ");
                        isError = true;
                        break;
                    }
                }
                if(isError){
                    isError = false;
                    continue;
                }
                buffer.append("  ");
            }
            buffer.append("] ");
        }
        System.out.print(buffer);
    }

    public static void showErrorLocation(ArrayList<Integer> errorPositionsList,boolean[][] codeWord){
        // method to show error location
        StringBuilder buffer = new StringBuilder();
        boolean isError = false;
        for(int j = 0; j < codeWord.length;j++){
            buffer.append("[ ");
            for(int i = 0;i < codeWord[0].length;i++){
                for(int e = 0;e < errorPositionsList.size();e++){
                    if(errorPositionsList.get(e) == j * codeWord[0].length + i){
                        buffer.append("X ");
                        isError = true;
                        break;
                    }
                }
                if(isError){
                    isError = false;
                    continue;
                }
                buffer.append("  ");
            }
            buffer.append("] ");
        }
        System.out.print(buffer);



    }

    public static ArrayList<Integer> locateErrorIndex(boolean[][] codeWord,boolean[][] syndrome){
        // syndrome[0] = horizontalSyndrome
        // syndrome[1] = verticalSyndrome

        ArrayList<Integer> errorIndexes = new ArrayList<Integer>();
        boolean allZeroVertical = true;
        boolean allZeroHorizontal = true;
        ArrayList<Integer> verticalOne = new ArrayList<Integer>();
        ArrayList<Integer> horizontalOne = new ArrayList<Integer>();
        for(int i = 0;i < syndrome[0].length;i++){
            if(syndrome[0][i]){
                horizontalOne.add(i);
                allZeroHorizontal = false;
            }
        }
        for(int i = 0;i < syndrome[1].length;i++){
            if(syndrome[1][i]){
                verticalOne.add(i);
                allZeroVertical = false;
            }
        }
        if(allZeroHorizontal && allZeroVertical){ // no 0 in both vertical column and horizontal row
            errorIndexes.add(-1);
            return errorIndexes;
        }
        if(allZeroHorizontal || allZeroVertical){ // have 1s in either column or row but not both
            errorIndexes.add(-2);
            return errorIndexes;
        }

        // calculate index based on X and Y
        //  example:
        //
        // 0 1 2 3
        // 4 5 6 7
        // 8 9 10 11
        // 12 13 14 15


        //  Y
        //  0 1 0 0 1         
        //  1 1 1 0 0             
        //  2 0 1 1 0
        //  3 1 1 0 1
        //    0 1 2 3 X
        // y = 0,x = 0 index -> 0
        // y = 1,x = 2 index -> 5
        // y = 2,x = 1 index -> 9

        for(int y : verticalOne){
            for(int x : horizontalOne){
                // calculate index based on x and y
                errorIndexes.add(y * codeWord[0].length + x);
            }
        }

        return errorIndexes;
    }

    

    public static boolean[][] generateRandomDataWord(){
        Random rand = new Random();
        int wordSize = 8;
        int numWord = 4;
        int minWordSize = 4;
        boolean[][] dataWord = new boolean[numWord][];
        for(int j = 0,size = -1;j < numWord;j++){
            size = rand.nextInt(wordSize - minWordSize) + minWordSize;
            dataWord[j] = new boolean[size];
            for(int i = 0;i < size;i++){
                dataWord[j][i] = (rand.nextInt(2) == 1)? true : false;
            }
        }
        return dataWord;
    }


    public static boolean isErrorCorrectlyDetected(boolean[][] codeWord,int[][] originalErrorPosition, ArrayList<Integer> foundErrorPositionList){
        if(originalErrorPosition.length != foundErrorPositionList.size()){
            return false;
        }

        int[] originalErrorIndexes = new int[originalErrorPosition.length];
        int[] foundErrorIndexes = new int[originalErrorPosition.length];
        
        for(int i = 0; i < originalErrorPosition.length;i++){
            originalErrorIndexes[i] = originalErrorPosition[i][0] + codeWord[0].length * originalErrorPosition[i][1];
            foundErrorIndexes[i] = foundErrorPositionList.get(i);
        }
        Arrays.sort(originalErrorIndexes);
        Arrays.sort(foundErrorIndexes);
        for(int i = 0; i < originalErrorPosition.length;i++){
            if(foundErrorIndexes[i] != originalErrorIndexes[i]){
                return false;
            }
        }
        return true;
    }

    public static void testParity(int Testindex,int numberOfErrors){
        ParityGenerator PG = new ParityGenerator();

        boolean[][] dataWord = generateRandomDataWord();

        System.out.print("Trial number " + Testindex + " \n\n");
        System.out.print("dataWord : ");
        showOriginalData(dataWord);
        System.out.print("\n\n");

        boolean[][] codeWord = PG.GenerateParity(dataWord, 8, 4);
        System.out.print("codeWord : ");
        showCodeBlock(codeWord); 

        System.out.print("\n\n");
        System.out.print("applying bit error....\n\n");

        //////////////////////////
        int[][] errorPositions = new int[numberOfErrors][];
        for(int i = 0; i < numberOfErrors;i++){
            errorPositions[i] = randomizeSingleBitErrorPosition(codeWord);
            codeWord[errorPositions[i][1]][errorPositions[i][0]] = !codeWord[errorPositions[i][1]][errorPositions[i][0]];
        }
        //////////////////////////
        System.out.print("flipped codeWord : ");
        showCodeBlock(codeWord); //show codeword with an error
        System.out.print("\n\n        error at : ");
        showErrorLocation(errorPositions, codeWord);

        boolean[][] syndrome = PG.calculateSyndrome(codeWord); //calculate the syndrome
        // syndrome[0] = horizontalSyndrome
        // syndrome[1] = verticalSyndrome
        System.out.print("\n\nshowing calculated syndrome\n\n");
        System.out.print("horizontal syndrome : ");
        showHorizontalSyndrome(syndrome);
        System.out.print("\n\nvertical syndrome : ");
        showVerticalSyndrome(syndrome);
        System.out.print("\n\n");
        System.out.print("calculating error positions...\n\n");
        ArrayList<Integer> foundErrorPositionsList = locateErrorIndex(codeWord, syndrome);
        
        System.out.print("flipped codeWord : ");
        showCodeBlock(codeWord); //show codeword with an error
        System.out.print("\n\n        error at : ");
        // System.out.println(errorPositionsList);
        showErrorLocation(foundErrorPositionsList,codeWord);
        System.out.print("\n\n");

        if(foundErrorPositionsList.get(0) == -1 && errorPositions.length == 0){
            System.out.print("0 bitflip occured and 0 bitflip detected\n\n");
        }else if(foundErrorPositionsList.get(0) == -2){
            // 2 bit flip on the same row or column
            // this message wont be printed if the number of bitflip is 1 
            System.out.print(numberOfErrors + " bitflips occured but the decoder could not locate the bitflip\n\n");
        }
        else if(isErrorCorrectlyDetected(codeWord, errorPositions, foundErrorPositionsList)){
            // number of generated bitflips equal to the number of found bitflips
            // and the bitflips indexs are correspond to the generated bitflips
            System.out.print("bitflip correctly detected and located\n\n");
        }else{
            // number of generated bitflips equal or not to the number of found bitflips
            // if equal,the bitflips indexs are not correspond to the generated bitflips
            System.out.print("bitflip wrongfullly detected or located\n\n");
        }
        // asserting cases
        // -1 no error detected
        // -2 unable to correctly locate error
        // else verify using isErrorCorrectlyDetected()

    }

    public static void main(String args[]){
        int trialNum = 1;

        for(int i = 1;i <= 10;i++){
            System.out.print("================================================================================\n\n");
            System.out.print(" Test Case : applying no bitflip\n\n");
            testParity(trialNum,0);
            trialNum++;
        }
        for(int i = 1;i <= 10;i++){
            System.out.print("================================================================================\n\n");
            System.out.print(" Test Case : applying a bitflip\n\n");
            testParity(trialNum,1);
            trialNum++;
        }

        System.out.print("\n");


    }

}