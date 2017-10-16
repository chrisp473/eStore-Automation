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

public class PurchaseWithWarranty extends eStoresBaseTest{

	@Test(groups = { "SmokeTest" }, invocationCount=1)
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
			
			basketPage.connection_Service.click();
			basketPage.removal_Service.click();
			basketPage.warranty_Service.click();
			
			Thread.sleep(2500);
			basketPage.assertServices();
			
			basketPage.connectionService_Remove.click();
			basketPage.removeItem_Button.click();
			
			basketPage.removalService_Remove.click();
			basketPage.removeItem_Button.click();
			
			basketPage.warrantyService_Remove.click();
			basketPage.removeItem_Button.click();
			
			basketPage.assertServicesAreRemoved();
			
			basketPage.connection_Service.click();
			basketPage.removal_Service.click();
			basketPage.warranty_Service.click();
			
			basketPage.assertServices();
			
			basketPage.continueToCheckoutWithWarranties_Button.click();
			basketPage.acceptWarranties_Button.click();
			
			eStoresPage.takeScreenshot();

			checkoutPage.checkPageTitle();
			checkoutPage.assertServices();
			
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
			
			System.out.print(eStoresPage.getNextDay(Calendar.MONDAY, 7));
			checkoutPage.selectDeliveryDate(eStoresPage.getNextDay(Calendar.MONDAY, 7));
			eStoresPage.takeScreenshot();
			checkoutPage.confirmDeliveryDate_Button.click();

			Thread.sleep(1000);
			checkoutPage.clickTCsCheckbox();
			eStoresPage.takeScreenshot();
			checkoutPage.payForItems_Button.click();
			
			checkoutPage.noProceedToPayment_Button.click();
			
			checkoutPage.nameOnCard_Input.inputText();
			checkoutPage.cardNumber_Input.inputText();
			checkoutPage.cardCCVNumber_Input.inputText();
			checkoutPage.cardExpiryMonth_DropDown.selectOption();
			checkoutPage.cardExpiryYear_DropDown.selectOption();
			
			// TODO complete these steps if on staging. 
			
			eStoresPage.takeScreenshot();
		} catch (Throwable e) {
			TestHelper.recordFailedTest(e);
		}
	}
}
