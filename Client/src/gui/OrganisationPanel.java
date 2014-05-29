package gui;

import gui.listeners.OrganisationPanelListener;

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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import database.beans.Organisation;

/**
 * Displays an organisation to the user.
 * @author Charlie
 */
public class OrganisationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel orgNameLbl;
	private JLabel createdByLbl;
	private JLabel orgIdLbl;
	private JTextField switchboardTxtFld;
	private JTextField emailTxtFld;
	private JTextField websiteTxtFLd;
	private JTextField addressTxtFld;

	// components = leftBottomPanel
	private JPanel leftBottomPanel;
	private JButton addTobBtn;
	private JButton removeTobBtn;
	
	// components - leftPanel
	private JPanel leftPanel;

	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	private JPanel tobPanel;
	private JTextArea documentArea;
	
	// the displayed Organisation
	private Organisation organisation;

	public OrganisationPanel() {
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
		leftTopPnlGbc.weighty = 1;
		leftTopPnlGbc.anchor = GridBagConstraints.CENTER;

		orgNameLbl = new JLabel("");
		orgNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(orgNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("");
		Utils.setGBC(leftTopPnlGbc, 1, 2, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);
		orgIdLbl = new JLabel("");
		Utils.setGBC(leftTopPnlGbc, 2, 2, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(orgIdLbl, leftTopPnlGbc);

		// labels
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Switchboard:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Email address:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Website:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Address:"), leftTopPnlGbc);

		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		switchboardTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(switchboardTxtFld, leftTopPnlGbc);
		emailTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(emailTxtFld, leftTopPnlGbc);
		websiteTxtFLd = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(websiteTxtFLd, leftTopPnlGbc);
		addressTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(addressTxtFld, leftTopPnlGbc);

		leftPanel.add(leftTopPanel);
	}

	private void initLeftBottomPanel() {
		leftBottomPanel = new JPanel(new GridBagLayout());
		leftBottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GridBagConstraints leftBottomPanelGbc = new GridBagConstraints();
		leftBottomPanelGbc.weightx = 1;
		leftBottomPanelGbc.weighty = 1;

		Utils.setGBC(leftBottomPanelGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftBottomPanel.add(new JLabel("Organisation Options:"), leftBottomPanelGbc);

		JPanel firstRowPnl = new JPanel(new GridLayout(1, 2));
		addTobBtn = new JButton("Add TOB");
		firstRowPnl.add(addTobBtn);
		removeTobBtn = new JButton("Remove TOB");
		firstRowPnl.add(removeTobBtn);
		Utils.setGBC(leftBottomPanelGbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		leftBottomPanel.add(firstRowPnl, leftBottomPanelGbc);

		leftPanel.add(leftBottomPanel);
	}

	private void initRightPanel() {
		rightPanel = new JPanel(new GridBagLayout());
		GridBagConstraints rightPanelGbc = new GridBagConstraints();
		rightPanelGbc.weightx = 1;
		rightPanelGbc.weighty = 1;

		tobPanel = new JPanel(new BorderLayout());
		documentArea = new JTextArea();
		documentArea.setEditable(false);
		JScrollPane tobScrlPane = new JScrollPane(documentArea);
		tobScrlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tobScrlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tobPanel.add(tobScrlPane, BorderLayout.CENTER);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Vacancies", new JPanel());
		tabbedPane.addTab("Terms of Business", tobPanel);
		tabbedPane.addTab("Contacts", new JPanel());
		tabbedPane.addTab("Notes", new JPanel());
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);

		add(rightPanel, BorderLayout.CENTER);
	}

	public void setDisplayedOrganisation(Organisation updatedOrganisation, Path tempFile) {
		organisation = updatedOrganisation;
		
		orgNameLbl.setText(updatedOrganisation.getOrganisationName());
		createdByLbl.setText(updatedOrganisation.getUserId());
		orgIdLbl.setText(String.valueOf(updatedOrganisation.getId()));
		switchboardTxtFld.setText(updatedOrganisation.getPhoneNumber());
		emailTxtFld.setText(updatedOrganisation.getEmailAddress());
		websiteTxtFLd.setText(updatedOrganisation.getWebsite());
		addressTxtFld.setText(updatedOrganisation.getAddress());
		
		readOrganisationTob(tempFile);
	}

	private void readOrganisationTob(Path path) {
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
				documentArea.setText("Terms of business not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			documentArea.setText("Terms of business not found.");
		} finally {
			if (extractor != null)
				try {
					extractor.close();
				} catch (IOException e) {
					e.printStackTrace();
					documentArea.setText("Terms of business not found.");
				}
		}
	}

	public Organisation getDisplayedOrganisation() {
		return organisation;
	}
	
	public void setOrganisationPanelListener(OrganisationPanelListener organisationPanelListener) {
		addTobBtn.addActionListener(organisationPanelListener);
		removeTobBtn.addActionListener(organisationPanelListener);
	}

}
