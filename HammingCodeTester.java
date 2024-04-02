
import java.util.Random;

public class HammingCodeTester {

    public static boolean XOR(boolean B1,boolean B2){
        return ((B1 && !B2) || (!B1 && B2));
    }

    public static int locateErrorPosition(boolean[] syndrome){
        int errorPos = 0;
        int power = 1;
        for(int i = 0;i < syndrome.length;i++){
            errorPos +=  syndrome[i]?(power << i):0;
        }
        return errorPos;
    }

    public static boolean[] calculateSyndrome(String codeWord){
        // user stringbuilder
        // to allow string reversal
        StringBuilder codeWordSB = new StringBuilder(); 
        codeWordSB.append(codeWord);
        codeWordSB.reverse();

        int paritySize = 4; // 1 2 4 8   (4 parity bits)
        boolean parity = false;
        boolean[] syndrome = new boolean[paritySize];
        for(int k = 1,j,power;k <= paritySize;k++){
            j = 1;
            power = 1 << (k - 1);
            while((2 * j - 1) * power <= codeWordSB.length()){
                for(int p = (2 * j - 1) * power;p <= codeWordSB.length() && p <= 2 * j * power - 1;p++){
                    parity = XOR(parity,codeWordSB.charAt(p - 1) == '1');
                }
                j++;
            }
            syndrome[k - 1] = parity;
            parity = false;
        }
        return syndrome;
    }

    public static void hammingCodeTrial(char alphabet){
        Random rand = new Random();
        System.out.print("alphabet : " + alphabet + "\n\n");

        String dataWord = HammingCodeGenerator.translaterToBitString(alphabet);
        System.out.print("dataWord : ");
        for(int i = 0; i < dataWord.length();i++){
            System.out.print(dataWord.charAt(i) + " ");
        }
        System.out.print("\n\n");

        String codeWord = HammingCodeGenerator.generateCodeWord(dataWord);
        System.out.print("codeWord : ");
        for(int i = 0; i < codeWord .length();i++){
            System.out.print(codeWord.charAt(i) + " ");
        }
        System.out.print("\n\n");

        int errorPosition = (codeWord.length()) - (rand.nextInt(codeWord.length() + 1));
        if(codeWord.length() - errorPosition  == 0){
            System.out.print("applying no error\n\n");
        }else{
            System.out.print("applying error at position " + (codeWord.length() - errorPosition) + "\n\n");
            codeWord = codeWord.substring(0, errorPosition) + (codeWord.charAt(errorPosition) == '1'?"0":"1") + codeWord.substring(errorPosition + 1);
            System.out.print("codeWord : ");
            for(int i = 0; i < codeWord.length();i++){
                System.out.print(codeWord.charAt(i) + " ");
            }
            System.out.print("\n\n");
            System.out.print("         [ ");
            for(int i = 0;i < codeWord.length();i++){
                System.out.print(i == errorPosition?"X ":"  ");
            }
            System.out.print("]" + "\n\n");
        }
        
        
        boolean[] syndrome = calculateSyndrome(codeWord);
        System.out.print("syndrome : ");
        for(int i = 0;i < syndrome.length;i++){
            System.out.print(syndrome[i]?"1 ":"0 ");
        }
        System.out.print("\n\nerror location : " +  locateErrorPosition(syndrome));
    }
    
    public static void main(String args[]){
        
        for(int i = 65;i <= 75;i++){
            hammingCodeTrial((char) i);
            System.out.print("\n\n==================================================================================\n\n");
        }
        
        

    }



}
