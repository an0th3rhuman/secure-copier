import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.codec.digest.DigestUtils;

class passauth extends JFrame implements ActionListener {
	public String pass;
	public JButton SUBMIT;
	public JPanel panel;
	public JLabel label2;
	public JTextField text2;
	public int pass_result;
	public int retval;

	public void pass_init() {

		label2 = new JLabel();
		label2.setText("Key:");
		text2 = new JPasswordField(15);
		SUBMIT = new JButton("SUBMIT");
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(label2);
		panel.add(text2);
		panel.add(SUBMIT);
		add(panel, BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setTitle("Passkey");
	}

	public void actionPerformed(ActionEvent ae) {

		try {
			retval = checkpass();
			System.out.println("retval: " + retval);
			if (retval == 1) {
				this.setVisible(false);
			}

			else {
				JOptionPane.showMessageDialog(this, "Incorrect login or password", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e)

		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	public int checkpass() throws Exception {
		String passhash = "";
		pass = text2.getText();
		String content = new String(Files.readAllBytes(Paths.get("/usr/local/sih/udev/keyhash")));
		content = content.replaceAll("\n", "");
		passhash = DigestUtils.sha256Hex(pass);
		if (passhash.equals(content)) {
			pass_result = 1;
		} else {
			pass_result = 0;
		}
		return pass_result;
	}

}
