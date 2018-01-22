
public class trimString {
	 public static void main(String[] args) {
		 String strOrig = "Hello readers";
	      int intIndex = strOrig.indexOf("r");
	      
	      if(intIndex == - 1) {
	         System.out.println("Hello not found");
	      } else {
	         System.out.println("Found Hello at index " + strOrig.substring(0, intIndex));
	      }
	 }
}
