package coop.digital.eStores.testAutomation.pages;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;

public class payPalLoginPage {
	
	//*** PAGE TITLE ***
	private static String pageTitle = "PayPal Checkout - Log In";

	//*** PAGE ELEMENTS ***
	public static final ElementProperties  logIn_Button = new ElementProperties(By.xpath("//button[text()='Log in']"), "Log in Button", ElementTypes.BUTTON,"//iframe[@name='injectedUl']");
	public static final ElementProperties  emailInput_Input = new ElementProperties(By.xpath("//div[@id='login_emaildiv']//input"), "Email input", ElementTypes.TEXTBOX,"//iframe[@name='injectedUl']");
	public static final ElementProperties  passwordInput_Input = new ElementProperties(By.xpath("//div[@id='login_passworddiv']//input"), "Password input", ElementTypes.TEXTBOX,"//iframe[@name='injectedUl']");
	
	//*** PAGE METHODS ***
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}

}
