package view.gui.product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Logistics;
import model.Data;
import model.Database;
import utilities.ComponentFactory;
import utilities.ViewFactory;
import view.Panel;

/**
 * Product panel that holds data of a product.
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractProductPanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Labels used in this panel */
	protected JLabel nameLabel, companyLabel, weightLabel, 
					 dateLabel, idLabel;
	
	/** Textfields used in this panel */
	protected JTextField nameTextField, weightTextField, 
						 dateTextField, idTextField;
	
	/** Combo box used to hold companies */
	protected JComboBox<Object> companyComboBox;
	
	/** Buttons used in this panel */
	protected JButton confirmButton, backButton;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel
	 */
	protected AbstractProductPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createComponents()
	{
		super.createComponents();
		createTextFields();
		createBoxes();
		createButtons();
	}
	
	@Override
	protected void addComponents()
	{
		super.addComponents();
		add(nameLabel);
		add(companyLabel);
		add(weightLabel);
		add(dateLabel);
		add(idLabel);
		
		add(nameTextField);
		add(weightTextField);
		add(dateTextField);
		add(idTextField);
		
		add(companyComboBox);
		
		add(confirmButton);
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
		
		nameLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Name:");
		companyLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Company:");
		weightLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Weight:");
		dateLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Date:");
		idLabel = factory.createLabel(ComponentFactory.SUBTITLE, "ID:");
	}
	
	/**
	 * Creates the textfields used in this panel
	 */
	protected void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		nameTextField = factory.createTextField(ComponentFactory.TEXT);
		weightTextField = factory.createTextField(ComponentFactory.TEXT);
		dateTextField = factory.createTextField(ComponentFactory.TEXT);
		idTextField = factory.createTextField(ComponentFactory.TEXT);
	}
	
	/**
	 * Creates the boxes used in this panel
	 */
	protected void createBoxes()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		companyComboBox = factory.createComboBox();
	}
	
	/**
	 * Creates the buttons used in this panel.
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
		// name label x and y
		layout.putConstraint(SpringLayout.EAST, nameLabel, -5, SpringLayout.WEST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 54, SpringLayout.SOUTH, titleLabel);
				
		// date label x and y
		layout.putConstraint(SpringLayout.WEST, dateLabel, 30, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, dateLabel, 45, SpringLayout.SOUTH, nameTextField);
				
		// company label x and y
		layout.putConstraint(SpringLayout.EAST, companyLabel, -5, SpringLayout.WEST, companyComboBox);
		layout.putConstraint(SpringLayout.NORTH, companyLabel, 54, SpringLayout.SOUTH, titleLabel);
				
		// unit weight label x and y
		layout.putConstraint(SpringLayout.EAST, weightLabel, -5, SpringLayout.WEST, weightTextField);
		layout.putConstraint(SpringLayout.NORTH, weightLabel, 45, SpringLayout.SOUTH, nameTextField);
				
		// id label x and y
		layout.putConstraint(SpringLayout.EAST, idLabel, -5, SpringLayout.WEST, idTextField);
		layout.putConstraint(SpringLayout.NORTH, idLabel, 45, SpringLayout.SOUTH, weightTextField);
	}
	
	/**
	 * Lays out the textfields of this panel
	 */
	protected void layoutTextFields()
	{
		// nameTextField x and y
		layout.putConstraint(SpringLayout.EAST, nameTextField, -30, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, nameTextField, 50, SpringLayout.SOUTH, titleLabel);
				
		// date TextField x and y
		layout.putConstraint(SpringLayout.WEST, dateTextField, 5, SpringLayout.EAST, dateLabel);
		layout.putConstraint(SpringLayout.NORTH, dateTextField, 40, SpringLayout.SOUTH, nameTextField);
				
		// weight TextField x and y
		layout.putConstraint(SpringLayout.EAST, weightTextField, 0, SpringLayout.EAST, nameTextField);
		layout.putConstraint(SpringLayout.NORTH, weightTextField, 40, SpringLayout.SOUTH, nameTextField);
				
		// id textfield
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, idTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, idTextField, 40, SpringLayout.SOUTH, weightTextField);
	}
	
	/**
	 * Lays out the boxes of this panel
	 */
	protected void layoutBoxes()
	{
		// company combo box x and y
		layout.putConstraint(SpringLayout.WEST, companyComboBox, 0,	SpringLayout.WEST, dateTextField);
		layout.putConstraint(SpringLayout.EAST, companyComboBox, 0, SpringLayout.EAST, dateTextField);
		layout.putConstraint(SpringLayout.NORTH, companyComboBox, 50, SpringLayout.SOUTH, titleLabel);
	}
	
	/**
	 * Lays out the buttons of this panel
	 */
	protected void layoutButtons()
	{
		// confirm button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, confirmButton, 40, SpringLayout.SOUTH, idTextField);
		
		// back button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, backButton, 25, SpringLayout.SOUTH, confirmButton);
	}
	
	/**
	 * Action taken when the back button is pressed.
	 */
	protected void backButtonAction()
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.PRODUCTS, Logistics.NO_ID);
	}

	/**
	 * Action taken when the confirm button is pressed.
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
		// empties the text on the textfields
		nameTextField.setText("");
		weightTextField.setText("");
		dateTextField.setText("");
		idTextField.setText("");
		
		// gets all of the companies to the combo box
		companyComboBox.removeAllItems();
		List<Data> companies = Database.getInstance().selectAll(Database.COMPANY);
		for(Data data : companies)
		{
			Data company = Database.getInstance().select(data.getId());
			companyComboBox.addItem(company);
		}
	}
}
