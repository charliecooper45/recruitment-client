package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import database.beans.Vacancy;

//TODO NEXT: set max width so doesn`t resize
public class VacancyPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel vacancyNameLbl;
	private JLabel createdByLbl;
	private JComboBox<String> statusCmbBox;
	private JTextField dateTxtFld;
	private JTextField contactTxtFld;
	private JTextField phoneNoTxtFld;

	// components = leftBottomPanel
	private JPanel leftBottomPanel;
	private JButton addVacancyProfileBtn;
	private JButton removeVacancyProfileBtn;

	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	private JPanel vacancyProfilePanel;
	private JTextArea documentArea;

	// the displayed vacancy
	private Vacancy vacancy;

	public VacancyPanel() {
		init();
	}

	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		initLeftTopPanel();
		initLeftBottomPanel();
		initRightPanel();
	}

	private void initLeftTopPanel() {
		leftTopPanel = new JPanel(new GridBagLayout());
		leftTopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftTopPnlGbc = new GridBagConstraints();
		leftTopPnlGbc.weightx = 1;
		leftTopPnlGbc.weighty = 1;
		leftTopPnlGbc.anchor = GridBagConstraints.CENTER;

		vacancyNameLbl = new JLabel("Project Co-Ordinator @ Accenture");
		vacancyNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(vacancyNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("Created by: MC01");
		Utils.setGBC(leftTopPnlGbc, 1, 2, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);

		// labels
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Job Status:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Added:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact Phone Number:"), leftTopPnlGbc);

		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		statusCmbBox = new JComboBox<>();
		statusCmbBox.addItem("Open");
		statusCmbBox.addItem("Closed");
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(statusCmbBox, leftTopPnlGbc);
		dateTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(dateTxtFld, leftTopPnlGbc);
		contactTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(contactTxtFld, leftTopPnlGbc);
		phoneNoTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(phoneNoTxtFld, leftTopPnlGbc);

		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		add(leftTopPanel, gbc);
	}

	private void initLeftBottomPanel() {
		leftBottomPanel = new JPanel(new GridBagLayout());
		leftBottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftBottomPanelGbc = new GridBagConstraints();
		leftBottomPanelGbc.weightx = 1;
		leftBottomPanelGbc.weighty = 1;

		Utils.setGBC(leftBottomPanelGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftBottomPanel.add(new JLabel("Vacancy Options:"), leftBottomPanelGbc);

		JPanel firstRowPnl = new JPanel(new GridLayout(1, 2));
		addVacancyProfileBtn = new JButton("Add profile");
		firstRowPnl.add(addVacancyProfileBtn);
		removeVacancyProfileBtn = new JButton("Remove profile");
		firstRowPnl.add(removeVacancyProfileBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(firstRowPnl, leftBottomPanelGbc);

		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		add(leftBottomPanel, gbc);
	}

	private void initRightPanel() {
		gbc.weightx = 4;
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;

		tabbedPane = new JTabbedPane();

		vacancyProfilePanel = new JPanel(new BorderLayout());
		documentArea = new JTextArea();
		documentArea.setEditable(false);
		JScrollPane vacancyScrlPane = new JScrollPane(documentArea);
		vacancyScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		vacancyScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		vacancyProfilePanel.add(vacancyScrlPane, BorderLayout.CENTER);

		tabbedPane.addTab("Vacancy Profile", vacancyProfilePanel);
		tabbedPane.addTab("Shortlist", new JPanel());
		tabbedPane.addTab("Progress Report", new JPanel());
		tabbedPane.addTab("Notes", new JPanel());
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);

		Utils.setGBC(gbc, 2, 1, 1, 2, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}

	public void setDisplayedVacancy(Vacancy updatedVacancy, Path tempFile) {
		this.vacancy = updatedVacancy;

		if (updatedVacancy.getStatus()) {
			vacancyNameLbl.setForeground(Color.GREEN);
		} else {
			vacancyNameLbl.setForeground(Color.RED);
		}
		vacancyNameLbl.setText(updatedVacancy.getName() + " @ " + updatedVacancy.getOrganisationName());
		createdByLbl.setText(updatedVacancy.getUserId());
		dateTxtFld.setText(updatedVacancy.getVacancyDate().toString());
		contactTxtFld.setText(updatedVacancy.getContactName());
		phoneNoTxtFld.setText(updatedVacancy.getContactPhoneNumber());

		boolean open = updatedVacancy.getStatus();
		if (open) {
			statusCmbBox.setSelectedItem("Open");
		} else {
			statusCmbBox.setSelectedItem("Closed");
		}

		readVacancyProfile(tempFile);
	}

	public Vacancy getDisplayedVacancy() {
		return vacancy;
	}

	private void readVacancyProfile(Path path) {
		documentArea.setText("");
		WordExtractor extractor = null;

		try {
			if (path != null) {
				if (path.toString().endsWith(".docx") || path.toString().endsWith(".doc")) {
					// handle doc and dox file types
					File file = null;
					file = path.toFile();
					FileInputStream fis = new FileInputStream(file.getAbsolutePath());
					HWPFDocument document = new HWPFDocument(fis);
					extractor = new WordExtractor(document);
					String[] fileData = extractor.getParagraphText();

					for (int i = 0; i < fileData.length; i++) {
						if (fileData[i] != null) {
							documentArea.append(fileData[i]);
						}
					}

				} else if (path.toString().endsWith(".txt")) {
					// handle .txt file types
					List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
					for (String line : lines) {
						documentArea.append(line);
					}
				} else if (path.toString().endsWith(".pdf")) {
					PDFParser parser = null;
					String parsedText = null;
					;
					PDFTextStripper pdfStripper = null;
					PDDocument pdDoc = null;
					COSDocument cosDoc = null;
					try {
						parser = new PDFParser(new FileInputStream(path.toFile()));
						try {
							parser.parse();
							cosDoc = parser.getDocument();
							pdfStripper = new PDFTextStripper();
							pdDoc = new PDDocument(cosDoc);
							parsedText = pdfStripper.getText(pdDoc);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (cosDoc != null)
									cosDoc.close();
								if (pdDoc != null)
									pdDoc.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						documentArea.setText(parsedText);
					} catch (IOException e) {
						System.err.println("Unable to open PDF Parser. " + e.getMessage());
					}
				}
			} else {
				documentArea.setText("Vacancy profile not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			documentArea.setText("Vacancy profile not found.");
		} finally {
			if (extractor != null)
				try {
					extractor.close();
				} catch (IOException e) {
					e.printStackTrace();
					documentArea.setText("Vacancy profile not found.");
				}
		}
	}

	public File showFileChooser(DialogTypes dialogType) {
		switch (dialogType) {
		case VACANCYADDPROFILE:
			JFileChooser fc = new JFileChooser();
			fc.setMultiSelectionEnabled(false);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.addChoosableFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.getName().endsWith(".doc")) {
						return true;
					} else if (f.getName().endsWith(".docx")) {
						return true;
					} else if (f.getName().endsWith(".pdf")) {
						return true;
					} else if (f.getName().endsWith(".txt")) {
						return true;
					}
					return false;
				}

				@Override
				public String getDescription() {
					return "Text/Office/PDF files";
				}
			});
			fc.setDialogTitle("Select profile to add.");
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				return fc.getSelectedFile();
			}
		default:
			return null;
		}
	}

	public boolean showDialog(DialogTypes dialogType) {
		switch (dialogType) {
		case VACANCYREMOVEPROFILE:
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this profile?", "Confirm remove profile.", JOptionPane.YES_NO_OPTION);
			if(response == 0) 
				return true;
		default:
			return false;
		}
	}

	public void showErrorDialog(ErrorMessages errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void setVacancyPanelListener(ActionListener actionListener) {
		addVacancyProfileBtn.addActionListener(actionListener);
		removeVacancyProfileBtn.addActionListener(actionListener);
	}
}
