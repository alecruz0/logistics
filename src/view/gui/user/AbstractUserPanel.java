package view.gui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Logistics;
import utilities.ComponentFactory;
import utilities.ViewFactory;
import view.Panel;

/**
 * User panel that holds data of an user.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractUserPanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Labels used in this panel */
	protected JLabel firstNameLabel, lastNameLabel, 
					 birthdayLabel, idLabel, 
					 usernameLabel, passwordLabel;
	
	/** TextFields used in this panel */
	protected JTextField firstNameTextField, lastNameTextField, 
						 birthdayTextField, idTextField, 
						 usernameTextField, passwordTextField;
	
	/** Administrator checkbox */
	protected JCheckBox administratorCheckBox;
	
	/** Buttons used in this panel */
	protected JButton confirmButton, backButton;

	/**
	 * Explicit constructor of this panel.
	 * @param manager - manager that manages this panel.
	 */
	protected AbstractUserPanel(Logistics manager) 
	{
		super(manager);
	}

	@Override
	protected void createComponents()
	{
		super.createComponents();
		createTextFields();
		createButtons();
		createBoxes();
	}
	
	@Override
	protected void addComponents()
	{
		super.addComponents();
		add(firstNameLabel);
		add(lastNameLabel);
		add(birthdayLabel);
		add(idLabel);
		add(usernameLabel);
		add(passwordLabel);
		
		add(firstNameTextField);
		add(lastNameTextField);
		add(birthdayTextField);
		add(idTextField);
		add(usernameTextField);
		add(passwordTextField);
		
		if (Logistics.getInstance().getUser().isAdministrator())
			add(administratorCheckBox);
		
		add(confirmButton);
		
		if (Logistics.getInstance().getUser().isAdministrator())
			add(backButton);
	}
	
	@Override
	protected void layoutComponents()
	{
		super.layoutComponents();
		layoutTextFields();
		layoutBoxes();
		layoutButtons();
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		ComponentFactory factory = ComponentFactory.getInstance();
		
		firstNameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "First Name:");
		lastNameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Last Name:");
		birthdayLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Date of Birth:");
		idLabel = factory.createLabel(ComponentFactory.SUBTITLE, "ID:");
		usernameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Username:");
		passwordLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Password:");
	}
	
	/**
	 * Creates the textfields of this panel.
	 */
	protected void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		firstNameTextField = factory.createTextField(ComponentFactory.TEXT);
		lastNameTextField = factory.createTextField(ComponentFactory.TEXT);
		birthdayTextField = factory.createTextField(ComponentFactory.TEXT);
		idTextField = factory.createTextField(ComponentFactory.TEXT);
		usernameTextField = factory.createTextField(ComponentFactory.TEXT);
		passwordTextField = factory.createTextField(ComponentFactory.PASSWORD);
	}
	
	/**
	 * Creates the buttons of this panel.
	 */
	protected void createButtons()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		confirmButton = factory.createButton("CONFIRM");
		confirmButton.addActionListener(this);
		
		backButton = factory.createButton("BACK");
		backButton.addActionListener(this);
	}
	
	/**
	 * Creates boxes of this panel
	 */
	protected void createBoxes()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		administratorCheckBox = factory.createCheckBox("Administrator");
	}
	
	@Override
	protected void layoutLabels()
	{
		super.layoutLabels();
		
		//birthday label x and y
		layout.putConstraint(SpringLayout.EAST, birthdayLabel, -5, SpringLayout.WEST, birthdayTextField);
		layout.putConstraint(SpringLayout.NORTH, birthdayLabel, 103, SpringLayout.SOUTH, titleLabel);
		
		//id label x and y
		layout.putConstraint(SpringLayout.EAST, idLabel, -5, SpringLayout.WEST, idTextField);
		layout.putConstraint(SpringLayout.NORTH, idLabel, 103, SpringLayout.SOUTH, titleLabel);
		
		// firstName label x and y
		layout.putConstraint(SpringLayout.EAST, firstNameLabel, -5, SpringLayout.WEST, firstNameTextField);
		layout.putConstraint(SpringLayout.SOUTH, firstNameLabel, -33, SpringLayout.NORTH, birthdayTextField);
				
		// lastName label x and y
		layout.putConstraint(SpringLayout.WEST, lastNameLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, lastNameLabel, -33, SpringLayout.NORTH, birthdayTextField);
		
		// password label x and y
		layout.putConstraint(SpringLayout.EAST, passwordLabel, -5, SpringLayout.WEST, passwordTextField);
		layout.putConstraint(SpringLayout.NORTH, passwordLabel, 35, SpringLayout.SOUTH, idTextField);
				
		// username label x and y
		layout.putConstraint(SpringLayout.EAST, usernameLabel, -5, SpringLayout.WEST, usernameTextField);
		layout.putConstraint(SpringLayout.NORTH, usernameLabel, 32, SpringLayout.SOUTH, birthdayTextField);
				
	}

	/**
	 * Lays out the text fields of this panel.
	 */
	protected void layoutTextFields() 
	{
		//birthday textfield x and y
		layout.putConstraint(SpringLayout.EAST, birthdayTextField, -25, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, birthdayTextField, 100, SpringLayout.SOUTH, titleLabel);
				
		//id textfield x and y
		layout.putConstraint(SpringLayout.EAST, idTextField, 0, SpringLayout.EAST, lastNameTextField);
		layout.putConstraint(SpringLayout.NORTH, idTextField, 100, SpringLayout.SOUTH, titleLabel);
				
		// firstName textfield x and y
		layout.putConstraint(SpringLayout.EAST, firstNameTextField, -25, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, firstNameTextField, -28, SpringLayout.NORTH, birthdayTextField);
				
		// lastName Textfield x and y
		layout.putConstraint(SpringLayout.WEST, lastNameTextField, 5, SpringLayout.EAST, lastNameLabel);
		layout.putConstraint(SpringLayout.SOUTH, lastNameTextField, -30, SpringLayout.NORTH, idTextField);
		
		// password textfield x and y
		layout.putConstraint(SpringLayout.EAST, passwordTextField, 0, SpringLayout.EAST, lastNameTextField);
		layout.putConstraint(SpringLayout.NORTH, passwordTextField, 30, SpringLayout.SOUTH, idTextField);
				
		// username textField x and y
		layout.putConstraint(SpringLayout.EAST, usernameTextField, -25, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, usernameTextField, 28, SpringLayout.SOUTH, birthdayTextField);
	}

	/**
	 * Lays out the boxes of this panel
	 */
	protected void layoutBoxes() 
	{
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, administratorCheckBox, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, administratorCheckBox, 65, SpringLayout.VERTICAL_CENTER, this);
	}

	/**
	 * Lays out the buttons of this panel
	 */
	protected void layoutButtons() 
	{
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, confirmButton, 125, SpringLayout.VERTICAL_CENTER, this);
	
		if (Logistics.getInstance().getUser().isAdministrator())
		{
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, backButton, 25, SpringLayout.SOUTH, confirmButton);
		}
	}
	
	/**
	 * Action taken by the back button
	 */
	protected void backButtonAction()
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.USERS, Logistics.NO_ID);
	}

	/**
	 * Action taken by the confirm button
	 */
	protected abstract void confirmButtonAction();
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() instanceof JButton)
		{
			JButton button = (JButton) event.getSource();
			if (button.getText().equals("CONFIRM"))
				confirmButtonAction();
			else
				backButtonAction();
		}
	}
	
	@Override
	public void reset()
	{
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		birthdayTextField.setText("");
		idTextField.setText("");
		usernameTextField.setText("");
		passwordTextField.setText("");
		administratorCheckBox.setSelected(false);
	}
}
