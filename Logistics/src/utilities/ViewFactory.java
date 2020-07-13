package utilities;

import javax.swing.JFrame;
import controller.Logistics;
import view.Panel;
import view.gui.LoginPanel;
import view.gui.company.AddCompanyPanel;
import view.gui.company.EditCompanyPanel;
import view.gui.company.RemoveCompanyPanel;
import view.gui.company.ShowCompanyPanel;
import view.gui.product.AddProductPanel;
import view.gui.product.EditProductPanel;
import view.gui.product.RemoveProductPanel;
import view.gui.product.ShowProductPanel;
import view.gui.user.AddUserPanel;
import view.gui.user.EditUserPanel;
import view.gui.user.RemoveUserPanel;
import view.gui.user.ShowUserPanel;
import view.gui.warehouse.AddWarehousePanel;
import view.gui.warehouse.AddWarehouseProductPanel;
import view.gui.warehouse.EditWarehousePanel;
import view.gui.warehouse.RemoveWarehousePanel;
import view.gui.warehouse.RemoveWarehouseProductPanel;
import view.gui.warehouse.ShowWarehousePanel;
import view.gui.warehouse.ShowWarehouseProductPanel;

/**
 * This class uses the factory pattern. That way every panel is created here.
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class ViewFactory 
{
	/** Constant to represent type of COMPANIES */
	public static final int COMPANIES = 1;
	
	/** Constant to represent type of PRODUCTS */
	public static final int PRODUCTS = 2;
	
	/** Constant to represent type of USERS */
	public static final int USERS = 3;
	
	/** Constant to represent type of WAREHOUSES */
	public static final int WAREHOUSES = 4;
	
	/** Constant to represent the operation of ADD */
	public static final int ADD = 8;
	
	/** Constant to represent the operation of EDIT */
	public static final int EDIT = 16;
	
	/** Constant to represent the operation of REMOVE */
	public static final int REMOVE = 24;
	
	/** Constant to represent the operation of SHOW */
	public static final int SHOW = 32;
	
	/** Constant to represent the login Panel */
	public static final int LOGIN = 64;
	
	/** Constant to represent USER previledges */
	public static final int USER = 128;
	
	/** Constant to represent ADMINISTRATOR previledges */
	public static final int ADMINISTRATOR = 192;
	
	/** Instance of View Factory */
	private static ViewFactory instance;
	
	/**
	 * Explicit constructor for view factory.
	 */
	private ViewFactory()
	{
		super();
	}
	
	/**
	 * Creates a frame with the given width and height
	 * @param width - width of frame
	 * @param height - height of frame
	 * @return frame with given width and height
	 */
	public JFrame createFrame(int width, int height)
	{
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		return frame;
	}
	
	/**
	 * Creates a panel based on the value given.<br><br>
	 * The value must contain the previledges + type + operation.
	 * @param value - value of the panel to create
	 * @param manager - manager that manages this panel
	 * @return panel based on value
	 */
	public Panel createPanel(int value, Logistics manager)
	{
		int previledges = value & 192; // get previledges
		
		Panel panel = null;
		
		if (value == LOGIN)
			panel = new LoginPanel(manager);
		else
		{
			value -= previledges; // remove previledges
			switch(value)
			{
				case COMPANIES + ADD:
					panel = new AddCompanyPanel(manager);
					break;
				case COMPANIES + EDIT:
					panel = new EditCompanyPanel(manager);
					break;
				case COMPANIES + REMOVE:
					panel = new RemoveCompanyPanel(manager);
					break;
				case COMPANIES + SHOW:
					panel = new ShowCompanyPanel(manager);
					break;
				case PRODUCTS + ADD:
					panel = new AddProductPanel(manager);
					break;
				case PRODUCTS + EDIT:
					panel = new EditProductPanel(manager);
					break;
				case PRODUCTS + REMOVE:
					panel = new RemoveProductPanel(manager);
					break;
				case PRODUCTS + SHOW:
					panel = new ShowProductPanel(manager);
					break;
				case USERS + ADD:
					panel = new AddUserPanel(manager);
					break;
				case USERS + EDIT:
					panel = new EditUserPanel(manager);
					break;
				case USERS + REMOVE:
					panel = new RemoveUserPanel(manager);
					break;
				case USERS + SHOW:
					if (previledges == ADMINISTRATOR)
						panel = new ShowUserPanel(manager);
					else
						panel = new EditUserPanel(manager);
					break;
				case WAREHOUSES + ADD:
					panel = new AddWarehousePanel(manager);
					break;
				case WAREHOUSES + EDIT:
					panel = new EditWarehousePanel(manager);
					break;
				case WAREHOUSES + REMOVE:
					panel = new RemoveWarehousePanel(manager);
					break;
				case WAREHOUSES + SHOW:
					panel = new ShowWarehousePanel(manager);
					break;
				case WAREHOUSES + SHOW + PRODUCTS:
					panel = new ShowWarehouseProductPanel(manager);
					break;
				case WAREHOUSES + PRODUCTS + ADD:
					panel = new AddWarehouseProductPanel(manager);
					break;
				case WAREHOUSES + PRODUCTS + REMOVE:
					panel = new RemoveWarehouseProductPanel(manager);
					break;
				default:
					String message = "ViewFactory - createPanel(int value, "
							+ "Logistics manager) - value is invalid";
					Utilities.getInstance().error(message);
			}
		}
		return panel;
	}
	
	/**
	 * Singleton for single view factory
	 * @return view factory
	 */
	public static ViewFactory getInstance()
	{
		if (instance== null)
			instance = new ViewFactory();
		return instance;
	}
}
