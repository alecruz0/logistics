package view.gui.product;

import controller.Logistics;
import model.Database;
import model.Product;
import utilities.ViewFactory;
import view.gui.AbstractTablePanel;

/**
 * Panel used to show all of the  products of the database.
 * @author Manuel Cruz
 * @version 1.0
 */
public class ShowProductPanel extends AbstractTablePanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Explicit constructor for this panel.
	 * @param manager - manager that manages this panel
	 */
	public ShowProductPanel(Logistics manager) 
	{
		super(manager, Database.PRODUCT);
	}
	
	@Override
	protected void createLabels()
	{
		super.createLabels();
		
		titleLabel.setText("Products");
	}

	@Override
	protected void comboBoxAction() 
	{
		String sortingMethod = (String) sortComboBox.getSelectedItem();
		if (sortingMethod.equals("Name"))
			Product.setSorting(Product.PRODUCT_NAME);
		else if (sortingMethod.equals("Company"))
			Product.setSorting(Product.PRODUCT_COMPANY);
		else if (sortingMethod.equals("Weight"))
			Product.setSorting(Product.PRODUCT_WEIGHT);
		else if (sortingMethod.equals("Date"))
			Product.setSorting(Product.PRODUCT_DATE);
		else if (sortingMethod.equals("ID"))
			Product.setSorting(Product.PRODUCT_ID);
		
		reset();
	}

	@Override
	protected void addButtonAction() 
	{
		manager.changePanel(ViewFactory.ADD + ViewFactory.PRODUCTS, Logistics.NO_ID);
	}

	@Override
	protected void editButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 4), 0x10);
		manager.changePanel(ViewFactory.EDIT + ViewFactory.PRODUCTS, identity);
	}

	@Override
	protected void removeButtonAction() 
	{
		int row = table.getSelectedRow();
		int identity = Integer.parseInt((String) table.getValueAt(row, 4), 0x10);
		manager.changePanel(ViewFactory.REMOVE + ViewFactory.PRODUCTS, identity);
	}

	@Override
	protected String[] getHeaderDetails() 
	{
		return Product.getHeader();
	}
}
