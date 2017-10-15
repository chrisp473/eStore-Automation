package coop.digital.eStores.testAutomation.actionsAndAssertions;

import static coop.digital.eStores.testAutomation.constants.Constants.BLANK_VALUE;
import static coop.digital.eStores.testAutomation.constants.Constants.OBJECT_SYNC_DEFAULT_TIMEOUT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import coop.digital.eStores.testAutomation.constants.Constants;
import coop.digital.eStores.testAutomation.helpers.BrowserHelper;
import coop.digital.eStores.testAutomation.helpers.ElementProperties;
import coop.digital.eStores.testAutomation.helpers.TestHelper;
import coop.digital.eStores.testAutomation.utilityAndFactories.TestLogger;


public class CoreActions {

	protected static int defaultTimeoutSeconds = OBJECT_SYNC_DEFAULT_TIMEOUT;
	private static int retryCount;
	private static int maxRetryCount;
	
	
	public static void click(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 2;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				if(!TestHelper.getSystemProperty("deviceType").equals("DESKTOP"))
					jumpToElement(elementName, locator);
				
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				
				WebElement ele = getWebElement(framePath, locator, true, true);
		
				((JavascriptExecutor)BrowserHelper.getDriver()).executeScript("arguments[0].scrollIntoView(true);", ele);
				WebDriverWait wait = new WebDriverWait(BrowserHelper.getDriver(), Constants.OBJECT_SYNC_DEFAULT_TIMEOUT);
				wait.until(ExpectedConditions.elementToBeClickable(ele));
				
				Thread.sleep(500);
				ele.click();

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static String getNextDay(int day, int diffe)
	{ // Usage: 'getNextDay(Calendar.MONDAY)' yeilds 'DD/MM/YY' in String.
		
        Calendar date = Calendar.getInstance();
        int diff = day - date.get(Calendar.DAY_OF_WEEK);
        if (!(diff > 0)) {
            diff += diffe;
        }
        date.add(Calendar.DAY_OF_MONTH, diff);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        
        return format1.format(date.getTime());
	}
	
	
	public static void click(String elementName, String framePath, WebElement Ele) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				Ele.click();

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void clickUsingActions(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement ele = getWebElement(framePath, locator, true, true);
				Actions action = new Actions(BrowserHelper.getDriver());
				action.click(ele).build().perform();				

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void clickViaJavaScript(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				if(!TestHelper.getSystemProperty("deviceType").equals("DESKTOP"))
					jumpToElement(elementName, locator);
				
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
							
				
				WebElement ele = getWebElement(framePath, locator, true, true);
				Point point = ele.getLocation();
				WebDriver driver = BrowserHelper.getDriver();
				((JavascriptExecutor)driver).executeScript("window.scroll("+point.getX()+","+(point.getY()-100)+")");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", ele);
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void clickViaOffset(String elementName, String framePath, By locator, int x, int y) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				if(!TestHelper.getSystemProperty("deviceType").equals("DESKTOP"))
					jumpToElement(elementName, locator);
				
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
							
				
				WebElement ele = getWebElement(framePath, locator, true, true);
				WebDriver driver = BrowserHelper.getDriver();

			    Actions act = new Actions(driver);
			    act.moveToElement(ele).moveByOffset(x, y).click().perform();
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void clickViaJavaScript(String elementName, String framePath, WebElement ele) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Clicking element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
					
				Point point = ele.getLocation();
				WebDriver driver = BrowserHelper.getDriver();
				((JavascriptExecutor)driver).executeScript("window.scroll("+point.getX()+","+(point.getY()-100)+")");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", ele);
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}

	public static void closeCurrentTab() throws Exception
	{
		setRetryCounts(2);
		
		while (isRetryRequired())
		{
			try
			{
				String logMessage = "Closing Current Tab";
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
				BrowserHelper.closeCurrentTab();
				
				TestHelper.incrementStepCount();
			}
			catch (Throwable e)
			{
				handleException(e.getMessage());
			}
		}
	}
	public static WebElement getWebElement(String framePath, By locator, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		return getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled, defaultTimeoutSeconds);
	}
	
	public static WebElement getWebElement(ElementProperties eleProperty, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		return getWebElement(eleProperty.getIframeXpath(), eleProperty.getLocator(), waitUntilDisplayed, waitUntilEnabled, defaultTimeoutSeconds);
	}
	
	public static WebElement getWebElement(ElementProperties eleProperty) throws Exception
	{
		return getWebElement(eleProperty.getIframeXpath(), eleProperty.getLocator(),true,true);
	}

	public static List<WebElement> getWebElements(String framePath, By locator) throws Exception
	{
		return getWebElements(framePath, locator, defaultTimeoutSeconds);
	}
	
	public static List<WebElement> getWebElements(ElementProperties elementProperties) throws Exception
	{
		return getWebElements(elementProperties.getIframeXpath(), elementProperties.getLocator(), defaultTimeoutSeconds);
	}

	public static String getWebElementText(String elementName, String framePath, By locator, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		String elementText = ""; 
		
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Getting text from element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				elementText = getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled).getText();

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String LogMessage2 = String.format( "Unable to get text from element %s because %s", elementName, e.getMessage());
				handleException(LogMessage2);				
			}
		}
		
		return elementText;
	}
	
	public static String getWebElementText(ElementProperties eleProperty, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		return getWebElementText(eleProperty.getDescription(),eleProperty.getIframeXpath(),eleProperty.getLocator(),waitUntilDisplayed,waitUntilEnabled);
	}
	
	public static WebElement getParentElement(String elementName, String framePath, By Locator) throws Exception
	{
		WebElement ParentElement = null;
		setRetryCounts(3);
		
		while (isRetryRequired())
		{
			try
			{
				WebElement Element = getWebElement("" , Locator, true, true);
				ParentElement = Element.findElement(By.xpath(".."));
				break;
			}
			catch(AssertionError | Exception e)
			{
				String LogMessage2 = String.format( "Unable to locate parent element of %s because %s", elementName, e.getMessage());
				handleException(LogMessage2);				
			}
		}
		return ParentElement;		
	}
	public static WebElement getSubElement(WebElement parentElement, String subElementName, String framePath, By Locator) throws Exception
	{
		WebElement subElement = null;
		setRetryCounts(3);
		
		while (isRetryRequired())
		{
			try
			{
				subElement = parentElement.findElement(Locator);
				break;
			}
			catch(AssertionError | Exception e)
			{
				String LogMessage2 = String.format( "Unable to locate sub element %s because %s", subElementName, e.getMessage());
				handleException(LogMessage2);				
			}
		}
		return subElement;		
	}
	public static String getTextElementText(String elementName, String framePath, By locator, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		String elementText = ""; 
		
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Getting text from text element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				elementText = getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled).getAttribute("value");

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				String LogMessage2 = String.format( "Unable to get text from text element %s because %s", elementName, e.getMessage());
				handleException(LogMessage2);				
			}
		}
		
		return elementText;
	}
	
	public static String getTextDropDownSelectedItem(ElementProperties eleProperty, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		return getTextDropDownSelectedItem(eleProperty.getDescription(),eleProperty.getIframeXpath(), eleProperty.getLocator(), waitUntilDisplayed, waitUntilEnabled);
	}
	
	public static String getTextDropDownSelectedItem(String elementName, String framePath, By locator, boolean waitUntilDisplayed, boolean waitUntilEnabled) throws Exception
	{
		
		String selectedOption = "";
		retryCount = 0;
		maxRetryCount = 1;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Selected option of element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				WebElement webElement = getWebElement(framePath, locator, true, false);
				selectedOption = new Select(webElement).getFirstSelectedOption().getText();

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
				String errorMessage = String.format("Element '%s' selected option vlaue is '%s'", elementName, selectedOption);
				handleException(errorMessage);
			}
		}
		
		return selectedOption;
	}


	public static void handleException(String errorMessage) throws Exception
	{
		TestLogger.logException(formatErrorMessage(errorMessage));
		
		if (retryCount == maxRetryCount)
		{
			RecordException(errorMessage);
		}
		retryCount++;
		Thread.sleep(1000);
	}
	public static void RecordException(String errorMessage) throws Exception {
		TestHelper.setTestResult("FAIL");
		saveScreenShot(true);
		TestHelper.reportFail(errorMessage, true);
	}
	


	public static boolean isElementPresentAndUnique(String framePath, By locator) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 0;
		boolean elementFound = false;
		
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				List<WebElement> webElements = getWebElements(framePath, locator);
				if(webElements.size() == 1)
				{
					elementFound = true;
					break;
				}
				retryCountLocal++;
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementFound;
	}
	
	public static boolean isElementPresentAndUnique(String framePath, By locator, int timeoutSeconds) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 1;
		boolean elementFound = false;
		
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				List<WebElement> webElements = getWebElements(framePath, locator,timeoutSeconds);
				retryCountLocal++;
				if(webElements.size() == 1)
				{
					elementFound = true;
					break;
				}
				
				if(webElements.size() > 1)
				{					
					break;
				}
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementFound;
	}
	
	public static boolean isElementPresentAndAtLeastOne(String framePath, By locator) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 0;
		boolean elementFound = false;
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				List<WebElement> webElements = getWebElements(framePath, locator);
				if(webElements.size() > 0)
				{
					elementFound = true;
					break;
				}
				retryCountLocal++;
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementFound;
	}
	
	public static boolean isElementDisplayed(String framePath, By locator) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 0;
		boolean elementDisplayed = false;
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				WebElement webElement = getWebElement(framePath, locator, false, false);
				return webElement.isDisplayed();								
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementDisplayed;
	}
	
	
	public static void inputText(String elementName, String framePath, By locator, String text) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Inputting text '%s' in element '%s'", text, elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = getWebElement(framePath, locator, true, true); 
				element.clear();
				element.sendKeys(text);

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}
	
	public static void inputPassword(String elementName, String framePath, By locator, String text) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Inputting password '*********' in element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = getWebElement(framePath, locator, true, true); 
				element.clear();
				element.sendKeys(text);

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}
	
	
	public static void inputTextWithAction(String elementName, String framePath, By locator, String text) throws Exception{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Inputting text '%s' in element '%s'", text, elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = getWebElement(framePath, locator, true, true); 
				Actions action = new Actions(BrowserHelper.getDriver());
				element.click();
				action.sendKeys(element, text).build().perform();
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}	
	}
	
	public static void inputText(String elementName, String framePath, By locator, String text, Keys keyValue) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Inputting text '%s' in element '%s'", text, elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = getWebElement(framePath, locator, true, true); 
				element.clear();
				element.sendKeys(text,keyValue);

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}

	public static boolean isRetryRequired()
	{
		return (retryCount <= maxRetryCount);
	}
	
	public static boolean isRetryCountLessThanMax()
	{
		return (retryCount < maxRetryCount);
	}
	
	public static void selectOption(String elementName, String framePath, By locator, String option) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Selecting option '%s' from element '%s'", option, elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);

				Select selector = new Select(getWebElement(framePath, locator, true, true));
				selector.selectByVisibleText(option);

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void selectOptionByClick(String elementName, String framePath, By locator, String option) throws Exception
	{
		setRetryCounts(3);
		
		while (isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Selecting option '%s' from element '%s'", option, elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				WebElement element = getWebElement("", locator, true, true);

				if (System.getProperty("deviceType").equals("ANDROID"))
				{
					Select selector = new Select(getWebElement(framePath, locator, true, true));
					selector.selectByVisibleText(option);
				}
				else {
				
					if (TestHelper.getWebBrowserName().equals("IE"))
					{
						String optionXpath = ".//option[.='%s']"; 
						optionXpath = String.format(optionXpath, option);
						element.click();
						element.findElement(By.xpath(optionXpath)).click();
					}
					else
					{
						String optionXpath = ".//option[.='%s']"; 
						optionXpath = String.format(optionXpath, option);
						element.click();
						element.findElement(By.xpath(optionXpath)).click();
					}
					
				}
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}

	public static void setRetryCounts(int maxRetries)
	{
		retryCount = 0;
		maxRetryCount = maxRetries;
	}

	
	public static void takeScreenshot() throws Exception
	{
		TestLogger.logTestStep(TestHelper.getStepCount(), "Taking screenshot");
		
		saveScreenShot(false);
		TestHelper.incrementStepCount();
	}
	
	public static void waitForElementToBeDisplayed(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
	
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Waiting for element '%s' to be displayed", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				getWebElement(framePath, locator, true, false, 30);
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void waitForElementToBeDisplayed(ElementProperties element) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
	
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Waiting for element '%s' to be displayed", element.getDescription());
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				getWebElement(element.getIframeXpath(), element.getLocator(), true, false, 30);
				
				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());				
			}
		}
	}
	
	public static void waitSeconds(int seconds) throws InterruptedException
	{
		String logMessage = String.format("Waiting %d seconds", seconds);
		TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
		
		Thread.sleep(seconds * 1000);
	}

	public static String formatErrorMessage(String errorMessage)
	{
		if (errorMessage.contains("Timed out after "))
		{
			errorMessage = errorMessage.substring(0,errorMessage.indexOf("seconds") + 7);
		}
		
		return errorMessage;
	}
	public static String getElementText(String elementName, String framePath, By locator) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 1;
		
		String actualText = "";
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Getting Text Value of Element: '%s'", elementName );
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement webElement = getWebElement(framePath, locator, true, false);
				actualText = webElement.getText();

				TestHelper.incrementStepCount();
				break;
			}
			catch(TimeoutException e)
			{
				String errorMessage = String.format("Element '%s' is not displayed", elementName);
				handleException(errorMessage);
			}
		}
			return actualText;
	}
	
	public static int getNumberItemsInList(By ListLocator,String ListDescription) throws Exception
	{
		setRetryCounts(3);
		while (isRetryRequired())
		{
			try
			{
				String logMessage = String.format("Getting number of items from list", ListDescription);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				List<WebElement> listProduct = getWebElements("", ListLocator); //List which has all the product description and buttons.
				
				TestHelper.incrementStepCount();
				return listProduct.size();
			}
			catch (AssertionError | Exception e)
			{
				String message  = "Error Occured attempting to get the number of items from " + ListDescription + ": " + e.getMessage();
				handleException(message);
				
			}
			
		}
		return 0 ;
		
	}

	private static WebElement getWebElement(String framePath, By locator, boolean waitUntilDisplayed, boolean waitUntilEnabled, int timeoutSeconds) throws Exception
	{
		BrowserHelper.switchFrame(framePath);
		
		if (waitUntilDisplayed &&
			waitUntilEnabled)
		{
			BrowserHelper.waitForElementToBeDisplayedAndEnabled(locator, timeoutSeconds);
		}
		else if (waitUntilDisplayed)
		{
			BrowserHelper.waitForElementToBeDisplayed(locator, timeoutSeconds);
		}
		else
		{
			BrowserHelper.waitForElement(locator, timeoutSeconds);
		}
		
		return BrowserHelper.getElement(locator);
	}
	

	private static List<WebElement> getWebElements(String framePath, By locator, int timeoutSeconds) throws Exception
	{
		BrowserHelper.switchFrame(framePath);
		//BrowserHelper.waitForElements(locator, timeoutSeconds);
		return BrowserHelper.getElements(locator);
	}

	public static void switchTabs() throws Exception 
	{

		setRetryCounts(2);
		
		while (isRetryRequired())
		{
			try
			{
				String logMessage = "Switching between Frmaes";
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
				BrowserHelper.switchWindow();
				
				TestHelper.incrementStepCount();
				break;
			}
			catch (Throwable e)
			{
				handleException(e.getMessage());
			}
		}
	}


	// WORKS
	public static void jumpToElement(String elementDescription, By locator) throws Exception
	{
		String logMessage = String.format("Attempting to jump to '" + elementDescription + "' on device");
		TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);

		WebElement element = getWebElement("", locator, false, false);
		BrowserHelper.executeJavascriptOnObject("arguments[0].scrollIntoView(true);", element);
	}


	
	public static void saveScreenShot(boolean isException) throws Exception
	{
		String type = "CAPTURE";

		if (isException)
		{
			type = "EXCEPTION";
		}
		
		String fileName = String.format("%s_%s_%s.png", type, "STEP" + TestHelper.getStepCount(), TestHelper.getDateAsString(Calendar.getInstance().getTime(), "ddMMyyyy-HHmmss"));
		String filePath = "%s"+File.separator+"%s";
		filePath = String.format(filePath, TestHelper.getTestCaseResultsDirectory(), fileName);
		
		FileUtils.copyFile(BrowserHelper.takeScreenShot(), new File(filePath));
//		BrowserHelper.takeScreenShotAShot(filePath);

		TestLogger.logScreenShotInHTMLReport(fileName);
	}
	
	public static void inputEncodedTextBase64(String elementName, String framePath, By locator, String encodedText) throws Exception
	{
		retryCount = 0;
		maxRetryCount = 3;
		
		String decodedText = TestHelper.decodeBase64(encodedText);
		
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Inputting encoded text in element '%s'", elementName);
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				
				WebElement element = getWebElement(framePath, locator, true, true); 
				element.clear();
				element.sendKeys(decodedText);

				TestHelper.incrementStepCount();
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}
	}
	
	public static void inputEncodedTextBase64(ElementProperties elementProperties, String encodedText) throws Exception
	{
		inputEncodedTextBase64(elementProperties.getDescription(), elementProperties.getIframeXpath(), elementProperties.getLocator(), encodedText);
	}
	
	public static String getWebElementAttribute(String framePath, By locator, String attribute, boolean waitUntilDisplayed, boolean waitUntilEnabled, int timeoutSeconds)
	{
		BrowserHelper.switchFrame(framePath);
		
		if (waitUntilDisplayed &&
			waitUntilEnabled)
		{
			BrowserHelper.waitForElementToBeDisplayedAndEnabled(locator, timeoutSeconds);
		}
		else if (waitUntilDisplayed)
		{
			BrowserHelper.waitForElementToBeDisplayed(locator, timeoutSeconds);
		}
		else
		{
			BrowserHelper.waitForElement(locator, timeoutSeconds);
		}
		
		return BrowserHelper.getElementAttribue(locator, attribute);
	}
	
	public static String getWebElementAttribute(ElementProperties eleProperty, String attribute, boolean waitUntilDisplayed, boolean waitUntilEnabled, int timeoutSeconds)
	{
		return getWebElementAttribute(eleProperty.getIframeXpath(),eleProperty.getLocator(),attribute,waitUntilDisplayed, waitUntilEnabled, timeoutSeconds);
	}
	
	public static int getDefaultTimeOut()
	{
		return defaultTimeoutSeconds;
	}
	
	public static void sendKeysToElement(String framePath,By locator,boolean waitUntilDisplayed,boolean waitUntilEnabled, Keys Key) throws Exception{
		WebDriver driver  = BrowserHelper.getDriver();
		WebElement element = getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled);
		Point point = element.getLocation();
		BrowserHelper.executeJavascriptOnObject("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor)driver).executeScript("window.scroll("+point.getX()+","+(point.getY()-100)+")");
		
		Actions action = new Actions(BrowserHelper.getDriver());
		action.sendKeys(element,Key).build().perform();	
	}
	
	public static void sendKeysToElement(String framePath,By locator,boolean waitUntilDisplayed,boolean waitUntilEnabled, String Key) throws Exception{
		WebDriver driver  = BrowserHelper.getDriver();
		WebElement element = getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled);
		Point point = element.getLocation();		
		BrowserHelper.executeJavascriptOnObject("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor)driver).executeScript("window.scroll("+point.getX()+","+(point.getY()-100)+")");
			
		Actions action = new Actions(BrowserHelper.getDriver());
		action.sendKeys(element,Key).build().perform();	
	}
	
	public static void sendKeysToElement(String framePath,WebElement element,boolean waitUntilDisplayed,boolean waitUntilEnabled, Keys Key) throws Exception{
		
		Point point = element.getLocation();
		BrowserHelper.executeJavascriptOnObject("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor)BrowserHelper.getDriver()).executeScript("window.scroll("+point.getX()+","+(point.getY()-100)+")");
				
		Actions action = new Actions(BrowserHelper.getDriver());
		action.sendKeys(element,Key).build().perform();	
	}
	
	// Brief Description : This function checks the drop down values, all values that needs to be looked into Drop down must be passed as an array of String
	public static boolean validateDropDownValues(String framePath,By locator,boolean waitUntilDisplayed,boolean waitUntilEnabled, String[] dropDownValues) throws Exception{
		
		retryCount = 0;
		maxRetryCount = 2;
		WebElement dropDown = null;
		while (retryCount <= maxRetryCount)
		{
			try
			{
				String logMessage = String.format("Validating Drop down values ");
				TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
				dropDown = getWebElement(framePath, locator, waitUntilDisplayed, waitUntilEnabled);
				break;
			}
			catch(AssertionError | Exception e)
			{
				handleException(e.getMessage());
			}
		}	
		
		List<WebElement> options =  dropDown.findElements(By.tagName("option"));
		if(options.size()==0){
			TestLogger.logTestStep(TestHelper.getStepCount(), "No values are present in the drop down list");
			return false;
		}
		String[] dropDownValuesActual = new String[options.size()];
		for(int dropDownActualCount=0;dropDownActualCount<options.size();dropDownActualCount++){
			dropDownValuesActual[dropDownActualCount] = options.get(dropDownActualCount).getText().trim();			
		}		
		
		boolean status = true;
		
		for(int dropDownCount=0;dropDownCount<dropDownValues.length;dropDownCount++){
			boolean valueFound = false;
			for(int dropDownActualCount=0;dropDownActualCount<dropDownValuesActual.length;dropDownActualCount++){
				if(dropDownValues[dropDownCount].equals(dropDownValuesActual[dropDownActualCount])){
					valueFound = true;
					break;
				}
			}
			if(valueFound){
				TestLogger.logTestStep(TestHelper.getStepCount(), "Value '"+dropDownValues[dropDownCount]+"' found in drop down");
			}else{
				//TestLogger.logTestStep(TestHelper.getStepCount(),"STEP " + TestHelper.getStepCount() + " ~ Value '"+dropDownValues[dropDownCount]+"' not found in drop down");
				TestLogger.logException(formatErrorMessage("STEP " + TestHelper.getStepCount() + " ~ Value '"+dropDownValues[dropDownCount]+"' not found in drop down"));
			}
				
			status = status & valueFound; 
		}		
		return status;
	}
	
	
	public static String toBrowserHexValue(int number) {
	      StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
	      while (builder.length() < 2) {
	          builder.append("0");
	      }
	      return builder.toString().toUpperCase();
	  }

	 public static String getElementColour(String elementName, String framePath, By locator) throws Exception
	  {
			retryCount = 0;
			maxRetryCount = 1;
			
			String hex = "";
			
			while (retryCount <= maxRetryCount)
			{
				try
				{
					String logMessage = String.format("Getting colour of element '%s'", elementName);
					TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
					
					WebElement webElement = getWebElement(framePath, locator, true, false);
					String rgb[] = webElement.getCssValue("color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))","").split(",");
					
					hex = String.format("#%s%s%s", toBrowserHexValue(Integer.parseInt(rgb[0])), toBrowserHexValue(Integer.parseInt(rgb[1])), toBrowserHexValue(Integer.parseInt(rgb[2])));
					
					TestHelper.incrementStepCount();
					break;
				}
				catch(AssertionError | Exception e)
				{
					String errorMessage = String.format("Element '%s' border is not as expected", elementName);
					handleException(errorMessage);
				}
			}
			
			return hex;
	  }
	
	
	/**
	 * @Description : This method is used for click event contains ElementProperties with XPath.
	 * @param elementProperties
	 * @param strXpath
	 * @return void
	 * @throws Exception
	 */
	/*public static void click(ElementProperties elementProperties, String strXpath) throws Exception
	{
		click(elementProperties.getDescription(), strXpath, elementProperties.getLocator());
		
	}*/
	
	/**
	 * @Description : This method is used for click event contains ElementProperties
	 * @param elementProperties
	 * @return void
	 * @throws Exception
	 */
	public static void click(ElementProperties elementProperties) throws Exception
	{
		click(elementProperties.getDescription(), elementProperties.getIframeXpath(), elementProperties.getLocator());
		
	}

	/**
	 * @Description : This method is used for click event using Actions class contains ElementProperties
	 * @param elementProperties
	 * @return void
	 * @throws Exception
	 */
	public static void clickUsingActions(ElementProperties elementProperties) throws Exception
	{
		clickUsingActions(elementProperties.getDescription(), elementProperties.getIframeXpath(), elementProperties.getLocator());
		
	}
	
	/**
	 * @Description : This method is used for input data into Text field.
	 * @param elementProperties
	 * @param inputText
	 * @return void
	 * @throws Exception
	 */
	public static void input(ElementProperties elementProperties, String inputText) throws Exception
	{
		inputText(elementProperties.getDescription(), BLANK_VALUE,elementProperties.getLocator(), inputText);
		
	}
	
	/**
	 * @Description : This method is used for input selecting combo value from ComboDropDown
	 * @param elementProperties
	 * @return void
	 * @throws Exception
	 */
	public static void comboInput(ElementProperties ByAndDesc, String InputText) throws Exception
	{
		inputText(ByAndDesc.getDescription(), BLANK_VALUE,ByAndDesc.getLocator(), InputText);
		WebElement comboBox = getWebElement(BLANK_VALUE,ByAndDesc.getLocator(),true,true);
		comboBox.sendKeys(Keys.ARROW_DOWN);
		comboBox.sendKeys(Keys.RETURN);
	}
	
	/**
	 * @Description : This method is used for selecting combo value from ComboDropDown
	 * @param elementProperties
	 * @param option
	 * @return void
	 * @throws Exception
	 */
	public static void selectOption(ElementProperties elementProperties, String option) throws Exception
	{
		{
			setRetryCounts(3);
			boolean optionFound = false;
			
			while (isRetryRequired())
			{
				try
				{
					String logMessage = String.format("Selecting option '%s' from element '%s'", option, elementProperties.getDescription());
					TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);

					Select selector = new Select(getWebElement(BLANK_VALUE, elementProperties.getLocator(), true, true));
					List<WebElement> options = selector.getOptions();
					int index = 0;
					
					for (WebElement element:options)
					{
						String optionText = element.getText();
						
						if (optionText.trim().equals(option)) 
						{
							optionFound = true;
							break;
						}
						else index++;
					}
					
					assertThat(optionFound,equalTo(true));
					
					selector.selectByIndex(index);

					TestHelper.incrementStepCount();
					break;
				}
				catch(AssertionError | Exception e)
				{
					handleException(e.getMessage());				
				}
			}
		}
	}
	
	/**
	 * @Description : This method is used for Input Text From Test Data Table and validate the Element Type.
	 * @param elementProperties
	 * @return void
	 * @throws Exception
	 */
	public static void InputTextFromDataTable(ElementProperties elementProperties) throws Exception
	{
		String DataToEnter = TestHelper.getTestDataValue(elementProperties.getColumnName());
		switch (elementProperties.getElementType())
		{
			case TEXTBOX:
				input(elementProperties, DataToEnter);
				break;
			case DROPDOWN: 
				selectOption(elementProperties, DataToEnter);
				break;
			default:
				TestHelper.handleExceptionNoRetry("Element Type not suitalbe for Entry from data Table");
				break;
		}
	}
	
	/**
	 * @Description : This method is used to validate the current element is visible or not over the browser to validate the input test data 
	 * that comes from Data Table.
	 * @param elementProperties
	 * @return void
	 * @throws Exception
	 */
	public static void CheckElementNotVisibleOrInputTextFromDataTable(ElementProperties elementProperties) throws Exception
	{
		String TextFromDataTable = TestHelper.getTestDataValue(elementProperties.getColumnName());
		
		if (TextFromDataTable.equals(BLANK_VALUE))
		{
			//assertElementIsNotDisplayed(ElementProperties);
		}
		else
		{
			InputTextFromDataTable(elementProperties);
		}
	}
	
	
	public static boolean isElementPresentAndAtLeastOne(ElementProperties elementProperties) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 1;
		boolean elementFound = false;
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				List<WebElement> webElements = getWebElements(BLANK_VALUE, elementProperties.getLocator());
				if(webElements.size() > 0)
				{						
					elementFound = true;
					break;
				}
				else{
					retryCountLocal++;
				}
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementFound;
	}
	
	
	/**
	 * @Description : This method is used to verify the passing element locator with framePath is present or not.
	 * @param framePath
	 * @param elementProperties
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isElementPresentAndAtLeastOne(String framePath, ElementProperties elementProperties) throws Exception
	{
		int retryCountLocal = 0;
		int maxRetryCountLocal = 1;
		boolean elementFound = false;
		while (retryCountLocal <= maxRetryCountLocal)
		{		
			try {
				List<WebElement> webElements = getWebElements(framePath, elementProperties.getLocator());
				if(webElements.size() > 0)
				{
					elementFound = true;
					break;
				}
			}
			
			catch (Exception e) {
				retryCountLocal++;
				if(retryCountLocal == maxRetryCountLocal) {
					break;
				}
			}
		}
		return elementFound;
	}
	
	/**
	 * @author uphtzc
	 * @Description This method is used to validate the data for the page object
	 *              and create Excel report after validating the fields
	 * @param PageObject2
	 *            This is the pageObject that contains the page elements
	 * @return Void
	 * Chris moved to CustomerComplaints - RespondAction Class
	 */
//	public static void iterateOverAllFieldsWithExcelReport(
//			CoreActions PageObject2, ExcelUtils excelResults) throws Exception {
//		// get the list of fields for the specified page object
//		Field[] ListOfFields = PageObject2.getClass().getFields();
//
//		for (Field CurrentField : ListOfFields) {
//
//			if (CurrentField.getType().equals(ElementProperties.class)) {
//				if (CurrentField.getDeclaringClass().getSimpleName().equalsIgnoreCase(PageObject2.getClass().getSimpleName())) {
//					// cast the contents of the field to an elementProperties
//					// object
//					ElementProperties CurrentElement = (ElementProperties) CurrentField.get(PageObject2);
//
//					// Check if their is a data table column associated with
//					// this item
//					if (!CurrentElement.getColumnNameExcel().equalsIgnoreCase(BLANK_VALUE)) {
//
//						if(Arrays.asList(excelColumnHeaders).contains(CurrentElement.getColumnNameExcel()))
//						{		
//							if (validateDataType(CurrentElement)) {
//								// System.out.println(TEST_RESULT_SUCCESS);
//								excelResults.setCellData(SUCCESS, CurrentElement.getColumnName());
//								
//							} else {
//								// System.out.println(TEST_RESULT_FAILURE);
//								excelResults.setCellData(FAILURE,CurrentElement.getColumnName());
//								TestHelper.setTestResult(FAIL);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
	
	public static void incrementRetryCount() throws Exception
	{
		retryCount++;
	}

	//* Please use the Calender class to pass the value of modifyType, for example Calender.WEEK_OF_MONTH 
	public static Calendar getRequiredDateFromCurrentDate(int modifyType, int modifyBy) throws Exception{
		Calendar c = Calendar.getInstance();
		c.add(modifyType, modifyBy); 
		return c;		
	}
	
	public static String formatStringForSpace(String stringToFormat) throws Exception{
		
		if(stringToFormat!=null){
			stringToFormat = stringToFormat.trim().replaceAll("\\t*\\s+\\t*\n", "\n");
			stringToFormat = stringToFormat.trim().replaceAll("\n\\t*\\s+\\t*", "\n");
			stringToFormat = stringToFormat.trim().replaceAll("\\s\\s", " ");
		}
		return stringToFormat;
	}

	public static void HoverMouseOver(String elementName, String framePath, By locator) throws Exception {
			
		try
		{
			String logMessage = String.format("Hovering Mouse over '%s'", elementName);
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			WebElement element = getWebElement(framePath, locator, true, true); 
			Actions Actions = BrowserHelper.getActionsBuilder();
			Actions.moveToElement(element);
			Actions.build().perform();

			TestHelper.incrementStepCount();
		}
		catch(AssertionError | Exception e)
		{
			RecordException(e.getMessage());
		}
	}
	
	/**
	 * Creates Log entry and Executes specified against this element. 
	 * no Retries are execrcised. 
	 * @param JavaScript - the java script to be executed
	 * @throws Exception
	 */
	public static void ExecuteJavaScript(String elementName, String framePath, By Locator, String JavaScript) throws Exception {
		
		
		try {
			
			String logMessage = String.format("Executing JavaScript '%s' on element %s", JavaScript, elementName);
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			WebElement object = getWebElement(framePath, Locator, true, true);
			BrowserHelper.executeJavascriptOnObject(JavaScript, object);
			TestHelper.incrementStepCount();
		}
		catch(AssertionError | Exception e)
		{
			CoreActions.RecordException(e.getMessage());
		}
	}
	
	/**
	 * Uses the ExecuteJavaScript routine to force teh innerHTML of the element to match teh input Text
	 * @param Text - Text to be inputted. 
	 * @throws Exception
	 */
	public static void InputTextWithJS(String elementName, String framePath, By Locator,String Text) throws Exception {
		String logMessage = String.format("Inputing %s into the %s Object", Text, elementName);
		TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
		
		String JavaScript = String.format("arguments[0].innerHTML = '%s'", Text);
		ExecuteJavaScript(elementName, framePath, Locator, JavaScript);
		
		//Don't increment step count as ExecuteJavaScript method will do this. 
	}
	
	/**
	 * Logs and inputs a special key (E.g. enter) to this object.
	 * @param KeyToInput - the key to type
	 * @throws Exception
	 */
	public static void InputKeys(String elementName, String framePath, By Locator,Keys KeyToInput) throws Exception {
		
		try
		{
			String logMessage = String.format("Inputting key  '%s' in element '%s'", KeyToInput.toString(), elementName);
			TestLogger.logTestStep(TestHelper.getStepCount(), logMessage);
			
			WebElement element = getWebElement(framePath, Locator, true, true);
			element.sendKeys(KeyToInput);

			TestHelper.incrementStepCount();
		}
		catch(AssertionError | Exception e)
		{
			CoreActions.RecordException(e.getMessage());
		}
		
	}
	
	public static String getElementText(ElementProperties elementProperties) throws Exception
	{
		String Text = getElementText(elementProperties.getDescription(), BLANK_VALUE, elementProperties.getLocator());
		return Text;
	}
		

	
}
