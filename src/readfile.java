
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class readfile {

	public static void main(String[] args) {
		
		String path = "pre.txt";
		File file = new File(path);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				int tab1Index = line.indexOf("\t");
				System.out.println(line.substring(0, tab1Index) + " : " + line.substring(tab1Index).trim());
//				System.out.println(line + "," + line.indexOf("\t"));
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
}