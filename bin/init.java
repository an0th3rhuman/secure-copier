import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.*;
import java.awt.*;  
import javax.swing.JFrame; 

public class init  {
	File f = null;
	OTF otfmain = new OTF();
	parser parse = new parser();
	database db = new database();
	passauth frame = new passauth();
        final JFrame waitframe=new JFrame();
	String hashval = "";
	String hashdb = "";
	String devnode = "";
	String SerialNo = "";
	int result;
	Jfilechooser fileui = new Jfilechooser();

	void auth() throws Exception {
		SerialNo = parse.fetch_serial();
		f = new File("/media/restricted/" + SerialNo);
		f.mkdirs();
		String serialhash = DigestUtils.sha256Hex(SerialNo);
		hashdb = db.getvalue(serialhash);
                devnode = parse.fetch_devnode();
		String[] part = devnode.split(" ");
		devnode = part[1];

		if (hashdb == null) {
			int output = JOptionPane.showConfirmDialog(null, "Yor Pendrive is no registered \n Do you wish to register ?", "Confirm",JOptionPane.YES_NO_OPTION);
			if (output == JOptionPane.YES_OPTION) {
				register(SerialNo);
			} else if (output == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
                          else if (output == JOptionPane.CLOSED_OPTION)
                           {
                              JOptionPane.showMessageDialog(null, "Please re-insert the pendrive to authenticate!");
                              while(true){System.out.println("You can never auth me :)");}
                              
                           }
		} else {
                        JOptionPane.showMessageDialog(null, "You are  already registered \n Checking for integrity... ");
			frame.pass_init();
			frame.setSize(300, 100);
			frame.setVisible(true);
			while (frame.isVisible()) {
				System.out.println("Waiting for input ");
			}
			if (frame.pass_result == 1) {
				try {
					OTF.Passphrase = frame.pass;
					OTF.Serial_ID = SerialNo;
					parse.execute(new String[] { "/bin/sh", "-c","mount " + "/dev/" + devnode + " /media/restricted/" + SerialNo });
					hashval = otfmain.getSum();
					if (hashdb.equals(hashval)) {
						JOptionPane.showMessageDialog(null, "You are  Authenticated !");
						open_browser();

					} else {
						JOptionPane.showMessageDialog(null, "The Pendrive is tampered!", "Error",
								JOptionPane.ERROR_MESSAGE);
						parse.execute(new String[] { "/bin/sh", "-c", "umount " + devnode });
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, " error " + e);
				}

			}

		}
	}

	void register(String SerialNo) throws Exception {   
                frame.pass_init();
		frame.setSize(300, 100);
		frame.setVisible(true);
		while (frame.isVisible()) {
			System.out.println("Waiting for input ");
		}
		if (frame.pass_result == 1) {
			try {
				devnode = parse.fetch_devnode();
				String[] part = devnode.split(" ");
				devnode = part[1];
				parse.execute(new String[] { "/bin/sh", "-c","mount " + "/dev/" + devnode + " /media/restricted/" + SerialNo });
				String hash_SerialNo = DigestUtils.sha256Hex(SerialNo);
				//JOptionPane.showMessageDialog(null, "password:" + frame.pass);
				OTF.Passphrase = frame.pass;
				OTF.Serial_ID = SerialNo; 
                                MyCanvas canvas=new MyCanvas();  
                                JFrame waitframe =new JFrame();  
                                waitframe.add(canvas);  
                                waitframe.setSize(400,400);  
                                waitframe.setVisible(true);
                                otfmain.reg();
                                waitframe.dispose();
				hashval = otfmain.getSum();
				//JOptionPane.showMessageDialog(null, "init calling store");
				db.updatevalue(hash_SerialNo, hashval);
				JOptionPane.showMessageDialog(null, "You are now Registered !");
				open_browser();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, " error " + e);
			}

		}

	}

	void open_browser() {
		fileui.Filebrowser("/media/restricted/" + SerialNo);
	}

	public static void main(String[] args) throws Exception {
                init obj = new init();
		obj.auth();
	}
}
