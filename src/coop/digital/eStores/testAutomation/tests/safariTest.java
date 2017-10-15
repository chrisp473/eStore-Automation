package coop.digital.eStores.testAutomation.tests;



import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

public class safariTest {

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
//    	DesiredCapabilities capabilities = DesiredCapabilities.safari();
//    	capabilities.setCapability(SafariOptions.CAPABILITY, new SafariOptions());
//    	WebDriver driver = new RemoteWebDriver(capabilities);
//    	return driver;
    	SafariOptions options = new SafariOptions();
    	options.setUseCleanSession(true);
//    	WebDriver driver = new SafariDriver(options);
//    	return driver;
    	 DesiredCapabilities capabilities = DesiredCapabilities.safari();
    	 capabilities.setBrowserName("Safari");
    	 capabilities = DesiredCapabilities.safari();
    	 capabilities.setCapability(SafariOptions.CAPABILITY, options);
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:1111"), capabilities);

		driver.get("http://google.co.uk");
		


	}

}
