package coop.digital.eStores.testAutomation.tests;

import org.testng.annotations.Test;

import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.pages.landingPage;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;
import coop.digital.eStores.testAutomation.superTest.eStoresBaseTest;

public class MembershipTest extends eStoresBaseTest{

	@Test(groups = { "Membership Log In Test" }, invocationCount=1)
	public void testSteps() throws Exception{
		try{
			landingPage.checkPageTitle();
			eStoresPage.takeScreenshot();
			
			landingPage.membership_Login.click();
			landingPage.membership_Input.inputText(TestHelper.getTestDataValue("Membership"));
			landingPage.membership_Enter.click();

			landingPage.checkPageTitle(); // Ensure we're back at homepage. 

			landingPage.membership_Login.click();
			landingPage.checkCurrentlySignedIn(TestHelper.getTestDataValue("Membership"));
			landingPage.membership_Logout.click();

			landingPage.checkPageTitle();
			landingPage.membership_Login.click();
			landingPage.checkCustomerIsLoggedOut();
			
			eStoresPage.takeScreenshot();
		} catch (Throwable e) {
			TestHelper.recordFailedTest(e);
		}
	}
}
