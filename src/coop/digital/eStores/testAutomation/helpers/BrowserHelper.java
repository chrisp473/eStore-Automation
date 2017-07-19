package coop.digital.eStores.testAutomation.helpers;

import static coop.digital.eStores.testAutomation.constants.Constants.OBJECT_SYNC_DEFAULT_TIMEOUT;
import static coop.digital.eStores.testAutomation.constants.Constants.PAGE_SYNC_DEFAULT_TIMEOUT;
import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import coop.digital.eStores.testAutomation.utilityAndFactories.WebDriverFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BrowserHelper {

	private static WebDriver driver;
	private static String baseUrl;
	private static long currentImplicitWaitTime = OBJECT_SYNC_DEFAULT_TIMEOUT;
	
	public static boolean doesDriverExist()
	{
		return !(driver==null);
	}
	
	public static long getCurrentImplicitWaitTime(){
		return currentImplicitWaitTime;
	}
	
	public static String getBrowserName(){
		String browserName = "NOT_SET";
		if(driver!=null){
			
			
			browserName = ((RemoteWebDriver) (BrowserHelper.getWrappedDriver())).getCapabilities().getBrowserName();
		}
		return browserName;
	}
	
	public static String getBrowserVersion(){
		String browserVersion = "NOT_SET";
		if(driver!=null){
			if(System.getProperty("BrowserName").trim().equalsIgnoreCase("FIREFOX")){
				browserVersion = ((RemoteWebDriver)(BrowserHelper.getWrappedDriver())).getCapabilities().getCapability("browserVersion").toString();
			}
			else browserVersion = ((RemoteWebDriver)(BrowserHelper.getWrappedDriver())).getCapabilities().getVersion().toString();
		}
		return browserVersion;
	}
	
	public static void updateImplicitWaitTime(long timeInSeconds){
		if(driver!=null){
			driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
			currentImplicitWaitTime = timeInSeconds;
		}
	}
	
	public static void resetImplicitWaitTime(){		
		driver.manage().timeouts().implicitlyWait(OBJECT_SYNC_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		currentImplicitWaitTime = OBJECT_SYNC_DEFAULT_TIMEOUT;
	}
	
	/** 
	 * If a Driver has been wrapped by an Event Firing Driver 
	 * return the "orginal" firefox/chrome/IE etc. Driver. 
	 * @return
	 */
	public static WebDriver getWrappedDriver() {
		
		if (driver instanceof EventFiringWebDriver) {
			return ((EventFiringWebDriver) (driver)).getWrappedDriver();
		}
		else {
			return driver;
		}
	}
	
	
	public static WebDriver getDriver()
	{

		return driver;
	}
	
	public static WebElement getElement(By locator)
	{
		return driver.findElement(locator);
	}
	
	public static String getElementAttribue(By locator, String attribute)
	{
		WebElement element =  driver.findElement(locator);
	
		return element.getAttribute(attribute);
	}

	public static List<WebElement> getElements(By locator)
	{
		return driver.findElements(locator);
	}
		
	public static String getPageTitle()
	{  		  
		waitForNavigationToComplete(PAGE_SYNC_DEFAULT_TIMEOUT);		
		return driver.getTitle();
	}

	public static String getUrl()
	{   driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		
		int i = 1;
		while(i<20){
			String status = (String) ((JavascriptExecutor)driver).executeScript("return document.readyState;");
			
			if(status.equals("complete")){
				break;
			}
			i++;	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}			
		}
	
		return driver.getCurrentUrl();
	}
	
	public static void goToUrl() throws AWTException, InterruptedException
	{
		goToUrl(baseUrl);
	}

	public static void goToUrl(String url) throws AWTException, InterruptedException
	{
		
		driver.get(url);		
		
		}

	public static void initialiseDriver(String deviceType, String deviceName, String driverType, String browser) throws Exception
    {
		// check if Browser Name and Version rqeuired (as listed in the system properties
		//match the exsistings driver and if they do then do nothing more and use this instance of driver. 
		if (doesDriverExist()){
			if (doesDriverMatchDisiredProperties()) {
				return;
			}
			else {
				//if Driver does exist but does not match desired broswer and version - kill it and instationate a new one. 
				killDriver();
			}
		}
			
		driver = WebDriverFactory.getDriver(deviceType, deviceName, driverType, browser);
		 
    }

	public static void initialiseDriver() throws Exception
    {	String deviceType = System.getProperty("deviceType");
    	String deviceName = System.getProperty("deviceName");
    	String driverType = System.getProperty("driverType");    	
    	String browser = System.getProperty("BrowserName");
    	
    	
		driver = WebDriverFactory.getDriver(deviceType, deviceName, driverType, browser);
		
    }
	
	/** check existing Driver Browser and version against desired system properties */	
	public static boolean doesDriverMatchDisiredProperties(){
		
		Boolean expectedBrowser = System.getProperty("BrowserName").equalsIgnoreCase(getBrowserName());
		Boolean expectedVersion = getVersionNumberfromPathProperty().contains(getBrowserVersion());
		
		return expectedBrowser & expectedVersion;
	}
	
	/**check the version number of the browser desired, from the version Path property 
	 * If none is available then returns ""
	 * Returns the full folder path with any underscores and spaces replaced with dots. 
	 * @return
	 */
	public static String getVersionNumberfromPathProperty() {
		return System.getProperty("BrowserVersionPath").replace("_", ".").replace(" ", ".");
	}
	
	public static void killDriver()
	{
		if(driver!=null){
			driver.quit();
		}
	}

	public static void refresh()
	{
		driver.navigate().refresh();
	}

	public static void setUrl(String url)
	{
		baseUrl = url;
	}

	public static void switchFrame(String framePath)
	{
		driver.switchTo().defaultContent();
		
		if (framePath.length() > 0)
		{
			WebElement frame = getElement(By.xpath(framePath));
			driver.switchTo().frame(frame);
		}
	}

	public static void switchWindow() throws InterruptedException
	{
//		String currentHandle = driver.getWindowHandle();
//		Set<String> handles = driver.getWindowHandles();
//		
//		for (String handle : handles)
//		{
//			if (!handle.equals(currentHandle))
//			{
//				driver.switchTo().window(handle);
//				break;
//			}
//		}
//		
//		Thread.sleep(3000);	
		
		
		Set<String> handles = driver.getWindowHandles();
//		String handle = "";
		driver.switchTo().window((String) handles.toArray()[handles.size()-1]);		
		Thread.sleep(3000);
	}
	
	public static void switchWindow(String windowHandle) throws InterruptedException
	{
		driver.switchTo().window(windowHandle);
	}
	
	public static String getWindowHandle() throws InterruptedException
	{
		return driver.getWindowHandle();
	}

	public static File takeScreenShot() throws AWTException
	{
		Screenshot scre = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	}
	
	public static void takeScreenShotRobot(String filePath) throws AWTException, IOException
	{
		Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot robot = new Robot();
		BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
		ImageIO.write(bufferedImage, "png", new File(filePath));
	}
	
	public static void takeScreenShotAShot(String filePath) throws AWTException, IOException
	{
		//using ashot jar to take full page screenshots - not got to work yet
		
//		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()) .takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(),"PNG",new File(filePath));
		new WebDriverCoordsProvider();

	}

	public static void waitForElement(final By locator, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
		wait.until(new Function<WebDriver,Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return driver.findElement(locator) != null;
			}
		});
	}

	public static void waitForElements(final By locator, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
		wait.until(new Function<WebDriver,Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return driver.findElements(locator).size() > 0;
			}
		});
	}
	
	public static void waitForElementToBeDisplayed(final By locator, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
		wait.until(new Function<WebDriver,Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return driver.findElement(locator).isDisplayed();
			}
		});
	}
	
	public static void waitForElementToBeDisplayedAndEnabled(final By locator, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
		wait.until(new Function<WebDriver,Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return 
					driver.findElement(locator).isDisplayed() && 
					driver.findElement(locator).isEnabled();
			}
		});
	}
	
	
	public static void waitForElementToBeDisplayedAndEnabled(final WebElement element, int timeoutSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
		wait.until(new Function<WebDriver,Boolean>()
		{
			@Override
			public Boolean apply(WebDriver driver)
			{
				return 
					element.isDisplayed() && 
					element.isEnabled();
			}
		});
	}

//	@SuppressWarnings("unchecked")
//	public static void deviceRotate(String orientationString)
//	{
//		AndroidDriver<WebElement> androidDriver = (AndroidDriver<WebElement>) driver;
//		
//		String originalContext = androidDriver.getContext();
//
//		androidDriver.context("NATIVE_APP");
//		
//		if(orientationString.toLowerCase().equals("portrait"))
//			androidDriver.rotate(ScreenOrientation.PORTRAIT);
//		else
//			androidDriver.rotate(ScreenOrientation.LANDSCAPE);
//		
//		androidDriver.context(originalContext);
//	}

	public static void closeCurrentTab() throws Exception
	{
//		driver.close();
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"w"); //Commented this line as did not work well on Win7 machine - no detailed analysis done on this yet - 21-Feb-2017 - Nilesh P.
//      ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
//      driver.switchTo().window(tabs.get(0));
      switchWindow();
	}
	
	public static void executeJavascriptOnObject(String script, Object object) {
		((JavascriptExecutor) BrowserHelper.getDriver()).executeScript(script, object);
	}

	public static void browserNavigateBack() throws Exception
	{
		driver.navigate().back();
	}	
	
    public static void waitForNavigationToComplete(int timeoutSeconds) {
        final ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver,timeoutSeconds);
        wait.until(pageLoadCondition);
    }
    
    
    public static int getWindowHeight() throws Exception
    {
    	return driver.manage().window().getSize().height;
    }
    
    public static int getWindowWidth() throws Exception
    {
    	return driver.manage().window().getSize().width;
    }
    
    public static void registerMyDriverForEventListner(WebDriverEventListener eventLister){
    	if(eventLister!=null){
    		//use "orginal" Driver which may now allready have been wrapped by the Event Firing Driver 
    		EventFiringWebDriver eventDriver = new EventFiringWebDriver(getWrappedDriver());
    		eventDriver.register(eventLister);
    		driver = eventDriver;
    	}
    }
    
    public static void unRegisterMyDriverForEventListner (WebDriverEventListener eventLister) {
    	EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
    	eventDriver.unregister(eventLister);
    	driver = eventDriver;
    }
    
    public static void openNewTab() throws Exception

    {
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
//        driver.switchTo().window(tabs.get(0));
        switchWindow();

    }
  
	
	public static void acceptAlert() throws Exception{
		driver.switchTo().alert().accept();
	}
    
	public static boolean isAlertPresent(int secondsToWait) throws Exception
	{ 
	  int i = 0;
	  boolean alertPresent = false;
	  while (i<secondsToWait)
	  {
		  try 
		    { 
		        driver.switchTo().alert(); 
		        alertPresent = true;
		        if(alertPresent) break;
		    }   // try 
		    catch (NoAlertPresentException Ex) 
		    { 
		    	Thread.sleep(1000);
		        // do nothing
		    }   // catch 
		}   // isAlertPresent()
	  return alertPresent;
	}
	
	public static void dismissAlert() throws Exception
	{
		driver.switchTo().alert().dismiss();
	}
	
	public static String insertUsernamePasswordToURL(String url, String username, String password) throws Exception
	{
		String returnURL = "";
		
		if (url.trim().toLowerCase().contains("https://"))
		{
			String endURL= url.replace("https://", "");
			returnURL = "https://"+username+":"+TestHelper.decodeBase64(password)+"@"+endURL;
		}
		else if (url.trim().toLowerCase().contains("http://")){
			String endURL= url.replace("http://", "");
			returnURL = "http://"+username+":"+TestHelper.decodeBase64(password)+"@"+endURL;
		} 
		else fail("URL passed not valid - ensure url starts with 'https://' or 'http://'");
		
		return returnURL;
	}
	
	public static Actions getActionsBuilder() {
		return new Actions(driver);
		
		
	}
}
