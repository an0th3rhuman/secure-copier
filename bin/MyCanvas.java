import java.awt.*;  
import javax.swing.JFrame;  
      
public class MyCanvas extends Canvas{  
       
        public void paint(Graphics g) {  
      
            Toolkit t=Toolkit.getDefaultToolkit();  
            Image i=t.getImage("/usr/local/sih/bin/hourglass.gif");  
            g.drawImage(i, 120,100,this);  
              
        }  
           
         
}  
      


