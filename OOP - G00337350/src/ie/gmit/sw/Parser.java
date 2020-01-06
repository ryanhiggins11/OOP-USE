package ie.gmit.sw;

//Imports
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileInputStream;


/**
* The Class Parser.
*/

public class Parser implements Runnable {
	//Declare Variables..
	private String file;
	private int k;
	private Database db = null;

/**
 * 
 * @param file
 * @param k
 */
	public Parser(String file, int k) {
		this.file = file;
		this.k = k;
	}
/**
 * 
 * @param db
 */
	public void setDb(Database db) {
		this.db = db;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if (record.length != 2)
					continue;
				parse(record[0], record[1]);
			}

			br.close();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}//End Of Run()...

/**
 * 
 * @param text
 * @param lang
 * @param ks
 */
	//Parses the text file
	public void parse(String text, String lang, int... ks) {
		
		Language l = Language.valueOf(lang);
		for (int i = 0; i < text.length() - k; i++) {
			
			// Gets 3-kmer
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, l);
		}
	} //End of Parse()...
	
/**
 * 
 * @param file
 * @throws IOException
 */
	//Checks the language which the query file was written in
	//use map to parse a query file then detects what language is outputted..
	public void analyseQuery(String file) throws IOException {
		int kmers = 0;
		int freq = 1;
		String queryFile;

		Map<Integer, LanguageEntry> query = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		//Start of while()
		while ((queryFile = br.readLine()) != null) {

			for (int i = 0; i <= queryFile.length() - k; i++) {
				CharSequence sequenceChar = queryFile.substring(i, i + k);
				kmers = sequenceChar.hashCode();
				
				if (query.containsKey(kmers)) {
					freq += query.get(kmers).getFrequency();
				}
				
				LanguageEntry l = new LanguageEntry(kmers, freq);
				query.put(kmers, l);
			}

		}// End of while()..

		Language language = db.getLanguage(query);
		System.out.println("The language detected is: " + language); //Shows Language that its detected..
	}//End of analyseQuery..
	
}//End Of Parser..