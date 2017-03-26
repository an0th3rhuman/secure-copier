import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class T { 
	static String ret_value(String a) throws IOException
	{
		 String filePath = "C:/Users/Aravindh/Desktop/test.txt";
		    HashMap<String, String> map = new HashMap<String, String>();
	        
		    String line;
		    BufferedReader reader = new BufferedReader(new FileReader(filePath));
		    while ((line = reader.readLine()) != null)
		    {
		        String[] parts = line.split(":", 2);
		        if (parts.length >= 2)
		        {
		            String key = parts[0];
		           
		            String value = parts[1];
		            map.put(key, value);
		        } else {
		            System.out.println("ignoring line: " + line);
		        }
		    }
		   
		  
		   
		    
		  
		    
		   
		    reader.close();
		    return map.get(a);
	}
	public static void main( String[] args ) throws IOException
	{ 
	
	 
		System.out.println(ret_value("lol" ));
	   
	}
}
