package coop.digital.eStores.testAutomation.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;

public class orderConfirmationPage extends eStoresPage{

	//*** PAGE TITLE ***
	private static String pageTitle = "Order Confirmation Page";
	
	//*** THANK YOU TEXT ***
	private static String thankYou_Text = "Thank you for your order!";
	
	//***Order Date
	private static String orderDateFormat = "EEEEEEEEE, MMMMMMMMMM d, yyyy";
	
	//*** PAGE ELEMENTS ***
	
	//order header
	public static final ElementProperties  thankYouText_Text= new ElementProperties(By.xpath("//h1"),"Thank You text",ElementTypes.PAGETEXT,Constants.BLANK_VALUE);
	public static final ElementProperties  orderNumber_Text= new ElementProperties(By.xpath("//li[contains(text(),'Order No: STG-')]"),"Order Number text",ElementTypes.PAGETEXT,"OrderNumber",Constants.BLANK_VALUE);
	public static final ElementProperties  orderDate_Text= new ElementProperties(By.xpath("//li[contains(text(),'Order Date: ')]"),"Order Date text",ElementTypes.PAGETEXT,Constants.BLANK_VALUE);
	
	//your details
	public static final ElementProperties  title_Text= new ElementProperties(By.xpath("//div[contains(text(),'Your Details')]//..//dd[1]"),"Your details - Title text",ElementTypes.PAGETEXT,"Title",Constants.BLANK_VALUE);
	public static final ElementProperties  firstName_Text= new ElementProperties(By.xpath("//div[contains(text(),'Your Details')]//..//dd[2]"),"Your details - First Name text",ElementTypes.PAGETEXT,"FirstName",Constants.BLANK_VALUE);
	public static final ElementProperties  lastName_Text= new ElementProperties(By.xpath("//div[contains(text(),'Your Details')]//..//dd[3]"),"Your details - Last Name text",ElementTypes.PAGETEXT,"LastName",Constants.BLANK_VALUE);
	public static final ElementProperties  contactNumber_Text= new ElementProperties(By.xpath("//div[contains(text(),'Your Details')]//..//dd[4]"),"Your details - Contact Number text",ElementTypes.PAGETEXT,"ContactNumber",Constants.BLANK_VALUE);
	public static final ElementProperties  email_Text= new ElementProperties(By.xpath("//div[contains(text(),'Your Details')]//..//dd[5]"),"Your details - email text",ElementTypes.PAGETEXT,"Email",Constants.BLANK_VALUE);
	
	//delivery details
	public static final ElementProperties  deliveryDate_Text= new ElementProperties(By.xpath("//div[contains(text(),'Delivery Details')]//..//dl[1]//dd[1]"),"Delivery details - Delivery Date text",ElementTypes.PAGETEXT,"SelectedDeliveryDate",Constants.BLANK_VALUE);
	public static final ElementProperties  addressLine1_Text= new ElementProperties(By.xpath("//div[contains(text(),'Delivery Details')]//..//dl[2]//dd[1]"),"Delivery details - Address Line 1 text",ElementTypes.PAGETEXT,"AddressLine1",Constants.BLANK_VALUE);
	public static final ElementProperties  addressLine2_Text= new ElementProperties(By.xpath("//div[contains(text(),'Delivery Details')]//..//dl[2]//dd[2]"),"Delivery details - Address Line 2 text",ElementTypes.PAGETEXT,"AddressLine2",Constants.BLANK_VALUE);
	//looks like address line 3 not included here
	public static final ElementProperties  addressLine4_Text= new ElementProperties(By.xpath("//div[contains(text(),'Delivery Details')]//..//dl[2]//dd[3]"),"Delivery details - Address Line 4 text",ElementTypes.PAGETEXT,"AddressLine4",Constants.BLANK_VALUE);
	public static final ElementProperties  postCode_Text= new ElementProperties(By.xpath("//div[contains(text(),'Delivery Details')]//..//dl[2]//dd[4]"),"Delivery details - Post Code text",ElementTypes.PAGETEXT,"PostCode",Constants.BLANK_VALUE);
	
	//payment details
	//delivery details
	public static final ElementProperties  paymentMethod_Text= new ElementProperties(By.xpath("//div[contains(text(),'Payment Details')]//..//dl[1]//dd[1]"),"Payment details - Payment Method text",ElementTypes.PAGETEXT,"PaymentMethod",Constants.BLANK_VALUE);
	public static final ElementProperties  billingAddressLine1_Text= new ElementProperties(By.xpath("//div[contains(text(),'Payment Details')]//..//dl[2]//dd[1]"),"Payment details - Address Line 1 text",ElementTypes.PAGETEXT,"BillingAddressLine1",Constants.BLANK_VALUE);
	public static final ElementProperties  billingAddressLine2_Text= new ElementProperties(By.xpath("//div[contains(text(),'Payment Details')]//..//dl[2]//dd[2]"),"Payment details - Address Line 2 text",ElementTypes.PAGETEXT,"BillingAddressLine2",Constants.BLANK_VALUE);
	//looks like address line 3 not included here
	public static final ElementProperties  billingAddressLine4_Text= new ElementProperties(By.xpath("//div[contains(text(),'Payment Details')]//..//dl[2]//dd[3]"),"Payment details - Address Line 4 text",ElementTypes.PAGETEXT,"BillingAddressLine4",Constants.BLANK_VALUE);
	public static final ElementProperties  billingPostCode_Text= new ElementProperties(By.xpath("//div[contains(text(),'Payment Details')]//..//dl[2]//dd[4]"),"Payment details - Post Code text",ElementTypes.PAGETEXT,"BillingPostCode",Constants.BLANK_VALUE);
	
	//*** PAGE METHODS ***
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
	public static void validateOrderHeaderDetails() throws Exception{
		//checks for Thank You message, order date in expected format, and order number
		
		thankYouText_Text.assertElementIsDisplayed();
		thankYouText_Text.assertElementTextEquals(thankYou_Text);
		
		orderNumber_Text.assertElementIsDisplayed();
		orderNumber_Text.assertElementTextMatchesRegEx("Order No: STG-\\d\\d\\d\\d");
		
		orderDate_Text.assertElementIsDisplayed();
		orderDate_Text.assertElementTextEquals("Order Date: "+TestHelper.getDateAsString(Calendar.getInstance().getTime(), orderDateFormat));
		
	}
	
	public static void validateOrderDetails() throws Exception{
		//check your details, delivery details and payment details sections
		
		//your details
		title_Text.assertElementTextMatchesDataTable();
		firstName_Text.assertElementTextMatchesDataTable();
		lastName_Text.assertElementTextMatchesDataTable();
		contactNumber_Text.assertElementTextMatchesDataTable();
		email_Text.assertElementTextMatchesDataTable();
		
		//delivery details
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		deliveryDate_Text.assertElementTextEquals(TestHelper.getDateAsString(df.parse(deliveryDate_Text.getDataValue()), orderDateFormat));
		addressLine1_Text.assertElementTextMatchesDataTable();
		addressLine2_Text.assertElementTextMatchesDataTable();
		addressLine4_Text.assertElementTextMatchesDataTable();
		postCode_Text.assertElementTextMatchesDataTable();
		
		paymentMethod_Text.assertElementTextMatchesDataTable();
		billingAddressLine1_Text.assertElementTextMatchesDataTable();
		billingAddressLine2_Text.assertElementTextMatchesDataTable();
		billingAddressLine4_Text.assertElementTextMatchesDataTable();
		billingPostCode_Text.assertElementTextMatchesDataTable();
	}
}
