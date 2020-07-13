package view.gui.warehouse;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import controller.Logistics;
import model.Database;
import model.Warehouse;
import utilities.ComponentFactory;
import utilities.ViewFactory;
import view.gui.AbstractTablePanel;

/**
 * Panel used to show all of the warehouses in the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class ShowWarehousePanel extends AbstractTablePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Button to manage the selected warehouse */
	private JButton manageButton;

	/**
	 * Explicit constructor of this panel.
	 * @param manager - manager used to manage this panel
	 */
	public ShowWarehousePanel(Logistics manager) 
	{
		super(manager, Database.WAREHOUSE);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Warehouses");
	}
	
	@Override
	protected void addComponents()
	{
		super.addComponents();
		
		add(manageButton);
	}
	
	@Override
	protected void createButtons()
	{
		super.createButtons();
		
		ComponentFactory factory = ComponentFactory.getInstance();
		
		manageButton = factory.createButton("MANAGE");
		manageButton.addActionListener(this);
	}
	
	@Override
	protected void layoutButtons()
	{
		// add button
		layout.putConstraint(SpringLayout.WEST, addButton, 0, SpringLayout.WEST, tablePanel);
		layout.putConstraint(SpringLayout.SOUTH, addButton, -50, SpringLayout.SOUTH, this);
				
		// remove button
		layout.putConstraint(SpringLayout.EAST, removeButton, 0, SpringLayout.EAST, tablePanel);
		layout.putConstraint(SpringLayout.SOUTH, removeButton, -50, SpringLayout.SOUTH, this);
				
		// edit button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, editButton, 0, SpringLayout.WEST, titleLabel);
		layout.putConstraint(SpringLayout.SOUTH, editButton, -50, SpringLayout.SOUTH, this);
		
		// manage button
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, manageButton, 0, SpringLayout.EAST, titleLabel);
		layout.putConstraint(SpringLayout.SOUTH, manageButton, -50, SpringLayout.SOUTH, this);
	}

	@Override
	protected void comboBoxAction() 
	{
		String sortingMethod = (String) sortComboBox.getSelectedItem();
		if (sortingMethod.equals("Name"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_NAME);
		else if (sortingMethod.equals("Product Count"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_PRODUCT_COUNT);
		else if (sortingMethod.equals("Capacity"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_CAPACITY);
		else if (sortingMethod.equals("Quantity"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_QUANTITY);
		else if (sortingMethod.equals("Date"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_DATE);
		else if (sortingMethod.equals("ID"))
			Warehouse.setSorting(Warehouse.WAREHOUSE_ID);
		
		reset();
	}
	
	@Override
	protected void buttonsActions(JButton button)
	{
		super.buttonsActions(button);
		
		if (button.getText().equals("MANAGE"))
			manageButtonAction();
	}

	@Override
	protected void addButtonAction() 
	{
		manager.changePanel(ViewFactory.ADD + ViewFactory.WAREHOUSES, Logistics.NO_ID);
	}

	@Override
	protected void editButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 5), 0x10);
		manager.changePanel(ViewFactory.EDIT + ViewFactory.WAREHOUSES, identity);
	}

	private void manageButtonAction()
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 5), 0x10);
		manager.changePanel(ViewFactory.SHOW + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS, identity);
	}
	
	@Override
	protected void removeButtonAction() {
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 5), 0x10);
		manager.changePanel(ViewFactory.REMOVE + ViewFactory.WAREHOUSES, identity);
	}
	
	@Override
	public void mouseClicked(MouseEvent event)
	{
		super.mouseClicked(event);
		
		if (event.getSource() instanceof JTable)
			manageButton.setEnabled(true);
	}
	
	@Override
	public void reset()
	{
		super.reset();
		
		manageButton.setEnabled(false);
	}

	@Override
	protected String[] getHeaderDetails() 
	{
		return Warehouse.getHeader();
	}

}
