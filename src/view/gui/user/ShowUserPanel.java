package view.gui.user;

import controller.Logistics;
import model.Database;
import model.User;
import utilities.ViewFactory;
import view.gui.AbstractTablePanel;

/**
 * Shows the users of this database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class ShowUserPanel extends AbstractTablePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor of this panel.
	 * @param manager - manager that manages this panel
	 */
	public ShowUserPanel(Logistics manager) 
	{
		super(manager, Database.USER);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Users");
	}

	@Override
	protected void comboBoxAction() 
	{
		String sortingMethod = (String) sortComboBox.getSelectedItem();
		if (sortingMethod.equals("First Name"))
			User.setSorting(User.USER_FIRST_NAME);
		else if (sortingMethod.equals("Last Name"))
			User.setSorting(User.USER_LAST_NAME);
		else if (sortingMethod.equals("Birthday"))
			User.setSorting(User.USER_BIRTHDAY);
		else if (sortingMethod.equals("ID"))
			User.setSorting(User.USER_ID);
		else if (sortingMethod.equals("Username"))
			User.setSorting(User.USER_USERNAME);
		
		reset();
	}

	@Override
	protected void addButtonAction() 
	{
		manager.changePanel(ViewFactory.ADD + ViewFactory.USERS, Logistics.NO_ID);
	}

	@Override
	protected void editButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 4), 0x10);
		manager.changePanel(ViewFactory.EDIT + ViewFactory.USERS, identity);
	}

	@Override
	protected void removeButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 4), 0x10);
		manager.changePanel(ViewFactory.REMOVE + ViewFactory.USERS, identity);
	}

	@Override
	protected String[] getHeaderDetails() 
	{
		return User.getHeader();
	}

}
