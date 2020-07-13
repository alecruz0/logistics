package view.gui.user;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.User;
import utilities.Utilities;
import utilities.ViewFactory;

/**
 * Panel used to remove an user from the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class RemoveUserPanel extends AbstractUserPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Identity of the current user being remove */
	private int userIdentity;

	/**
	 * Explicit constructor of this panel.
	 * @param manager - manager that manages this panel.
	 */
	public RemoveUserPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Remove User");
	}
	
	@Override
	protected void createTextFields()
	{
		super.createTextFields();
		
		// disable text fields
		firstNameTextField.setEditable(false);
		lastNameTextField.setEditable(false);
		birthdayTextField.setEditable(false);
		idTextField.setEditable(false);
		usernameTextField.setEditable(false);
		passwordTextField.setEditable(false);
	}
	
	@Override
	protected void createBoxes()
	{
		super.createBoxes();
		
		administratorCheckBox.setEnabled(false);
	}

	@Override
	protected void confirmButtonAction() {
		String message = "Are you sure you want to remove this user?";
		String title = "Remove User";
		int value = JOptionPane.showConfirmDialog(manager.getFrame(), message, title, JOptionPane.YES_NO_OPTION);
		if (value == 0)
			removeUser();
	}
	
	/**
	 * Removes an user ffrom the database
	 */
	private void removeUser()
	{
		boolean success = Database.getInstance().delete(userIdentity);
		if (success) // if success show message
		{
			String title = "Remove User";
			String message = "User was successfully removed";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
			
			// if current user is removed then restart to login
			if (manager.getUser().getId() == userIdentity)
				manager.changePanel(ViewFactory.LOGIN, Logistics.NO_ID);
			else
				backButtonAction();
		}
		else
		{
			String title = "Remove Error";
			String message = "User could not be removed";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		if (data == null)
		{
			String message = "RemoveUserPanel - setData(int id) - null ptr "
					+ "invalid user: " + id;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof User))
		{
			String message = "RemoveUserPanel - setData(int id) - invalid instance "
					+ "invalid user: " + id;
			Utilities.getInstance().error(message);
		}
		
		User user = (User) data;
		firstNameTextField.setText(user.getFirstName());
		lastNameTextField.setText(user.getLastName());
		birthdayTextField.setText(user.getBirthday().toString());
		idTextField.setText(Integer.toHexString(user.getId()));
		usernameTextField.setText(user.getUsername());
		passwordTextField.setText(user.getPassword());
		administratorCheckBox.setSelected(user.isAdministrator());
	
		userIdentity = id;
	}

}
