package view.gui.company;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Company;
import model.Database;
import model.Date;

/**
 * Panel used to add a company to the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class AddCompanyPanel extends AbstractCompanyPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - Manager that manages this panel
	 */
	public AddCompanyPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Add Company");
	}
	
	/**
	 * Check whether the given string is a valid id for the company. <br><br>
	 * Make sure the first 3 digits are the id for company
	 * @param text - text to check for
	 * @return true if this string is a valid id. False otherwise.
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
			int identity = Integer.parseInt(text.strip(), 0x10);
			
			// make sure it is a company id
			if ((identity & Database.DATA_TYPE_MASK) != Database.COMPANY)
				return false;
			
			return Database.getInstance().validID(identity);
		}
		catch (NumberFormatException nfe) // must be an int of hexadecimal
		{
			return false;
		}
	}
	
	/**
	 * Checks that the company to be added is valid
	 * @return True if the company is valid. False otherwise
	 */
	private boolean validCompany()
	{
		// checks the name if it fails show message of error
		boolean name = !nameTextField.getText().isBlank();
		if (!name)
		{
			String message = "Make sure it is not empty";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks the date if it fails show message error
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks the identity if it fails show message error
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
						   + "Length of ID must be 7 characters\n"
						   + "Make sure the first 3 digits are 100\n"
						   + "Make sure the characters are numbers (0-9)\n"
						   + "and/or letters (a-f)";
			
			String title = "Identity Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds a company to the database.
	 * @return true if it was successfully added. False otherwise.
	 */
	private boolean addCompany()
	{
		String name = nameTextField.getText().strip();
		Date date = new Date(dateTextField.getText().strip());
		int id = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
		Company company = new Company(name, date, id);
		
		return Database.getInstance().insert(company);
	}

	@Override
	protected void confirmButtonAction() {
		if (validCompany()) // check that is a valid company
		{
			if (addCompany()) // add the company if success show message
			{
				String message = "Company has been added";
				String title = "Add Company";
				showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				reset();
			}
			else // if it fails show error message
			{
				String message = "Company could not be added to the database";
				String title = "Add Company";
				showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void reset()
	{
		super.reset();
		
		int identity = Database.generateID(Database.COMPANY);
		idTextField.setText(Integer.toHexString(identity));
		
		Date date = new Date();
		dateTextField.setText(date.toString());
	}
}
