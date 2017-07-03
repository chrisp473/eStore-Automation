package coop.digital.eStores.testAutomation.pages;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class orderConfirmationPage extends eStoresPage{

	//*** PAGE TITLE ***
	private static String pageTitle = "Order Confirmation Page";
	
	//*** PAGE ELEMENTS ***
	
	//*** PAGE METHODS ***
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
}
