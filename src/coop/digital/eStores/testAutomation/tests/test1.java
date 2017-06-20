package coop.digital.eStores.testAutomation.tests;

import org.testng.annotations.Test;

import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.pages.basketPage;
import coop.digital.eStores.testAutomation.pages.checkoutPage;
import coop.digital.eStores.testAutomation.pages.landingPage;
import coop.digital.eStores.testAutomation.pages.productPage;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;
import coop.digital.eStores.testAutomation.superTest.eStoresBaseTest;

public class test1 extends eStoresBaseTest{

	@Test(invocationCount=1)
	public void testSteps() throws Exception{
		try{
			landingPage.checkPageTitle();
			eStoresPage.takeScreenshot();
		
			landingPage.searchBox_Input.inputText(TestHelper.getTestDataValue("ItemID"));
			landingPage.search_Button.click();
			eStoresPage.takeScreenshot();
			
			productPage.addToBasket_Button.click();
			productPage.waitSeconds(2);
			productPage.checkout_Button.click();
			eStoresPage.takeScreenshot();
			
			basketPage.checkPageTitle();
			basketPage.continueToCheckout_Button.click();
			eStoresPage.takeScreenshot();

			checkoutPage.checkPageTitle();
			checkoutPage.inputPostCode();
			checkoutPage.address_Text.click();
			checkoutPage.validateSelectedAddress();
			checkoutPage.title_DropDown.selectOption("Mr");
			checkoutPage.firstName_Input.input();
			checkoutPage.lastName_Input.input();
			checkoutPage.contactNumber_Input.input();
			checkoutPage.email_Input.input();
			eStoresPage.takeScreenshot();
			checkoutPage.confirmDeliveryDetails_Button.click();
			
			checkoutPage.selectDeliveryDate("23/06/2017");
			eStoresPage.takeScreenshot();
			checkoutPage.confirmDeliveryDate_Button.click();
			
			checkoutPage.clickTCsCheckbox();
			eStoresPage.takeScreenshot();
			checkoutPage.payForItems_Button.click();
			
			checkoutPage.noProceedToPayment_Button.click();
			
			checkoutPage.nameOnCard_Input.inputText("Test");
			checkoutPage.cardNumber_Input.inputText("511111111111111 8");
			checkoutPage.cardCCVNumber_Input.inputText("202");
			checkoutPage.cardExpiryMonth_DropDown.selectOption("5");
			checkoutPage.cardExpiryYear_DropDown.selectOption("2021");
			checkoutPage.payAndComplete_Button.click();
			
			eStoresPage.takeScreenshot();
			
			eStoresPage.waitSeconds(10);//need to verify success page

			
		
		} catch (Throwable e) {
			TestHelper.recordFailedTest(e);
		}
	}
}
