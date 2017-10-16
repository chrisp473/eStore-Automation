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
	public static final ElementProperties continueToCheckoutWithWarranties_Button = new ElementProperties(By.xpath("//button[text()='Continue to Checkout']"),"Continue to Checkout Button",ElementTypes.BUTTON,Constants.BLANK_VALUE);

	public static final ElementProperties productOne = new ElementProperties(By.xpath("//div/div/figure/a[contains(@title, '"+ TestHelper.getTestDataValue("ItemID") +"')]"),"Product One",ElementTypes.TEXTBOX,Constants.BLANK_VALUE);
	public static final ElementProperties productTwo = new ElementProperties(By.xpath("//div/div/figure/a[contains(@title, '"+ TestHelper.getTestDataValue("ItemID_II") +"')]"),"Product Two",ElementTypes.TEXTBOX,Constants.BLANK_VALUE);
	public static final ElementProperties connection_Service = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][1]/div/label"), "Connection Service Checkbox", ElementTypes.CHECKBOX);
	public static final ElementProperties removal_Service = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][2]/div/label"), "Removal Service Checkbox", ElementTypes.CHECKBOX);
	public static final ElementProperties warranty_Service = new ElementProperties(By.xpath("//div[contains(@ng-if,'item.Warranties')]/div/div[contains(@class, 'equalize-this')]/div/div[3]/div/label"), "Warranty Service Radiobutton", ElementTypes.RADIOBUTTON);

	public static final ElementProperties connetionService_Value = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][1]/div[contains(@class, 'text-right')]/strong"), "Connection Service Value", ElementTypes.PAGETEXT);
	public static final ElementProperties removalService_Value = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][2]/div[contains(@class, 'text-right')]/strong"), "Removal Service Value", ElementTypes.PAGETEXT);
	public static final ElementProperties warrantyService_Value = new ElementProperties(By.xpath("//div[contains(@ng-if,'item.Warranties')]/div/div[contains(@class, 'equalize-this')]/div/div[3]/div[contains(@class,'text-right')]/strong"), "Warranty Service Value", ElementTypes.PAGETEXT);

	public static final ElementProperties connectionService_Remove = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][1]/div/a"), "Connection Service Remove", ElementTypes.BUTTON);
	public static final ElementProperties removalService_Remove = new ElementProperties(By.xpath("//div[contains(@ng-id,'item.Services')]/div/div/div/div/div[contains(@class, 'checkbox')][2]/div/a"), "Removal Service Remove", ElementTypes.BUTTON);
	public static final ElementProperties warrantyService_Remove = new ElementProperties(By.xpath("//div[contains(@ng-if,'item.Warranties')]/div/div[contains(@class, 'equalize-this')]/div/div[3]/div/a"), "Warranty Service Remove", ElementTypes.BUTTON);

	public static final ElementProperties removeItem_Button = new ElementProperties(By.xpath("//button[contains(@ng-click, 'removeSelectedItem')]"), "Remove Service Button", ElementTypes.BUTTON);
	public static final ElementProperties acceptWarranties_Button = new ElementProperties(By.xpath("//button[contains(@ng-click, 'AcceptWarrantyTermsAndConditions')]"), "Accept Warranties Button", ElementTypes.BUTTON);

	// Voucher Elements
	public static final ElementProperties voucherInput_Textbox = new ElementProperties(By.xpath("//form[contains(@ng-submit,'AddCoupon()')]/div/input"), "Voucher Input Field", ElementTypes.TEXTBOX);
	public static final ElementProperties voucherSubmit_Button = new ElementProperties(By.xpath("//form[contains(@ng-submit,'AddCoupon()')]/div/span/button"), "Voucher Submit Button", ElementTypes.BUTTON);
	public static final ElementProperties voucherInvalidCode_Text = new ElementProperties(By.xpath("//div[contains(@ng-if,'InvalidCoupon')]"), "Invalid Code Textbox", ElementTypes.PAGETEXT);
	public static final ElementProperties voucherInvalidDismiss = new ElementProperties(By.xpath("//a[contains(@ng-click,'DismissInvalid()')]/i"), "Dismiss Invalid Code Button", ElementTypes.BUTTON);
	
	//*** PAGE METHODS***
	
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
	public static void assertInvalidCode() throws Exception{
		CoreAssertions.assertElementIsDisplayed(voucherInvalidCode_Text);
	}
	
	public static void assertErrorDismissed() throws Exception{
		CoreAssertions.assertElementIsNotDisplayed(voucherInvalidCode_Text);
	}

	public static void assertProducts() throws Exception{
		CoreAssertions.assertElementIsDisplayed(productOne);
		CoreAssertions.assertElementIsDisplayed(productTwo);
	}
	
	public static void assertServices() throws Exception{
		CoreAssertions.assertElementIsDisplayed(connetionService_Value);
		CoreAssertions.assertElementIsDisplayed(removalService_Value);
		CoreAssertions.assertElementIsDisplayed(warrantyService_Value);
	}
	
	public static void assertServicesAreRemoved() throws Exception{
		CoreAssertions.assertElementIsNotDisplayed(connetionService_Value);
		CoreAssertions.assertElementIsNotDisplayed(removalService_Value);
		CoreAssertions.assertElementIsNotDisplayed(warrantyService_Value);
	}
}
