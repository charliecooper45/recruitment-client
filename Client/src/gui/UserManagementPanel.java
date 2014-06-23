package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class UserManagementPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// components - topPanel
	private JPanel topPanel;
	private JComboBox<String> userTypeCmbBox;
	private JComboBox<String> userStatusCmbBox;
	private JButton addUserBtn;
	private JButton userActBtn;
	private JButton delUserBtn;
	private JButton resetPasswordBtn;
	private JButton changeActStsBtn;

	// components - mainPanel
	private JPanel mainPanel;
	private JTable usersTbl;
	private JScrollPane tableScrll;

	public UserManagementPanel() {
		setLayout(new BorderLayout());
		init();
	}

	private void init() {
		initTopPanel();
		initBottomPanel();
	}

	private void initTopPanel() {
		topPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.insets = new Insets(20, 0, 20, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("Account Status:"), gbc);

		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		userTypeCmbBox = new JComboBox<>();
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(userTypeCmbBox, gbc);
		
		gbc.insets = new Insets(20, 0, 20, 5);
		gbc.anchor = GridBagConstraints.LINE_END;
		Utils.setGBC(gbc, 3, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(new JLabel("Account Type:"), gbc);
		
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.anchor = GridBagConstraints.LINE_START;
		userStatusCmbBox = new JComboBox<>();
		Utils.setGBC(gbc, 4, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		topPanel.add(userStatusCmbBox, gbc);

		gbc.insets = new Insets(10, 40, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		addUserBtn = new JButton("Add User");
		Utils.setGBC(gbc, 5, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(addUserBtn, gbc);

		delUserBtn = new JButton("Delete User");
		Utils.setGBC(gbc, 6, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(delUserBtn, gbc);

		userActBtn = new JButton("View User Activity");
		Utils.setGBC(gbc, 7, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(userActBtn, gbc);
		
		resetPasswordBtn = new JButton("Reset Password");
		Utils.setGBC(gbc, 8, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(resetPasswordBtn, gbc);
		
		changeActStsBtn = new JButton("Change Status");
		Utils.setGBC(gbc, 9, 1, 1, 1, GridBagConstraints.NONE);
		topPanel.add(changeActStsBtn, gbc);
		
		add(topPanel, BorderLayout.NORTH);
	}

	private void initBottomPanel() {
		mainPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(0, 20, 10, 20);
		usersTbl = new JTable(new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			private String[] columns = { "UserID", "First Name", "Surname", "Email address", "Phone number", "Account status", "Account Type" };

			@Override
			public Object getValueAt(int row, int col) {
				return "Test Data";
			}

			@Override
			public int getRowCount() {
				return 7;
			}

			@Override
			public int getColumnCount() {
				return 7;
			}

			@Override
			public String getColumnName(int index) {
				return columns[index];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
		usersTbl.setRowHeight(30);
		usersTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < usersTbl.getColumnCount(); i++) {
			usersTbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		usersTbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					//TODO NEXT B: Implement this - show the users`s activity report
				}
			}
		});
		tableScrll = new JScrollPane(usersTbl);
		tableScrll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
		mainPanel.add(tableScrll, gbc);

		add(mainPanel, BorderLayout.CENTER);
	}
}
