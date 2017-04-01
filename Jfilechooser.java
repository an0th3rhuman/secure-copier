/*import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Jpanel ;*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.FileSystemView;

public class Jfilechooser extends JFrame {

private static FileSystemView fileSystemView;
private static File currentFile;
private static Desktop desktop;
private static File srcfile;
private static File destfile;


  public void Filebrowser(String defaultfile) {
    final JFrame frame = new JFrame("JFileChoose");
    final JFrame destframe = new JFrame("Copy To");                             
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    destframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fileSystemView = FileSystemView.getFileSystemView();
    desktop = Desktop.getDesktop();


    JPanel mainpanel = new JPanel();                                      // Main panel with Borderlayout
    JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));        // Bottom pannel with flowlayout
    //panel.setBackground(Color.darkGray);
    mainpanel.setSize(300,300);
    BorderLayout layout = new BorderLayout();
    layout.setHgap(10);
    layout.setVgap(10);
      
    mainpanel.setLayout(layout);   

    JButton open = new JButton("Open");

    JButton copy = new JButton("Copy");
    btnPnl.add(open);
    btnPnl.add(copy);
    btnPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  

    final JLabel directoryLabel = new JLabel(" ");
    directoryLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
    mainpanel.add(directoryLabel, BorderLayout.NORTH);

    final JFileChooser fileChooser = new JFileChooser(defaultfile);
    final JFileChooser destChooser = new JFileChooser(".");
    fileChooser.setMultiSelectionEnabled(true);
    fileChooser.setControlButtonsAreShown(false);
    destChooser.setControlButtonsAreShown(false);
    destChooser.setAcceptAllFileFilterUsed(false);
    destChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    mainpanel.add(fileChooser, BorderLayout.CENTER);
    mainpanel.add(btnPnl, BorderLayout.SOUTH);
    JPanel destpanel = new JPanel(); 
    destpanel.setLayout(layout); 
    JPanel btmcopy = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton ok = new JButton("Ok"); 
    

    open.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
                        currentFile = fileChooser.getSelectedFile();
                        desktop.open(currentFile);
                    } catch(Throwable t) {
                         System.out.println(t);
                    }
                    frame.repaint();
                }
            });


    copy.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    try {
			btmcopy.add(ok);
                        ok.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
                        File[] srcfiles = fileChooser.getSelectedFiles();
                        //code
                        destfile = destChooser.getSelectedFile();
                       //code     
                        skip skipobj = new  skip();
                        skipobj.fileskip(srcfiles); 
                        destframe.setVisible(false);}});
			destpanel.add(btmcopy, BorderLayout.SOUTH);
			destpanel.add(destChooser, BorderLayout.CENTER);
			destframe.add(destpanel);
   
                        destframe.pack();
                        destframe.setVisible(true);
                        destfile = destChooser.getSelectedFile();
                    } catch(Throwable t) {
                         System.out.println(t);
                    }
                    destframe.repaint();
                }
            });


    frame.add(mainpanel);
    frame.pack();
    frame.setVisible(true);
  }

public static void main(String[] args) throws Exception
     {
        Jfilechooser fileui = new Jfilechooser();
        fileui.Filebrowser(".");  
     }

}
