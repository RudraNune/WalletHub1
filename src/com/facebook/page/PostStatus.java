package com.facebook.page;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class PostStatus {
	
	public static WebDriver driver;
	public static Properties prop;
 
	
	public PostStatus(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\facebook\\properties"
					+ "\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void initialization(){
		String browserName = prop.getProperty("browser");
		
		if(browserName.equals("chrome")){
			
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\com\\facebook\\resources\\chromedriver.exe");
			HashMap<String, Object> perfsm = new HashMap<String, Object>();
			perfsm.put("profile.default_content_settings.popups", 0);
			ChromeOptions cp = new ChromeOptions();
			cp.addArguments("--disable-notifications");
			cp.setExperimentalOption("prefs", perfsm);
				
			driver = new ChromeDriver(cp); 
 		}
		else if(browserName.equals("IE")){
			
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\com\\facebook\\resources\\IEDriverServer.exe");	
			driver = new InternetExplorerDriver(); 
		}
	}


	public static void main(String[] args) throws InterruptedException {
 
		PostStatus PS = new PostStatus();
		PS.initialization();
		
//		Setting browsers
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));

//		Logging to facebook application
		driver.findElement(By.id("email")).sendKeys(prop.getProperty("username"));
		driver.findElement(By.id("pass")).sendKeys(prop.getProperty("password"));
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	js.executeScript("document.getElementById('u_0_b').click()");
    	
//    	CLick on Create Post and update the status as 'Hello World' 
		driver.findElement(By.xpath("//*[text()='Create post']")).click();
		Actions ac = new Actions(driver);
		WebElement txtelement = driver.findElement(By.xpath("//div[@id='pagelet_composer']//textarea"));
			ac.moveToElement(txtelement)
							.doubleClick()
							.sendKeys("Hello World")
							.build()
							.perform();
	 	driver.findElement(By.xpath("//button//*[text()='Post']")).click();
		
	}
	

}
