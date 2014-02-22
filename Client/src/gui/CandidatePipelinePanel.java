package gui;

import gui.listeners.CandidateDisplayedListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Displays the pipeline (current user or everyones) to the user
 * @author Charlie
 */
public class CandidatePipelinePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints gbc;
	
	// alerts the GUI when a candidate needs to be displayed to the user
	private CandidateDisplayedListener candidateDisplayedListener;
	
	// components - topPanel
	private JPanel topPanel;
	private JCheckBox[] chckBoxes;
	private JCheckBox shortlistedChckBox;
	private JCheckBox cvsChckBox;
	private JCheckBox interviewsChckBox;
	private JCheckBox placementsChckBox;
	private ButtonGroup group;
	private JRadioButton myPipelineRdBtn;
	private JRadioButton companyPipelineRdBtn;
	
	// components - mainPanel
	private JPanel mainPanel;
	private JTable pipelineTbl;
	private JScrollPane tableScrll;
	
	public CandidatePipelinePanel() {
		setLayout(new BorderLayout());
		init();
	}
	
	private void init() {
		initTopPanel();
		initMainPanel();
	}
	
	private void initTopPanel() {
		JPanel leftJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rightJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Insets leftInsets = new Insets(30, 20, 0, 0);
		Insets rightInsets = new Insets(30, 0, 0, 20);
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// left JPanel
		shortlistedChckBox = new JCheckBox("Shortlisted Candidates");
		cvsChckBox = new JCheckBox("CVs Sent");
		interviewsChckBox = new JCheckBox("Interviews");
		placementsChckBox = new JCheckBox("Placements");
		chckBoxes = new JCheckBox[4];
		chckBoxes[0] = shortlistedChckBox;
		chckBoxes[1] = cvsChckBox;
		chckBoxes[2] = interviewsChckBox;
		chckBoxes[3] = placementsChckBox;
		for(JCheckBox box : chckBoxes) {
			leftJPanel.add(box);
		}
		gbc.insets = leftInsets;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(leftJPanel, gbc);
		
		// right JPanel
		group = new ButtonGroup();
		myPipelineRdBtn = new JRadioButton("My Pipeline");
		companyPipelineRdBtn = new JRadioButton("Company Pipeline");
		group.add(myPipelineRdBtn);
		rightJPanel.add(myPipelineRdBtn);
		group.add(companyPipelineRdBtn);
		rightJPanel.add(companyPipelineRdBtn);
		gbc.insets = rightInsets;
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(rightJPanel, gbc);
		
		add(topPanel, BorderLayout.NORTH);
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(30, 20, 30, 20);
		pipelineTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = {"Candidate", "Event", "Job Title", "Organisation", "Date", "User"};

			@Override
			public Object getValueAt(int arg0, int arg1) {
				return "Test Data";
			}
			
			@Override
			public int getRowCount() {
				return 5;
			}
			
			@Override
			public int getColumnCount() {
				return 6;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}
		
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		pipelineTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
					candidateDisplayedListener.candidateDisplayed();
				}
			}
		});
		pipelineTbl.setRowHeight(30);
		tableScrll = new JScrollPane(pipelineTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * @param candidateDisplayedListener the candidateDisplayedListener to set
	 */
	public void setCandidateDisplayedListener(CandidateDisplayedListener candidateDisplayedListener) {
		this.candidateDisplayedListener = candidateDisplayedListener;
	}
}
