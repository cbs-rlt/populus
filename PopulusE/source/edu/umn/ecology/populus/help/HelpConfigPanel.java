/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.help;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.core.PopPreferencesStorage;
import edu.umn.ecology.populus.fileio.BasicFilenameFilter;
import edu.umn.ecology.populus.fileio.IOUtility;
import edu.umn.ecology.populus.fileio.Logging;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class HelpConfigPanel extends JPanel {
	private static final long serialVersionUID = 687468888728886427L;
	private JTextField filePathField = new JTextField();
	private JComboBox fileOrURLBox = new JComboBox();
	private JComboBox languageBox = new JComboBox();
	private JLabel fileStatusLabel = new JLabel("");
	private JTextField txtCustomExecStr = new JTextField();
	private String[] langStrings = new String[] {"English", "Spanish", "Portuguese"};
	private JRadioButton rdbtnCustomCommand = new JRadioButton("Custom command");
	private JRadioButton rdbtnJnlpApi = new JRadioButton("JNLP API");
	private JRadioButton rdbtnDesktopApi = new JRadioButton("Desktop API");
	private JButton btnTestButton = new JButton("Test");
	private JTextArea testResultText = new JTextArea();
	private JButton browseButton = new JButton("Browse...");

	public HelpConfigPanel() {
		setLayout(new BorderLayout(0, 0));

		String loc = PDFHelpFileMgr.getHelpFileAsFileName(false);
		if (loc.equals(PopPreferencesStorage.DEFAULT_HELP_FILE)) {
			try {
				String storeDir = System.getProperty( "user.home", "." );
				loc = storeDir + File.separator + "PopulusHelp5.5.pdf";
			} catch (Exception e) {
				Logging.log("Couldn't get default help location");
				Logging.log(e);
			}
		}

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.WEST);

		JPanel filePanel = new JPanel();
		tabbedPane.addTab("Help File", null, filePanel, null);
		GridBagLayout gbl_filePanel = new GridBagLayout();
		gbl_filePanel.columnWidths = new int[]{164, 137, 0};
		gbl_filePanel.rowHeights = new int[]{22, 0, 0};
		gbl_filePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_filePanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		filePanel.setLayout(gbl_filePanel);

		JPanel fileLocationPanel = new JPanel();
		fileLocationPanel.setBorder(new TitledBorder(null, "Help File Location", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_fileLocationPanel = new GridBagConstraints();
		gbc_fileLocationPanel.insets = new Insets(5, 5, 5, 5);
		gbc_fileLocationPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileLocationPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_fileLocationPanel.gridx = 0;
		gbc_fileLocationPanel.gridy = 0;
		filePanel.add(fileLocationPanel, gbc_fileLocationPanel);
		GridBagLayout gbl_fileLocationPanel = new GridBagLayout();
		gbl_fileLocationPanel.columnWidths = new int[]{44, 86, 0, 0};
		gbl_fileLocationPanel.rowHeights = new int[]{20, 0, 0};
		gbl_fileLocationPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_fileLocationPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		fileLocationPanel.setLayout(gbl_fileLocationPanel);
		fileOrURLBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filenameChanged();
			}
		});

		fileOrURLBox.setToolTipText("Select whether resource is local file or remote URI (e.g., http)");
		fileOrURLBox.setModel(new DefaultComboBoxModel(new String[] {"file", "URI"}));
		fileOrURLBox.setSelectedIndex(0);
		GridBagConstraints gbc_fileOrURLBox = new GridBagConstraints();
		gbc_fileOrURLBox.insets = new Insets(0, 0, 5, 5);
		gbc_fileOrURLBox.anchor = GridBagConstraints.NORTHWEST;
		gbc_fileOrURLBox.gridx = 0;
		gbc_fileOrURLBox.gridy = 0;
		fileLocationPanel.add(fileOrURLBox, gbc_fileOrURLBox);
		filePathField.setText(loc);

		filePathField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filenameChanged();
			}
		});

		ButtonGroup bgMethods = new ButtonGroup();

		GridBagConstraints gbc_filePathField = new GridBagConstraints();
		gbc_filePathField.weightx = 1.0;
		gbc_filePathField.insets = new Insets(0, 0, 5, 5);
		gbc_filePathField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePathField.anchor = GridBagConstraints.NORTHWEST;
		gbc_filePathField.gridx = 1;
		gbc_filePathField.gridy = 0;
		fileLocationPanel.add(filePathField, gbc_filePathField);
		filePathField.setColumns(10);

		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isFileMode()) {
					//get filename
					JFileChooser fc = new JFileChooser();

					//Switched from URI to file
					String fileStr = filePathField.getText();
					File f = new File(fileStr);
					fc = new JFileChooser( f.getParent() );
					
					fc.setSelectedFile( f );
					fc.setFileFilter( new BasicFilenameFilter( "pdf" ) );

					//get user input
					int state = fc.showSaveDialog( DesktopWindow.defaultWindow );
					if( state == JFileChooser.APPROVE_OPTION ) {
						String filename;
						filename = fc.getSelectedFile().getAbsolutePath();
						filePathField.setText(filename);
						filenameChanged();
					}
				} else {
					Logging.log("We shouldn't be allowed to click browse button in URI mode.");
				}

			}
		});
		GridBagConstraints gbc_browseButton = new GridBagConstraints();
		gbc_browseButton.insets = new Insets(0, 0, 5, 0);
		gbc_browseButton.gridx = 2;
		gbc_browseButton.gridy = 0;
		fileLocationPanel.add(browseButton, gbc_browseButton);

		fileStatusLabel.setToolTipText("file status");
		fileStatusLabel.setForeground(Color.RED);
		GridBagConstraints gbc_fileStatusLabel = new GridBagConstraints();
		gbc_fileStatusLabel.gridwidth = 3;
		gbc_fileStatusLabel.insets = new Insets(5, 5, 5, 5);
		gbc_fileStatusLabel.gridx = 0;
		gbc_fileStatusLabel.gridy = 1;
		fileLocationPanel.add(fileStatusLabel, gbc_fileStatusLabel);

		JPanel languagePanel = new JPanel();
		languagePanel.setBorder(new TitledBorder(null, "Copy help file to disk", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_languagePanel = new GridBagConstraints();
		gbc_languagePanel.insets = new Insets(5, 5, 5, 5);
		gbc_languagePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_languagePanel.anchor = GridBagConstraints.NORTH;
		gbc_languagePanel.gridx = 0;
		gbc_languagePanel.gridy = 1;
		filePanel.add(languagePanel, gbc_languagePanel);

		JLabel languageLabel = new JLabel("Language:");
		languagePanel.add(languageLabel);

		languageBox.setModel(new DefaultComboBoxModel(langStrings));
		languagePanel.add(languageBox);

		JButton btnCopyHelpTo = new JButton("Copy help file to local disk now");
		btnCopyHelpTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyHelpFileOut(true);
			}
		});
		languagePanel.add(btnCopyHelpTo);

		JPanel openMethodPanel = new JPanel();
		tabbedPane.addTab("Open Method", null, openMethodPanel, null);
		GridBagLayout gbl_openMethodPanel = new GridBagLayout();
		gbl_openMethodPanel.columnWidths = new int[]{0, 165, 85, 69, 0};
		gbl_openMethodPanel.rowHeights = new int[]{23, 0, 0, 0, 0, 0};
		gbl_openMethodPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_openMethodPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		openMethodPanel.setLayout(gbl_openMethodPanel);

		bgMethods.add(rdbtnDesktopApi);
		GridBagConstraints gbc_rdbtnDesktopApi = new GridBagConstraints();
		gbc_rdbtnDesktopApi.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnDesktopApi.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnDesktopApi.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnDesktopApi.gridx = 1;
		gbc_rdbtnDesktopApi.gridy = 0;
		rdbtnDesktopApi.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				openMethodChanged();
			}
		});
		openMethodPanel.add(rdbtnDesktopApi, gbc_rdbtnDesktopApi);

		bgMethods.add(rdbtnJnlpApi);
		GridBagConstraints gbc_rdbtnJnlpApi = new GridBagConstraints();
		gbc_rdbtnJnlpApi.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnJnlpApi.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnJnlpApi.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnJnlpApi.gridx = 1;
		gbc_rdbtnJnlpApi.gridy = 1;
		openMethodPanel.add(rdbtnJnlpApi, gbc_rdbtnJnlpApi);
		rdbtnJnlpApi.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				openMethodChanged();
			}
		});

		bgMethods.add(rdbtnCustomCommand);
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnNewRadioButton.gridx = 1;
		gbc_rdbtnNewRadioButton.gridy = 2;
		openMethodPanel.add(rdbtnCustomCommand, gbc_rdbtnNewRadioButton);
		rdbtnCustomCommand.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				openMethodChanged();
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		btnTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<String> results = new Vector<String>();
				boolean worked = HelpUtilities.displayHelpTrial(getOpenPDFMethod(), results);
				StringBuffer sb = new StringBuffer();
				for (String s : results) {
					sb.append(s);
					sb.append("\n");
				}
				testResultText.setText(sb.toString());
				if (worked) {
					testResultText.setForeground(Color.black);
				} else {
					testResultText.setForeground(Color.red);
				}
			}
		});
		openMethodPanel.add(btnTestButton, gbc_btnNewButton);

		GridBagConstraints gbcCustomExecStr = new GridBagConstraints();
		gbcCustomExecStr.weightx = 1.0;
		gbcCustomExecStr.fill = GridBagConstraints.HORIZONTAL;
		gbcCustomExecStr.insets = new Insets(0, 20, 5, 5);
		gbcCustomExecStr.gridx = 1;
		gbcCustomExecStr.gridy = 3;
		txtCustomExecStr.setToolTipText("Command to run, %1 is filename with prefix; %2 is help topic tag; %3 is raw file name");

		openMethodPanel.add(txtCustomExecStr, gbcCustomExecStr);

		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.weightx = 1.0;
		gbc_textArea.weighty = 1.0;
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 4;
		testResultText.setEditable(false);
		openMethodPanel.add(testResultText, gbc_textArea);

		loadState();
	}

	private boolean isFileMode() {
		return 0 == fileOrURLBox.getSelectedIndex();
	}

	private void loadState() {
		languageBox.setSelectedItem(PopPreferencesStorage.getHelpLang());
		rdbtnDesktopApi.setSelected(OpenPDFMethod.DESKTOP == PopPreferencesStorage.getOpenPDFMethod());
		rdbtnJnlpApi.setSelected(OpenPDFMethod.JNLP == PopPreferencesStorage.getOpenPDFMethod());
		rdbtnCustomCommand.setSelected(OpenPDFMethod.CUSTOM_EXEC == PopPreferencesStorage.getOpenPDFMethod());
		txtCustomExecStr.setText(PopPreferencesStorage.getOpenPDFCommand());

		openMethodChanged();
		filenameChanged();
	}

	private void openMethodChanged() {
		int selected = getOpenPDFMethod().getOpenMethod();
		txtCustomExecStr.setEditable(OpenPDFMethod.CUSTOM_EXEC == selected);
	}

	private String getLanguageSelected() {
		return (String) languageBox.getSelectedItem();
	}

	private boolean isFilenameValid() {
		boolean okay = false;
		if (isFileMode()) {
			String s = filePathField.getText();
			java.io.File f = new java.io.File(s);
			okay = f.canRead();
		} else {
			okay = true;
		}
		return okay;
	}
	
	private void updateFileStatusLabel() {
		boolean okay = isFilenameValid();
		if (okay) {
			fileStatusLabel.setText("");
		} else {
			fileStatusLabel.setText("Will copy file over since it doesn't exist.");
		}	
	}

	//Possibly update the filename if needed.
	private void filenameChanged() {
		//Logging.log("Selected idx is " + fileOrURLBox.getSelectedIndex());
		if (isFileMode()) {
			//Switched from URI to file
			String uriStr = filePathField.getText();
			filePathField.setText(IOUtility.convertURIToPath(uriStr, uriStr));
			browseButton.setEnabled(true);
		} else {
			//Switched from file to URI
			String fileStr = filePathField.getText();
			filePathField.setText(IOUtility.convertPathToURI(fileStr, fileStr));
			browseButton.setEnabled(false);
		}
		updateFileStatusLabel();
	}

	
	private String getURIString() {
		String fileStr = filePathField.getText();
		String uriText = "";
		if (isFileMode()) {
			//If it's a file, we need to convert it to URI
			try {
				uriText = IOUtility.convertPathToURI(fileStr, fileStr);
			} catch (Exception e) {
				Logging.log("Cannot convert " + fileStr + " to URI");
			}
		} else {
			uriText = fileStr;
		}
		return uriText;
	}

	private void copyHelpFileOut(boolean force) {
		//Copy file from resource to local directory
		//if file is not there already...
		try {
			String uriString = getURIString();
			String fname = IOUtility.convertURIToPath(uriString, uriString);
			File newf = new File(fname);
			if(force || !newf.canRead())
			{
				Logging.log("Copying source file from " + PDFHelpFileMgr.getHelpLangSourceFile()
						+ " to " + newf.toString());
				PopPreferencesStorage.setHelpLanguage(getLanguageSelected());
				InputStream is = HelpUtilities.class.getResourceAsStream(PDFHelpFileMgr.getHelpLangSourceFile());
				OutputStream os = new FileOutputStream(newf);

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}

				is.close();
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			Logging.log("Could not save help file out to local disk:");
			Logging.log(e);
		}
		updateFileStatusLabel();
	}
	
	
	public void doConfirmAction() {
		PopPreferencesStorage.setHelpFileLocation(this.getURIString());
		PopPreferencesStorage.setHelpLanguage(this.getLanguageSelected());
		if(!this.isFilenameValid()) {
			this.copyHelpFileOut(false); //Will not copy out by default
		}
		PopPreferencesStorage.setOpenPDFObject(getOpenPDFMethod());
	}

	private OpenPDFMethod getOpenPDFMethod() {
		int opm = OpenPDFMethod.DESKTOP;
		if (rdbtnJnlpApi.isSelected()) {
			opm = OpenPDFMethod.JNLP;
		} else if (rdbtnCustomCommand.isSelected()){
			opm = OpenPDFMethod.CUSTOM_EXEC;
		}
		return new OpenPDFMethod(opm, txtCustomExecStr.getText());
	}

	public static void launchWindow() {
		HelpConfigPanel configPanel = new HelpConfigPanel();
		int result = JOptionPane.showConfirmDialog(DesktopWindow.defaultWindow,
				configPanel,
				"Set up help file",
				JOptionPane.OK_CANCEL_OPTION);

		if(result == 0) {
			configPanel.doConfirmAction();
		}
	}

}
