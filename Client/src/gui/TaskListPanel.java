package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Displays the user`s task list 
 * @author Charlie
 */
public class TaskListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints gbc;
	
	// components
	private JLabel taskLbl;
	private JScrollPane tasksScrl;
	private JButton addTaskBtn;

	public TaskListPanel() {
		setPreferredSize(new Dimension(250, 500));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		init();
	}
	
	private void init() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		
		// task label
		gbc.anchor = GridBagConstraints.CENTER;
		taskLbl = new JLabel("Task List:");
		taskLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		taskLbl.setHorizontalAlignment(JLabel.CENTER);
		Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		add(taskLbl, gbc);
		
		// tasks panel
		gbc.weighty = 12;
		JPanel tasksPanel = new JPanel();
		tasksPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		tasksPanel.setLayout(new GridBagLayout());
		GridBagConstraints tasksGbc = new GridBagConstraints();
		tasksGbc.anchor = GridBagConstraints.FIRST_LINE_START;
		tasksGbc.weightx = 1;
		tasksGbc.weighty = 1;
		Utils.setGBC(tasksGbc, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL);
		tasksPanel.add(new Task("Call Microsoft", new GregorianCalendar()), tasksGbc);
		
		tasksScrl = new JScrollPane(tasksPanel);
		tasksScrl.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tasksScrl.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
		add(tasksScrl, gbc);
		
		// add task button
		gbc.weighty = 1;
		addTaskBtn = new JButton("Add Task");
		addTaskBtn.setPreferredSize(new Dimension(150, 30));
		Utils.setGBC(gbc, 1, 3, 1, 1, GridBagConstraints.NONE);
		add(addTaskBtn, gbc);
	}
	
	/**
	 * A panel that displays the information associated with one task 
	 */
	private class Task extends JPanel{
		private static final long serialVersionUID = 1L;
		
		private GridBagConstraints gbc;
		private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd'th' MMM");
		private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh':'mm aa");
		
		// components
		private JCheckBox completedBox;
		
		//TODO NEXT: maximum size of subject needs to be maintained
		private String subject;
		private Calendar date;
		
		public Task(String subject, Calendar date) {
			//TODO NEXT: Bring up a page to edit the task on double click
			this.subject = subject;
			this.date = date;
			
			setSize(new Dimension(200, 50));
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			init();
		}
		
		private void init() {
			setLayout(new GridBagLayout());
			gbc = new GridBagConstraints();
			gbc.weightx = 4;
			gbc.weighty = 1;
			gbc.insets = new Insets(5, 2, 5, 2);
						
			Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
			add(new JLabel(dateFormat.format(date.getTime())), gbc);
			
			Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
			add(new JLabel(subject), gbc);
			
			gbc.anchor = GridBagConstraints.LINE_END;
			gbc.weightx = 1;
			Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.NONE);
			add(new JLabel(timeFormat.format(date.getTime())), gbc);
			completedBox = new JCheckBox();
			Utils.setGBC(gbc, 2, 2, 1, 1, GridBagConstraints.NONE);
			add(completedBox, gbc);
		}
	}
}
