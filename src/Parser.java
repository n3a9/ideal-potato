import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Parser {
	
	public static void parse(String path){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line;
			ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
			
			while((line = br.readLine()) != null){
				ArrayList<String> splitted = split(line);
				for(int i = 0; i < splitted.size(); i++){
					ArrayList<Double> column;
					
					if(data.size() <= i){
						column = new ArrayList<Double>();
						data.add(column);
					}
					else
						column = data.get(i);
					column.add((double)Integer.parseInt(splitted.get(i).trim()));
				}
			}
			
			for(ArrayList<Double> column : data){
				Collections.sort(column);
				System.out.println(column);
				Trimmer.dataTrimmer(column, 2);
			}
			
			br.close();
		} catch (IOException e) {
			System.err.println("While parsing the file, a "+e.getClass().getName()+" was encountered. Please try again.");
		
			return;
		}
	}
	
	private static ArrayList<String> split(String line){
		ArrayList<String> splitted = new ArrayList<String>();
		
		int ind = line.indexOf(",");
		while(ind != -1){
			splitted.add(line.substring(0, ind));
			line = line.substring(ind+1, line.length());
			
			ind = line.indexOf(",");
		}
		
		splitted.add(line);
		
		return splitted;
	}

}
