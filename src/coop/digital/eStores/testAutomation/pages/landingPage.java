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
	public static final ElementProperties membership_Login = new ElementProperties(By.xpath("//a/span[contains(@class,'card-label')]"), "Membership Sign In", ElementTypes.BUTTON);
	public static final ElementProperties membership_Input = new ElementProperties(By.xpath("//*[@id='memberNumberTextbox']"), "Membership Input Box", ElementTypes.TEXTBOX);
	public static final ElementProperties membership_Enter = new ElementProperties(By.xpath("//form/a[contains(@ng-click,'CheckHeaderMembershipNumber')]"), "Membership Sign In", ElementTypes.BUTTON);
	public static final ElementProperties membership_Customer = new ElementProperties(By.xpath("//div/div[contains(@class,'text-center ng-binding')]"),"Membership Number Textbox", ElementTypes.PAGETEXT);
	public static final ElementProperties membership_Logout = new ElementProperties(By.xpath("//div/a[contains(@ng-click,'RemoveMembershipCookie')]"), "Membership Logout", ElementTypes.BUTTON);
	public static final ElementProperties ElementName = new ElementProperties(By.xpath("//input[contains(@ng-change, 'doSearch')]"), "Elemnts Name", ElementTypes.TEXTBOX);
	
	//*** PAGE METHODS***
	public static void checkPageTitle() throws Exception {
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
	public static void checkCurrentlySignedIn(String CardNumber) throws Exception {
		CoreAssertions.assertElementTextEquals(membership_Customer, "633174"+CardNumber);
	}
	
	public static void checkCustomerIsLoggedOut() throws Exception {
		CoreAssertions.assertElementIsDisplayed(membership_Input);
	}
}
