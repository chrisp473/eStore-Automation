package coop.digital.eStores.testAutomation.utilityAndFactories;


import static coop.digital.eStores.testAutomation.constants.Constants.OBJECT_SYNC_DEFAULT_TIMEOUT;
import static coop.digital.eStores.testAutomation.constants.Constants.PAGE_SYNC_DEFAULT_TIMEOUT;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import coop.digital.eStores.testAutomation.helpers.TestHelper;

public class WebDriverFactory {

	private static URL androidServerUrl;
	private static URL gridHubUrl;
	private static URL iosServerUrl;
//	static
//	{
//		try
//		{
//			androidServerUrl = new URL("http://localhost:4723/wd/hub");
//			gridHubUrl = new URL("http://localhost:4444/wd/hub");
////			iosServerUrl = new URL("http://10.26.138.138:4723/wd/hub");
////			iosServerUrl = new URL("http://192.168.1.75:4723/wd/hub");
//			iosServerUrl = new URL("http://169.254.248.5:4723/wd/hub");
//		}
//		catch (MalformedURLException e)
//		{
//			// Do nothing... exception not expected here.
//		}
//	}
	
	public static WebDriver getDriver(String deviceType, String deviceName, String driverType, String browser) throws Exception
	{
		
		
		
			WebDriver driver = null;
		
		switch (deviceType)
		{
			case "DESKTOP" :
				driver = getDesktopDriver(driverType, browser);
				break;
//			case "ANDROID" :
//			case "IOS" :
//				driver = getDeviceDriver(deviceType, deviceName, browser);
//				break;
			default:
				throw new Exception("Exception initialising driver");
		}

		return driver;
	}
	
	
	
	private static WebDriver getDesktopDriver(String driverType, String browser) throws Exception
	{
		WebDriver driver = null;		
		boolean isRemote = driverType.equals("REMOTE");
		
		if(isRemote){
			gridHubUrl = new URL("http://"+ System.getProperty("RemoteServerIP") +":"+ System.getProperty("RemoteServerport") +"/wd/hub");
		}
		
		switch (browser)
		{
			case "CHROME":
				driver = initialiseChromeDriver(isRemote);
				break;
			case "FIREFOX":
				driver = initialiseFirefoxDriver(isRemote);
				break;
			case "IE":
				driver = initialiseIEDriver(isRemote);
				break;
			case "SAFARI":
				driver = initialiseSafariDriver(isRemote);
				break;	
			default:
				throw new Exception("Exception initialising driver for browser: " + browser);
		}
		
        driver.manage().deleteAllCookies();
        driver.manage().window().setSize(new Dimension(2400,900));
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(PAGE_SYNC_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(OBJECT_SYNC_DEFAULT_TIMEOUT, TimeUnit.SECONDS);
		return driver;
	}
	
//	private static WebDriver getDeviceDriver(String deviceType, String deviceName, String browser) throws Exception
//	{
//		WebDriver driver = null;
//				
//		
//				
//		switch (deviceType)
//		{
//			case "ANDROID" :
//				androidServerUrl = new URL("http://"+ System.getProperty("AppiumServerIP") +":"+ System.getProperty("AppiumServerPort") +"/wd/hub");
//				driver = initialiseAndroidDriver(deviceName);
//				break;
//			case "IOS" :
//				iosServerUrl = new URL("http://"+ System.getProperty("AppiumServerIP") +":"+ System.getProperty("AppiumServerPort") +"/wd/hub");
//				driver = initialiseIosDriver(deviceName);
//				break;
//			default:
//				throw new Exception("Exception initialising driver for device: " + deviceName);
//		}
//		
//		return driver;
//	}
//
//	public static AndroidDriver<WebElement> initialiseAndroidDriver(String deviceName)
//	{		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability("deviceName", deviceName);
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("browserName", "Chrome");
//        capabilities.setCapability("newCommandTimeout", "3000"); // timeout in seconds
//        
//        try {
//			androidServerUrl = new URL("http://" + System.getProperty("AppiumServerIP") + ":"+ System.getProperty("AppiumServerIP") + "/wd/hub");
//		} catch (MalformedURLException e) {
//			// Do Nothing ...............
//		}	
//        return new AndroidDriver<WebElement>(androidServerUrl, capabilities);
//	}
	
	private static WebDriver initialiseChromeDriver(boolean isRemote) throws MalformedURLException
	{
		WebDriver driver;
		
		if (isRemote)
		{
			driver = initialiseRemoteChromeDriver();
		}
		else
		{
			driver = initialiseLocalChromeDriver();
		}
		
		return driver;
	}

	private static WebDriver initialiseFirefoxDriver(boolean isRemote) throws MalformedURLException
	{
		try {
			
		
			WebDriver driver;
		
			if (isRemote)
			{
				driver = initialiseRemoteFirefoxDriver();
			}
			else
			{
				driver = initialiseLocalFirefoxDriver();
			}
			
			return driver;
		}
		catch (Exception e)
		{
			
			
			return null;
		}
	}
	
	private static WebDriver initialiseSafariDriver(boolean isRemote) throws MalformedURLException
	{
		try {
			
		
			WebDriver driver;
		
			if (isRemote)
			{
				driver = initialiseRemoteSafariDriver();
			}
			else
			{
				driver = initialiseLocalSafariDriver();
			}
			
			return driver;
		}
		catch (Exception e)
		{
			
			
			return null;
		}
	}	

	private static WebDriver initialiseIEDriver(boolean isRemote) throws IOException
	{
		WebDriver driver=null;
		
		if (isRemote)
		{
			driver = initialiseRemoteIEDriver();
		}
		else
		{
			driver = initialiseLocalIEDriver();
		}
		
		return driver;
	}

//	private static WebDriver initialiseIosDriver(String deviceName)
//	{		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability("deviceName", deviceName);
//		capabilities.setCapability("platformName", "iOS");
//		capabilities.setCapability("platformVersion", "9.2");
////		capabilities.setCapability("udid", "901d19566744cfd91062b396d7f82717cec42cc7");
//		capabilities.setCapability("browserName", "safari");
//		
//        return new IOSDriver<WebElement>(iosServerUrl, capabilities);
//	}
	
    private static WebDriver initialiseLocalChromeDriver()
    {
    	String driverLocation = "";
    	if(System.getProperty("os.name").toLowerCase().contains("mac")){
    		driverLocation = TestHelper.getDriverDirectory() + File.separator+"chromedriver";
    	}
    	else driverLocation = TestHelper.getDriverDirectory() + File.separator+"chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverLocation);
        
        return new ChromeDriver(setChromeCapability());
    }
    
    // This method is not tested, can be tested on ly when executed on Windows7/10/Mac machines
    private static DesiredCapabilities setChromeCapability(){
    	
    	Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", TestHelper.getSystemProperty("rootDirectory")+File.separator+"Results"+File.separator+TestHelper.getSystemProperty("resultsFolder")+"/");
        
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);  
//        options.addArguments("--kiosk");
        options.addArguments("--start-maximized");
        
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);   	
    	
        return capabilities;    	
    }
    
    private static WebDriver initialiseLocalSafariDriver() throws MalformedURLException
    {    		
//    	return new SafariDriver();   
//    	DesiredCapabilities capabilities = DesiredCapabilities.safari();
//    	capabilities.setCapability(SafariOptions.CAPABILITY, new SafariOptions());
//    	WebDriver driver = new RemoteWebDriver(capabilities);
//    	return driver;
    	SafariOptions options = new SafariOptions();
//    	options.setUseCleanSession(true);
//    	WebDriver driver = new SafariDriver(options);
//    	return driver;
    	 DesiredCapabilities capabilities = DesiredCapabilities.safari();
    	 capabilities.setCapability(SafariOptions.CAPABILITY, options);
    	 RemoteWebDriver driver = new RemoteWebDriver(
    	     new URL("http://localhost:3333"), capabilities);
    	 return driver;

    }

    private static WebDriver initialiseRemoteSafariDriver() throws MalformedURLException
    {
    	DesiredCapabilities dc = DesiredCapabilities.safari();
    	return new RemoteWebDriver(gridHubUrl, dc);
    }
    
    private static WebDriver initialiseLocalFirefoxDriver()
    {   
    	String driverLocation = "";
    	if(System.getProperty("os.name").toLowerCase().contains("mac")){
    		driverLocation = TestHelper.getDriverDirectory() + File.separator+"geckodriver";
    	}
    	else driverLocation = TestHelper.getDriverDirectory() + File.separator+"geckodriver.exe";
    		System.setProperty("webdriver.gecko.driver", driverLocation);
    	
    	
    	return new FirefoxDriver(SetFireFoxCapabilities());    	
    }

    private static WebDriver initialiseLocalIEDriver() throws IOException
    {
    	String driverLocation = TestHelper.getDriverDirectory() + File.separator+"IEDriverServer.exe";
    	System.setProperty("webdriver.ie.driver", driverLocation);
    	
    	TestHelper.executeCmdFile("ClearIEHistory");

        return new InternetExplorerDriver(DesiredCapabilities.internetExplorer());
    }

    private static WebDriver initialiseRemoteChromeDriver() throws MalformedURLException
    {
    	String driverLocation = TestHelper.getDriverDirectory() + File.separator+"chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverLocation);
    	
        ChromeOptions options = new ChromeOptions();
        
    	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    	
        return new RemoteWebDriver(gridHubUrl, capabilities);
    }

    private static WebDriver initialiseRemoteFirefoxDriver() throws MalformedURLException
    {
    	return new RemoteWebDriver(gridHubUrl, SetFireFoxCapabilities());
    }

    private static WebDriver initialiseRemoteIEDriver() throws IOException
    {
    	String driverLocation = TestHelper.getDriverDirectory() + File.separator+"IEDriverServer.exe";
    	System.setProperty("webdriver.ie.driver", driverLocation);
    	
    	TestHelper.executeCmdFile("ClearIEHistory");

    	return new RemoteWebDriver(gridHubUrl, DesiredCapabilities.internetExplorer());
    }

    
	private static DesiredCapabilities SetFireFoxCapabilities() 
	{
    	DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    	String browserVersionPath = System.getProperty("BrowserVersionPath");
    	String fireFoxProfile = System.getProperty("FireFoxProfile");
    	    	
    	if (browserVersionPath != null && !browserVersionPath.equals("") ) {
    		capabilities.setCapability("firefox_binary",browserVersionPath + File.separator+"firefox.exe" );
    	}
    	// Sets the desired FF profile
    	if(fireFoxProfile!=null && !fireFoxProfile.equals("")){
    		FirefoxProfile fp = new ProfilesIni().getProfile("");
    		fp = new ProfilesIni().getProfile(fireFoxProfile);   
    		
    		//Start ----- This is Temporary fix -If Needed can be optimized
        	if(TestHelper.getSystemProperty("resultsFolder")!=null)
        	{
        		fp.setPreference("browser.download.manager.showWhenStarting",false);
        		fp.setPreference("browser.helperApps.neverAsk.saveToDisk","text/html, application/xhtml+xml, application/xml, application/csv, text/plain, application/vnd.ms-excel, text/csv, text/comma-separated-values, application/pdf");
        		fp.setPreference("browser.download.folderList",2);
        		fp.setPreference("browser.download.dir", TestHelper.getSystemProperty("rootDirectory")+File.separator+"Results"+TestHelper.getSystemProperty("resultsFolder")+File.separator);
        	}//End ----- This is Temporary fix
    		
	    	capabilities.setCapability(FirefoxDriver.PROFILE, fp);    		
    	}else{
    		FirefoxProfile fp = new FirefoxProfile();
        	fp.setPreference("browser.startup.homepage", "about:blank");
        	fp.setPreference("startup.homepage_welcome_url", "about:blank");
        	fp.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        	fp.setAssumeUntrustedCertificateIssuer(false);
        	fp.setAcceptUntrustedCertificates(true);
        	fp.setEnableNativeEvents(false);
//        	fp.setPreference("webdriver.load.strategy","unstable");
//        	fp.setPreference("network.proxy.type", 1);
//        	fp.setPreference("network.proxy.http", "localHost");
//        	fp.setPreference("newtwork.proxy.http_port",3128);
        	if(TestHelper.getSystemProperty("resultsFolder")!=null)
        	{
        		fp.setPreference("browser.download.manager.showWhenStarting",false);
        		fp.setPreference("browser.helperApps.neverAsk.saveToDisk","text/html, application/xhtml+xml, application/xml, application/csv, text/plain, application/vnd.ms-excel, text/csv, text/comma-separated-values, application/pdf");
        		fp.setPreference("browser.download.folderList",2);
        		fp.setPreference("browser.download.dir", TestHelper.getSystemProperty("rootDirectory")+File.separator+"Results"+File.separator+TestHelper.getSystemProperty("resultsFolder")+File.separator);
        	}
       	
        	capabilities.setCapability(FirefoxDriver.PROFILE, fp);    
    	}
		return capabilities;
	}

}
