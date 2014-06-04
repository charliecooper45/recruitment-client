package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import database.beans.Candidate;

public class CandidatePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// components - leftTopPanel
	private JPanel leftTopPanel;
	private JLabel candidateNameLbl;
	private JLabel createdByLbl;
	private JTextField titleTxtFld;
	private JTextField phoneNoTxtFld;
	private JTextField emailTxtFld;
	private JTextField addressTxtFld;

	// components = leftBottomPanel
	private JPanel leftBottomPanel;

	// components - rightPanel
	private JPanel rightPanel;
	private JTabbedPane tabbedPane;
	private JPanel linkedInPanel;

	// the displayed candidate
	private Candidate candidate;
	private JFXPanel jfxPanel;
	private WebEngine engine;

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

		candidateNameLbl = new JLabel("Lionel Messi");
		candidateNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		Utils.setGBC(leftTopPnlGbc, 1, 1, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(candidateNameLbl, leftTopPnlGbc);
		createdByLbl = new JLabel("Created by: MC01");
		Utils.setGBC(leftTopPnlGbc, 1, 2, 2, 1, GridBagConstraints.NONE);
		leftTopPanel.add(createdByLbl, leftTopPnlGbc);

		// labels
		leftTopPnlGbc.insets = new Insets(0, 15, 0, 0);
		leftTopPnlGbc.anchor = GridBagConstraints.LINE_START;
		Utils.setGBC(leftTopPnlGbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Job Title:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 4, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Phone Number:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 5, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Email Address:"), leftTopPnlGbc);
		Utils.setGBC(leftTopPnlGbc, 1, 6, 1, 1, GridBagConstraints.NONE);
		leftTopPanel.add(new JLabel("Address:"), leftTopPnlGbc);

		// fields
		leftTopPnlGbc.insets = new Insets(0, 0, 0, 15);
		leftTopPnlGbc.weightx = 3;
		titleTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 3, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(titleTxtFld, leftTopPnlGbc);
		phoneNoTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 4, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(phoneNoTxtFld, leftTopPnlGbc);
		emailTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 5, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(emailTxtFld, leftTopPnlGbc);
		addressTxtFld = new JTextField();
		Utils.setGBC(leftTopPnlGbc, 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
		leftTopPanel.add(addressTxtFld, leftTopPnlGbc);

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

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("LinkedIn Profile", linkedInPanel);
		tabbedPane.addTab("CV", new JPanel());
		tabbedPane.addTab("Key Skills", new JPanel());
		tabbedPane.addTab("Events", new JPanel());
		tabbedPane.addTab("Notes", new JPanel());
		Utils.setGBC(rightPanelGbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		rightPanel.add(tabbedPane, rightPanelGbc);

		Utils.setGBC(gbc, 2, 1, 1, 2, GridBagConstraints.BOTH);
		add(rightPanel, gbc);
	}

	public void setDisplayedCandidate(final Candidate updatedCandidate, Path tempFile) {
		this.candidate = updatedCandidate;

		candidateNameLbl.setText(updatedCandidate.getFirstName() + " " + updatedCandidate.getSurname());
		createdByLbl.setText(updatedCandidate.getUserId());
		titleTxtFld.setText(updatedCandidate.getJobTitle());
		phoneNoTxtFld.setText(updatedCandidate.getPhoneNumber());
		emailTxtFld.setText(updatedCandidate.getEmailAddress());
		addressTxtFld.setText(updatedCandidate.getAddress());

		// show the LinkedIn profile
		// Load the url
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				engine.load(updatedCandidate.getLinkedInProfile());
			}
		});
	}
}
