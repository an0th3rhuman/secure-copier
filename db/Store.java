import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class STore {
	static void store(String a ,String b) throws IOException
	{ 
			   BufferedWriter bw = null;
		FileWriter fw = null;

		fw = new FileWriter("C:/Users/Aravindh/Desktop/test.txt",true);
		bw = new BufferedWriter(fw);
		try {int c;
		 String content=null;
			content=a+":"+b+"\n";
			
			bw.append(content);
			
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		}
	public static void main( String[] args ) throws IOException
	{
		store("lol","mkil");
	}
		
	}


