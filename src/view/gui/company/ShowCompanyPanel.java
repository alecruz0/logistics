package view.gui.company;

import controller.Logistics;
import model.Company;
import model.Database;
import utilities.ViewFactory;
import view.gui.AbstractTablePanel;

/**
 * Panel used to show all of the companies of the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class ShowCompanyPanel extends AbstractTablePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor of this panel.
	 * @param manager - manager used to manage this panel.
	 */
	public ShowCompanyPanel(Logistics manager) 
	{
		super(manager, Database.COMPANY);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Companies");
	}

	@Override
	protected void comboBoxAction() 
	{
		String sortingMethod = (String) sortComboBox.getSelectedItem();
		if (sortingMethod.equals("Name"))
			Company.setSorting(Company.COMPANY_NAME);
		else if (sortingMethod.equals("Date"))
			Company.setSorting(Company.COMPANY_DATE);
		else if (sortingMethod.equals("ID"))
			Company.setSorting(Company.COMPANY_ID);
		
		reset();
	}

	@Override
	protected void addButtonAction() 
	{
		manager.changePanel(ViewFactory.ADD + ViewFactory.COMPANIES, Logistics.NO_ID);
	}

	@Override
	protected void editButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 2), 0x10);
		manager.changePanel(ViewFactory.EDIT + ViewFactory.COMPANIES, identity);
	}

	@Override
	protected void removeButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 2), 0x10);;
		manager.changePanel(ViewFactory.REMOVE + ViewFactory.COMPANIES, identity);
	}

	@Override
	protected String[] getHeaderDetails() 
	{
		return Company.getHeader();
	}
}
