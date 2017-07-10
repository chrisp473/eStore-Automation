package coop.digital.eStores.testAutomation.pages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreAssertions;
import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.constants.ElementTypes;
import coop.digital.eStores.testAutomation.helpers.BrowserHelper;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.superPage.eStoresPage;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestLogger;

public class checkoutPage extends eStoresPage{
	
	private static List<String> visibleDeliveryDates;

	//*** PAGE TITLE ***-----------------------------------------------
	private static final String pageTitle = "Checkout Page";
	//------------------------------------------------------------------
	
	//*** PAGE ELEMENTS ***--------------------------------------------
	
	//Delivery Details
	public static final ElementProperties  postCode_Input= new ElementProperties(By.id("inputPCAnywhere"),"Post Code Input",ElementTypes.TEXTBOX,"PostCode",Constants.BLANK_VALUE);
	public static final ElementProperties  address_Text= new ElementProperties(By.xpath("//div[contains(@class,'pcaitem')]"),"PCA Address Text",ElementTypes.PAGETEXT,Constants.BLANK_VALUE);
	public static final ElementProperties  selectedAddress_Text= new ElementProperties(By.id("AddressAreaSingleField"),"Selected Address Text",ElementTypes.PAGETEXT,Constants.BLANK_VALUE);
	public static final ElementProperties  title_DropDown = new ElementProperties(By.name("deliverySalutation"), "Title Drop Down", ElementTypes.DROPDOWN, "Title",Constants.BLANK_VALUE);
	public static final ElementProperties  firstName_Input= new ElementProperties(By.id("deliveryFName"),"First Name Input",ElementTypes.TEXTBOX,"FirstName",Constants.BLANK_VALUE);
	public static final ElementProperties  lastName_Input= new ElementProperties(By.id("deliveryLName"),"Last Name Input",ElementTypes.TEXTBOX,"LastName",Constants.BLANK_VALUE);
	public static final ElementProperties  contactNumber_Input= new ElementProperties(By.id("deliveryTel"),"Contact Number Input",ElementTypes.TEXTBOX,"ContactNumber",Constants.BLANK_VALUE);
	public static final ElementProperties  email_Input= new ElementProperties(By.id("deliveryEmail"),"Email Input",ElementTypes.TEXTBOX,"Email",Constants.BLANK_VALUE);
	public static final ElementProperties  deliveryInstructions_DropDown = new ElementProperties(By.name("deliveryInstructions"), "Delivery Instructions Drop Down", ElementTypes.DROPDOWN, "DeliveryInstructions",Constants.BLANK_VALUE);
	public static final ElementProperties  confirmDeliveryDetails_Button = new ElementProperties(By.xpath("//button[@title='Confirm delivery details']"), "Confirm Delivery Details Button", ElementTypes.BUTTON, Constants.BLANK_VALUE);
	
	//Delivery Date
	public static final ElementProperties  firstDeliveryDateInDisplay_Button = new ElementProperties(By.xpath("(//li[@aria-hidden='false']//input[@id='availableDate'])[1]//.."), "First Visible Delivery Date Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  lastDeliveryDateInDisplay_Button = new ElementProperties(By.xpath("(//li[@aria-hidden='false']//input[@id='availableDate'])[last()]//.."), "Last Visible Delivery Date Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  deliveryDateInDisplay_Button = new ElementProperties(By.xpath("//li[@aria-hidden='false']//input[@id='availableDate']//.."), "Visible Delivery Date Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  deliveryDateOutOfDisplay_Button = new ElementProperties(By.xpath("//li[@aria-hidden='true']//input[@id='availableDate']//.."), "Delivery Date Button out of display", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  deliveryDateScrollNext_Button = new ElementProperties(By.xpath("//div[@id='checkout-step-2' and @aria-expanded='true']//button[@aria-label='Next']"), "Delivery Date Scroll Right Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  confirmDeliveryDate_Button = new ElementProperties(By.xpath("//button[@id='reviewtcbutton' and @ng-href='#checkout-step-3']"), "Confirm Delivery Date Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//Terms and Conditions
	public static final ElementProperties  termsAndConditions_Checkbox = new ElementProperties(By.xpath("//input[@id='terms']//..//label"), "Terms and Conditions Checkbox", ElementTypes.CHECKBOX,Constants.BLANK_VALUE);
	public static final ElementProperties  payForItems_Button = new ElementProperties(By.id("payforitems"), "Pay for Items Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//Membership Details
	public static final ElementProperties  noProceedToPayment_Button = new ElementProperties(By.xpath("//div[@id='checkout-step-4' and @aria-expanded='true']//a[@role='button' and contains(text(),'No, proceed to payment')]"), "Membership - No Proceed To Payment Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	public static final ElementProperties  yesEnterDetails_Button = new ElementProperties(By.xpath("//a[@role='button' and contains(text(),'Yes, enter details')]"), "Membership - Yes Enter Details Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//Pay for your Items
	public static final ElementProperties  nameOnCard_Input = new ElementProperties(By.id("tnsiNameOnCard"), "Name on Card Input", ElementTypes.TEXTBOX,"NameOnCard",Constants.BLANK_VALUE);
	public static final ElementProperties  cardNumber_Input = new ElementProperties(By.xpath("//input"), "Card Number Input", ElementTypes.TEXTBOX,"CardNumber","//iframe[@class='gw-proxy-cardNumber']");
	public static final ElementProperties  cardCCVNumber_Input = new ElementProperties(By.xpath("//input"), "Card CCV Number Input", ElementTypes.TEXTBOX,"CCVNumber","//iframe[@class='gw-proxy-securityCode']");
	public static final ElementProperties  cardExpiryMonth_DropDown = new ElementProperties(By.id("expiry-month"), "Card Expiry Month Drop Down", ElementTypes.DROPDOWN, "CardExpiryMonth",Constants.BLANK_VALUE);
	public static final ElementProperties  cardExpiryYear_DropDown = new ElementProperties(By.id("expiry-year"), "Card Expiry Year Drop Down", ElementTypes.DROPDOWN, "CardExpiryYear",Constants.BLANK_VALUE);
	public static final ElementProperties  payAndComplete_Button = new ElementProperties(By.id("tnsiSubmit"), "Pay and Complete Button", ElementTypes.BUTTON,Constants.BLANK_VALUE);
	
	//------------------------------------------------------------------
	
	//*** PAGE METHODS***
	
	public static void checkPageTitle() throws Exception{
		CoreAssertions.assertPageTitleEquals(pageTitle);
	}
	
	public static void inputPostCode() throws Exception{

		try
		{
			String logMessage = String.format("Inputting Postcode");
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			String postCode = postCode_Input.getDataValue();
			String houseNumber = TestHelper.getTestDataValue("HouseNumber");
			
			String[] postCodeInput = (postCode+", "+houseNumber).split("");
//			String inputString = postCodeInput[0];
			WebElement postCodeInputBox = getWebElement(postCode_Input);
			
			for(int i=0;i<postCodeInput.length;i++){
//				inputString += postCodeInput[i];
//				getWebElement(postCode_Input).sendKeys(inputString);
				postCodeInputBox.sendKeys(postCodeInput[i]);
				Thread.sleep(500);
			}
			waitSeconds(2);
			TestHelper.incrementStepCount();
		}
		catch(AssertionError|Exception e){
			TestHelper.handleExceptionNoRetry(e.getMessage());
		}
	}
	
	
	public static void validateSelectedAddress() throws Exception{

		try
		{
			String logMessage = String.format("Validating selected address");
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			String postCode = postCode_Input.getDataValue();
			String address1 = TestHelper.getTestDataValue("AddressLine1");
			String address2 = TestHelper.getTestDataValue("AddressLine2");
			String address3 = TestHelper.getTestDataValue("AddressLine3");
			String address4 = TestHelper.getTestDataValue("AddressLine4");
			
//			String addressString = (address1+"\n"+address2+"\n"+address3+"\n"+address4+"\n"+postCode).trim();
			
			String addressString = address1;
			if (address2.trim() !="") addressString+="\n"+address2;
			if (address3.trim() !="") addressString+="\n"+address3;
			if (address4.trim() !="") addressString+="\n"+address4;
			addressString+="\n"+postCode;
			WebElement addressText = getWebElement(selectedAddress_Text);
			
			assertThat(addressText.getText().trim(),equalTo(addressString.trim()));
			TestHelper.incrementStepCount();
		}
		catch(AssertionError|Exception e){
			TestHelper.handleExceptionNoRetry(e.getMessage());
		}
	}
	
	public static void selectDeliveryDate(String deliveryDate) throws Exception{

		try
		{
			boolean dateFound = false;
			// may want to add case for special delivery
			String logMessage = String.format("Selecting delivery date");
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			//set first expected date (tomorrow)
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 1);
			Date firstValidDate = c.getTime();
			
			//check date passed is not before tomorrow
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date delDate = df.parse(deliveryDate);
			
			
			if (delDate.before(firstValidDate)){
				throw new Exception ("Delivery date selected is before first available");
			}
			// else search for date including scrolling right
			else{
				String lastVisibleDate="";
				String lastVisibleDateAfter="";
//				while (deliveryDateScrollNext_Button.getWebElement().isEnabled()){
				do{
				
					lastVisibleDate = lastDeliveryDateInDisplay_Button.getElementText().trim().substring(0,5)+"/"+c.get(Calendar.YEAR);
					
					Date lastVisibleDateD = df.parse(lastVisibleDate);
					
					if (lastVisibleDateD.after(delDate) || lastVisibleDate.equals(delDate)){
						for (WebElement date: deliveryDateInDisplay_Button.getWebElements()){
							if ((date.getText().trim().substring(0,5)+"/"+c.get(Calendar.YEAR)).equals(deliveryDate)){
								date.click();
								dateFound = true;
								break;
							}
						}
						assertThat(dateFound,equalTo(true));
					}
					else{
						deliveryDateScrollNext_Button.click();
					}
					
					if(dateFound) break;
					
					lastVisibleDateAfter = lastDeliveryDateInDisplay_Button.getElementText().trim().substring(0,5)+"/"+c.get(Calendar.YEAR);
					
				}while(!lastVisibleDate.equals(lastVisibleDateAfter));
			}
			
			
//			assertThat(dateFound,equalTo(true));
			if (!dateFound){
				throw new Exception("Delivery date "+deliveryDate+" not found");
			}
			TestHelper.incrementStepCount();
//			waitSeconds(1);
		}
		catch(AssertionError|Exception e){
			TestHelper.handleExceptionNoRetry(e.getMessage());
		}
	}
	
	public static void clickTCsCheckbox() throws Exception{
		
		try
		{
			String logMessage = String.format("Clicking element "+termsAndConditions_Checkbox.getDescription() );
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			WebElement ele = getWebElement(termsAndConditions_Checkbox.getIframeXpath(), termsAndConditions_Checkbox.getLocator(), true, true);
			int width = ele.getSize().getWidth();
			Actions action = new Actions(BrowserHelper.getDriver());
			action.moveByOffset(width/10000, 0).click(ele).build().perform();				

			TestHelper.incrementStepCount();
		}
		catch(AssertionError | Exception e)
		{
			handleException(e.getMessage());				
		}
	}
	
//	public static void inputCardDetails(){
//		driver.findElement(By.id("tnsiNameOnCard")).sendKeys("TestCP");
//		WebElement frame = driver.findElement(By.xpath("//iframe[@class='gw-proxy-cardNumber']"));
//		driver.switchTo().frame(frame);
//		driver.findElement(By.xpath("//input")).sendKeys("511111111111111 8");
//		
//		driver.switchTo().defaultContent();
//		frame = driver.findElement(By.xpath("//iframe[@class='gw-proxy-securityCode']"));
//		driver.switchTo().frame(frame);
//		driver.findElement(By.xpath("//input")).sendKeys("202");
//		
//		driver.switchTo().defaultContent();
//		Select selector = new Select(driver.findElement(By.id("expiry-month")));
//		selector.selectByValue("5");
//		
//		Select selector2 = new Select(driver.findElement(By.id("expiry-year")));
//		selector2.selectByValue("2021");
//		
//		driver.findElement(By.id("tnsiSubmit")).click();;
//	}
}
