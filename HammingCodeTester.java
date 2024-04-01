

public class HammingCodeTester {

    public static boolean XOR(boolean B1,boolean B2){
        return ((B1 && !B2) || (!B1 && B2));
    }

    public static boolean[] locateErrorPosition(String codeWord){
        // user stringbuilder
        // to allow string reversal
        StringBuilder codeWordSB = new StringBuilder(); 
        codeWordSB.append(codeWord);
        codeWordSB.reverse();

        int paritySize = 4; // 1 2 4 8   (4 parity bits)
        boolean parity = false;
        boolean[] syndrome = new boolean[paritySize];
        for(int k = 1,j;k <= paritySize;k++){
            j = 1;
            while((2 * j - 1) * k <= codeWordSB.length()){
                for(int p = (2 * j - 1) * k;p <= codeWordSB.length() && p <= 2 * j * k - 1;p++){
                    parity = XOR(parity,codeWordSB.charAt(p - 1) == '1');
                }
                j++;
            }
            syndrome[k - 1] = parity;
            parity = false;
        }


        return syndrome;
    }
    
    public static void main(String args[]){
        
        char alphabet = 'O';
        System.out.print("alphabet : " + alphabet + "\n\n");
        String dataWord = HammingCodeGenerator.translaterToBitString(alphabet);
        System.out.print("dataWord : " + dataWord + "\n\n");
        String codeWord = HammingCodeGenerator.generateCodeWord(dataWord);
        System.out.print("codeWord : " + codeWord + "\n\n");
        boolean[] syndrome = locateErrorPosition(codeWord);
        for(int i = 0;i < syndrome.length;i++){
            System.out.print(syndrome[i]?"1":"0");
        }

    }



}
