package main;

import session.Session;
import models.ConnectionGateway;
import models.InventoryModel;
import models.ItemConnectionGateway;
import models.PartsModel;
import models.ProductTemplatePartsGateway;
import models.TemplateGateway;
import controllers.InventoryController;
import controllers.LoginController;
import controllers.TemplateController;


/**
 * The purpose of this software is to generate an inventory that can be added to
 * and deleted from, as well as giving the user the ability to edit an item in the inventory.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class Main
{
	public static Session session = null;

    public static void main(String[] args)
    {
    	LoginController loginController = new LoginController(session);
    	
    	
    }
}
