package gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Displays the window that allows the user to login to the client 
 * @author Charlie
 */
public class LoginWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	
	// components
	private JTextField userIDTxtField;
	private JPasswordField passwordTxtField;
	private JButton loginButton;
	private JLabel passwordLabel;
	
	public LoginWindow() {
		//TODO NEXT B: Add logo
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Recruitment Software Login");
		setSize(300, 400);
		setResizable(false);
		init();
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// labels
		Font lblFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
		gbc.anchor = GridBagConstraints.LINE_END;
		Insets labelInsets = new Insets(0, 40, 0, 0);
		gbc.insets = labelInsets;
		JLabel userIDLbl = new JLabel("UserID:");
		userIDLbl.setFont(lblFont);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(userIDLbl, gbc);
		JLabel passwordLbl = new JLabel("Password:");
		passwordLbl.setFont(lblFont);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		add(passwordLbl, gbc);
		
		// textfields
		gbc.weightx = 4;
		gbc.anchor = GridBagConstraints.LINE_START;
		Insets txtFieldInsets = new Insets(0, 0, 0, 20);
		gbc.insets = txtFieldInsets;
		userIDTxtField = new JTextField();
		userIDTxtField.setFont(lblFont);
		Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(userIDTxtField, gbc);
		passwordTxtField = new JPasswordField();
		passwordTxtField.setFont(lblFont);
		Utils.setGBC(gbc, 2, 2, 1, 1, GridBagConstraints.HORIZONTAL);
		add(passwordTxtField, gbc);
		
		// login button
		gbc.anchor = GridBagConstraints.CENTER;
		Insets blankInsets = new Insets(0, 0, 0, 0);
		gbc.insets = blankInsets;
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(150, 30));
		Utils.setGBC(gbc, 1, 3, 2, 1, GridBagConstraints.NONE);
		add(loginButton, gbc);
		
		// forgotten password label
		passwordLabel = new JLabel("<html><a href=\"\">"+ "Forgotten Password?" +"</a></html>");
		passwordLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(LoginWindow.this, "Please contact your system administrator", "Forgotten Password", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		passwordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		Utils.setGBC(gbc, 1, 4, 2, 1, GridBagConstraints.NONE);
		add(passwordLabel, gbc);
	}
}