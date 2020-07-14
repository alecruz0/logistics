package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Logistics;
import utilities.ComponentFactory;
import utilities.ViewFactory;

/**
 * Abstract class that represents the view for add or removing to a warehouse.
 * 
 * @author Manuel Cruz
 * @version 1.0
 */
public abstract class AbstractWarehouseProductPanel extends Panel implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Labels contained in this panel */
	protected JLabel selectLabel, quantityLabel;
	
	/** Buttons contained in this panel */
	protected JButton confirmButton, backButton;
	
	/** Combo box to select products */
	protected JComboBox<Object> productComboBox;
	
	/** TextField to type quantity */
	protected JTextField quantityTextField;
	
	/** Identity of the warehouse being edited */
	protected int warehouseIdentity;
	
	/**
	 * Explicit constructor of this panel to add and remove to a warehouse
	 * @param manager - manager that manages this panel
	 */
	protected AbstractWarehouseProductPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createComponents()
	{
		super.createComponents();
		createButtons();
		createTextFields();
		createBoxes();
	}

	@Override
	protected void addComponents()
	{
		super.addComponents();
		
		add(selectLabel);
		add(quantityLabel);
		
		add(confirmButton);
		add(backButton);
		
		add(productComboBox);
		
		add(quantityTextField);
	}
	
	@Override
	protected void layoutComponents()
	{
		super.layoutComponents();
		
		layoutBoxes();
		layoutTextFields();
		layoutButtons();
	}

	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		ComponentFactory factory = ComponentFactory.getInstance();
		
		selectLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Select Product:");
		quantityLabel = factory.createLabel(ComponentFactory.SUBTITLE, "Quantity:");
	}
	
	/**
	 * Creates textfields used in this panel
	 */
	protected void createTextFields()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		quantityTextField = factory.createTextField(ComponentFactory.TEXT);
	}
	
	/**
	 * Creates boxes used in this panel
	 */
	protected void createBoxes()
	{
		ComponentFactory factory = ComponentFactory.getInstance();
		
		productComboBox = factory.createComboBox();
	}
	
	/**
	 * Creates buttons used in this panel
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
		
		// select label
		layout.putConstraint(SpringLayout.EAST, selectLabel, 0, SpringLayout.WEST, productComboBox);
		layout.putConstraint(SpringLayout.NORTH, selectLabel, 52, SpringLayout.SOUTH, titleLabel);
		
		// quantity label
		layout.putConstraint(SpringLayout.EAST, quantityLabel, 0, SpringLayout.WEST, quantityTextField);
		layout.putConstraint(SpringLayout.NORTH, quantityLabel, 27, SpringLayout.SOUTH, productComboBox);
	}
	
	/**
	 * Lays out boxes of this panel
	 */
	protected void layoutBoxes()
	{
		// product combo box
		layout.putConstraint(SpringLayout.EAST, productComboBox, 0, SpringLayout.EAST, quantityTextField);
		layout.putConstraint(SpringLayout.WEST, productComboBox, 0, SpringLayout.WEST, quantityTextField);
		layout.putConstraint(SpringLayout.NORTH, productComboBox, 50, SpringLayout.SOUTH, titleLabel);
	}
	
	/**
	 * Lays out textfields of this panel
	 */
	protected void layoutTextFields()
	{
		// quantity textfield
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, quantityTextField, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, quantityTextField, 25, SpringLayout.SOUTH, productComboBox);
	}
	
	/**
	 * Lays out buttons of this panel
	 */
	protected void layoutButtons()
	{
		// confirm button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, confirmButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, confirmButton, 35, SpringLayout.SOUTH, quantityTextField);
		
		// back button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, backButton, 25, SpringLayout.SOUTH, confirmButton);
	}
	
	/**
	 * Action taken when the confirm button is pressed
	 */
	public abstract void confirmButtonAction();
	
	/**
	 * Action taken when the back button is pressed
	 */
	public void backButtonAction()
	{
		manager.changePanel(ViewFactory.SHOW + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS, warehouseIdentity);
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
}
