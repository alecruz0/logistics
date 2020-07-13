package view.gui.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Logistics;
import utilities.ComponentFactory;
import utilities.ViewFactory;
import view.Panel;

/**
 * Abstract panel to show the data of a company.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractCompanyPanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Name label of this panel */
	protected JLabel nameLabel;
	
	/** Date label of this panel */
	protected JLabel dateLabel;
	
	/** id label of this panel */
	protected JLabel idLabel;
	
	/** textfield to hold the name of the company */
	protected JTextField nameTextField;
	
	/** textfield to hold the date of the company  */
	protected JTextField dateTextField;
	
	/** textfield to hold the id of the company */
	protected JTextField idTextField;
	
	/** button to confirm changes */
	protected JButton confirmButton;
	
	/** button to go back to company table */
	protected JButton backButton;

	/**
	 * Explicit constructor
	 * @param manager - manager that manages this panel
	 */
	protected AbstractCompanyPanel(Logistics manager) 
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
		
		// add labels
		add(nameLabel);
		add(dateLabel);
		add(idLabel);
		
		// add textfields
		add(nameTextField);
		add(dateTextField);
		add(idTextField);
		
		// add buttons
		add(confirmButton);
		add(backButton);
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
		ComponentFactory factory = ComponentFactory.getInstance();
		
		nameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Name:");
		dateLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Date:");
		idLabel = factory.createLabel(ComponentFactory.SUBTITLE, "ID:");
	}
	
	protected void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		nameTextField = factory.createTextField(ComponentFactory.TEXT);
		dateTextField = factory.createTextField(ComponentFactory.TEXT);
		idTextField = factory.createTextField(ComponentFactory.TEXT);
	}
	
	/**
	 * Creates buttons for this panel
	 */
	protected void createButtons()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// confirm button
		confirmButton = factory.createButton("CONFIRM");
		confirmButton.addActionListener(this);
		
		// back button
		backButton = factory.createButton("BACK");
		backButton.addActionListener(this);
	}
	
	@Override
	protected void layoutLabels()
	{
		super.layoutLabels();
		
		// name TextField
		layout.putConstraint(SpringLayout.EAST, nameLabel, -5, SpringLayout.WEST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 54, SpringLayout.SOUTH, titleLabel);
						
		// date TextField
		layout.putConstraint(SpringLayout.EAST, dateLabel, -5, SpringLayout.WEST, dateTextField);
		layout.putConstraint(SpringLayout.NORTH, dateLabel, 29, SpringLayout.SOUTH, nameTextField);
						
		// id TextField
		layout.putConstraint(SpringLayout.EAST, idLabel, -5, SpringLayout.WEST, idTextField);
		layout.putConstraint(SpringLayout.NORTH, idLabel, 29, SpringLayout.SOUTH, dateTextField);
	}
	
	/**
	 * Lays out textfields.
	 */
	protected void layoutTextFields()
	{
		// name TextField
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nameTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 50, SpringLayout.SOUTH, titleLabel);
				
		// date TextField
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, dateTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, dateTextField, 25, SpringLayout.SOUTH, nameTextField);
				
		// id TextField
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, idTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, idTextField, 25, SpringLayout.SOUTH, dateTextField);
	}
	
	/**
	 * Lays out buttons.
	 */
	protected void layoutButtons()
	{
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, confirmButton, 50, SpringLayout.SOUTH, idTextField);
	
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, backButton, 25, SpringLayout.SOUTH, confirmButton);
	}
	
	/**
	 * Action taken by the confirm button
	 */
	protected abstract void confirmButtonAction();
	
	/**
	 * Action taken by the back button
	 */
	protected void backButtonAction()
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.COMPANIES, Logistics.NO_ID);
	}
	
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
		nameTextField.setText("");
		dateTextField.setText("");
		idTextField.setText("");
	}
}
