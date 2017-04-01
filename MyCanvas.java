    import java.awt.*;  
    import javax.swing.JFrame;  
      
    public class MyCanvas extends Canvas{  
         
        public void paint(Graphics g) {  
      
            Toolkit t=Toolkit.getDefaultToolkit();  
            Image i=t.getImage("hourglass.gif");  
            g.drawImage(i, 120,100,this);  
              
        }  
            public static void main(String[] args) {  
            MyCanvas m=new MyCanvas();  
            JFrame f=new JFrame();  
            

            f.add(m);  
            f.setSize(400,400);  
            f.setVisible(true);  
            int ggwp = m.TimeDelay();
             m.iquit(ggwp);
              
        }
         public void iquit(int yo)
              {
                if (yo ==1)
                    {
                      System.exit(0);
              }
}  
      
public int TimeDelay()//just to demo return TimeDelay() is not required during actual code
           {
             int n=99999999;
              for(int i=0;i<=n;i++)
                   {
  
  }                
return 1; 
}
 }

