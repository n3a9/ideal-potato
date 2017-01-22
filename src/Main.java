import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
    	p();
    	
    	while(scanner.hasNext()){
    		String path = scanner.nextLine();
    		Parser.parse(path);

    		System.out.println("");
    		p();
        }
    }
    
    private static void p(){
    	System.out.println("Please enter the path of the CSV file.");
    }
}
