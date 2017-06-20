package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class landingPage extends eStoresPage{

	//*** PAGE TITLE ***
	private static final String pageTitle = "Co-op Electrical: Buy Electrical Appliances at Lowest Prices";
	
	//*** PAGE ELEMENTS ***
	public static final ElementProperties searchBox_Input = new ElementProperties(By.id("oneBoxSearch"),"Search Box",ElementTypes.TEXTBOX);
	public static final ElementProperties search_Button = new ElementProperties(By.xpath("//*[@id='oneBoxSearch']//..//button"),"Search Button",ElementTypes.BUTTON);
	
	//*** PAGE METHODS***
	
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
}
