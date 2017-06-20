package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class productPage extends eStoresPage {

	//*** PAGE TITLE ***
	private static String pageTitle = "";//different dependent on product
	
	//*** PAGE ELEMENTS ***
	public static final ElementProperties addToBasket_Button = new ElementProperties(By.xpath("//*[@role='button' and text()='Add To Basket']"),"Add to Basket Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties checkout_Button = new ElementProperties(By.xpath("//div[@id='addedToBasketMessage']//a[@href='/basket-page/']"),"Checkout Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);

	public static final ElementProperties viewBasket_Button = new ElementProperties(By.xpath("//span[contains(text(),'View Basket')]"),"View Basket Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//*** PAGE METHODS***
	
	public static void checkPageTitle(String fullProductName) throws Exception{
		pageTitle = fullProductName+" | Co-op Electrical";
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
}
