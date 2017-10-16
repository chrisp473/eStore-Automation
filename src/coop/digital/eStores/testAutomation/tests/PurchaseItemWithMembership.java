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

public class PurchaseItemWithMembership extends eStoresBaseTest{

	@Test(groups = { "PurchaseItemWithMembership" }, invocationCount=1)
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
			
			checkoutPage.yesEnterDetails_Button.click();
			checkoutPage.memberNumberInput.inputText(TestHelper.getTestDataValue("CardNumber"));
			checkoutPage.memberNumberSubmit.click();
			checkoutPage.yesCheckMyDetails_Button.click();
			checkoutPage.membershipDisplayMemberPoints.click();
			checkoutPage.membershipAmount.inputText("500.21");
			checkoutPage.assertError();
			
			// £0.01 for Live, need to split if for staging so it makes a complete payment TODO
			checkoutPage.membershipAmount.inputText("0.01");
			checkoutPage.membershipUpdate.click();
			checkoutPage.assertSuccess();
			checkoutPage.payRemainingBalance.click();
			checkoutPage.assertMembershipSpend();
			
			// TODO Staging test and complete payment.
			
			eStoresPage.takeScreenshot();
		} catch (Throwable e) {
			TestHelper.recordFailedTest(e);
		}
	}
}
