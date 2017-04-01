import javax.swing.JOptionPane;
import java.io.*;

public class skip
{
 public  void fileskip(File[] cp_files) throws Exception
    {   
        int i = 0 ; 
	Object[] choices = {"Yes", "Skip"};
        File[] final_list = new File[100];
	Object defaultChoice = choices[0];
        for(File file:cp_files)
		{
	int output =  JOptionPane.showOptionDialog(null,"Do u wish to copy"+file.getName(),"Copying",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,choices,defaultChoice);
                 if(output == JOptionPane.YES_OPTION)
                      {
                         System.out.println("Yes");
                         final_list[i] = file;
                      } 
                 else if(output == JOptionPane.NO_OPTION)
                      {
                         System.out.println("Skip");
                       } 
                   i = i+1;
               }
     for(File pfile:final_list)
		{
                   System.out.println("Final list"+pfile.getName());
                }
    
     }

}
