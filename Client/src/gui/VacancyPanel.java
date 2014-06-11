package gui;

import gui.listeners.VacancyPanelListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import database.beans.Event;
import database.beans.Vacancy;

//TODO NEXT: set max width so doesn`t resize
/**
 * Displays a vacancy to the user.
 * @author Charlie
 */
public class VacancyPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel vacancyNameLbl;
	private JLabel organisationNameLbl;
	private JLabel createdByLbl;
	private JLabel vacancyIdLbl;
	private JComboBox<String> statusCmbBox;
	private JTextField dateTxtFld;
	private JTextField contactTxtFld;
	private JTextField phoneNoTxtFld;

	// components - leftBottomPanel
	private JPanel leftBottomPanel;
	private JButton addVacancyProfileBtn;
	private JButton removeVacancyProfileBtn;

	// components - leftPanel
	private JPanel leftPanel;

	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	private JPanel vacancyProfilePanel;
	private JTextArea documentArea;
	private JPanel shortlistPanel;
	private JTable shortlistTbl;
	private JScrollPane shortlistScrl;

	// the displayed vacancy
	private Vacancy vacancy;
	
	// the shortlist events shown in the shortlistTbl
	private List<Event> shortlistEvents;

	public VacancyPanel() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());

		leftPanel = new JPanel(new GridLayout(2, 1));
		leftPanel.setPreferredSize(new Dimension(350, 10));
		initLeftTopPanel();
		initLeftBottomPanel();
		add(leftPanel, BorderLayout.WEST);
		initRightPanel();
	}

	private void initLeftTopPanel() {
		leftTopPanel = new JPanel(new GridBagLayout());
		leftTopPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftTopPnlGbc = new GridBagConstraints();
		leftTopPnlGbc.weightx = 1;
		leftTopPnlGbc.weighty = 0.5;
		leftTopPnlGbc.anchor = GridBagConstraints.CENTER;

		vacancyNameLbl = new JLabel();
		vacancyNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(vacancyNameLbl, leftTopPnlGbc);
		organisationNameLbl = new JLabel();
		organisationNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 2, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(organisationNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("");
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);
		vacancyIdLbl = new JLabel("");
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(vacancyIdLbl, leftTopPnlGbc);

		// labels
		leftTopPnlGbc.weighty = 1;
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Job Status:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Added:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Contact Phone Number:"), leftTopPnlGbc);

		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		statusCmbBox = new JComboBox<>();
		statusCmbBox.addItem("Open");
		statusCmbBox.addItem("Closed");
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(statusCmbBox, leftTopPnlGbc);
		dateTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(dateTxtFld, leftTopPnlGbc);
		contactTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(contactTxtFld, leftTopPnlGbc);
		phoneNoTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 7, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(phoneNoTxtFld, leftTopPnlGbc);

		leftPanel.add(leftTopPanel);
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

		leftPanel.add(leftBottomPanel);
	}

	private void initRightPanel() {
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;

		// set up the vacancy profile panel
		vacancyProfilePanel = new JPanel(new BorderLayout());
		documentArea = new JTextArea();
		documentArea.setEditable(false);
		JScrollPane vacancyScrlPane = new JScrollPane(documentArea);
		vacancyScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		vacancyScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		vacancyProfilePanel.add(vacancyScrlPane, BorderLayout.CENTER);
		
		// setup the shortlist panel
		shortlistEvents = new ArrayList<>();
		shortlistPanel = new JPanel(new BorderLayout());
		shortlistTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Candidate ID", "Candidate Name", "Phone Number", "Date", "UserID"};
			
			@Override
			public Object getValueAt(int row, int column) {
				Event event = shortlistEvents.get(row);
				
				switch (column) {
				case 0:
					return event.getCandidate().getId();
				case 1:
					return event.getCandidate().getFirstName() + " " + event.getCandidate().getSurname();
				case 2:
					return event.getCandidate().getPhoneNumber();
				case 3:
					return event.getDate();
				case 4:
					return event.getUserId();
				}
				return "test data";
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public int getRowCount() {
				if(shortlistEvents != null) {
					return shortlistEvents.size();
				} else { 
					return 0;
				}
			}
			
			@Override
			public int getColumnCount() {
				return 5;
			}
			
			@Override
			public String getColumnName(int index) {
				return columns[index];
			}
		});
		shortlistTbl.getTableHeader().setFont(shortlistTbl.getFont().deriveFont(Font.BOLD, 16));
		shortlistTbl.setFont(shortlistTbl.getFont().deriveFont(14.0f));
		shortlistTbl.setRowHeight(20);
		shortlistScrl = new JScrollPane(shortlistTbl);
		shortlistScrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		shortlistScrl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		shortlistPanel.add(shortlistScrl, BorderLayout.CENTER);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Vacancy Profile", vacancyProfilePanel);
		tabbedPane.addTab("Shortlist", shortlistPanel);
		tabbedPane.addTab("Progress Report", new JPanel());
		tabbedPane.addTab("Notes", new JPanel());
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);

		add(rightPanel, BorderLayout.CENTER);
	}

	public void setDisplayedVacancy(Vacancy updatedVacancy, Path tempFile) {
		this.vacancy = updatedVacancy;
		tabbedPane.setSelectedIndex(0);

		if (updatedVacancy.getStatus()) {
			vacancyNameLbl.setForeground(Color.GREEN);
			organisationNameLbl.setForeground(Color.GREEN);
		} else {
			vacancyNameLbl.setForeground(Color.RED);
			organisationNameLbl.setForeground(Color.RED);
		}
		vacancyNameLbl.setText(updatedVacancy.getName());
		organisationNameLbl.setText(updatedVacancy.getOrganisationName());
		createdByLbl.setText(updatedVacancy.getUserId());
		vacancyIdLbl.setText(String.valueOf(updatedVacancy.getVacancyId()));
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

	public Event getSelectedShortlistEvent() {
		return shortlistEvents.get(shortlistTbl.getSelectedRow());
	}
	
	public void setVacancyStatus(boolean status) {
		vacancy.setStatus(status);

		if (status) {
			vacancyNameLbl.setForeground(Color.GREEN);
			organisationNameLbl.setForeground(Color.GREEN);
		} else {
			vacancyNameLbl.setForeground(Color.RED);
			organisationNameLbl.setForeground(Color.RED);
		}
	}

	public void updateShortlist(List<Event> shortlistEvents) {
		this.shortlistEvents = shortlistEvents;
		DefaultTableModel model = (DefaultTableModel) shortlistTbl.getModel();
		model.fireTableDataChanged();
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

	public void setVacancyPanelListener(VacancyPanelListener listener) {
		addVacancyProfileBtn.addActionListener(listener);
		removeVacancyProfileBtn.addActionListener(listener);
		statusCmbBox.addActionListener(listener);
		tabbedPane.addMouseListener(listener);
		shortlistTbl.addKeyListener(listener);
	}

}
