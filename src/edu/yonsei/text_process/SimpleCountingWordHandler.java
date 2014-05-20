package edu.yonsei.text_process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SimpleCountingWordHandler {
	
	public SimpleCountingWordHandler()
	{
	}
	
	public static void main(String args[])
	{
		HashMap<String,Integer> frequencyMap = new HashMap();
		//String test = "This is a test and test is good to do for system test";
		
		
		
		  try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("./data/output.txt");
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String test;
			  //Read File Line By Line
			  	while ((test = br.readLine()) != null)   {
  	
				  	String[] splited = test.split(" ");
					
					for (int i = 0; i < splited.length; ++i) {
						String word = splited[i];	
						System.out.println("Word: " + word);
						
						if (!frequencyMap.containsKey(word)) {
							frequencyMap.put(word, 1);
						} else {
							frequencyMap.put(word, frequencyMap.get(word)+1);
						}
					}
			  	}
			  	//Close the input stream
			  	in.close();
		    } catch (Exception e){//Catch exception if any
		       System.err.println("Error: " + e.getMessage());
		    }
	

		
		for (Iterator iter = frequencyMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String)entry.getKey();
			Integer value = (Integer)entry.getValue();	
			
			System.out.println("Word: " + key + " Frequency: " + value);
		}
		
		
	}

	
	public void writeToFile(HashMap frequencyMap)
	{
		 try {

			    // Create file 
			    FileWriter fstream = new FileWriter("");
			    BufferedWriter out = new BufferedWriter(fstream);
			  
			    for (Iterator iter = frequencyMap.entrySet().iterator(); iter.hasNext();) {
			         Map.Entry entry = (Map.Entry) iter.next();
			         String key = (String)entry.getKey();
			         Integer value = (Integer)entry.getValue(); 
			         out.write("Word: " + key + " Frequency: " + value + "\n");
			         System.out.println("Word: " + key + " Frequency: " + value);
			      }

			     //Close the output stream
			     out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }

	}
}
