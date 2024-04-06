import java.io.IOException;

public class Checksumtester {
    public static void main(String[] args) throws IOException{
       String text =  ChecksumGenerator.Checksum_gen("File1.txt");
       System.out.println(text);
    }
}
