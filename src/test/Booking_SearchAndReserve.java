package test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Booking_SearchAndReserve {
WebDriver driver;
	
	//Pre conditions annotations -- starting with @Before
	@BeforeMethod
	@Parameters({"browser","url"})
	public void setUp(String browser,String url) {
		//Set browser system path for both windows and mac and initialize driver
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("mac")) {
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
				driver = new ChromeDriver();
			} else if (browser.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/geckodriver");
				driver = new FirefoxDriver();
			}
		}else {
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browser.equals("firefox")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Navigate to booking.com
		driver.get(url);
	}
	
	@Test(priority=1,groups="search&reserve",timeOut=30000)
	public void getPageLoad() throws InterruptedException {
		System.out.println("getPageLoad start.");
		//validate page title
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertTrue(title.contains("Booking.com"),"title is not matched");
		
		//validate page load
		WebElement cityInputBox=driver.findElement(By.id("ss"));
		Assert.assertTrue(cityInputBox.isDisplayed());
		drawBorder(driver,cityInputBox);
		Thread.sleep(1000);
		System.out.println("getPageLoad is done.");
	}
	
	@Test(priority=2,groups="search&reserve",dependsOnMethods="getPageLoad",timeOut=30000)
	@Parameters({"cityName"})
	public void searchTest(String cityName) throws InterruptedException{
		System.out.println("searchTest start.");
		//Input city name
		WebElement cityInputBox=driver.findElement(By.id("ss"));
		cityInputBox.clear();
		cityInputBox.sendKeys(cityName);
		
		//Open date picker and select today
		driver.findElement(By.cssSelector(".sb-date-field__icon")).click();;
		WebElement dateCheckIn= driver.findElement(By.cssSelector(".bui-calendar__date--today"));
		Assert.assertTrue(dateCheckIn.isDisplayed());
		dateCheckIn.click();
		
		WebElement searchBtn=driver.findElement(By.cssSelector(".sb-searchbox__button"));
		//Highlight the search button
		drawBorder(driver,searchBtn);
		//Click by explicit wait
		clickOn(driver,searchBtn,10);
		
		//Navigate to search result page
		Thread.sleep(2000);
		System.out.println("Search result page title: "+ driver.getTitle());
		WebElement headText=driver.findElement(By.cssSelector(".sr_header "));
		Assert.assertTrue(headText.getText().contains("properties found"));
		Assert.assertTrue(headText.getText().startsWith("Los Angeles"));
		
		//Click the first cta button
		List <WebElement> ctaBtns=driver.findElements(By.cssSelector(".sr_cta_button"));
		drawBorder(driver,ctaBtns.get(0));
		clickOn(driver,ctaBtns.get(0),10);
		
		//Navigate to room reserve page
		//Need to switch to the new window
		Thread.sleep(2000);
		Set<String> handler =driver.getWindowHandles();
		Iterator<String> it=handler.iterator();
		it.next();
		driver.switchTo().window(it.next());
		System.out.println("Room reserve page title: "+ driver.getTitle());
		//locate to the room reserve section
		WebElement txtAvailable = driver.findElement(By.xpath("//*[@id='availability_target']"));
		Assert.assertTrue(txtAvailable.isDisplayed());
		scrollIntoView(driver,txtAvailable);
		
		Select aptSelect=new Select(driver.findElement(By.cssSelector(".hprt-nos-select")));
		aptSelect.selectByIndex(aptSelect.getOptions().size()-1);	
		List <WebElement> bookBtns=driver.findElements(By.cssSelector(".js-reservation-button"));
		drawBorder(driver,bookBtns.get(0));
		clickOn(driver,bookBtns.get(0),10);
		
		//Navigate to checkout page
        //Sometimes it pops up dialog, close it
		Thread.sleep(2000);
		if (driver.findElement(By.cssSelector(".bp_rlu_footer_close")).isDisplayed()) {
			driver.findElement(By.cssSelector(".bp_rlu_footer_close")).click();
		}
		System.out.println("Checkout page title: "+ driver.getTitle());
		Assert.assertTrue(driver
				.findElement(By.cssSelector(".bp_legacy_form_box__title"))
				.getText().contains("Enter Your Details"));
		//locate to information input section
		WebElement firstname = driver.findElement(By.id("firstname"));
		scrollIntoView(driver,firstname);
		drawBorder(driver,firstname);
		firstname.sendKeys("Zheng");
		Thread.sleep(1000);
		System.out.println("searchTest is done.");
	}
	
	//Explicit wait
	public static void clickOn(WebDriver driver, WebElement locator, int timeout) {
		new WebDriverWait(driver,timeout)
		.ignoring(StaleElementReferenceException.class)
		.until(ExpectedConditions.elementToBeClickable(locator));
		locator.click();
	}
	
	//Draw border
	public static void drawBorder(WebDriver driver, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	//scroll down to an element
	public static void scrollIntoView(WebDriver driver, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
	//Post conditions
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}	
}