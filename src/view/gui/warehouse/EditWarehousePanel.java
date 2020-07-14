package view.gui.warehouse;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Data;
import model.Database;
import model.Date;
import model.Warehouse;
import utilities.Utilities;

/**
 * Panel used to edit a warehouse in the database.
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class EditWarehousePanel extends AbstractWarehousePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Warehouse currently being edited */
	private int warehouseIdentity;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel.
	 */
	public EditWarehousePanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Edit Warehouse");
	}
	
	/**
	 * Checks whether there has been a change in the warehouse being edited
	 * @return true if there is a change. false othrerwise.
	 */
	private boolean change()
	{
		Data data = Database.getInstance().select(warehouseIdentity);
		
		// program must fail if null
		if (data == null)
		{
			String message = "EditWarehousePanel - change() - null ptr error "
					+ "reading warehouse: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		// makes sure it is a warehouse or else failure
		if (!(data instanceof Warehouse))
		{
			String message = "EditWarehousePanel - change() - invalid instance "
					+ "error reading company: " + warehouseIdentity;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		
		// checks for change in the name
		boolean name = !nameTextField.getText().strip().equals(warehouse.getName());
		if (name) return true;
		
		// checks for change in the capacity
		boolean capacity = !capacityTextField.getText().strip().equals(warehouse.getCapacity() + "");
		if (capacity) return true;
		
		// checks for change in the date
		boolean date = !dateTextField.getText().strip().equals(warehouse.getDate().toString());
		if (date) return true;
		
		// checks for change in the identity
		boolean identity = !idTextField.getText().strip().equals(Integer.toHexString(warehouse.getId()));
		if (identity) return true;
		
		return false;
	}
	
	/**
	 * Makes sure the changes that were made are valid.
	 * @return true if all of the changes are valid. false otherwise.
	 */
	private boolean validChange()
	{
		// checks change in name
		boolean name = !nameTextField.getText().isBlank();
		if (!name)
		{
			String message = "Make sure it is not empty";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in capacity
		boolean capacity = validCapacity(capacityTextField.getText().strip());
		if (!capacity)
		{
			String message = "Make sure it is not empty and only whole positive\n"
					+ "numbers are used and it doesn't go below warehouse quantity";
			String title = "Capacity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in date
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Birthday Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in identity
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
					   + "Length of ID must be 8 characters\n"
					   + "Make sure the first 3 digits are 400\n"
					   + "Make sure the characters are numbers (0-9)\n"
					   + "and/or letters (a-f)";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks whether the given string is a valid capacity or not.
	 * @param text - string to check for
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validCapacity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		try
		{
			int capacity = Integer.parseInt(text);
			
			Data data = Database.getInstance().select(warehouseIdentity);
			if (data == null)
			{
				String message = "EditWarehousePanel - validCapacity(String text) - "
						+ "null ptr invalid warehouse: " + this.warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			if (!(data instanceof Warehouse))
			{
				String message = "EditWarehousePanel - validCapacity(String text) - "
						+ "invalid instance invalid warehouse: " + this.warehouseIdentity;
				Utilities.getInstance().error(message);
			}
			
			Warehouse warehouse = (Warehouse) data;
			
			return capacity > 0 && capacity >= warehouse.getQuantity();
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given string is a valid identity or not.
	 * Make sure the first 3 digits are the id for warehouse
	 * @return true if it is a valid string. false otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.strip().length() != 7)
			return false;
		
		try
		{
			int id = Integer.parseInt(text);
			List<Data> warehouses = Database.getInstance().selectAll(Database.WAREHOUSE);
			for (Data data : warehouses)
			{
				if (data.getId() == id && data.getId() != warehouseIdentity)
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
	 * Updates the changes made to this warehouse
	 * @return true if all the changes were successfully cahnged. false otherwise.
	 */
	private boolean updateChange()
	{
		Database dtbs = Database.getInstance();
		
		// updates name
		boolean name = dtbs.update(warehouseIdentity, 
								   Warehouse.WAREHOUSE_NAME, 
								   nameTextField.getText().strip());
		// checks for failure
		if (!name)
		{
			String message = "Failure to change the name";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates capacity
		boolean capacity = dtbs.update(warehouseIdentity, 
									   Warehouse.WAREHOUSE_CAPACITY, 
									   Integer.parseInt(capacityTextField.getText().strip()));
		// checks for failure
		if (!capacity)
		{
			String message = "Failure to change the capacity";
			String title = "Capacity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates date
		boolean date = dtbs.update(warehouseIdentity, 
								   Warehouse.WAREHOUSE_DATE, 
								   new Date(dateTextField.getText().strip()));
		// checks for failure
		if (!date)
		{
			String message = "Failure to change the date";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates identity
		boolean identity = dtbs.update(warehouseIdentity, 
									   Warehouse.WAREHOUSE_ID, 
									   Integer.parseInt(idTextField.getText().strip(), 0x10));
		// checks for failure
		if (!identity)
		{
			String message = "Failure to change the ID";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// since id was changed, change this one too
		warehouseIdentity = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		return true;
	}

	@Override
	protected void confirmButtonAction() 
	{
		if (change()) // check for change
		{
			if (validChange()) // check is a valid change
			{
				if (updateChange()) // set the changes
				{
					// show success message
					String message = "Warehouse has been edited";
					String title = "Edit Warehouse";
					showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				
				}
			}
		}
		else
		{
			String message = "There is no change";
			String title = "Warehouse Product";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		if (data == null)
		{
			String message = "EditWarehousePanel - setData(int id) - null ptr "
					+ "error reading warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Warehouse))
		{
			String message = "EditWarehousePanel - setData(int id) - invalid instance "
					+ "error reading warehouse: " + id;
			Utilities.getInstance().error(message);
		}
		
		Warehouse warehouse = (Warehouse) data;
		
		nameTextField.setText(warehouse.getName());
		capacityTextField.setText(warehouse.getCapacity() + "");
		dateTextField.setText(warehouse.getDate().toString());
		idTextField.setText(Integer.toHexString(warehouse.getId()));
		
		warehouseIdentity = id;
	}
}
