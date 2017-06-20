package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class basketPage extends eStoresPage{

	//*** PAGE TITLE ***
	private static final String pageTitle = "Your Basket";
	
	//*** PAGE ELEMENTS ***
	public static final ElementProperties continueToCheckout_Button = new ElementProperties(By.xpath("//a[text()='Continue to Checkout']"),"Continue to Checkout Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//*** PAGE METHODS***
	
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
}
