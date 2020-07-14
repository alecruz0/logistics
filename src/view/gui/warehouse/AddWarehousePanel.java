package view.gui.warehouse;

import javax.swing.JOptionPane;
import controller.Logistics;
import model.Database;
import model.Date;
import model.Warehouse;

/**
 * Panel used to add a warehouse to the database
 * @author Manuel Cruz
 * @version 1.0
 */
public class AddWarehousePanel extends AbstractWarehousePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor for this panel
	 * @param manager - manager that manages this panel
	 */
	public AddWarehousePanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		titleLabel.setText("Add Warehouse");
	}
	
	/**
	 * Checks whether the given string is a valid capacity.
	 * @param text - string to check
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validCapacity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		try
		{
			int capacity = Integer.parseInt(text);
			return capacity > 0; // must be positive
		}
		catch (NumberFormatException nfe) // invalid number fails
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given string is a valid identity
	 * @param text - string to check
	 * @return true if it is valid. false otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.strip().length() != 7) // must be 7 chars
			return false;
		
		try
		{
			// try to get an 0x number
			int identity = Integer.parseInt(text, 0x10);
			
			// make sure it is a warehouse id
			if ((identity & Database.DATA_TYPE_MASK) != Database.WAREHOUSE)
				return false;
			
			return Database.getInstance().validID(identity);
		}
		catch (NumberFormatException nfe) // must be an int of hex
		{
			return false;
		}
	}
	
	/**
	 * Checks that the warehouse to be added is valid
	 * @return true if the warehouse is valid. false otherwise.
	 */
	private boolean validWarehouse()
	{
		// checks the name if it fails show error message
		boolean name = !nameTextField.getText().isBlank();
		if (!name)
		{
			String message = "Make sure it is not empty";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks capacity if it fails show error message
		boolean capacity = validCapacity(capacityTextField.getText().strip());
		if (!capacity)
		{
			String message = "Make sure it is not empty and only whole positive "
					+ "numbers are used";
			String title = "Capacity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks date if it fails show error message
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Birthday Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks identity if it fails show error message
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
	 * Adds a warehouse to the database.
	 * @return true if it was successfully added. false otherwise.
	 */
	private boolean addWarehouse()
	{
		String name = nameTextField.getText().strip();
		int capacity = Integer.parseInt(capacityTextField.getText().strip());
		Date date = new Date(dateTextField.getText().strip());
		int identity = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		Warehouse warehouse = new Warehouse(name, capacity, date, identity);
		
		return Database.getInstance().insert(warehouse);
	}

	@Override
	protected void confirmButtonAction() {
		if (validWarehouse()) // check that is a valid warehouse
		{
			if (addWarehouse()) // add the warehouse if success show message
			{
				String message = "Warehouse has been added";
				String title = "Add Warehouse";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else // if it fails show  error messsage
			{
				String message = "Warehouse could not be added to database";
				String title = "Add Warehouse";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void reset()
	{
		super.reset();
		
		int identity = Database.generateID(Database.WAREHOUSE);
		idTextField.setText(Integer.toHexString(identity));
		
		Date date = new Date();
		dateTextField.setText(date.toString());
	}
}
