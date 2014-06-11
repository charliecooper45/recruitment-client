package gui.listeners;

import java.awt.event.MouseAdapter;

import controller.ClientController;

/**
 * Superclass of all other GUI listeners in the client.
 * @author Charlie
 */
public abstract class ClientListener extends MouseAdapter {
	protected final ClientController controller;

	public ClientListener(ClientController controller) {
		this.controller = controller;
	}
}
