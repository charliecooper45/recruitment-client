package gui;

import gui.listeners.TaskListPanelListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.beans.Task;

/**
 * Displays the user`s task list 
 * @author Charlie
 */
public class TaskListPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc;

	// TaskPanels that are displayed
	private List<TaskPanel> taskPanels = new ArrayList<>();;
	
	// components
	private JLabel taskLbl;
	private JPanel tasksPanel;
	private GridBagConstraints tasksGbc;
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
		tasksPanel = new JPanel();
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		tasksPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		//tasksPanel.setLayout(new GridBagLayout());
		tasksGbc = new GridBagConstraints();
		tasksGbc.anchor = GridBagConstraints.FIRST_LINE_START;
		tasksGbc.weightx = 1;
		tasksGbc.weighty = 1;

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

	public void updateTasks(List<Task> tasks) {
		taskPanels.clear();
		Collections.sort(tasks);
		tasksPanel.removeAll();

		if (tasks != null) {
			for(int i = 0; i < tasks.size(); i++) {
				Utils.setGBC(tasksGbc, 0, i, 1, 1, GridBagConstraints.HORIZONTAL);
				Task task = tasks.get(i);
				TaskPanel taskPanel = new TaskPanel(task);
				taskPanel.setActionListener(addTaskBtn.getActionListeners()[0]);
				tasksPanel.add(taskPanel);
				taskPanels.add(taskPanel);
			}
		}
		
		tasksPanel.revalidate();
		tasksPanel.repaint();
	}
	
	public Task getSelectedTask() {
		for(TaskPanel taskPanel : taskPanels) {
			if(taskPanel.isSelected()) {
				return taskPanel.getTask();
			}
		}
		return null;
	}
	
	public void uncheckAllTasks() {
		for(TaskPanel taskPanel : taskPanels) {
			if(taskPanel.isSelected()) {
				taskPanel.setSelected(false);
			}
		}
	}
	
	public void setTaskListPanelListener(TaskListPanelListener taskListPanelListener) {
		addTaskBtn.addActionListener(taskListPanelListener);		
	}

	/**
	 * A panel that displays the information associated with one task 
	 */
	private class TaskPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private Task task;
		
		private GridBagConstraints gbc;
		private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd'th' MMM");
		private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh':'mm aa");

		// components
		private JCheckBox completedBox;

		public TaskPanel(Task task) {
			//TODO NEXT: Bring up a page to edit the task on double click
			this.task = task;

			setSize(new Dimension(200, 50));
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			init(task.getDescription(), task.getDate(), task.getTime());
		}

		private void init(String description, Date date, Date time) {
			setLayout(new GridBagLayout());
			setMaximumSize(new Dimension(5000, 75));
			gbc = new GridBagConstraints();
			gbc.weightx = 4;
			gbc.weighty = 1;
			gbc.insets = new Insets(5, 2, 5, 2);

			Utils.setGBC(gbc, 1, 1, 1, 1, GridBagConstraints.BOTH);
			add(new JLabel(dateFormat.format(date.getTime())), gbc);

			Utils.setGBC(gbc, 1, 2, 1, 1, GridBagConstraints.BOTH);
			add(new JLabel(description), gbc);

			gbc.anchor = GridBagConstraints.LINE_END;
			gbc.weightx = 1;
			Utils.setGBC(gbc, 2, 1, 1, 1, GridBagConstraints.NONE);
			add(new JLabel(timeFormat.format(time.getTime())), gbc);
			completedBox = new JCheckBox();
			Utils.setGBC(gbc, 2, 2, 1, 1, GridBagConstraints.NONE);
			add(completedBox, gbc);
		}
	
		private boolean isSelected() {
			return completedBox.isSelected();
		}
		
		private void setSelected(boolean selected) {
			completedBox.setSelected(selected);
		}
		
		private Task getTask() {
			return task;
		}
		
		private void setActionListener(ActionListener actionListener) {
			completedBox.addActionListener(actionListener);
		}
	}
}
