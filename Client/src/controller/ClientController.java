package controller;

import gui.ClientView;
import gui.listeners.AddVacancyDialogListener;
import gui.listeners.LoginListener;
import gui.listeners.MenuListener;
import gui.listeners.RemoveVacancyDialogListener;
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
	
	// menu listeners
	private MenuListener menuListener;
	private AddVacancyDialogListener addVacancyDialogListener;
	private RemoveVacancyDialogListener removeVacancyDialogListener;
	
	public ClientController(ClientView view, ClientModel model) {
		this.view = view;
		this.model = model;
		this.view.setController(this);

		// create the listeners
		vacanciesPanelListener = new VacanciesPanelListener(this);
		loginListener = new LoginListener(this);
		menuListener = new MenuListener(this);
		vacancyPanelListener = new VacancyPanelListener(this);
		addVacancyDialogListener = new AddVacancyDialogListener(this);
		removeVacancyDialogListener = new RemoveVacancyDialogListener(this);

		setListenersAndShowGUI();
	}

	private void setListenersAndShowGUI() {
		// shows the GUI to the user and sets the login listener for the login screen
		view.showGUI(loginListener);

		// sets the menu listener
		view.setMenuListener(menuListener);

		// sets the listeners for the GUI
		view.setVacanciesPanelListeners(vacanciesPanelListener);
		view.setVacancyPanelListener(vacancyPanelListener);
		view.setAddVacancyDialogListener(addVacancyDialogListener);
		view.setRemoveVacancyDialogListener(removeVacancyDialogListener);
	}

	public Path storeFile(InputStream inStream, String name) {
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
}
