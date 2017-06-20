package coop.digital.eStores.testAutomation.actionsAndAssertions;

import static coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions.getWebElement;
import static coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions.handleException;
import static coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions.isRetryRequired;
import static coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions.setRetryCounts;
import static coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions.toBrowserHexValue;
import static coop.digital.eStores.testAutomation.constants.Constants.PAGE_URL_WAIT_TIMEOUT;
import static coop.digital.eStores.testAutomation.constants.Constants.SUCCESS;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import coop.digital.eStores.testAutomation.actionsAndAssertions.CoreActions;
import coop.digital.eStores.testAutomation.helpers.BrowserHelper;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestLogger;

import static org.testng.AssertJUnit.*;


public class CoreAssertions {

	private static int retryCount;
	private static int maxRetryCount;
	
	
	public static void assertIntValuesEqual(int firstInt, int secondInt) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while(isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Asserting int value %s and int value %s are equal", firstInt, secondInt);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				assertThat(firstInt, equalTo(secondInt));
				TestLogger.logTestStep(TestHelper.getStepCount(), SUCCESS);
				TestHelper.incrementStepCount();
				
				break;
			}
			catch (AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}
	
	public static void assertBooleanValue(boolean inputBoolean, boolean expectedValue) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while(isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Asserting boolean value %s and boolean value %s are equal", inputBoolean, expectedValue);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				assertThat(inputBoolean, equalTo(expectedValue));
				TestLogger.logTestStep(TestHelper.getStepCount(), SUCCESS);
				TestHelper.incrementStepCount();
				
				break;
			}
			catch (AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}

	public static void assertStringValuesEqual(String firstString, String secondString) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while(isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Asserting String value %s and String value %s are equal", firstString, secondString);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				assertThat(CoreActions.formatStringForSpace(firstString), equalTo(CoreActions.formatStringForSpace(secondString)));
				TestLogger.logTestStep(TestHelper.getStepCount(), SUCCESS);
				TestHelper.incrementStepCount();
				
				break;
			}
			catch (AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}			
		}
	}	
	
	public static void assertStringValuesNotEqual(String firstString, String secondString) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while(isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Asserting String value %s and String value %s are not equal", firstString, secondString);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				assertThat(CoreActions.formatStringForSpace(firstString), is(not(equalTo(CoreActions.formatStringForSpace(secondString)))));
				TestLogger.logTestStep(TestHelper.getStepCount(), SUCCESS);
				TestHelper.incrementStepCount();
				
				break;
			}
			catch (AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}			
		}
	}
	
	public static void assertStringValuesEqual(String firstString, String secondString, boolean secondStringRegExPattern) throws Exception
	{
		if(secondStringRegExPattern){
			Pattern pattern = Pattern.compile(secondString);
			Matcher matcher = pattern.matcher(firstString);
			assertThat(matcher.find(),equalTo(true));		 
			
		}else{
			assertStringValuesEqual(CoreActions.formatStringForSpace(firstString),CoreActions.formatStringForSpace(secondString));
		}
	}
	
	public static void assertThatElementsinTwoArraysEqual(String[] Array1, String[] Array2, String LogMessage) throws Exception 
	{

		try
		{
			boolean NotMatched = false;
			TestLogger.logTestStep(TestHelper.getStepCount(), LogMessage);
			for (int i = 0; i < Array1.length; i++)
			{
				if (!Array1[i].equals(Array2[i]))
				{
					NotMatched = true;
				}
			}
	
			
			assertThat(NotMatched , equalTo(false));
			TestHelper.incrementStepCount();
		}
		catch (AssertionError | Exception e)
		{
			String errorMessage = "the Array of two strings could not be compared";
			handleException(errorMessage);	
		}
		
	}
	
	public static void assertElementTextEquals(String elementName, String framePath, By locator, String expectedText) throws Exception
	{
		 retryCount = 0;
		 maxRetryCount = 1;
		
		String actualText = "";
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' text equals '%s'", elementName, expectedText);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				actualText = webElement.getText();

				assertThat(CoreActions.formatStringForSpace(actualText), equalTo(CoreActions.formatStringForSpace(expectedText)));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' text does not equal '%s'. Actual text is: '%s'", elementName, expectedText, actualText);
				handleException(errorMessage);
			}
		}
	}
	


	public static void assertWebElementsOrder(List<WebElement> webElements, String dataType, String expectedOrder) throws Exception
	{
		String logMessage;
		int comparisonOutput = 0;
		
		Date firstDate = new Date();
		Date secondDate = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

		String firstWebElementString = "";
		String secondWebElementString = "";

		setRetryCounts(1);
		
		while (isRetryRequired())
		{
			try
			{	
				
				logMessage = "Validating Elements transactions are in " + expectedOrder + " order";
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				TestHelper.incrementStepCount();
				
				boolean errorFound = false;
				
            	for (int loopCounter = 0; loopCounter<webElements.size()-1; loopCounter++)
				{
            		firstWebElementString = webElements.get(loopCounter).getText();
            		secondWebElementString = webElements.get(loopCounter+1).getText();
            		
            		switch (dataType.toLowerCase()) {
		            	case "date":
							firstDate = formatter.parse(firstWebElementString);
							secondDate = formatter.parse(secondWebElementString);
							
							comparisonOutput = firstDate.compareTo(secondDate);
							break;
		            	case "string":
							comparisonOutput = firstWebElementString.compareTo(secondWebElementString);
							break;
		            	case "currency":
		            		
		            		BigDecimal tempAmount1 = new BigDecimal(firstWebElementString.replace("�","").replace(",", ""));
		            		BigDecimal tempAmount2 = new BigDecimal(secondWebElementString.replace("�", "").replace(",", ""));
		            		
							comparisonOutput = tempAmount1.compareTo(tempAmount2);
							break;			
							
    				}
						
					if (expectedOrder.toLowerCase().equals("descending") && comparisonOutput < 0)
					{
						logMessage = "Web Element number " + (loopCounter+1) + " '" + firstWebElementString + "' and Web Element number " + (loopCounter+2) + " '" + secondWebElementString + "' of type " + dataType + " are NOT in " + expectedOrder + " order as expected";
						errorFound = true;
						break;
					}
					else if (expectedOrder.toLowerCase().equals("ascending") && comparisonOutput > 0)
					{
						logMessage = "Web Element number " + (loopCounter+1) + " '" + firstWebElementString + "' and Web Element number " + (loopCounter+2) + " '" + secondWebElementString + "' of type " + dataType + " are NOT in " + expectedOrder + " order as expected";
						errorFound = true;
						break;
					}
					else {
						logMessage = "Web Element number " + (loopCounter+1) + " '" + firstWebElementString + "' and Web Element number " + (loopCounter+2) + " '" + secondWebElementString + "' of type " + dataType + " are in " + expectedOrder + " order as expected";
						TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
						TestHelper.incrementStepCount();
					}
				}
				if (errorFound)
					handleException(logMessage);
				else {
					logMessage = "No error found - Web Elements are in " + expectedOrder + " order as expected";
					TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
					TestHelper.incrementStepCount();
					break;
				}
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}	
	
	
	public static void assertElementIsDisabled(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is disabled", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				assertThat(webElement.isEnabled(), equalTo(false));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError e)
			{
				String errorMessage = String.format("Element %s is not disabled", elementName);
				handleException(errorMessage);
			}
			catch(Exception e)
			{
				String errorMessage = String.format("Element %s is not displayed", elementName);
				handleException(errorMessage);
			}
		}
	}

	public static void assertElementIsDisplayed(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is displayed", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, false, false);
				assertThat(webElement.isDisplayed(), equalTo(true));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertElementIsDisplayed(ElementProperties element) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is displayed", element.getDescription());
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(element.getIframeXpath(), element.getLocator(), false, false);
				assertThat(webElement.isDisplayed(), equalTo(true));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", element.getDescription());
				handleException(errorMessage);
			}
		}
	}

	public static void assertElementIsNotPresent(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 2;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is not present", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				BrowserHelper.switchFrame(framePath);
						
				WebElement element = BrowserHelper.getElement(locator);
				assertThat(element,equalTo(null));						
				assertThat((String.format("Element '%s' is present", elementName)),equalTo(String.format("Element '%s' is not present", elementName)));
			}
			catch(AssertionError | Exception e){
				
				if( e.getClass().toString().contains("NoSuchElementException") || e.getClass().toString().contains("TimeoutException")){
					assertThat((String.format("Element '%s' is not present", elementName)),equalTo(String.format("Element '%s' is not present", elementName)));
					break;
				}
							
				//assertThat((String.format("Element '%s' is present", elementName)),equalTo(String.format("Element '%s' is not present", elementName)));
				String errorMessage = "Element is present";
				handleException(errorMessage);
			}
		}		
	}
	
	public static void assertElementIsEnabled(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is enabled", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				assertThat(webElement.isEnabled(), equalTo(true));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is disabled", elementName);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertElementIsEnabled(ElementProperties eleProperty) throws Exception
	{
		 assertElementIsEnabled(eleProperty.getDescription(),eleProperty.getIframeXpath(), eleProperty.getLocator());
	}
	
	public static void assertElementIsSelected(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is selected", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, false, false);
				assertThat(webElement.isSelected(), equalTo(true));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is not selected", elementName);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertElementIsSelected(ElementProperties ele) throws Exception
	{
		assertElementIsSelected(ele.getDescription(), ele.getIframeXpath(), ele.getLocator());
	}
	
	public static void assertElementIsNotSelected(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is not selected", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, false, false);
				assertThat(webElement.isSelected(), equalTo(false));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError e)
			{
				String errorMessage = String.format("Element %s is selected", elementName);
				handleException(errorMessage);
			}
			catch(Exception e)
			{
				String errorMessage = String.format("Element %s is not displayed", elementName);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertElementIsNotSelected(ElementProperties ele) throws Exception
	{
		assertElementIsNotSelected(ele.getDescription(), ele.getIframeXpath(), ele.getLocator());
	}

	public static void assertElementIsNotDisplayed(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is not displayed", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, false, false);
				assertThat(webElement.isDisplayed(), equalTo(false));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				retryCount = maxRetryCount+1; // CP added to break loop
				// Do nothing... element doesn't exist so definitely not displayed
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is displayed", elementName);
				handleException(errorMessage);
			}
		}
	}

	public static void assertElementTextContains(String elementName, String framePath, By locator, String expectedText) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		String actualText = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' text contains '%s'", elementName, expectedText);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				actualText = webElement.getText();

				assertThat(CoreActions.formatStringForSpace(actualText), containsString(CoreActions.formatStringForSpace(expectedText)));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' text does not contain '%s'. Actual text is: '%s'", elementName, expectedText, actualText);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertDropDownSelectedItemEquals(ElementProperties eleProperty,String expectedText) throws Exception
	{
		assertDropDownSelectedItemEquals(eleProperty.getDescription(),eleProperty.getIframeXpath(), eleProperty.getLocator(),expectedText);
	}

	public static void assertDropDownSelectedItemEquals(String elementName, String framePath, By locator, String expectedText) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		String selectedOption = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting the selected option of element '%s' text equals '%s'", elementName, expectedText);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
			
				selectedOption = new Select(webElement).getFirstSelectedOption().getText();
				
				assertThat(selectedOption, equalTo(expectedText));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' selected option text does not equal '%s'. Actual text is: '%s'", elementName, expectedText, selectedOption);
				handleException(errorMessage);
			}
		}
	}

	public static void assertElementAttributeEquals(String elementName, String framePath, By locator, String attributeIdentifier, String expectedText) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		String actualText = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' attribute with name of '%s' equals '%s'", elementName, attributeIdentifier, expectedText);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				actualText = webElement.getAttribute(attributeIdentifier);
		
				assertThat(actualText, equalTo(expectedText));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' attribute with name of '%s' does not equal '%s'. Actual value is: '%s'", elementName, attributeIdentifier, expectedText, actualText);
				handleException(errorMessage);
			}
		}
	}
	
	
//	public static void assertPageTitleEquals(String expectedTitle) throws Exception
//	{
//		retryCount = 0;
//		maxRetryCount = 3;
//
//		String actualTitle = "";
//		
//		while (retryCount <= maxRetryCount)
//		{
//			try
//			{
//				String logMessage = String.format("Asserting page title equals '%s'", expectedTitle);
//				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
//				
//				actualTitle = BrowserHelper.getPageTitle();
//				assertThat(actualTitle, equalTo(expectedTitle));
//
//				TestHelper.incrementStepCount();
//				break;
//			}
//			catch(AssertionError | Exception e)
//			{
//				String errorMessage = String.format("The page title does not equal '%s'. Actual page title is: '%s'", expectedTitle, actualTitle);
//				handleException(errorMessage);
//			}
//		}
//	}
	
	public static void assertPageTitleEquals(String expectedTitle) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;

		String actualTitle = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting page title equals '%s'", expectedTitle);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				actualTitle = BrowserHelper.getPageTitle();
				
				WebDriverWait wait = new WebDriverWait(BrowserHelper.getDriver(),PAGE_URL_WAIT_TIMEOUT);
				wait.until(ExpectedConditions.titleContains(expectedTitle));
				actualTitle = BrowserHelper.getPageTitle();
				
				assertThat(actualTitle, equalTo(expectedTitle));

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("The page title does not equal '%s'. Actual page title is: '%s'", expectedTitle, actualTitle);
				handleException(errorMessage);
			}
		}
	}

	public static void assertPagePartialURLEquals(String PartialURL) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 0;

		String actualURL = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				WebDriverWait wait = new WebDriverWait(BrowserHelper.getDriver(),40);
				wait.until(ExpectedConditions.urlContains(PartialURL));
				
				String logMessage = String.format("Asserting page URL containts '%s'", PartialURL);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				actualURL = BrowserHelper.getUrl();
				assertThat(actualURL, containsString(PartialURL));
								
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				actualURL = BrowserHelper.getUrl();	
				String errorMessage = String.format("The page URL does not contain '%s'. Actual page URL is: '%s'",PartialURL, actualURL);
				handleException(errorMessage);
			}
		}
	}
	public static void assertSubElementIsDisplayed(String elementName, By locator, WebElement ParentElement) throws Exception
	{

	//	retryCount = 0;
	//	maxRetryCount = 1;
		
		setRetryCounts(1);
		while (isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Asserting Sub element '%s' is displayed", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);				
				
				WebElement SubWebElement = ParentElement.findElement(locator);
				assertThat(SubWebElement.isDisplayed(), equalTo(true));
				
				TestHelper.incrementStepCount();
				break;
			}
			catch( AssertionError | Exception e)
			{
				String errorMessage = String.format("Sub Element '%s' is not displayed", elementName);
				
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertUrlEquals(String expectedUrl) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;

		String actualUrl = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting url equals '%s'", expectedUrl);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				actualUrl = BrowserHelper.getUrl();
				assertThat(BrowserHelper.getUrl(), equalTo(expectedUrl));

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("The page title does not equal '%s'. Actual page title is: '%s'", expectedUrl, actualUrl);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertBorderColourOfElement(String elementName, String framePath, By locator, String hexColourCode) throws Exception
	  {
			retryCount = 0;
			maxRetryCount = 1;
			
			while (retryCount <= maxRetryCount)
			{
				try
				{
					String logMessage = String.format("Asserting element '%s' has border colour of '%s'", elementName,hexColourCode);
					TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
					
					WebElement webElement = getWebElement(framePath, locator, true, false);
					String rgbBottom[] = webElement.getCssValue("border-bottom-color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))","").split(",");
					String rgbTop[] = webElement.getCssValue("border-top-color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))","").split(",");
					String rgbLeft[] = webElement.getCssValue("border-left-color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))","").split(",");
					String rgbRight[] = webElement.getCssValue("border-right-color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))","").split(",");
					
					String hexBottom = String.format("#%s%s%s", toBrowserHexValue(Integer.parseInt(rgbBottom[0])), toBrowserHexValue(Integer.parseInt(rgbBottom[1])), toBrowserHexValue(Integer.parseInt(rgbBottom[2])));
					String hexTop = String.format("#%s%s%s", toBrowserHexValue(Integer.parseInt(rgbTop[0])), toBrowserHexValue(Integer.parseInt(rgbTop[1])), toBrowserHexValue(Integer.parseInt(rgbTop[2])));
					String hexLeft = String.format("#%s%s%s", toBrowserHexValue(Integer.parseInt(rgbLeft[0])), toBrowserHexValue(Integer.parseInt(rgbLeft[1])), toBrowserHexValue(Integer.parseInt(rgbLeft[2])));
					String hexRight = String.format("#%s%s%s", toBrowserHexValue(Integer.parseInt(rgbRight[0])), toBrowserHexValue(Integer.parseInt(rgbRight[1])), toBrowserHexValue(Integer.parseInt(rgbRight[2])));

					boolean allValuesAsExpected = false;
					hexColourCode = hexColourCode.trim().toUpperCase();
					
					if (hexBottom.equals(hexColourCode) && hexTop.equals(hexColourCode)
						&& hexLeft.equals(hexColourCode) && hexRight.equals(hexColourCode))
					{
						allValuesAsExpected = true;
					}
					
					assertThat(allValuesAsExpected,equalTo(true));
					
					TestHelper.incrementStepCount();
					break;
				}
				catch(AssertionError | Exception e)
				{
					String errorMessage = String.format("Element '%s' border is not as expected", elementName);
					handleException(errorMessage);
				}
			}
		}

	public static void assertElementIsNotDisplayed(ElementProperties elementProperties) throws Exception
	{
		assertElementIsNotDisplayed(elementProperties.getDescription(),elementProperties.getIframeXpath(), elementProperties.getLocator());
	}
	
	public static void assertElementTextEquals(ElementProperties elementProperties, String Text) throws Exception
	{
		assertElementTextEquals(elementProperties.getDescription(), elementProperties.getIframeXpath(), elementProperties.getLocator(), Text);
	}
	
	
	public static void assertElementTextContains(ElementProperties element, String expectedText) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		String actualText = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' text contains '%s'", element.getDescription(), expectedText);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(element.getIframeXpath(), element.getLocator(), true, false);
				actualText = webElement.getText();

				assertThat(CoreActions.formatStringForSpace(actualText), containsString(CoreActions.formatStringForSpace(expectedText)));

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", element.getDescription());
				handleException(errorMessage);
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' text does not contain '%s'. Actual text is: '%s'", element.getDescription(), expectedText, actualText);
				handleException(errorMessage);
			}
		}
	}
	
	public static void assertElementIsDisplayed(String elementName, WebElement webElement) throws Exception
	{
		int retryCount = 0;
		int maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Asserting element '%s' is displayed", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				assertThat(webElement.isDisplayed(), equalTo(true));
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
		}
	}
}
