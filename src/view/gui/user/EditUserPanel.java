package view.gui.user;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Date;
import model.User;
import utilities.Utilities;

/**
 * Panel used to edit an user.
 * @author Manuel Cruz
 * @version 1.0
 */
public class EditUserPanel extends AbstractUserPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** User identity of the current user being edited */
	private int userIdentity;

	/**
	 * Explicit constructor of this panel
	 * @param manager - manager that manages this panel
	 */
	public EditUserPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Edit User");
	}
	
	/**
	 * Checks if there has been a change on the user
	 * @return true if there is a change. false otherwise.
	 */
	private boolean change()
	{
		Data data = Database.getInstance().select(userIdentity);
		
		if (data == null)
		{
			String message = "EditUserPanel - change() - null ptr "
					+ "invalid user: " + userIdentity;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof User))
		{
			String message = "EditUserPanel - change() - invalid instance "
					+ "invalid user: " + userIdentity;
			Utilities.getInstance().error(message);
		}
		
		User user = (User) data;
		
		// checks first name
		boolean firstName = !firstNameTextField.getText().trim().equals(user.getFirstName());
		if (firstName) return true;
		
		// checks last name
		boolean lastName = !lastNameTextField.getText().trim().equals(user.getLastName());
		if (lastName) return true;
		
		// check birthday
		boolean birthday = !birthdayTextField.getText().trim().equals(user.getBirthday().toString());
		if (birthday) return true;
		
		// check id
		boolean id = !idTextField.getText().trim().equals(Integer.toHexString(user.getId()));
		if (id) return true;
		
		// check username
		boolean username = !usernameTextField.getText().trim().equals(user.getUsername());
		if (username) return true;
		
		// checks password
		boolean password = !passwordTextField.getText().trim().equals(user.getPassword());
		if (password) return true;
		
		// checks administrator
		boolean administrator = administratorCheckBox.isSelected() ^ user.isAdministrator();
		if (administrator) return true;
		
		return false;
	}
	
	/**
	 * Checks whether the given string is a valid identity or not.
	 * @param text - string to check
	 * @return true if it is valid or not.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blank
			return false;
		
		if (text.length() != 7)
			return false;
		
		try
		{
			int id = Integer.parseInt(text, 0x10);
			
			if ((id & Database.DATA_TYPE_MASK) != Database.USER)
				return false;
			
			List<Data> users = Database.getInstance().selectAll(Database.USER);
			for(Data data : users)
			{
				if (data.getId() == id && data.getId() != userIdentity)
					return false;
			}
			return true;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given username is valid or not
	 * @param username - username to check for
	 * @return true if it valid. false otherwise.
	 */
	private boolean validUsername(String username)
	{
		if (username == null)
			return false;
		
		if (username.trim().length() < 4) // must have 4 chars at least
			return false;
		
		List<Data> users = Database.getInstance().selectAll(Database.USER);
		for (Data data : users)
		{
			if (!(data instanceof User))
			{
				String message = "EditUserPanel - validUsername(String username) - invalid instance";
				Utilities.getInstance().error(message);
			}
			
			User user = (User) data;
			if (user.getUsername().equals(username) && user.getId() != userIdentity)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Checks whether the given string is a valid password or not.
	 * @param password - password to check for
	 * @return true if it is a valid password false otherwise.
	 */
	private boolean validPassword(String password)
	{
		if (password.trim().length() < 4) // must be 4 chars
			return false;
		
		return true;
	}
	
	/**
	 * Checks whether the current change is valid or not.
	 * @return true if the change is valid. false otherwise.
	 */
	private boolean validChange()
	{
		// checks for firstName
		boolean firstName = !firstNameTextField.getText().isBlank();
		if (!firstName)
		{
			String message = "Make sure it is not empty";
			String title = "First Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for last name
		boolean lastName = !lastNameTextField.getText().isBlank();
		if (!lastName)
		{
			String message = "Make sure it is not empty";
			String title = "Last Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for birthday
		boolean birthday = Date.validDate(birthdayTextField.getText().strip());
		if (!birthday)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Birthday Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks the identity
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
					   + "Length of ID must be 7 characters\n"
					   + "Make sure the first 3 digits are 300\n"
					   + "Make sure the characters are numbers (0-9)\n"
					   + "and/or letters (a-f)";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks the username
		boolean username = validUsername(usernameTextField.getText().strip());
		if (!username)
		{
			String message = "Make sure username is unique and is at least 4 characters.";
			String title = "Username Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks the password
		boolean password = validPassword(passwordTextField.getText().strip());
		if (!password)
		{
			String message = "Make sure password is at least 4 characters";
			String title = "Password Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Updates the change given by the user.
	 * @return true if it was successfully update. false otherwise.
	 */
	private boolean updateChange()
	{
		Database dtbs = Database.getInstance();
		
		// changes first name
		boolean firstName = dtbs.update(userIdentity, 
										User.USER_FIRST_NAME, 
										firstNameTextField.getText().strip());
		// check for error
		if (!firstName)
		{
			String message = "Failure to change the first name";
			String title = "First Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes last name
		boolean lastName = dtbs.update(userIdentity, 
									   User.USER_LAST_NAME, 
									   lastNameTextField.getText().strip());
		// checks for error
		if (!lastName)
		{
			String message = "Failure to change the last name";
			String title = "Last Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes birthday
		boolean birthday = dtbs.update(userIdentity, 
									   User.USER_BIRTHDAY, 
									   new Date(birthdayTextField.getText().strip()));
		// checks for error
		if (!birthday)
		{
			String message = "Failure to change the date of birth";
			String title = "Date of birth Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes username
		boolean username = dtbs.update(userIdentity, 
									   User.USER_USERNAME, 
									   usernameTextField.getText().strip());
		// checks for username
		if (!username)
		{
			String message = "Failure to change the username";
			String title = "Username Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes password
		boolean password = dtbs.update(userIdentity, 
									   User.USER_PASSWORD, 
									   passwordTextField.getText().strip());
		// checks for error
		if (!password)
		{
			String message = "Failure to change the password";
			String title = "password Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes the administrator
		boolean administrator = dtbs.update(userIdentity, 
											User.USER_ADMINISTRATOR, 
											administratorCheckBox.isSelected());
		// checks for error
		if (!administrator)
		{
			String message = "Failure to change the previledges";
			String title = "previledges Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes identity
		boolean identity = dtbs.update(userIdentity, 
									   User.USER_ID, 
									   Integer.parseInt(idTextField.getText().strip(), 0x10));
		// checks for error
		if (!identity)
		{
			String message = "Failure to change the ID";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// changes the identity of the user being changed
		userIdentity = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		return true;
	}
	
	@Override
	protected void confirmButtonAction() 
	{
		if (change()) // checks for changes
		{
			if (validChange()) // validates changes
			{
				if (updateChange()) // updates
				{
					String message = "User has been edited";
					String title = "Edit User";
					showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else
		{
			String message = "There is no change";
			String title = "Edit User";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		if (data == null)
		{
			String message = "EditUserPanel - setData(int id) - null ptr "
					+ "invalid user: " + id;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof User))
		{
			String message = "EditUserPanel - setData(int id) - invalid instance "
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
		
		if (Logistics.getInstance().getUser().isAdministrator())
			administratorCheckBox.setSelected(user.isAdministrator());
		
		userIdentity = id;
	}
}
