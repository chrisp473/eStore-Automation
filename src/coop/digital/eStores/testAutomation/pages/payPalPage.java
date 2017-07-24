package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;

public class payPalPage {
	
	//*** PAGE TITLE ***
	private static String pageTitle = "PayPal Checkout - Create a PayPal account.";

	//*** PAGE ELEMENTS ***
	public static final ElementProperties  logIn_Button = new ElementProperties(By.xpath("//a[text()='Log In']"), "Log in Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//*** PAGE METHODS ***
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}

}
