import java.util.HashMap;
import java.io.*;
import javax.swing.JOptionPane;

class database {
	static HashMap<String, String> map = new HashMap<String, String>();
	static String filePath = "/usr/local/sih/bin/db";

	void store(String a, String b) throws IOException {
		//JOptionPane.showMessageDialog(null, "Storing in db ", "Error", JOptionPane.ERROR_MESSAGE);
		BufferedWriter bw = null;
		FileWriter fw = null;
		fw = new FileWriter("/usr/local/sih/bin/db", true);
		bw = new BufferedWriter(fw);

		try {

			String data = a + ":" + b + "\n";

			File file = new File(filePath);

			bw.append(data);

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

	public void load() throws IOException {
		map.clear();
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(":");
			if (parts.length >= 2) {
				String key = parts[0];

				String value = parts[1];
				map.put(key, value);
			} else {
				System.out.println("ignoring line: " + line);
			}
		}
		reader.close();
	}

	public String getvalue(String a) throws IOException {
		load();
		return map.get(a);
	}

	public void updatevalue(String key, String value) throws IOException {
		//JOptionPane.showMessageDialog(null, "Updating in db ", "Error", JOptionPane.ERROR_MESSAGE);
		load();
		map.put(key, value);
		File del = new File(filePath);
		del.delete();
		File create = new File(filePath);
		for (String key1 : map.keySet())
			store(key1, map.get(key1));
	}

}
