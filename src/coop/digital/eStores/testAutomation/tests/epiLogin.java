package coop.digital.eStores.testAutomation.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class epiLogin {
	
	public static void main(String[] args) throws InterruptedException{

    System.setProperty("webdriver.chrome.driver", "/Users/PottCh/Documents/TestAutomation/eStores/ProjectResources/drivers/chromedriver");
	
		WebDriver driver= new ChromeDriver(setChromeCapability());
		
		driver.navigate().to("https://staging.electrical.coop.co.uk/episerver");
		Thread.sleep(5000);
		
		driver.findElement(By.id("LoginControl_UserName")).sendKeys("chris.potter@coopdigital.co.uk");
		driver.findElement(By.id("LoginControl_Password")).sendKeys("w.98.b2");
		driver.findElement(By.id("LoginControl_Button1")).click();
		
		driver.findElement(By.linkText("CMS")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("Admin")).click();
		Thread.sleep(1000);
		WebElement frame = driver.findElement(By.id("FullRegion_AdminMenu"));
		driver.switchTo().frame(frame);
		driver.findElement(By.xpath("//ul[@id='admin_tools_sub']//a[@href='/Plugins/GenerateVoucherCodes.aspx']")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		WebElement frame2 = driver.findElement(By.id("FullRegion_InfoFrame"));
		driver.switchTo().frame(frame2);
		driver.findElement(By.name("txtCampaign")).sendKeys("AUTO");
		driver.findElement(By.name("txtBatchId")).sendKeys("2");
		driver.findElement(By.name("txtNumber")).sendKeys("20");
		driver.findElement(By.name("txtValue")).sendKeys("50");
		driver.findElement(By.name("validFrom")).sendKeys("10/04/2017");
		driver.findElement(By.name("validTo")).sendKeys("10/30/2017");
		
		Thread.sleep(20000);
		
		driver.close();
		driver.quit();
	}
	
    private static DesiredCapabilities setChromeCapability(){
        
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--kiosk");
        options.addArguments("--start-maximized");
        
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);   	
    	
        return capabilities;    	
    }


}
