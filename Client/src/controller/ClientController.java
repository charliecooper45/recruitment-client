package controller;

import gui.ClientView;
import gui.listeners.AddCandidateDialogListener;
import gui.listeners.AddContactDialogListener;
import gui.listeners.AddEventDialogListener;
import gui.listeners.AddLinkedInProfileListener;
import gui.listeners.AddOrganisationDialogListener;
import gui.listeners.AddCandidateSkillDialogListener;
import gui.listeners.AddSkillDialogListener;
import gui.listeners.AddTaskDialogListener;
import gui.listeners.AddUserDialogListener;
import gui.listeners.AddVacancyDialogListener;
import gui.listeners.AdminPanelListener;
import gui.listeners.CandidatePanelListener;
import gui.listeners.CandidatePipelinePanelListener;
import gui.listeners.EditUserDialogListener;
import gui.listeners.LoginListener;
import gui.listeners.MenuListener;
import gui.listeners.OrganisationPanelListener;
import gui.listeners.OrganisationsPanelListener;
import gui.listeners.RemoveCandidateDialogListener;
import gui.listeners.RemoveContactDialogListener;
import gui.listeners.RemoveEventDialogListener;
import gui.listeners.RemoveOrganisationDialogListener;
import gui.listeners.RemoveCandidateSkillDialogListener;
import gui.listeners.RemoveUserDialogListener;
import gui.listeners.RemoveVacancyDialogListener;
import gui.listeners.ReportPanelListener;
import gui.listeners.SearchPanelListener;
import gui.listeners.SkillsManagementPanelListener;
import gui.listeners.TaskListPanelListener;
import gui.listeners.TopMenuListener;
import gui.listeners.UserManagementPanelListener;
import gui.listeners.VacanciesPanelListener;
import gui.listeners.VacancyPanelListener;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import model.ClientModel;

/**
 * Controller part of MVC, responsible for interaction between the view and the model.
 * @author Charlie
 */
public class ClientController {
	private final ClientView view;
	private final ClientModel model;

	// listeners
	private LoginListener loginListener;
	private VacanciesPanelListener vacanciesPanelListener;
	private VacancyPanelListener vacancyPanelListener;
	private OrganisationsPanelListener organisationsPanelListener;
	private OrganisationPanelListener organisationPanelListener;
	private SearchPanelListener searchPanelListener;
	private CandidatePanelListener candidatePanelListener;
	private CandidatePipelinePanelListener candidatePipelineListener;
	private TaskListPanelListener taskListPanelListener;
	private AdminPanelListener adminPanelListener;
	private UserManagementPanelListener userManagementPanelListener;
	private SkillsManagementPanelListener skillsManagementPanelListener;
	private ReportPanelListener reportPanelListener;
	
	// top panel listener
	private TopMenuListener topMenuListener;
	
	// menu and dialog listeners
	private MenuListener menuListener;
	private AddVacancyDialogListener addVacancyDialogListener;
	private RemoveVacancyDialogListener removeVacancyDialogListener;
	private AddOrganisationDialogListener addOrganisationDialogListener;
	private RemoveOrganisationDialogListener removeOrganisationDialogListener;
	private AddCandidateDialogListener addCandidateDialogListener;
	private RemoveCandidateDialogListener removeCandidateDialogListener;
	private AddContactDialogListener addContactDialogListener;
	private RemoveContactDialogListener removeContactDialogListener;
	private AddLinkedInProfileListener addLinkedInProfileListener;
	private AddCandidateSkillDialogListener addCandidateSkillDialogListener;
	private RemoveCandidateSkillDialogListener removeCandidateSkillDialogListener;
	private AddEventDialogListener addEventDialogListener;
	private RemoveEventDialogListener removeEventDialogListener;
	private AddTaskDialogListener addTaskDialogListener;
	private AddUserDialogListener addUserDialogListener;
	private RemoveUserDialogListener removeUserDialogListener;
	private EditUserDialogListener editUserDialogListener;
	private AddSkillDialogListener addSkillDialogListener;
	
	public ClientController(ClientView view, ClientModel model) {
		this.view = view;
		this.model = model;
		this.view.setController(this);

		// create the listeners
		loginListener = new LoginListener(this);
		menuListener = new MenuListener(this);
		topMenuListener = new TopMenuListener(this);
		vacanciesPanelListener = new VacanciesPanelListener(this);
		vacancyPanelListener = new VacancyPanelListener(this);
		organisationsPanelListener = new OrganisationsPanelListener(this);
		organisationPanelListener = new OrganisationPanelListener(this);
		searchPanelListener = new SearchPanelListener(this);
		candidatePanelListener = new CandidatePanelListener(this);
		candidatePipelineListener = new CandidatePipelinePanelListener(this);
		taskListPanelListener = new TaskListPanelListener(this);
		adminPanelListener = new AdminPanelListener(this);
		userManagementPanelListener = new UserManagementPanelListener(this);
		skillsManagementPanelListener = new SkillsManagementPanelListener(this);
		reportPanelListener = new ReportPanelListener(this);
		addVacancyDialogListener = new AddVacancyDialogListener(this);
		removeVacancyDialogListener = new RemoveVacancyDialogListener(this);
		addOrganisationDialogListener = new AddOrganisationDialogListener(this);
		removeOrganisationDialogListener = new RemoveOrganisationDialogListener(this);
		addCandidateDialogListener = new AddCandidateDialogListener(this);
		removeCandidateDialogListener = new RemoveCandidateDialogListener(this);
		addContactDialogListener = new AddContactDialogListener(this);
		removeContactDialogListener = new RemoveContactDialogListener(this);
		addLinkedInProfileListener = new AddLinkedInProfileListener(this);
		addCandidateSkillDialogListener = new AddCandidateSkillDialogListener(this);
		removeCandidateSkillDialogListener = new RemoveCandidateSkillDialogListener(this);
		addEventDialogListener = new AddEventDialogListener(this);
		removeEventDialogListener = new RemoveEventDialogListener(this);
		addTaskDialogListener = new AddTaskDialogListener(this);
		addUserDialogListener = new AddUserDialogListener(this);
		removeUserDialogListener = new RemoveUserDialogListener(this);
		editUserDialogListener = new EditUserDialogListener(this);
		addSkillDialogListener = new AddSkillDialogListener(this);

		setListenersAndShowGUI();
	}

	private void setListenersAndShowGUI() {
		// shows the GUI to the user and sets the login listener for the login screen
		view.showGUI(loginListener);

		// sets the menu listener
		view.setMenuListener(menuListener);
		
		// set the top panel button listener
		view.setTopMenuListener(topMenuListener);

		// sets the listeners for the GUI
		view.setVacanciesPanelListeners(vacanciesPanelListener);
		view.setVacancyPanelListener(vacancyPanelListener);
		view.setOrganisationsPanelListener(organisationsPanelListener);
		view.setOrganisationPanelListener(organisationPanelListener);
		view.setSearchPanelListener(searchPanelListener);
		view.setCandidatePanelListener(candidatePanelListener);
		view.setCandidatePipelinePanelListener(candidatePipelineListener);
		view.setTaskListPanelListener(taskListPanelListener);
		view.setAdminPanelListener(adminPanelListener, userManagementPanelListener, skillsManagementPanelListener, reportPanelListener);
		
		// dialog listeners
		view.setAddVacancyDialogListener(addVacancyDialogListener);
		view.setRemoveVacancyDialogListener(removeVacancyDialogListener);
		view.setAddOrganisationDialogListener(addOrganisationDialogListener);
		view.setRemoveOrganisationDialogListener(removeOrganisationDialogListener);
		view.setAddCandidateDialogListener(addCandidateDialogListener);
		view.setRemoveCandidateDialogListener(removeCandidateDialogListener);
		view.setAddContactDialogListener(addContactDialogListener);
		view.setRemoveContactDialogListener(removeContactDialogListener);
		view.setAddLinkedInProfileListener(addLinkedInProfileListener);
		view.setAddCandidateSkillDialogListener(addCandidateSkillDialogListener);
		view.setRemoveCandidateSkillDialogListener(removeCandidateSkillDialogListener);
		view.setAddEventDialogListener(addEventDialogListener);
		view.setRemoveEventDialogListener(removeEventDialogListener);
		view.setAddTaskDialogListener(addTaskDialogListener);
		view.setAddUserDialogListener(addUserDialogListener);
		view.setRemoveUserDialogListener(removeUserDialogListener);
		view.setEditUserDialogListener(editUserDialogListener);
		view.setAddSkillDialogListener(addSkillDialogListener);
	}

	public Path storeTempFile(InputStream inStream, String name) {
		// write the file to a temp file
		try {
			String suffix = name.substring(name.lastIndexOf("."));
			Path tempFile = Files.createTempFile(null, suffix);
			tempFile.toFile().deleteOnExit();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
			FileOutputStream outputStream;

			outputStream = new FileOutputStream(tempFile.toFile());

			int size = 0;
			byte[] byteBuff = new byte[1024];
			while ((size = bufferedInputStream.read(byteBuff)) != -1) {
				outputStream.write(byteBuff, 0, size);
			}

			outputStream.close();
			bufferedInputStream.close();

			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ClientModel getModel() {
		return model;
	}

	public ClientView getView() {
		return view;
	}
	
	public VacanciesPanelListener getVacanciesPanelListener() {
		return vacanciesPanelListener;
	}

	public OrganisationsPanelListener getOrganisationsPanelListener() {
		return organisationsPanelListener;
	}
	
	public UserManagementPanelListener getUserManagementPanelListener() {
		return userManagementPanelListener;
	}
}
