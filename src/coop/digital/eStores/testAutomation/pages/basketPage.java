package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class basketPage extends eStoresPage{

	//*** PAGE TITLE ***
	private static final String pageTitle = "Your Basket";
	
	//*** PAGE ELEMENTS ***
	public static final ElementProperties continueToCheckout_Button = new ElementProperties(By.xpath("//a[text()='Continue to Checkout']"),"Continue to Checkout Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties productOne = new ElementProperties(By.xpath("//div/div/figure/a[contains(@title, '"+ TestHelper.getTestDataValue("ItemID") +"')]"),"Product One",ElementTypes.TEXTBOX,Constants.BLANK_VALUE);
	public static final ElementProperties productTwo = new ElementProperties(By.xpath("//div/div/figure/a[contains(@title, '"+ TestHelper.getTestDataValue("ItemID_II") +"')]"),"Product Two",ElementTypes.TEXTBOX,Constants.BLANK_VALUE);
		
	//*** PAGE METHODS***
	
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
	public static void assertProducts() throws Exception{
		CoreAssertions.assertElementIsDisplayed(productOne);
		CoreAssertions.assertElementIsDisplayed(productTwo);
	}
}
