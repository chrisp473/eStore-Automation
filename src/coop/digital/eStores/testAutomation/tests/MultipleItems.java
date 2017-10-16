package coop.digital.eStores.testAutomation.tests;

import java.util.Calendar;

import org.testng.annotations.Test;

import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.pages.basketPage;
import coop.digital.eStores.testAutomation.pages.checkoutPage;
import coop.digital.eStores.testAutomation.pages.landingPage;
import coop.digital.eStores.testAutomation.pages.productPage;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;
import coop.digital.eStores.testAutomation.superTest.eStoresBaseTest;

public class MultipleItems extends eStoresBaseTest{
	
	@Test(groups = { "PurchaseMultipleItems" }, invocationCount=1)
	public void testSteps() throws Exception{ 
		try{
			landingPage.checkPageTitle();
			eStoresPage.takeScreenshot();
		
			landingPage.searchBox_Input.inputText(TestHelper.getTestDataValue("ItemID"));
			landingPage.search_Button.click();
			eStoresPage.takeScreenshot();
			
			productPage.addToBasket_Button.click();
			productPage.checkout_Button.click();
			eStoresPage.takeScreenshot();
			
			basketPage.checkPageTitle();
			basketPage.continueToCheckout_Button.click();
			eStoresPage.takeScreenshot();

			checkoutPage.checkPageTitle();
			checkoutPage.inputPostCode();
			checkoutPage.address_Text.click();
			checkoutPage.validateSelectedAddress();
			checkoutPage.title_DropDown.selectOption();
			checkoutPage.firstName_Input.input();
			checkoutPage.lastName_Input.input();
			checkoutPage.contactNumber_Input.input();
			checkoutPage.email_Input.input();
			eStoresPage.takeScreenshot();
			checkoutPage.confirmDeliveryDetails_Button.click();
			
			checkoutPage.selectDeliveryDate(eStoresPage.getNextDay(Calendar.MONDAY, 7));
			eStoresPage.takeScreenshot();
			checkoutPage.confirmDeliveryDate_Button.click();

			Thread.sleep(1000);
			checkoutPage.clickTCsCheckbox();
			eStoresPage.takeScreenshot();
			checkoutPage.payForItems_Button.click();
			
			checkoutPage.noProceedToPayment_Button.click();
			
			// TODO complete these steps if on staging. 
			
//			checkoutPage.payAndComplete_Button.click();
//			Thread.sleep(10000);
//			orderConfirmationPage.checkPageTitle();
//			orderConfirmationPage.validateOrderHeaderDetails();
//			orderConfirmationPage.outputOrderNumber();
//			orderConfirmationPage.validateOrderDetails();
			
			eStoresPage.takeScreenshot();
		} catch (Throwable e) {
			TestHelper.recordFailedTest(e);
		}
	}
}
