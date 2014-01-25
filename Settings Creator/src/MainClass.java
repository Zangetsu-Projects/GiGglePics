import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainClass extends JFrame {

	private static final long serialVersionUID = 1L;

	String requiredConstructors[] = { "name:", "image:", "position:",
			"authorization:", "venue:", "bio:" };

	String completedFields[] = { "1", "2", "3", "4", "5", "6" };

	String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", " " };

	JFrame mainFrame;
	Container cPane;

	JButton saveFile;
	JButton loadFile;

	String fileName;

	String Input = null;

	static String version = "v 1.2";
	
	
	//String version = getResourceAsStream("test");
	public static void loadUserPreferences() {
		
		Preferences prefs = Preferences.userNodeForPackage(MainClass.class);
		
		version = prefs.get("CLIENT_VERSION", "v 1.2");
	}
	
	public static void updateUserPreferences(String PREF_NAME, String newValue) {
		
		Preferences prefs = Preferences.userNodeForPackage(MainClass.class);

		prefs.put(PREF_NAME, newValue);
		
		loadUserPreferences();
		
	}
		

	public static void main(String args[]) {

		new MainClass();

	}


	String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static int infoBox(String infoMessage, String location, String type) {
		
		if (type.equals("info")) {
			
			JOptionPane.showMessageDialog(null, infoMessage, location,
					JOptionPane.INFORMATION_MESSAGE);
			return 0;
		} else if (type.equals("question")) {
			
			int test = JOptionPane.showConfirmDialog(null, infoMessage, location, JOptionPane.YES_NO_OPTION);
			return test;
		} else {
			return 0;
		}
		
		
		
		
	}
	
	public static String getFileContent(URL url) {
		
		 
		String inputLine = "fail";
		
		
		try {

			URLConnection conn = url.openConnection();
 
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
 
			inputLine = br.readLine();
 
			/*save to this filename
			//String fileName = "/users/mkyong/test.html";
			//File file = new File(fileName);
 
			//if (!file.exists()) {
			//	file.createNewFile();
			//}
 
			//use FileWriter to write file
			//FileWriter fw = new FileWriter(file.getAbsoluteFile());
			//BufferedWriter bw = new BufferedWriter(fw);
 
			while ((br.readLine()) != null) {
				//bw.write(inputLine);
			}
 
			bw.close();
			*/
			br.close();
 
		} catch (MalformedURLException e) {
			e.printStackTrace();
			infoBox("Could not contact server", "Error", "info");
		} catch (IOException e) {
			e.printStackTrace();
			infoBox("Could not contact server", "Error", "info");
		}
		
		System.out.println(inputLine);
		return inputLine;		
		
	}

	public MainClass() {
		
		loadUserPreferences();		

		JPanel panel = new JPanel(new GridBagLayout());
		this.getContentPane().add(panel);

		final JTextField nameInput = new JTextField();

		final JTextField imageInput = new JTextField();
		final JTextField positionInput = new JTextField();
		final JTextField dateInput = new JTextField();

		final JTextField venueInput = new JTextField();

		final JTextArea bioInput = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(bioInput);
		scrollPane.setPreferredSize(new Dimension(400, 100));

		final JTextField volunteerID = new JTextField();

		final JTextField authorizationCode = new JTextField();

		venueInput.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {

			}

			public void removeUpdate(DocumentEvent e) {
				if (venueInput.getText().length() > 4) {
					warn();
				} else {
					authorizationCode.setText("");
				}
			}

			public void insertUpdate(DocumentEvent e) {
				if (venueInput.getText().length() > 4) {
					warn();
				} else {
					authorizationCode.setText("");
				}
			}

			public void warn() {

				String volunteerVenue = venueInput.getText().replace(" ", "")
						.replace("_", " ");
				String convertedString = "";
				String passwordToCompare = "";

				for (int i = 0; i < volunteerVenue.length(); i++) {

					int code = Arrays.asList(alphabet).indexOf(
							volunteerVenue.substring(i, i + 1).toUpperCase(
									Locale.UK));
					convertedString = convertedString + code;

				}

				passwordToCompare = convertedString;

				while (passwordToCompare.length() > 4) {

					for (int i1 = 0; i1 < passwordToCompare.length(); i1++) {

						String firstChar = "" + passwordToCompare.charAt(i1);
						passwordToCompare = passwordToCompare.replace(
								firstChar, "");

					}
				}

				if (passwordToCompare.length() < 4) {

					int length = 4 - passwordToCompare.length();
					int start = convertedString.length() / 2;

					String charToAdd = convertedString.substring(start, start
							+ length);
					passwordToCompare = charToAdd + passwordToCompare;
				}

				authorizationCode.setText(passwordToCompare);

			}
		});

		authorizationCode.setEditable(false);

		saveFile = new JButton("Save");
		saveFile.setPreferredSize(new Dimension(40, 40));
		saveFile.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String nameOutput = nameInput.getText();
				String imageOutput = imageInput.getText();
				String positionOutput = positionInput.getText();
				String dateOutput = dateInput.getText();
				String venueOutput = venueInput.getText();
				String bioOutput = bioInput.getText();

				fileName = volunteerID.getText().toUpperCase(Locale.UK)
						+ ".txt";

				if (nameOutput.equals("") || imageOutput.equals("")
						|| positionOutput.equals("") || dateOutput.equals("")
						|| venueOutput.equals("") || bioOutput.equals("")
						|| fileName.equals("")) {

					String missingConstructors = "";

					if (nameOutput.equals("")) {
						missingConstructors = "Name";
					} else if (imageOutput.equals("")) {
						missingConstructors = "Image";
					} else if (positionOutput.equals("")) {
						missingConstructors = "Position";
					} else if (dateOutput.equals("")) {
						missingConstructors = "Date";
					} else if (venueOutput.equals("")) {
						missingConstructors = "Venue";
					} else if (bioOutput.equals("")) {
						missingConstructors = "Bio";
					} else if (volunteerID.getText().equals("")) {
						missingConstructors = "Volunteer ID";
					}

					infoBox("Missing Constructor Information: "
							+ missingConstructors, "Error", "info");

				} else {

					String eol = System.getProperty("line.separator");

					String content = "<!-- GiGgle Pics Application Settings File -->"
							+ eol
							+ "name: "
							+ nameOutput
							+ eol
							+ "image: "
							+ imageOutput
							+ eol
							+ "position: "
							+ positionOutput
							+ eol
							+ "authorization: "
							+ dateOutput
							+ eol
							+ "venue: "
							+ venueOutput
							+ eol
							+ "bio: "
							+ bioOutput;

					Writer writer = null;

					try {
						writer = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(fileName), "utf-8"));
						writer.write(content);
					} catch (IOException ex) {
						// report
					} finally {
						try {
							writer.close();
						} catch (Exception ex) {
						}
					}

					infoBox("File " + fileName + " Saved", "Success", "info");
				}
			}
		});

		loadFile = new JButton("Load");
		loadFile.setPreferredSize(new Dimension(40, 40));
		loadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (volunteerID.getText().toUpperCase(Locale.UK).equals("")) {

					infoBox("Missing Constructor Information: File Name",
							"Error", "info");

				} else {

					fileName = volunteerID.getText().toUpperCase(Locale.UK)
							+ ".txt";

					try {
						Input = readFile(fileName);
					} catch (IOException e1) {
						infoBox("Incorrect Constructor Information: File Name",
								"Error", "info");
						e1.printStackTrace();
					}

					if (Input.equals("")) {

						infoBox("File not found: Please ensure correct FileName",
								"Error", "info");

					} else {

						int nameLocation = Input.indexOf("name");
						int urlLocation = Input.indexOf("image");
						int positionLocation = Input.indexOf("position");
						int authorizationLocation = Input
								.indexOf("authorization");
						int venueLocation = Input.indexOf("venue");
						int bioLocation = Input.indexOf("bio");

						nameInput.setText(Input.substring(nameLocation + 5,
								urlLocation));
						imageInput.setText(Input.substring(urlLocation + 6,
								positionLocation));
						positionInput.setText(Input.substring(
								positionLocation + 9, authorizationLocation));
						dateInput.setText(Input.substring(
								authorizationLocation + 14, venueLocation));
						venueInput.setText(Input.substring(venueLocation + 6,
								bioLocation));
						bioInput.setText(Input.substring(bioLocation + 4,
								Input.length()));

						infoBox("File " + fileName + " Loaded", "Success", "info");

					}
				}
			}

		});

		BufferedImage myPicture = null;

		try {
			myPicture = ImageIO.read(getClass().getResource(
					"data/gigglepicslogo.png"));
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		JLabel headerImage = new JLabel(new ImageIcon(myPicture));
		headerImage.setPreferredSize(new Dimension(400, 100));

		JLabel developerInfo = new JLabel("~ Master Zangetsu Solutions ~");

		TitledBorder nameLabel;
		nameLabel = BorderFactory.createTitledBorder("Name");
		nameInput.setBorder(nameLabel);

		TitledBorder imageLabel;
		imageLabel = BorderFactory.createTitledBorder("Image URL");
		imageInput.setBorder(imageLabel);

		TitledBorder positionLabel;
		positionLabel = BorderFactory.createTitledBorder("Position");
		positionInput.setBorder(positionLabel);

		TitledBorder dateLabel;
		dateLabel = BorderFactory.createTitledBorder("Authorised for Date");
		dateInput.setBorder(dateLabel);

		TitledBorder venueLabel;
		venueLabel = BorderFactory.createTitledBorder("Venue");
		venueInput.setBorder(venueLabel);

		TitledBorder bioLabel;
		bioLabel = BorderFactory.createTitledBorder("Bio");
		scrollPane.setBorder(bioLabel);

		TitledBorder fileNameLabel;
		fileNameLabel = BorderFactory
				.createTitledBorder("Volunteer ID/ FileName");
		volunteerID.setBorder(fileNameLabel);

		TitledBorder authorizationLabel;
		authorizationLabel = BorderFactory.createTitledBorder("Venue Password");
		authorizationCode.setBorder(authorizationLabel);

		JPanel constructorPanel = new JPanel();
		constructorPanel.setLayout(new BoxLayout(constructorPanel,
				BoxLayout.Y_AXIS));
		constructorPanel.add(nameInput);
		constructorPanel.add(imageInput);
		constructorPanel.add(positionInput);
		constructorPanel.add(dateInput);
		constructorPanel.add(venueInput);
		constructorPanel.add(scrollPane);

		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
		resultsPanel.add(volunteerID);
		resultsPanel.add(authorizationCode);
		resultsPanel.add(loadFile);
		resultsPanel.add(saveFile);

		JPanel volunteerSettingsPanel = new JPanel();
		volunteerSettingsPanel.setLayout(new GridBagLayout());

		JPanel applicationSettingsPanel = new JPanel();
		applicationSettingsPanel.setLayout(new GridBagLayout());

		final JCheckBox enabled = new JCheckBox("Application Enabled");
		// enabled.setMnemonic(KeyEvent.VK_C);
		enabled.setSelected(true);

		final JTextField soundcloud1 = new JTextField();
		TitledBorder soundcloudLabel1;
		soundcloudLabel1 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud1.setBorder(soundcloudLabel1);
		soundcloud1.setColumns(15);

		final JTextField mixupUrl1 = new JTextField();
		TitledBorder urlLabel1;
		urlLabel1 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl1.setBorder(urlLabel1);
		mixupUrl1.setColumns(15);

		final JTextField soundcloud2 = new JTextField();
		TitledBorder soundcloudLabel2;
		soundcloudLabel2 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud2.setBorder(soundcloudLabel2);
		soundcloud2.setColumns(15);

		final JTextField mixupUrl2 = new JTextField();
		TitledBorder urlLabel2;
		urlLabel2 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl2.setBorder(urlLabel2);
		mixupUrl2.setColumns(15);

		final JTextField soundcloud3 = new JTextField();
		TitledBorder soundcloudLabel3;
		soundcloudLabel3 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud3.setBorder(soundcloudLabel3);
		soundcloud3.setColumns(15);

		final JTextField mixupUrl3 = new JTextField();
		TitledBorder urlLabel3;
		urlLabel3 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl3.setBorder(urlLabel3);
		mixupUrl3.setColumns(15);

		final JTextField soundcloud4 = new JTextField();
		TitledBorder soundcloudLabel4;
		soundcloudLabel4 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud4.setBorder(soundcloudLabel4);
		soundcloud4.setColumns(15);

		final JTextField mixupUrl4 = new JTextField();
		TitledBorder urlLabel4;
		urlLabel4 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl4.setBorder(urlLabel4);
		mixupUrl4.setColumns(15);

		final JTextField soundcloud5 = new JTextField();
		TitledBorder soundcloudLabel5;
		soundcloudLabel5 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud5.setBorder(soundcloudLabel5);
		soundcloud5.setColumns(15);

		final JTextField mixupUrl5 = new JTextField();
		TitledBorder urlLabel5;
		urlLabel5 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl5.setBorder(urlLabel5);
		mixupUrl5.setColumns(15);

		final JTextField soundcloud6 = new JTextField();
		TitledBorder soundcloudLabel6;
		soundcloudLabel6 = BorderFactory
				.createTitledBorder("Monthly Mixup Name");
		soundcloud6.setBorder(soundcloudLabel6);
		soundcloud6.setColumns(15);

		final JTextField mixupUrl6 = new JTextField();
		TitledBorder urlLabel6;
		urlLabel6 = BorderFactory.createTitledBorder("Monthly Mixup Url");
		mixupUrl6.setBorder(urlLabel6);
		mixupUrl6.setColumns(15);

		JButton saveAppSettings = new JButton("Save");
		saveAppSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Boolean appEnabled = enabled.isEnabled();
				String name1 = soundcloud1.getText();
				String name2 = soundcloud2.getText();
				String name3 = soundcloud3.getText();
				String name4 = soundcloud4.getText();
				String name5 = soundcloud5.getText();
				String name6 = soundcloud6.getText();

				String url1 = mixupUrl1.getText();
				String url2 = mixupUrl2.getText();
				String url3 = mixupUrl3.getText();
				String url4 = mixupUrl4.getText();
				String url5 = mixupUrl5.getText();
				String url6 = mixupUrl6.getText();

				String eol = System.getProperty("line.separator");

				if (name1.equals("") || name2.equals("") || name3.equals("")
						|| name4.equals("") || name5.equals("")
						|| name6.equals("") || url1.equals("")
						|| url2.equals("") || url3.equals("")
						|| url4.equals("") || url5.equals("")
						|| url6.equals("")) {
					
					infoBox("Missing Constructors", "Error", "info");

				} else {

					String content = "enabled: " + appEnabled.booleanValue()
							+ eol + eol + "name1: " + name1 + eol + "url1: "
							+ url1 + eol + eol + "name2: " + name2 + eol
							+ "url2: " + url2 + eol + eol + "name3: " + name3
							+ eol + "url3: " + url3 + eol + eol + "name4: "
							+ name4 + eol + "url4: " + url4 + eol + eol
							+ "name5: " + name5 + eol + "url5: " + url5
							+ eol + eol + "name6: " + name6 + eol + "url6: "
							+ url6;

					Writer writer = null;

					try {
						writer = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(
										"gigglepicsApplicationSettings.txt"),
								"utf-8"));
						writer.write(content);
					} catch (IOException ex) {
						// report
					} finally {
						try {
							writer.close();
						} catch (Exception ex) {
						}
					}

					infoBox("GiGgle Pics App Settings file Saved", "Success", "info");
				}

			}

		});
		
		JButton loadAppSettings = new JButton("Load");
		loadAppSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


					try {
						Input = readFile("gigglepicsApplicationSettings.txt");
					} catch (IOException e1) {
						infoBox("Settings file not found",
								"Error", "info");
						e1.printStackTrace();
					}

					if (Input.equals("")) {

						infoBox("File not found: Please ensure correct FileName",
								"Error", "info");

					} else {

						int name1Location = Input.indexOf("name1");
						int url1Location = Input.indexOf("url1");
						
						int name2Location = Input.indexOf("name2");
						int url2Location = Input.indexOf("url2");
						
						int name3Location = Input.indexOf("name3");
						int url3Location = Input.indexOf("url3");
						
						int name4Location = Input.indexOf("name4");
						int url4Location = Input.indexOf("url4");
						
						int name5Location = Input.indexOf("name5");
						int url5Location = Input.indexOf("url5");
						
						int name6Location = Input.indexOf("name6");
						int url6Location = Input.indexOf("url6");


						soundcloud1.setText(Input.substring(name1Location + 6, url1Location).replace(" ",""));
						mixupUrl1.setText(Input.substring(url1Location + 5, name2Location).replace(" ",""));
						
						soundcloud2.setText(Input.substring(name2Location + 6, url2Location).replace(" ",""));
						mixupUrl2.setText(Input.substring(url2Location + 5, name3Location).replace(" ",""));
						
						soundcloud3.setText(Input.substring(name3Location + 6, url3Location).replace(" ",""));
						mixupUrl3.setText(Input.substring(url3Location + 5, name4Location).replace(" ",""));
						
						soundcloud4.setText(Input.substring(name4Location + 6, url4Location).replace(" ",""));
						mixupUrl4.setText(Input.substring(url4Location + 5, name5Location).replace(" ",""));
						
						soundcloud5.setText(Input.substring(name5Location + 6, url5Location).replace(" ",""));
						mixupUrl5.setText(Input.substring(url5Location + 5, name6Location).replace(" ",""));
						
						soundcloud6.setText(Input.substring(name6Location + 6, url6Location).replace(" ",""));
						mixupUrl6.setText(Input.substring(url6Location + 5, Input.length()).replace(" ",""));
						

						infoBox("gigglepicsApplicationSettings file Loaded", "Success", "info");

					}
				}

		});

		JPanel imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		imagePanel.add(headerImage);

		JPanel developerPanel = new JPanel();
		developerPanel.add(developerInfo);

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Volunteer Profiles", null, volunteerSettingsPanel,
				null);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Application Settings", null,
				applicationSettingsPanel, null);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		GridBagConstraints gbc = new GridBagConstraints();

		// Constructors

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel.add(imagePanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		panel.add(tabbedPane, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		panel.add(developerPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		volunteerSettingsPanel.add(constructorPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		volunteerSettingsPanel.add(resultsPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		applicationSettingsPanel.add(enabled, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = WIDTH;
		applicationSettingsPanel.add(soundcloud1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(soundcloud2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(soundcloud3, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl3, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(soundcloud4, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl4, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(soundcloud5, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl5, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(soundcloud6, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		applicationSettingsPanel.add(mixupUrl6, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.fill = WIDTH;
		applicationSettingsPanel.add(saveAppSettings, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.fill = WIDTH;
		applicationSettingsPanel.add(loadAppSettings, gbc);
		

		nameInput.setColumns(15);

		imageInput.setColumns(15);
		positionInput.setColumns(15);
		dateInput.setColumns(15);
		venueInput.setColumns(15);
		bioInput.setColumns(15);

		volunteerID.setColumns(15);

		authorizationCode.setColumns(15);

		this.pack();
		this.setTitle("GiGgle Pics Settings Constructor " + version);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			System.out.println("Server call started");
			String latestVersion = getFileContent(new URL("http://masterzangetsu.eu/Apps/Client/GiGglePics/version.txt"));
			
			if (latestVersion.equals(version)) {
				
			} else if (latestVersion.equals("fail")) {
				
			} else {
				
				int result = infoBox("Update Client Version to " + latestVersion, "New Version Available", "question");
				if (result == 0) {
					
					System.out.println("Yes i want to update");
					
				} else if (result == 1){
					
					System.out.println("No i dont want to update");
					
				}	
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			infoBox("Could not contact server", "Error", "question");
		}

	}
}
