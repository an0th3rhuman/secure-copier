import java.io.*;
import javax.swing.JOptionPane;

public class parser {

	String fetch_file(String filename) {
		String line = "";
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
			JOptionPane.showMessageDialog(null, "Unable to open file '" + filename + "'");
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Unable to open file '" + filename + "'");
		}

		return line;

	}

	public String execute(String[] command) {

		String s = null;
		String result = "";

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			while ((s = stdInput.readLine()) != null) {
				result += s;
			}

		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			JOptionPane.showMessageDialog(null, "Error" + e);
			System.exit(-1);
		}
		return result;
	}

	public String fetch_devnode() {
		String devnode_part = "";
		String node = "";
		String[] cmd = { "/bin/sh", "-c",
				"grep -i -B 1 'Attached SCSI removable disk' /var/log/syslog   | tail -2  | head -1" };
		String list = execute(cmd);
		String[] parts = list.split("]");
		devnode_part = parts[1];
		String[] last = devnode_part.split(":");
		node = last[1];
		return node;
	}

	public String fetch_serial() {
		String path = fetch_file("/usr/local/sih/udev/usb_path");
		String Serial_no = fetch_file(path + "/serial");
		return Serial_no;
	}

}
