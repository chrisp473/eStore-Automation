package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;

public class payPalCheckoutPage {

	//*** PAGE TITLE ***
	private static String pageTitle = "PayPal Checkout - Review your payment";

	//*** PAGE ELEMENTS ***
	public static final ElementProperties  payNow_Button = new ElementProperties(By.id("confirmButtonTop"), "Pay Now Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//*** PAGE METHODS ***
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
}
