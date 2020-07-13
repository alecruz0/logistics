package controller;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import utilities.ComponentFactory;
import utilities.ViewFactory;

/**
 * Class used to implement the Menu of logistics. To navegate through the different 
 * types of data in the database
 * 
 * @author Manuel Cruz
 * @version 1.0
 *
 */
public class LogisticsMenu extends JMenuBar implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Single instance of logisticsMenu */
	private static LogisticsMenu instance;

	/**
	 * Explicit Constructor for the LogisticsMenu.
	 */
	private LogisticsMenu() 
	{
		setup();
	}
	
	/**
	 * Sets up the contents of the logisticsMenu
	 */
	private void setup()
	{
		// gets factory to create menu
		ComponentFactory factory = ComponentFactory.getInstance();
		
		// sets the margins for the menus
		Insets logisticMenuInsets = new Insets(5, 25, 5, 25);
		Insets menuItemInsets = new Insets(5, 5, 5, 5);
		
		// creates the menu buttons to substain the submenus
		JMenu logisticsMenu = factory.createMenuTab("Menu");
		logisticsMenu.setMargin(logisticMenuInsets);
		
		// sets the users menu
		JMenuItem users = factory.createMenuItem("Users");
		users.setMargin(menuItemInsets);
		users.addActionListener(this);
		
		// sets the companies menu
		JMenuItem companies = factory.createMenuItem("Companies");
		companies.setMargin(menuItemInsets);
		companies.addActionListener(this);
		
		// sets the products menu
		JMenuItem products = factory.createMenuItem("Products");
		products.setMargin(menuItemInsets);
		products.addActionListener(this);
		
		// sets the warehouse menu
		JMenuItem warehouses = factory.createMenuItem("Warehouses");
		warehouses.setMargin(menuItemInsets);
		warehouses.addActionListener(this);
		
		// sets the help menu
		JMenuItem help = factory.createMenuItem("Help");
		help.setMargin(menuItemInsets);
		help.addActionListener(this);
		
		// sets the about menu
		JMenuItem about = factory.createMenuItem("About");
		about.setMargin(menuItemInsets);
		about.addActionListener(this);
		
		// sets the log out menu
		JMenuItem logout = factory.createMenuItem("Log out");
		logout.setMargin(menuItemInsets);
		logout.addActionListener(this);
		
		// sets the exit menu
		JMenuItem exit = factory.createMenuItem("Exit");
		exit.setMargin(menuItemInsets);
		exit.addActionListener(this);
		
		// add all the submenus to the menu
		logisticsMenu.add(users);
		logisticsMenu.add(companies);
		logisticsMenu.add(products);
		logisticsMenu.add(warehouses);
		logisticsMenu.addSeparator();
		logisticsMenu.add(help);
		logisticsMenu.add(about);
		logisticsMenu.addSeparator();
		logisticsMenu.add(logout);
		logisticsMenu.addSeparator();
		logisticsMenu.add(exit);
		
		// add menu to menubar
		add(logisticsMenu);
	}
	
	/**
	 * Action taken when the help menu is pressed
	 */
	private void helpAction()
	{
		String message = "If you need any help, please contact your supervisor or administrator";
		String title = "Help";
		JOptionPane.showMessageDialog(Logistics.getInstance().getFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Action taken when the about menu is pressed
	 */
	private void aboutAction()
	{
		String message = "Logistics is a program used to interact with a database of logistics.";
		String title = "About";
		JOptionPane.showMessageDialog(Logistics.getInstance().getFrame(), message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Decides what action to take based on the menu that was pressed/
	 * @param menuItem
	 */
	private void menuAction(JMenuItem menuItem)
	{		
		String menuText = menuItem.getText();
		
		Logistics logistics = Logistics.getInstance();
		
		// check which menu was selected
		if (menuText.equals("Users"))
		{
			if (logistics.getUser().isAdministrator())
				logistics.changePanel(ViewFactory.SHOW + ViewFactory.USERS, logistics.getUser().getId());
			else
				logistics.changePanel(ViewFactory.EDIT + ViewFactory.USERS, logistics.getUser().getId());
		}
		else if (menuText.equals("Companies"))
			logistics.changePanel(ViewFactory.SHOW + ViewFactory.COMPANIES, Logistics.NO_ID);
		else if (menuText.equals("Products"))
			logistics.changePanel(ViewFactory.SHOW + ViewFactory.PRODUCTS, Logistics.NO_ID);
		else if (menuText.equals("Warehouses"))
			logistics.changePanel(ViewFactory.SHOW + ViewFactory.WAREHOUSES, Logistics.NO_ID);
		else if (menuText.equals("Help"))
			helpAction();
		else if (menuText.equals("About"))
			aboutAction();
		else if (menuText.equals("Log out"))
			logistics.changePanel(ViewFactory.LOGIN, Logistics.NO_ID);
		else if (menuText.equals("Exit"))
			logistics.windowClosing(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (!(e.getSource() instanceof JMenuItem))
			return;

		JMenuItem menuItem = (JMenuItem) e.getSource();
		menuAction(menuItem);	
	}

	/**
	 * Singleton for single LogisticsMenu.
	 * @return instance of logisticsMenu
	 */
	public static LogisticsMenu getInstance()
	{
		if (instance == null)
			instance = new LogisticsMenu();
		return instance;
	}

}
