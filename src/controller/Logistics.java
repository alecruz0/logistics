package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import controller.Logistics;
import model.Database;
import model.User;
import utilities.Utilities;
import utilities.ViewFactory;
import view.Panel;

/**
 * Logistics class is the main controller of this app. It has the starting point of 
 * the program that loads and manages the switch input the user requests.
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class Logistics implements Runnable, WindowListener 
{
	/** Constant used to describe no id is required for a switch */
	public final static int NO_ID = -1;
	
	/** Single instance of the logistics class */
	private static Logistics instance;
	
	/** Width of window frame */
	private int width;
	
	/** Height of window frame */
	private int height;
	
	/** Frame used to interact with user */
	private JFrame frame;
	
	/** User logged in */
	private User user;
	
	// All of the different panels that logistics manage
	private Panel addCompany,   editCompany,   removeCompany,   showCompany;
	private Panel addProduct,   editProduct,   removeProduct,   showProduct;
	private Panel addUser,      editUser,      removeUser,      showUser;
	private Panel addWarehouse, editWarehouse, removeWarehouse, showWarehouse;
	private Panel addWarehouseProduct, removeWarehouseProduct, showWarehouseProduct;
	private Panel loginPanel;
	
	/** Previledges that the user holds */
	private int previledge;
	
	/**
	 * Explicit constructor of logistics. <br>
	 * 
	 * Private used to mantain single instance
	 * 
	 * @param width - Width of the frame window
	 * @param height - Height of the frame window
	 */
	private Logistics(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.previledge = -1;
	}
	
	/**
	 * Sets the user that logged in <br>
	 * 
	 * User can be null when the previous user logs out
	 * 
	 * @param user - User that logged in
	 */
	public void setUser(User user)
	{
		this.user = user;
	}
	
	/**
	 * Gets the current user that is currently logged in.
	 * 
	 * @return The current user logged in
	 */
	public User getUser()
	{
		return user;
	}
	
	/**
	 * Gets the previledges that the current user contains.
	 * 
	 * @return previledge of the user logged in
	 */
	public int getPreviledge()
	{
		return previledge;
	}
	
	/**
	 * Logs into the warehouse. Makes the menu visible. It changes from the login 
	 * panel to the manage of user. <br>
	 * Depending on the previledges the user has.
	 */
	public void login()
	{	
		// sets the menu visible
		LogisticsMenu.getInstance().setVisible(true);
		if (user.isAdministrator())
		{
			setPreviledge(ViewFactory.ADMINISTRATOR);
			changePanel(ViewFactory.SHOW + ViewFactory.USERS, NO_ID);
		}
		else
		{
			setPreviledge(ViewFactory.USER);
			changePanel(ViewFactory.EDIT + ViewFactory.USERS, user.getId());
		}
	}
	
	/**
	 * Logs out the current user. It then changes to the login window.
	 */
	public void logout()
	{
		setUser(null); // makes sure there is no user
		LogisticsMenu.getInstance().setVisible(false); // makes menu invisible
		changePanel(ViewFactory.LOGIN, NO_ID);
	}
	
	/**
	 * Sets the previledges of the user.
	 * 
	 * @param value - Value of previledges
	 */
	private void setPreviledge(int value)
	{
		/* if the new user has a different previledge
		 * then everything must be reseted and built again
		 */
		if (previledge != value)
		{
			addCompany = null;
			editCompany = null;
			removeCompany = null;
			showCompany = null;
			
			addProduct = null;
			editProduct = null;
			removeProduct = null;
			showProduct = null;
			
			addUser = null;
			editUser = null;
			removeUser = null;
			showUser = null;
			
			addWarehouse = null;
			editWarehouse = null;
			removeWarehouse = null;
			showWarehouse = null;
			
			previledge = value;
		}
	}

	/**
	 * Changes to a different panel.
	 * 
	 * @param change - Panel to change to. It must have the operation 
	 * and the type added together
	 * 
	 * @param id - Some panels might need to set to a specific Data 
	 * from warehouse given the id. If no id is necessary then the 
	 * constant NO_ID can be used.
	 */
	public void changePanel(int change, int id) 
	{
		Panel panel = null;
		String title = "Logistics";
		
		if (user != null)
			title += " - " + user.getLastName() + " - ";
		
		// get specific panel
		switch(change)
		{
			case ViewFactory.ADD + ViewFactory.COMPANIES:
				panel = getAddCompanyPanel();
				title += "Add Company";
				break;
			case ViewFactory.EDIT + ViewFactory.COMPANIES:
				panel = getEditCompanyPanel();
				title += "Edit Company";
				break;
			case ViewFactory.REMOVE + ViewFactory.COMPANIES:
				panel = getRemoveCompanyPanel();
				title += "Remove Company";
				break;
			case ViewFactory.SHOW + ViewFactory.COMPANIES:
				panel = getShowCompanyPanel();
				title += "Show Companies";
				break;
			case ViewFactory.ADD + ViewFactory.PRODUCTS:
				panel = getAddProductPanel();
				title += "Add Product";
				break;
			case ViewFactory.EDIT + ViewFactory.PRODUCTS:
				panel = getEditProductPanel();
				title += "Edit Product";
				break;
			case ViewFactory.REMOVE + ViewFactory.PRODUCTS:
				panel = getRemoveProductPanel();
				title += "Remove Product";
				break;
			case ViewFactory.SHOW + ViewFactory.PRODUCTS:
				panel = getShowProductPanel();
				title += "Show Products";
				break;
			case ViewFactory.ADD + ViewFactory.USERS:
				panel = getAddUserPanel();
				title += "Add User";
				break;
			case ViewFactory.EDIT + ViewFactory.USERS:
				panel = getEditUserPanel();
				title += "Edit User";
				break;
			case ViewFactory.REMOVE + ViewFactory.USERS:
				panel = getRemoveUserPanel();
				title += "Remove User";
				break;
			case ViewFactory.SHOW + ViewFactory.USERS:
				panel = getShowUserPanel();
				title += "Show Users";
				break;
			case ViewFactory.ADD + ViewFactory.WAREHOUSES:
				panel = getAddWarehousePanel();
				title += "Add Warehouse";
				break;
			case ViewFactory.EDIT + ViewFactory.WAREHOUSES:
				panel = getEditWarehousePanel();
				title += "Edit Warehouse";
				break;
			case ViewFactory.REMOVE + ViewFactory.WAREHOUSES:
				panel = getRemoveWarehousePanel();
				title += "Remove Warehouse";
				break;
			case ViewFactory.SHOW + ViewFactory.WAREHOUSES:
				panel = getShowWarehousePanel();
				title += "Show Warehouses";
				break;
			case ViewFactory.LOGIN:
				title = "Logistics";
				panel = getLoginPanel();
				break;
			case ViewFactory.SHOW + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS:
				title += "Show Warehouse Products";
				panel = getShowWarehouseProductPanel();
				break;
			case ViewFactory.ADD + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS:
				title += "Add Warehouse Product";
				panel = getAddWarehouseProductPanel();
				break;
			case ViewFactory.REMOVE + ViewFactory.WAREHOUSES + ViewFactory.PRODUCTS:
				title += "Remove Warehouse Product";
				panel = getRemoveWarehouseProductPanel();
				break;
			default:
				String message = "Logistics - changePanel(int panel, int id) - case "
						+ " not found Invalid change parameter";
				Utilities.getInstance().error(message);
		}
		
		frame.setTitle(title);
		
		// sets the specific id given
		if (id != NO_ID)
			panel.setData(id);
		else
			panel.reset();
		
		switchPanel(panel); // switch
	}
	
	/**
	 * Gets the frame used to manage logistics in the database
	 * 
	 * @return the frame used
	 */
	public JFrame getFrame()
	{
		return frame;
	}
	
	/**
	 * Sets the frame that will be used to manage the database.
	 */
	private void setupFrame()
	{
		ViewFactory factory = ViewFactory.getInstance();
		
		// creates the frame
		frame = factory.createFrame(width, height);
		frame.addWindowListener(this);
		
		// set icon image
		ImageIcon iconImage = new ImageIcon("../data/logistics.png");
		frame.setIconImage(iconImage.getImage());
		
		// sets menu
		JMenuBar menubar = LogisticsMenu.getInstance();
		frame.setJMenuBar(menubar);
		menubar.setVisible(false);
		
		// sets the location and makes visible frame
		Dimension dimmension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimmension.width / 2 - frame.getSize().width / 2, 
						  dimmension.height / 2 - frame.getSize().height / 2);
		frame.setVisible(true);
	}

	/**
	 * Switches the panel.
	 * @param panel
	 */
	private void switchPanel(Panel panel)
	{
		// panel cannot be null
		if (panel == null)
		{
			String message = "Logistics - switchPanel(Panel panel) - Null parameter(null)";
			Utilities.getInstance().error(message);
		}
		
		// change content panel
		frame.setContentPane(panel);
		
		// repaint 
		panel.repaint();
		frame.repaint();
		frame.revalidate();
	}
	
	/**
	 * Sets the graphics to the system's look and feel
	 */
	private void setGraphics()
	{
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try 
		{
			UIManager.setLookAndFeel(lookAndFeel);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the panel that adds a company to the database
	 * @return panel to add company
	 */
	private Panel getAddCompanyPanel() 
	{
		// if the panel is not built it will create it
		if (addCompany == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.COMPANIES + ViewFactory.ADD;
			addCompany = factory.createPanel(value, this);
		}
		return addCompany;
	}

	/**
	 * Gets the panel that edits a company in the database
	 * @return panel to edit company
	 */
	private Panel getEditCompanyPanel() 
	{
		// if the panel is not built it will create it
		if (editCompany == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.COMPANIES + ViewFactory.EDIT;
			editCompany = factory.createPanel(value, this);
		}
		return editCompany;
	}

	/**
	 * Gets the panel that removes a company in the database
	 * @return panel to remove company
	 */
	private Panel getRemoveCompanyPanel() 
	{
		// if the panel is not built it will create it
		if (removeCompany == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.COMPANIES + ViewFactory.REMOVE;
			removeCompany = factory.createPanel(value, this);
		}
		return removeCompany;
	}

	/**
	 * Gets the panel that shows all of the companies in the database
	 * @return panel to show companies
	 */
	private Panel getShowCompanyPanel() 
	{
		// if the panel is not built it will create it
		if (showCompany == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.COMPANIES + ViewFactory.SHOW;
			showCompany = factory.createPanel(value, this);
		}
		return showCompany;
	}

	/**
	 * Gets the panel to add a product to the database
	 * @return panel to add product
	 */
	private Panel getAddProductPanel() 
	{
		// if the panel is not built it will create it
		if (addProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.PRODUCTS + ViewFactory.ADD;
			addProduct = factory.createPanel(value, this);
		}
		return addProduct;
	}

	/**
	 * Gets the panel to edit a product in the database
	 * @return panel to edit product
	 */
	private Panel getEditProductPanel() 
	{
		// if the panel is not built it will create it
		if (editProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.PRODUCTS + ViewFactory.EDIT;
			editProduct = factory.createPanel(value, this);
		}
		return editProduct;
	}

	/**
	 * Gets the panel to remove a product in the database
	 * @return panel to remove product
	 */
	private Panel getRemoveProductPanel() 
	{
		// if the panel is not built it will create it
		if (removeProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.PRODUCTS + ViewFactory.REMOVE;
			removeProduct = factory.createPanel(value, this);
		}
		return removeProduct;
	}

	/**
	 * Gets the panel to show all the products in the database
	 * @return panel to show products
	 */
	private Panel getShowProductPanel() 
	{
		// if the panel is not built it will create it
		if (showProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.PRODUCTS + ViewFactory.SHOW;
			showProduct = factory.createPanel(value, this);
		}
		return showProduct;
	}

	/**
	 * Gets the panel to add user in the database
	 * @return panel to add user
	 */
	private Panel getAddUserPanel() 
	{
		// if the panel is not built it will create it
		if (addUser == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.USERS + ViewFactory.ADD;
			addUser = factory.createPanel(value, this);
		}
		return addUser;
	}

	/**
	 * Gets the panel to edit user in the database
	 * @return panel to edit user
	 */
	private Panel getEditUserPanel() 
	{
		// if the panel is not built it will create it
		if (editUser == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.USERS + ViewFactory.EDIT;
			editUser = factory.createPanel(value, this);
		}
		return editUser;
	}

	/**
	 * Gets the panel to remove user in database
	 * @return panel to remove user
	 */
	private Panel getRemoveUserPanel() 
	{
		// if the panel is not built it will create it
		if (removeUser == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.USERS + ViewFactory.REMOVE;
			removeUser = factory.createPanel(value, this);
		}
		return removeUser;
	}

	/**
	 * Gets the panel to show users in database
	 * @return panel to show users
	 */
	private Panel getShowUserPanel() 
	{
		// if the panel is not built it will create it
		if (showUser == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.USERS + ViewFactory.SHOW;
			showUser = factory.createPanel(value, this);
		}
		return showUser;
	}

	/**
	 * Gets panel to add warehouse in database
	 * @return panel to add warehouse
	 */
	private Panel getAddWarehousePanel() 
	{
		// if the panel is not built it will create it
		if (addWarehouse == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.ADD;
			addWarehouse = factory.createPanel(value, this);
		}
		return addWarehouse;
	}

	/**
	 * Gets panel to edit warehouse in database
	 * @return panel to edit warehouse
	 */
	private Panel getEditWarehousePanel() 
	{
		// if the panel is not built it will create it
		if (editWarehouse == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.EDIT;
			editWarehouse = factory.createPanel(value, this);
		}
		return editWarehouse;
	}

	/**
	 * Gets panel to remove warehouse in database
	 * @return panel to remove warehouse
	 */
	private Panel getRemoveWarehousePanel() 
	{
		// if the panel is not built it will create it
		if (removeWarehouse == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.REMOVE;
			removeWarehouse = factory.createPanel(value, this);
		}
		return removeWarehouse;
	}

	/**
	 * Gets panel to show warehouses in database
	 * @return panel to show warehouses
	 */
	private Panel getShowWarehousePanel() 
	{
		// if the panel is not built it will create it
		if (showWarehouse == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.SHOW;
			showWarehouse = factory.createPanel(value, this);
		}
		return showWarehouse;
	}
	
	/**
	 * Gets panel to show the products of a specific warehouse in the database
	 * @return panel to show warehouse products
	 */
	private Panel getShowWarehouseProductPanel()
	{
		// if the panel is not built it will create it
		if (showWarehouseProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.SHOW + ViewFactory.PRODUCTS;
			showWarehouseProduct = factory.createPanel(value, this);
		}
		return showWarehouseProduct;
	}
	
	/**
	 * Gets the panel to add product to a specific warehouse in the database
	 * @return panel to add product to a warehouse
	 */
	private Panel getAddWarehouseProductPanel()
	{
		// if the panel is not built it will create it
		if (addWarehouseProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.ADD + ViewFactory.PRODUCTS;
			addWarehouseProduct = factory.createPanel(value, this);
		}
		return addWarehouseProduct;
	}
	
	/**
	 * Gets the panel to remove products from a specific warehouse in the database
	 * @return panel to remove product of a warehouse
	 */
	private Panel getRemoveWarehouseProductPanel()
	{
		// if the panel is not built it will create it
		if (removeWarehouseProduct == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = previledge + ViewFactory.WAREHOUSES + ViewFactory.REMOVE + ViewFactory.PRODUCTS;
			removeWarehouseProduct = factory.createPanel(value, this);
		}
		return removeWarehouseProduct;
	}
	
	/**
	 * Gets the panel used to login
	 * @return the login panel
	 */
	private Panel getLoginPanel() 
	{
		// if the panel is not built it will create it
		if (loginPanel == null)
		{
			ViewFactory factory = ViewFactory.getInstance();
			int value = ViewFactory.LOGIN;
			loginPanel = factory.createPanel(value, this);
		}
		return loginPanel;
	}

	@Override
	public void run()
	{
		setGraphics();
		setupFrame();
		logout();
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		Database.getInstance().save();
		System.exit(0);
	}

	/**
	 * Singleton for single instance of Logistics.
	 * @return logistics instance
	 */
	public static Logistics getInstance()
	{
		if (instance == null)
			instance = new Logistics(950, 600);
		return instance;
	}

	/**
	 * Start point of app.
	 * @param args - args from command line. They are not needed.
	 */
	public static void main(String[] args)
	{
		// start a thread to set up the database
		Database dtbs = Database.getInstance();
		Thread database = new Thread(dtbs);
		database.setName("Database Thread");
		database.start();
		
		// another thread for the logisitics
		Logistics logistics = Logistics.getInstance();
		try {
			SwingUtilities.invokeAndWait(logistics);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
}
