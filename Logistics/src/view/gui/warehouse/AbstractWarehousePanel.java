package view.gui.warehouse;

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
 * Abstarct panel to show the data of a warehouse.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractWarehousePanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Labels used in this panel */
	protected JLabel nameLabel, capacityLabel,
					 dateLabel, idLabel;
	
	/** Textfields used in this panel */
	protected JTextField nameTextField, dateTextField, 
						 idTextField,   capacityTextField;
	
	/** Buttons used in this panel */
	protected JButton confirmButton, backButton;

	/**
	 * Explicit constructor of this panel
	 * @param manager - managerthat manages this panel
	 */
	protected AbstractWarehousePanel(Logistics manager) 
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
		add(nameLabel);
		add(capacityLabel);
		add(dateLabel);
		add(idLabel);
		
		add(nameTextField);
		add(dateTextField);
		add(idTextField);
		add(capacityTextField);
		
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
		capacityLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Capacity:");
		dateLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Date:");
		idLabel = factory.createLabel(ComponentFactory.SUBTITLE, "ID:");
	}
	
	/**
	 * Creates the textfields of this panel
	 */
	protected void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		nameTextField = factory.createTextField(ComponentFactory.TEXT);
		dateTextField = factory.createTextField(ComponentFactory.TEXT);
		idTextField = factory.createTextField(ComponentFactory.TEXT);
		capacityTextField = factory.createTextField(ComponentFactory.TEXT);
	}
	
	/**
	 * Creates the buttons used in this panel
	 */
	protected void createButtons()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		confirmButton = factory.createButton("CONFIRM");
		confirmButton.addActionListener(this);
		
		backButton = factory.createButton("BACK");
		backButton.addActionListener(this);
	}
	
	@Override
	protected void layoutLabels()
	{
		super.layoutLabels();
		
		// name label
		layout.putConstraint(SpringLayout.EAST, nameLabel, -5, SpringLayout.WEST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 54, SpringLayout.SOUTH, titleLabel);
						
		// id label
		layout.putConstraint(SpringLayout.WEST, idLabel, 50, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, idLabel, 45, SpringLayout.SOUTH, nameTextField);
						
		// capacity label
		layout.putConstraint(SpringLayout.EAST, capacityLabel, -5, SpringLayout.WEST, capacityTextField);
		layout.putConstraint(SpringLayout.NORTH, capacityLabel, 54, SpringLayout.SOUTH, titleLabel);
						
		// date label
		layout.putConstraint(SpringLayout.EAST, dateLabel, -5, SpringLayout.WEST, dateTextField);
		layout.putConstraint(SpringLayout.NORTH, dateLabel, 45, SpringLayout.SOUTH, nameTextField);
	}
	
	/**
	 * Lays out the textfields
	 */
	protected void layoutTextFields()
	{
		// nameTextField x and y
		layout.putConstraint(SpringLayout.EAST, nameTextField, -30, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 50, SpringLayout.SOUTH, titleLabel);
						
		// id TextField x and y
		layout.putConstraint(SpringLayout.WEST, idTextField, 5, SpringLayout.EAST, idLabel);
		layout.putConstraint(SpringLayout.NORTH, idTextField, 40, SpringLayout.SOUTH, nameTextField);
						
		// date TextField x and y
		layout.putConstraint(SpringLayout.EAST, dateTextField, 0, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, dateTextField, 40, SpringLayout.SOUTH, nameTextField);
		
		// capacity textfield x and y
		layout.putConstraint(SpringLayout.WEST, capacityTextField, 0,	SpringLayout.WEST, idTextField);
		layout.putConstraint(SpringLayout.NORTH, capacityTextField, 50, SpringLayout.SOUTH, titleLabel);
	}
	
	/**
	 * Lays out the buttons
	 */
	protected void layoutButtons()
	{
		//confirm button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, confirmButton, 50, SpringLayout.SOUTH, dateTextField);
		
		// back buttons
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, backButton, 25, SpringLayout.SOUTH, confirmButton);
	}
	
	/**
	 * Action taken when the confirm button is pressed
	 */
	protected abstract void confirmButtonAction();
	
	/**
	 * Action taken when the back button is pressed
	 */
	protected void backButtonAction() 
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.WAREHOUSES, Logistics.NO_ID);
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
		capacityTextField.setText("");
	}
}
