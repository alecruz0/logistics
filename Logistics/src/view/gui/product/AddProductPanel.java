package view.gui.product;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Company;
import model.Database;
import model.Date;
import model.Product;

/**
 * Panel used to add a product to the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class AddProductPanel extends AbstractProductPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel.
	 */
	public AddProductPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Add Product");
	}
	
	/**
	 * Checks whether the given string is a valid identity or not.
	 * Make sure the first 3 digits are the id for product
	 * @param text - string to check
	 * @return true if it is valid false otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.strip().length() != 7) // must be 7 chars
			return false;
		
		try
		{
			int identity = Integer.parseInt(text.trim(), 0x10);
			
			if ((identity & Database.DATA_TYPE_MASK) != Database.PRODUCT)
				return false;
			
			return Database.getInstance().validID(identity);
		}
		catch (NumberFormatException nfe) // invalid int
		{
			return false;
		}
	}
	
	/**
	 * Checks that the given string it is a valid weight. Must be positive.
	 * @param text - string to check
	 * @return true if it is a valid weight. False otherwise.
	 */
	private boolean validWeight(String text)
	{
		if (text.isBlank())
			return false;
		
		try
		{
			double weight = Double.parseDouble(text.trim());
			return weight >= 0; // must be positive
		}
		catch (NumberFormatException nfe) // checking it is a double
		{
			return false;
		}
	}
	
	/**
	 * Checks whether the given product is valid or not.
	 * @return true if it is a valid product. false otherwise.
	 */
	private boolean validProduct()
	{
		// checks for name
		boolean name = !nameTextField.getText().isBlank();
		if (!name)
		{
			String message = "Make sure it is not empty";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for company
		boolean company = companyComboBox.getItemCount() > 0;
		if (!company)
		{
			String message = "Make sure companies are added";
			String title = "Company Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for weight
		boolean weight = validWeight(weightTextField.getText().strip());		
		if (!weight)
		{
			String message = "Make sure it is only numbers and positive values";
			String title = "Weight Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for date
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks for identity
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
					   + "Length of ID must be 7 characters\n"
					   + "Make sure the first 3 digits are 200\n"
					   + "Make sure the characters are numbers (0-9)\n"
					   + "and/or letters (a-f)";
			
			String title = "Identity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds a product to the warehouse.
	 * @return true if it was added successfully. False Otherwise.
	 */
	private boolean addProduct()
	{
		String name = nameTextField.getText().strip();
		Company company = (Company) companyComboBox.getSelectedItem();
		double weight = Double.parseDouble(weightTextField.getText().strip());
		Date date = new Date(dateTextField.getText().strip());
		int id = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		Product product = new Product(name, company.getId(), id, weight, date);
		
		return Database.getInstance().insert(product);
	}

	@Override
	protected void confirmButtonAction() 
	{
		if (validProduct()) // checks if it is a valid product
		{
			if (addProduct()) // tries to add the product
			{
				String message = "Product has been added";
				String title = "Add Product";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else
			{
				String message = "Product could not be added to the database";
				String title = "Add Product";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void reset()
	{
		super.reset();
		
		int identity = Database.generateID(Database.PRODUCT);
		idTextField.setText(Integer.toHexString(identity));
		
		Date date = new Date();
		dateTextField.setText(date.toString());
	}
}
