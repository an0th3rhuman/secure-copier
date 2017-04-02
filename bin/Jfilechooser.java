import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Jfilechooser extends JFrame {

	static File currentFile;
	static Desktop desktop;
	static File srcfile;
	static File destfile;
	OTF otf = new OTF();

	void Filebrowser(String defaultfile) {
		final JFrame frame = new JFrame("JFileChoose");
		final JFrame destframe = new JFrame("Copy To");
                final JFrame moveframe = new JFrame("move To");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		destframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                moveframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		desktop = Desktop.getDesktop();

		JPanel mainpanel = new JPanel();
		JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

		mainpanel.setSize(300, 300);
		BorderLayout layout = new BorderLayout();
		layout.setHgap(10);
		layout.setVgap(10);

		mainpanel.setLayout(layout);

		JButton open = new JButton("Open");
		JButton copy = new JButton("Copy");
                JButton move = new JButton("move");
                JButton del = new JButton("delete");
                 

		btnPnl.add(open);
		btnPnl.add(copy);
                btnPnl.add(move);
                btnPnl.add(del);

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
                JPanel movepanel = new JPanel();
		movepanel.setLayout(layout);
		JPanel btmcopy = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JPanel btmmove = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton ok = new JButton("Ok");

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					currentFile = fileChooser.getSelectedFile();
					otf.open(currentFile);
				} catch (Throwable t) {
					System.out.println(t);
				}
				frame.repaint();
			}
		});

              move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					btmmove.add(ok);
					ok.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							File[] srcfiles = fileChooser.getSelectedFiles();
							destfile = destChooser.getSelectedFile();
							try {
								File[] user_files = fileskip(srcfiles);
								otf.move(user_files, destfile);
							} catch (Exception e1) {

							}

							moveframe.setVisible(false);
						}
					});
					movepanel.add(btmmove, BorderLayout.SOUTH);
					movepanel.add(destChooser, BorderLayout.CENTER);
					moveframe.add(movepanel);

					moveframe.pack();
					moveframe.setVisible(true);
					destfile = destChooser.getSelectedFile();
				} catch (Throwable t) {
					System.out.println(t);
				}
				moveframe.repaint();
			}
		});



		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					btmcopy.add(ok);
					ok.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							File[] srcfiles = fileChooser.getSelectedFiles();
							destfile = destChooser.getSelectedFile();
							try {
								File[] user_files = fileskip(srcfiles);
								otf.copy(user_files, destfile);
							} catch (Exception e1) {

							}

							destframe.setVisible(false);
						}
					});
					destpanel.add(btmcopy, BorderLayout.SOUTH);
					destpanel.add(destChooser, BorderLayout.CENTER);
					destframe.add(destpanel);

					destframe.pack();
					destframe.setVisible(true);
					destfile = destChooser.getSelectedFile();
				} catch (Throwable t) {
					System.out.println(t);
				}
				destframe.repaint();
			}
		});

		frame.add(mainpanel);
		frame.pack();
		frame.setVisible(true);
	}

	public File[] fileskip(File[] cp_files) throws Exception {
		Object[] choices = { "Yes", "Skip" };
		ArrayList<File> final_list = new ArrayList<File>();
		Object defaultChoice = choices[0];
		for (File file : cp_files) {
			int output = JOptionPane.showOptionDialog(null, "Do you wish to copy " + file.getName(), "Copying",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, defaultChoice);
			if (output == JOptionPane.YES_OPTION) {
				System.out.println("Yes");
				final_list.add(file);
			} else if (output == JOptionPane.NO_OPTION) {
				System.out.println("Skip");
			}
		}
		File[] dsf = new File[final_list.size()];
		final_list.toArray(dsf);
		return dsf;
	}
}
