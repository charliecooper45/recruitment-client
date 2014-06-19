package gui;

import gui.listeners.CandidatePanelListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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

import database.beans.Candidate;
import database.beans.CandidateSkill;
import database.beans.Event;
import database.beans.Organisation;

public class CandidatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel candidateNameLbl;
	private JLabel createdByLbl;
	private JLabel candidateIdLbl;
	private JTextField titleTxtFld;
	private JComboBox<Organisation> orgCmbBox;
	private JTextField phoneNoTxtFld;
	private JTextField emailTxtFld;
	private JTextField addressTxtFld;
	private JButton saveChangesBtn;

	// components = leftBottomPanel
	private JPanel leftBottomPanel;
	private JButton addLinkedInProfileBtn;
	private JButton removeLinkedInProfileBtn;
	private JButton addCVBtn;
	private JButton removeCVBtn;
	private JButton addSkillBtn;
	private JButton removeSkillBtn;
	private JButton addEventBtn;
	private JButton removeEventBtn;

	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	private JPanel linkedInPanel;
	private JFXPanel jfxPanel;
	private WebEngine engine;
	private JPanel candidateCvPanel;
	private JTextArea documentArea;
	private JPanel skillsPanel;
	private JTable skillsTbl;
	private JScrollPane skillsScrl;
	private JPanel eventsPanel;
	private JTable eventsTbl;
	private JScrollPane eventsScrl;
	private JPanel notesPanel;
	private JTextArea notesTxtArea;
	private JScrollPane notesScrlPane;
	private JButton saveNotesBtn;

	// the displayed candidate
	private Candidate candidate;

	// the displayed organisations
	private List<Organisation> organisations;

	// the skills in the skillsTbl
	private List<CandidateSkill> skills;

	// the events in the eventsTbl
	private List<Event> events;

	public CandidatePanel() {
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

		candidateNameLbl = new JLabel("");
		candidateNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(candidateNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("Created by: MC01");
		Utils.setGBC(leftTopPnlGbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);
		candidateIdLbl = new JLabel("");
		Utils.setGBC(leftTopPnlGbc, 2, 2, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(candidateIdLbl, leftTopPnlGbc);

		// labels
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Job Title:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Organisation:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Phone Number:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Email Address:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 7, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Address:"), leftTopPnlGbc);

		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		titleTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(titleTxtFld, leftTopPnlGbc);
		orgCmbBox = new JComboBox<Organisation>();
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(orgCmbBox, leftTopPnlGbc);
		phoneNoTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(phoneNoTxtFld, leftTopPnlGbc);
		emailTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(emailTxtFld, leftTopPnlGbc);
		addressTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 7, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(addressTxtFld, leftTopPnlGbc);

		// button
		leftTopPnlGbc.anchor = GridBagConstraints.CENTER;
		saveChangesBtn = new JButton("Save candidate data");
		Utils.setGBC(leftTopPnlGbc, 1, 8, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(saveChangesBtn, leftTopPnlGbc);

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
		leftBottomPanel.add(new JLabel("Candidate Options:"), leftBottomPanelGbc);

		JPanel firstRowPnl = new JPanel(new GridLayout(1, 2));
		addLinkedInProfileBtn = new JButton("Add LinkedIn   ");
		firstRowPnl.add(addLinkedInProfileBtn);
		removeLinkedInProfileBtn = new JButton("Remove LinkedIn");
		firstRowPnl.add(removeLinkedInProfileBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(firstRowPnl, leftBottomPanelGbc);

		JPanel secondRowPnl = new JPanel(new GridLayout(1, 2));
		addCVBtn = new JButton("Add CV         ");
		secondRowPnl.add(addCVBtn);
		removeCVBtn = new JButton("Remove CV      ");
		secondRowPnl.add(removeCVBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(secondRowPnl, leftBottomPanelGbc);

		JPanel thirdRowPnl = new JPanel(new GridLayout(1, 2));
		addSkillBtn = new JButton("Add Skill       ");
		thirdRowPnl.add(addSkillBtn);
		removeSkillBtn = new JButton("Remove Skill    ");
		thirdRowPnl.add(removeSkillBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(thirdRowPnl, leftBottomPanelGbc);
		
		JPanel fourthRowPnl = new JPanel(new GridLayout(1, 2));
		addEventBtn = new JButton("Add Event       ");
		fourthRowPnl.add(addEventBtn);
		removeEventBtn = new JButton("Remove Event    ");
		fourthRowPnl.add(removeEventBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(fourthRowPnl, leftBottomPanelGbc);

		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		add(leftBottomPanel, gbc);
	}

	private void initRightPanel() {
		gbc.weightx = 4;
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;

		// create the panel to show the user`s LinkedIn profile
		linkedInPanel = new JPanel(new BorderLayout());
		jfxPanel = new JFXPanel();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Platform.setImplicitExit(false);
				WebView view = new WebView();
				engine = view.getEngine();
				jfxPanel.setScene(new Scene(view));
			}
		});
		linkedInPanel.add(jfxPanel, BorderLayout.CENTER);

		// create the panel to show the user`s CV
		candidateCvPanel = new JPanel(new BorderLayout());
		documentArea = new JTextArea();
		documentArea.setEditable(false);
		JScrollPane vacancyScrlPane = new JScrollPane(documentArea);
		vacancyScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		vacancyScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		candidateCvPanel.add(vacancyScrlPane, BorderLayout.CENTER);

		// setup the skills panel
		skillsPanel = new JPanel(new BorderLayout());
		skillsTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Name", "User ID" };

			@Override
			public Object getValueAt(int row, int column) {
				CandidateSkill skill = skills.get(row);

				switch (column) {
				case 0:
					return skill.getSkillName();
				case 1:
					return skill.getUserId();
				}
				return "";
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public int getRowCount() {
				if (skills != null) {
					return skills.size();
				} else {
					return 0;
				}
			}

			@Override
			public int getColumnCount() {
				return 2;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}
		});
		skillsTbl.getTableHeader().setFont(skillsTbl.getFont().deriveFont(Font.BOLD, 16));
		skillsTbl.setFont(skillsTbl.getFont().deriveFont(14.0f));
		skillsTbl.setRowHeight(20);
		skillsScrl = new JScrollPane(skillsTbl);
		skillsScrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		skillsScrl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		skillsPanel.add(skillsScrl, BorderLayout.CENTER);

		// setup the events panel
		eventsPanel = new JPanel(new BorderLayout());
		eventsTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "Date", "Time", "Event Type", "Vacancy", "User" };

			@Override
			public Object getValueAt(int row, int column) {
				Event event = events.get(row);

				switch (column) {
				case 0:
					return event.getDate();
				case 1:
					return event.getTime();
				case 2:
					return event.getEventType();
				case 3:
					return event.getVacancyName();
				case 4:
					return event.getUserId();
				}
				return "";
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public int getRowCount() {
				if (events != null) {
					return events.size();
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
		eventsTbl.getTableHeader().setFont(eventsTbl.getFont().deriveFont(Font.BOLD, 16));
		eventsTbl.setFont(eventsTbl.getFont().deriveFont(14.0f));
		eventsTbl.setRowHeight(20);
		eventsScrl = new JScrollPane(eventsTbl);
		eventsScrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		eventsScrl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		eventsPanel.add(eventsScrl, BorderLayout.CENTER);

		// setup the notes panel
		notesPanel = new JPanel(new BorderLayout());
		notesTxtArea = new JTextArea();
		notesTxtArea.setLineWrap(true);
		notesScrlPane = new JScrollPane(notesTxtArea);
		notesScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		notesScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesPanel.add(notesScrlPane, BorderLayout.CENTER);
		saveNotesBtn = new JButton("Save Notes");
		notesPanel.add(saveNotesBtn, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("LinkedIn Profile", linkedInPanel);
		tabbedPane.addTab("CV", candidateCvPanel);
		tabbedPane.addTab("Key Skills", skillsPanel);
		tabbedPane.addTab("Events", eventsPanel);
		tabbedPane.addTab("Notes", notesPanel);
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);

		Utils.setGBC(gbc, 2, 1, 1, 2, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}

	public void setDisplayedCandidate(final Candidate updatedCandidate, Path tempFile, List<Organisation> organisations) {
		this.candidate = updatedCandidate;
		tabbedPane.setSelectedIndex(0);

		candidateNameLbl.setText(updatedCandidate.getFirstName() + " " + updatedCandidate.getSurname());
		createdByLbl.setText(updatedCandidate.getUserId());
		candidateIdLbl.setText(String.valueOf(updatedCandidate.getId()));
		titleTxtFld.setText(updatedCandidate.getJobTitle());
		phoneNoTxtFld.setText(updatedCandidate.getPhoneNumber());
		emailTxtFld.setText(updatedCandidate.getEmailAddress());
		addressTxtFld.setText(updatedCandidate.getAddress());
		setDisplayedOrganisations(organisations);

		// show the LinkedIn profile
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				engine.load(updatedCandidate.getLinkedInProfile());
			}
		});

		// load the cv
		readCandidateCv(tempFile);
	}

	private void setDisplayedOrganisations(List<Organisation> organisations) {
		this.organisations = organisations;
		Organisation displayedOrg = null;
		orgCmbBox.removeAllItems();

		orgCmbBox.addItem(new Organisation(-1, "No Organisation", null, null, null, null, null, null, null, -1));
		for (Organisation org : organisations) {
			orgCmbBox.addItem(org);

			if (org.getId() == candidate.getOrganisationId()) {
				displayedOrg = org;
			}
		}

		if (displayedOrg != null)
			orgCmbBox.setSelectedItem(displayedOrg);
	}

	private void readCandidateCv(Path path) {
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
				documentArea.setText("Candidate CV not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			documentArea.setText("Candidate CV not found.");
		} finally {
			if (extractor != null)
				try {
					extractor.close();
				} catch (IOException e) {
					e.printStackTrace();
					documentArea.setText("Candidate CV not found.");
				}
		}
	}

	public Candidate getSelectedCandidate() {
		return candidate;
	}

	public String getCandidateNotes() {
		return notesTxtArea.getText();
	}
	
	public Candidate getUpdatedCandidate() {
		String jobTitle = titleTxtFld.getText().trim();
		Organisation organisation = (Organisation) orgCmbBox.getSelectedItem();
		String phoneNumber = phoneNoTxtFld.getText().trim();
		String email = emailTxtFld.getText().trim();
		String address = addressTxtFld.getText().trim();

		if (jobTitle.length() == 0)
			jobTitle = null;
		if (phoneNumber.length() == 0)
			phoneNumber = null;
		if (email.length() == 0)
			email = null;
		if (address.length() == 0)
			address = null;

		Candidate updatedCandidate = new Candidate(candidate.getId(), candidate.getFirstName(), candidate.getSurname(), jobTitle, organisation.getOrganisationId(), organisation.getOrganisationName(), phoneNumber, email, address, candidate.getNotes(), candidate.getLinkedInProfile(), candidate.getCV(), candidate.getUserId());

		this.candidate = updatedCandidate;
		return updatedCandidate;
	}

	public void updateShownLinkedInProfile(final URL url) {
		if (url == null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					engine.load("");
				}
			});
		} else {
			// Load the url
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					engine.load(url.toString());
				}
			});
		}
	}

	public void updateDisplayedCandidateSkills(List<CandidateSkill> candidateSkills) {
		this.skills = candidateSkills;
		DefaultTableModel model = (DefaultTableModel) skillsTbl.getModel();
		model.fireTableDataChanged();
	}

	public void updateDisplayedCandidateEvents(List<Event> events) {
		this.events = events;
		DefaultTableModel model = (DefaultTableModel) eventsTbl.getModel();
		model.fireTableDataChanged();
	}

	public void setCandidatePanelListener(CandidatePanelListener listener) {
		saveChangesBtn.addActionListener(listener);
		addLinkedInProfileBtn.addActionListener(listener);
		removeLinkedInProfileBtn.addActionListener(listener);
		addCVBtn.addActionListener(listener);
		removeCVBtn.addActionListener(listener);
		addSkillBtn.addActionListener(listener);
		removeSkillBtn.addActionListener(listener);
		addEventBtn.addActionListener(listener);
		removeEventBtn.addActionListener(listener);
		saveNotesBtn.addActionListener(listener);
		tabbedPane.addMouseListener(listener);
	}
}
