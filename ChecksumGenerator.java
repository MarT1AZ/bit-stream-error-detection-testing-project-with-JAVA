import java.io.*;
import java.util.*;
public class ChecksumGenerator {

    public static String Checksum_gen(String fileName)  {
    	String text = new String();
    	File file = new File(fileName);
        //Scanner scan;
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine())
			{	
				// text.add(scan.nextLine());
                text += scan.hasNextLine();
                //System.out.println(scan.nextLine());
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        return text;
    }
}