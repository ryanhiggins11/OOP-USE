package ie.gmit.sw;

//Imports
import java.util.*;

public class Runner {
	//Variables..
	private static String wili;
	private static String query;
	private static Scanner console = new Scanner(System.in);
	/**
	 * 
	 * @param args
	 */
	//Main
	public static void main(String[] args) {
		
		System.out.println("------------------------------------------------");
		System.out.println("------------G00337350 Ryan Higgins--------------");
		System.out.println("------------Language Detector-------------------");
		System.out.println("------------------------------------------------");
		System.out.println("Welcome to my Language Detector....");
		System.out.println("Please Enter wili File Location(include the .txt): ");
		wili = console.next();
		
		try{
		Database db = new Database();
		Parser p = new Parser(wili, 3);
		System.out.println("Please Wait while this loads....");
		
		p.setDb(db);
		Thread t = new Thread(p);
		t.start();
		t.join();
		
		db.resize(300);
		
		System.out.println("Loading Completed....");
		
		System.out.println("Please Enter Test File Location(include the .txt): ");
		query = console.next();
		
		String s = "This is an example of the English language";
		p.analyseQuery(query);
		}
		catch (Exception e) {
			
		}

	}//End of Main
}//End of Runner
