package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Logistics;
import model.Data;
import model.Database;
import model.User;
import utilities.ComponentFactory;
import view.Panel;

/**
 * Login panel to prompt user for authentication.
 * @author Manuel Cruz
 * @version 1.0
 */
public class LoginPanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Labels used in this panel */
	private JLabel usernameLabel, passwordLabel;
	
	/** Textfield used in this panel */
	private JTextField usernameTextField, passwordTextField;
	
	/** Buttons used in this panel */
	private JButton loginButton, forgotButton;
	
	/**
	 * Explicit constructor
	 * @param manager - Manager that manages this panel
	 */
	public LoginPanel(Logistics manager)
	{
		super(manager);
	}
	
	@Override
	protected void createComponents()
	{
		super.createComponents();
		createTextFields();
		createButtons();
	}
	
	@Override
	protected void addComponents()
	{
		super.addComponents();
		add(usernameLabel);
		add(passwordLabel);
		
		add(usernameTextField);
		add(passwordTextField);
		
		add(loginButton);
		add(forgotButton);
	}
	
	@Override
	protected void layoutComponents()
	{
		super.layoutComponents();
		layoutTextFields();
		layoutButtons();
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		// set the title of the panel
		titleLabel.setText("Logistics");
		ComponentFactory factory = ComponentFactory.getInstance();
		
		usernameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Username:");
		passwordLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Password:");
	}
	
	/**
	 * Creates textfields of this panel.
	 */
	private void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		usernameTextField = factory.createTextField(ComponentFactory.TEXT);
		passwordTextField = factory.createTextField(ComponentFactory.PASSWORD);
	}
	
	/**
	 * Creates buttons of this panel.
	 */
	private void createButtons()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// login button
		loginButton = factory.createButton("LOGIN");
		loginButton.addActionListener(this);
		
		// forgot button
		forgotButton = factory.createButton("FORGOT");
		forgotButton.addActionListener(this);
	}
	
	@Override
	protected void layoutLabels()
	{
		super.layoutLabels();
		
		//user label x and y
		layout.putConstraint(SpringLayout.EAST, usernameLabel, -10, SpringLayout.WEST, usernameTextField);
		layout.putConstraint(SpringLayout.NORTH, usernameLabel, 79, SpringLayout.SOUTH, titleLabel);
		
		//password label x and y
		layout.putConstraint(SpringLayout.EAST, passwordLabel, -10, SpringLayout.WEST,  passwordTextField);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel, 33, SpringLayout.SOUTH, usernameTextField);
		
	}
	
	/**
	 * Lays out the textfields of this panel.
	 */
	private void layoutTextFields()
	{
		// used to try to center it
		int xdiff = usernameLabel.getText().length() * 5;
		//userTextField x and y
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, usernameTextField, xdiff, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, usernameTextField, 75, SpringLayout.SOUTH, titleLabel);
		
		xdiff = passwordLabel.getText().length() * 5;
		//passwordTextField x and y
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, passwordTextField, xdiff,   SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, passwordTextField, 25,  SpringLayout.SOUTH, usernameTextField);
		
	}
	
	/**
	 * Lays out the buttons of this panel.
	 */
	private void layoutButtons()
	{
		//forgotButton
		layout.putConstraint(SpringLayout.EAST,  forgotButton, 0, SpringLayout.EAST,    usernameTextField);
		layout.putConstraint(SpringLayout.NORTH, forgotButton, 75, SpringLayout.SOUTH, passwordTextField);
		
		//signin button
		layout.putConstraint(SpringLayout.NORTH, loginButton, 75, SpringLayout.SOUTH, passwordTextField);
		layout.putConstraint(SpringLayout.WEST,  loginButton, 0, SpringLayout.WEST,    usernameLabel);
	}
	
	/**
	 * Action taken when the login button is pressed.
	 */
	private void loginButtonAction()
	{
		if (validUser())
			manager.login();
		else
			invalidUser();
	}
	
	/**
	 * Checks if the user prompted is valid or not.
	 * @return true if the user prompted is valid. False otherwise.
	 */
	private boolean validUser()
	{
		List<Data> identities = Database.getInstance().selectAll(Database.USER);
		
		// gets the username and password
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		
		// compares to all the users and sees if one matches.
		// if one matches then is a valid user
		// it send the user to the manager and returns true
		// if it is not found it will just return false
		for (Data data : identities)
		{			
			if (!(data instanceof User))
				continue;
			
			User user = (User) data;
			
			boolean valid = user.getUsername().equals(username) && user.getPassword().equals(password);
			if (valid)
			{
				manager.setUser(user);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Action taken if the forgot action is pressed.
	 */
	private void forgotButtonAction()
	{
		String message = "For help logging in, please contact your supervisor.";
		String title = "Log in Message";
		showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Message shown if the prompted user is invalid.
	 */
	private void invalidUser()
	{
		String message = "Invalid User. Please Try Again";
		String title = "Log In Error";
		showMessage(message, title, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void reset() 
	{
		usernameTextField.setText("");
		passwordTextField.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object object = e.getSource();
		if (object  instanceof JButton)
		{
			JButton button = (JButton) object;
			if (button.getText().equals("LOGIN"))
				loginButtonAction();
			else if (button.getText().equals("FORGOT"))
				forgotButtonAction();
		}
	}
}
