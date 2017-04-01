import java.io.*;
import java.util.Collection;
import java.awt.Desktop;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import org.bouncycastle.openpgp.PGPException;
import org.c02e.jpgpj.Encryptor;
import org.c02e.jpgpj.Decryptor;
import org.apache.commons.codec.digest.DigestUtils;
import javax.swing.JOptionPane;

public class OTF {

	static String Serial_ID;
	// static String Serial_ID_path = "/media/restricted/" + Serial_ID;
	static String Passphrase;

	void encrypt(File[] src_files, File dest_file) {
		JOptionPane.showMessageDialog(null, "ENCRYPTING : src- "+src_files+" dest- "+dest_file, "Error", JOptionPane.ERROR_MESSAGE);
		Encryptor encryptor = new Encryptor();
		encryptor.setSymmetricPassphrase(Passphrase);
		encryptor.setSigningAlgorithm(org.c02e.jpgpj.HashingAlgorithm.Unsigned);
		encryptor.setCompressionAlgorithm(org.c02e.jpgpj.CompressionAlgorithm.Uncompressed);

		for (File file : src_files) {
			String sep = file.getParent();
			if (file.isDirectory()) {
				Collection<File> file_arr = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
				file_arr.parallelStream().forEach((item) -> {
					String dest_path = dest_file + item.toString().split(sep)[1];
					try {
						FileInputStream src = new FileInputStream(item);
						File des = new File(dest_path);
						des.getParentFile().mkdirs();
						FileOutputStream dest = new FileOutputStream(des);
						try {
							encryptor.encrypt(src, dest);
						} catch (IOException e) {
							System.out.println("Something Wrong in IO!");
						} catch (PGPException e) {
							System.out.println("Something Wrong in PGP!");
						}
					} catch (Exception e) {
						System.out.println("Something Wrong in File IO!");
					}
				});
			}
			if (file.isFile()) {
				String dest_path = dest_file + file.toString().split(sep)[1];
				try {
					FileInputStream src = new FileInputStream(file);
					File des = new File(dest_path);
					des.getParentFile().mkdirs();
					FileOutputStream dest = new FileOutputStream(des);
					try {
						encryptor.encrypt(src, dest);
					} catch (IOException e) {
						System.out.println("Something Wrong in IO!");
					} catch (PGPException e) {
						System.out.println("Something Wrong in PGP!");
					}
				} catch (Exception e) {
					System.out.println("Something Wrong in File IO!");
				}
			}
		}
	}

	void decrypt(File[] src_files, File dest_file) {
		JOptionPane.showMessageDialog(null, "DECRYPTING : src- "+src_files+" dest- "+dest_file, "Error", JOptionPane.ERROR_MESSAGE);
		Decryptor decryptor = new Decryptor();
		decryptor.setSymmetricPassphrase(Passphrase);
		decryptor.setVerificationRequired(false);

		for (File file : src_files) {
			String sep = file.getParent();
			if (file.isDirectory()) {
				Collection<File> file_arr = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
				file_arr.parallelStream().forEach((item) -> {
					String dest_path = dest_file + item.toString().split(sep)[1];
					try {
						FileInputStream src = new FileInputStream(item);
						File des = new File(dest_path);
						des.getParentFile().mkdirs();
						FileOutputStream dest = new FileOutputStream(des);
						try {
							decryptor.decrypt(src, dest);
						} catch (IOException e) {
							System.out.println("Something Wrong in IO!");
						} catch (PGPException e) {
							System.out.println("Something Wrong in PGP!");
						}
					} catch (Exception e) {
						System.out.println("Something Wrong in File IO!");
					}
				});
			}
			if (file.isFile()) {
				String dest_path = dest_file + file.toString().split(sep)[1];
				try {
					FileInputStream src = new FileInputStream(file);
					File des = new File(dest_path);
					des.getParentFile().mkdirs();
					FileOutputStream dest = new FileOutputStream(des);
					try {
						decryptor.decrypt(src, dest);
					} catch (IOException e) {
						System.out.println("Something Wrong in IO!");
					} catch (PGPException e) {
						System.out.println("Something Wrong in PGP!");
					}
				} catch (Exception e) {
					System.out.println("Something Wrong in File IO!");
				}
			}
		}
	}

	void open(File open_file) {
		JOptionPane.showMessageDialog(null, "OPENING : src- "+open_file, "Error", JOptionPane.ERROR_MESSAGE);
		File dest_file = new File("/dev/shm");
		File[] src_file = { open_file };
		decrypt(src_file, dest_file);
		if (Desktop.isDesktopSupported()) {
			try {
				if (Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
					File tmp_file = new File(dest_file.getAbsolutePath() + "/" + open_file.getName());
					Desktop.getDesktop().open(tmp_file);
				} else {
					System.out.println("The file is not open supported!");
				}
			} catch (Exception exp) {
				System.out.println("Error opening the file!");
			}
		}
	}

	void deletefile(File[] src_files) {
		JOptionPane.showMessageDialog(null, "DELETING : src- "+src_files, "Error", JOptionPane.ERROR_MESSAGE);
		for (File file : src_files) {
			try {
				FileUtils.forceDelete(file);
			} catch (Exception e) {
				System.out.println("Problem with IO during delete");
			}
		}
		//update();
	}

	void copy(File[] src_files, File dest_file) {
		JOptionPane.showMessageDialog(null, "COPYING : src- "+src_files+" dest- "+dest_file, "Error", JOptionPane.ERROR_MESSAGE);
		boolean[] file_status = analyse(src_files[0], dest_file);
		if (file_status[0] == false && file_status[1] == true) {
			encrypt(src_files, dest_file);
			update();
		} else if (file_status[0] == true && file_status[1] == false) {
			decrypt(src_files, dest_file);
		} else {
			for (File file : src_files) {
				try {
					if (file.isDirectory()) {
						FileUtils.copyDirectory(file, dest_file);
					}
					if (file.isFile()) {
						FileUtils.copyFile(file, dest_file);
					}
				} catch (Exception e) {
					System.out.println("Problem with IO");
				}
			}
			update();
		}
	}

	void move(File[] src_files, File dest_file) {
		JOptionPane.showMessageDialog(null, "MOVING : src- "+src_files+" dest- "+dest_file, "Error", JOptionPane.ERROR_MESSAGE);
		copy(src_files, dest_file);
		deletefile(src_files);
		//update();
	}

	static boolean[] analyse(File one, File two) {
		JOptionPane.showMessageDialog(null, "ANALYZING : src- "+one+" dest- "+two, "Error", JOptionPane.ERROR_MESSAGE);
		boolean one_status = one.getAbsolutePath().contains("/media/restricted/" + Serial_ID);
		boolean two_status = two.getAbsolutePath().contains("/media/restricted/" + Serial_ID);
		boolean[] file_status = { one_status, two_status };
		return file_status;
	}

	String getSum() {
		JOptionPane.showMessageDialog(null, "GETTING SUM ", "Error", JOptionPane.ERROR_MESSAGE);
		File file = new File("/media/restricted/" + Serial_ID);
		Collection<File> list = FileUtils.listFiles(file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		String sum = list.toString();

		for (File each : list) {
			//sum = sum + String.valueOf(each.length());
			//System.out.println(each.lastModified());
			JOptionPane.showMessageDialog(null, "file "+each.getName(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		//JOptionPane.showMessageDialog(null, "sum "+sum, "Error", JOptionPane.ERROR_MESSAGE);
		return DigestUtils.sha256Hex(sum);
	}

	void reg() {
		JOptionPane.showMessageDialog(null, "REGISTERING : ", "Error", JOptionPane.ERROR_MESSAGE);
		Encryptor encryptor = new Encryptor();
		encryptor.setSymmetricPassphrase(Passphrase);
		encryptor.setSigningAlgorithm(org.c02e.jpgpj.HashingAlgorithm.Unsigned);
		encryptor.setCompressionAlgorithm(org.c02e.jpgpj.CompressionAlgorithm.Uncompressed);

		File src_file = new File("/media/restricted/" + Serial_ID);
		Collection<File> file_arr = FileUtils.listFiles(src_file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

		file_arr.parallelStream().forEach((item) -> {
			String dest_path = item.toString() + ".gpg";
			try {
				FileInputStream src = new FileInputStream(item);
				FileOutputStream dest = new FileOutputStream(dest_path);
				try {
					encryptor.encrypt(src, dest);
				} catch (IOException e) {
					System.out.println("Something Wrong in IO!");
				} catch (PGPException e) {
					System.out.println("Something Wrong in PGP!");
				}
			} catch (Exception e) {
				System.out.println("Something Wrong in File IO!");
			}
		});
		File[] foos = file_arr.toArray(new File[file_arr.size()]);
		deletefile(foos);
		Collection<File> file_arr2 = FileUtils.listFiles(src_file, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File rn : file_arr2) {
			String name = rn.getAbsolutePath();
			rn.renameTo(new File(name.substring(0, name.length() - 4)));
		}
		try {	
		FileUtils.copyFile(new File("/usr/local/sih/udev/autorun.sh"), src_file);
		}catch (Exception e) {
		System.out.println("Problem with IO in format script");
		}
	}

	void rename(File src, String des) {
		JOptionPane.showMessageDialog(null, "RENAMING : src- "+src+" dest- "+des, "Error", JOptionPane.ERROR_MESSAGE);
		String name = src.getAbsolutePath();
		String renameTo = name.substring(0, name.length() - src.getName().length()) + des;
		src.renameTo(new File(renameTo));
	}

	void update() {
		JOptionPane.showMessageDialog(null, "UPDATING IN DB", "Error", JOptionPane.ERROR_MESSAGE);
		database d = new database();
		passauth p = new passauth();
		String value = getSum();
		String key = DigestUtils.sha256Hex(Serial_ID);
		try {
			d.updatevalue(key, value);
		} catch (IOException e) {
			System.out.println("Error in update");
		}
	}
}
