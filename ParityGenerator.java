import java.util.Random;

public class ParityGenerator {
    
    Random randomGenerator;
    public ParityGenerator(){
        randomGenerator = new Random();
    }

    public boolean XOR(boolean B1,boolean B2){
        return ((B1 && !B2) || (!B1 && B2));
    }

    public boolean[][] GenerateParity(boolean[][] dataWord,int wordSize,int numWord){
        // true represents 1
        // false represents 0

        boolean[][] outputString = new boolean[numWord + 1][wordSize + 1];
        outputString[numWord - 1][wordSize] = false; // remove later
        
        boolean parityBit = false;

        // assigning vertical parity bit
        // 1 0 0 1 P
        // 0 0 0 1 P
        // 1 1 0 0 P
        // 1 0 1 0 P
        for(int j = 0;j < outputString.length - 1;j++){
            for(int i = 0; i < wordSize;i++){
                if(i >= dataWord[j].length){ // check if the dataword need to be padded
                    parityBit = XOR(parityBit, false);
                }else{
                    outputString[j][i] = dataWord[j][i]; // assign original bit to the output slot
                    parityBit = XOR(parityBit, dataWord[j][i]);
                }
            }
            outputString[j][wordSize] = parityBit;
            parityBit = false;
        }

        // assigning horizontal parity bit
        // 1 0 0 1 0
        // 0 0 0 1 1
        // 1 1 0 0 0
        // 1 0 1 0 0
        // p p p p

        for(int i = 0;i < wordSize;i++){
            for(int j = 0;j < numWord;j++){
                if(i >= dataWord[j].length){
                    parityBit = XOR(parityBit, false);
                }else{
                    outputString[j][i] = dataWord[j][i];
                    parityBit = XOR(parityBit, dataWord[j][i]);
                }
            }
            outputString[numWord][i] = parityBit;
            parityBit = false;
        }

        // assigning final parity bit (corner bit)
        // 1 0 0 1 0
        // 0 0 0 1 1
        // 1 1 0 0 0
        // 1 0 1 0 0
        // 1 1 1 0 P

        for(int j = 0;j < numWord;j++){
            parityBit = XOR(parityBit, outputString[j][wordSize]);
        }
        outputString[numWord][wordSize] = parityBit;

        return outputString;

    }

    public int[] randomizeSingleBitErrorPosition(boolean[][] codeword){
        
        int randX = this.randomGenerator.nextInt(codeword[0].length);
        int randY = this.randomGenerator.nextInt(codeword.length);
        return new int[]{randX,randY};
        // showParityProcess(codeword,randX,randY);

    }

    public boolean[][] calculateSyndrome(boolean[][] codeword){
        boolean[][] syndrome = new boolean[2][];

        boolean parity = false;
        boolean[] verticalSyndrome = new boolean[codeword.length];
        boolean[] horizontalSyndrome = new boolean[codeword[0].length];

        // finding vertical syndrome
        for(int j = 0;j < codeword.length;j++){
            for(int i = 0; i < codeword[0].length;i++){
                parity = XOR(parity, codeword[j][i]);
            }
            verticalSyndrome[j] = parity;
            parity = false;
        }

        // finding horizontal syndrome
        for(int i = 0; i < codeword[0].length;i++){
            for(int j = 0;j < codeword.length;j++){
                parity = XOR(parity, codeword[j][i]);
            }
            horizontalSyndrome[i] = parity;
            parity = false;
        }
        syndrome[0] = horizontalSyndrome;
        syndrome[1] = verticalSyndrome;
        return syndrome;

    }

    public int locateErrorPosition(boolean[][] syndrome){
        // -1
        int errorX = -1;
        int errorY = -1;

        for(int i = 0; i < syndrome[0].length;i++){
            if(syndrome[0][i]){
                errorX = i;
                break;
            }
        }

        if(errorX == -1){
            return -1;
        }

        for(int j = 0; j < syndrome[1].length;j++){
            if(syndrome[1][j]){
                errorY = j;
                break;
            }
        }

        return errorX + 1 + (errorY * syndrome[0].length - 1);

    }

    

}
