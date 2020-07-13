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
 * Panel used to add user to the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class AddUserPanel extends AbstractUserPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor of this panel
	 * @param manager - manager that manages this panel
	 */
	public AddUserPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Add User");
	}
	
	/**
	 * Checks whether the given string is valid or not.
	 * @param text - string to check for
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.strip().length() != 7)
			return false;
		
		try
		{
			int identity = Integer.parseInt(text.trim(), 0x10);
			
			if ((identity & Database.DATA_TYPE_MASK) != Database.USER)
				return false;
			
			return Database.getInstance().validID(identity);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given username is valid or not
	 * @param username - username to check for
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validUsername(String username)
	{
		if (username.trim().length() < 4) // must be 4 chars
			return false;
		
		List<Data> users = Database.getInstance().selectAll(Database.USER);
		for (Data data : users)
		{
			if (!(data instanceof User))
			{
				String message = "AddUserPanel - validUsername(String username) - "
						+ "invalid instance";
				Utilities.getInstance().error(message);
			}
			
			User user = (User) data;
			if (user.getUsername().equals(username))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Checks whether the given string is a valid password or not.
	 * @param password - password to check for
	 * @return true if it is a valid password. false otherwise.
	 */
	private boolean validPassword(String password)
	{
		if (password.trim().length() < 4) // must be 4 chars
			return false;
		
		return true;
	}
	
	/**
	 * Checks whether the adding user is valid or not.
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validUser()
	{
		// checks first name
		boolean firstName = !firstNameTextField.getText().isBlank();
		if (!firstName)
		{
			String message = "Make sure it is not empty";
			String title = "First Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks last name
		boolean lastName = !lastNameTextField.getText().isBlank();
		if (!lastName)
		{
			String message = "Make sure it is not empty";
			String title = "Last Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks birthday
		boolean birthday = Date.validDate(birthdayTextField.getText());
		if (!birthday)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Birthday Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks identity
		boolean identity = validIdentity(idTextField.getText().trim());
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
		
		// checks username
		boolean username = validUsername(usernameTextField.getText());
		if (!username)
		{
			String message = "Make sure username is unique and is at least 4 characters.";
			String title = "Username Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks password
		boolean password = validPassword(passwordTextField.getText());
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
	 * Adds user to the database
	 * @return true if it was successful false otherwise.
	 */
	private boolean addUser()
	{
		String firstName = firstNameTextField.getText().strip();
		String lastName = lastNameTextField.getText().strip();
		Date birthday = new Date(birthdayTextField.getText().strip());
		int id = Integer.parseInt(idTextField.getText().strip(), 0x10);
		boolean administrator = administratorCheckBox.isSelected();
		String username = usernameTextField.getText().strip();
		String password = passwordTextField.getText().strip();
		User newUser = new User(firstName, lastName, birthday, id, administrator, username, password);
		return Database.getInstance().insert(newUser);
	}

	@Override
	protected void confirmButtonAction() {
		if (validUser()) // checks is a valid user
		{
			if (addUser()) // adds user
			{
				String message = "User has been added";
				String title = "Add User";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else
			{
				String message = "User could not be added to database";
				String title = "Add User";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void reset()
	{
		super.reset();
		int newUserId = Database.generateID(Database.USER);
		idTextField.setText(Integer.toHexString(newUserId));
		
		Date date = new Date();
		birthdayTextField.setText(date.toString());
	}
}
