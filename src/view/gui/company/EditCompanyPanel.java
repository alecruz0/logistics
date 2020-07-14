package view.gui.company;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Logistics;
import model.Company;
import model.Data;
import model.Database;
import model.Date;
import utilities.Utilities;

/**
 * Panel used to edit a company in the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class EditCompanyPanel extends AbstractCompanyPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Company currently being edited */
	private int companyIdentity;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel
	 */
	public EditCompanyPanel(Logistics manager) 
	{
		super(manager);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Edit Company");
	}
	
	/**
	 * Checks whether there has been a change in the company being edit.
	 * @return true if there is a change. False otherwise.
	 */
	private boolean change()
	{
		Data data = Database.getInstance().select(companyIdentity);
		
		// program must fail if null
		if (data == null)
		{
			String message = "EditCompanyPanel - change() - null ptr error "
					+ "reading company: " + companyIdentity;
			Utilities.getInstance().error(message);
		}
		
		// makes sure it is a company or else failure
		if (!(data instanceof Company))
		{
			String message = "EditCompanyPanel - change() - invalid instance "
					+ "error reading company: " + companyIdentity;
			Utilities.getInstance().error(message);
		}
		
		Company company = (Company) data;
		
		// checks for change in the name
		boolean name = !nameTextField.getText().strip().equals(company.getName());
		if (name) return true;
		
		// checks for change in the date
		boolean date = !dateTextField.getText().strip().equals(company.getDate().toString());
		if (date) return true;
		
		// checks for change in the identity
		boolean identity = !idTextField.getText().strip().equals(Integer.toHexString(company.getId()));
		if (identity) return true;
		
		return false;
	}
	
	/**
	 * Check whether the given string is a valid identity or not.
	 * Make sure the first 3 digits are the id for company
	 * @param text - string to check for
	 * @return true if it is a valid string. False otherwise.
	 */
	private boolean validIdentity(String text)
	{
		if (text.isBlank()) // no blanks
			return false;
		
		if (text.length() != 7) // must be 7 chars
			return false;
		
		try
		{
			// try to get an 0x number
			int identity = Integer.parseInt(text, 0x10);
			
			if ((identity & Database.DATA_TYPE_MASK) != Database.COMPANY)
				return false;
			
			// check it is a unique id
			List<Data> companies = Database.getInstance().selectAll(Database.COMPANY);
			for (Data data : companies)
			{								
				if (data.getId() == identity && data.getId() != companyIdentity)
					return false;
			}
			return true;
		}
		catch (NumberFormatException nfe) // must be an int of hexadecimal
		{
			return false;
		}
	}
	
	/**
	 * Makes sure the changes that were made are valid.
	 * @return True if all the changes are valid. false otherwise.
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
		
		// checks change in date
		boolean date = Date.validDate(dateTextField.getText().strip());
		if (!date)
		{
			String message = "Make sure it is in the format of mm/dd/yyyy";
			String title = "Date Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// checks change in id
		boolean identity = validIdentity(idTextField.getText().strip());
		if (!identity)
		{
			String message = "Make sure this ID is unique.\n"
					   + "Length of ID must be 7 characters\n"
					   + "Make sure the first 3 digits are 100\n"
					   + "Make sure the characters are numbers (0-9)\n"
					   + "and/or letters (a-f)";
			String title = "ID Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Updates the change made to this company
	 * @return true if all the changes were successfully changed. False otherwise.
	 */
	private boolean updateChange()
	{
		Database dtbs = Database.getInstance();
		
		// updates name
		boolean name = dtbs.update(companyIdentity,
								   Company.COMPANY_NAME, 
								   nameTextField.getText().strip());
		// checks for failure
		if (!name)
		{
			String message = "Failure to change the name";
			String title = "Name Error";
			showMessage(message, title, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// updates date
		boolean date = dtbs.update(companyIdentity, 
								   Company.COMPANY_DATE, 
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
		boolean identity = dtbs.update(companyIdentity, 
									   Company.COMPANY_ID, 
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
		companyIdentity = Integer.parseInt(idTextField.getText().strip(), 0x10);
		
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
					String message = "Company has been edited";
					String title = "Edit Company";
					showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else
		{
			String message = "There is no change";
			String title = "Edit Company";
			showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void setData(int id)
	{
		Data data = Database.getInstance().select(id);
		
		if (data == null)
		{
			String message = "EditCompanyPanel - setData(int id) - null ptr "
					+ "error reading company: " + id;
			Utilities.getInstance().error(message);
		}
		
		if (!(data instanceof Company))
		{
			String message = "EditCompanyPanel - setData(int id) - invalid instance "
					+ "error reading company: " + id;
			Utilities.getInstance().error(message);
		}
		
		Company company = (Company) data;
		
		nameTextField.setText(company.getName());
		dateTextField.setText(company.getDate().toString());
		idTextField.setText(Integer.toHexString(company.getId()));
		
		companyIdentity = id;
	}
}
