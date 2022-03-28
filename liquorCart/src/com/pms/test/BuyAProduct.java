package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.pms.util.TestData;
import com.pms.util.WebPageNavigation;
import com.pms.util.WebelementType;

import jxl.read.biff.BiffException;

public class BuyAProduct {

	private TestData testData;
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/buyAproduct "+ timeStamp +".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("SingleUserLogin");
	}

	@BeforeMethod
	@Parameters("browserName")
	public void launchUrl(String browserName) throws BiffException, IOException
	{				
		testData = new TestData("SingleUserLogin");

		this.navigation = new WebPageNavigation(testData,browserName);

		htmlReporter.config().setReportName("SingleUserLogin "+browserName);

		logger = extent.createTest("Browser Name :" +browserName);
	}
	@Test(alwaysRun = true)
	public void loginURL() throws IOException, Exception
	{	

		WebPageNavigation.openBrowser("https://devstore.epicommercestore.com/storelocator");	

		logger = extent.createTest("Select Location");

//		WebElement deliveryAddress = this.navigation.chooseElement(WebelementType.XPATH, "//input[@type='search']");
//		deliveryAddress.sendKeys("boca");
//	
//		WebElement Continue = this.navigation.chooseElement(WebelementType.XPATH, "//button[text()='Continue']");
//		Continue.click();
//		Continue.click();

		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);
		
		JavascriptExecutor js = (JavascriptExecutor) this.navigation.getDriver();
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		WebElement clickOnStore = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Boca Liquors']");
		js.executeScript("arguments[0].scrollIntoView()",clickOnStore);
		clickOnStore.click();

		Set<String> ids = this.navigation.getDriver().getWindowHandles();
		Iterator<String> iterator = ids.iterator();
		String parentID = iterator.next();
		String childID = iterator.next();
		
		this.navigation.getDriver().switchTo().window(childID);

		WebElement SignInButton = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign In']");
		SignInButton.click();

		Thread.sleep(3000);

		WebElement email = this.navigation.chooseElement(WebelementType.ID, "email");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
		email.sendKeys("sonal.kashyap140587@gmail.com");

		logger.log(Status.PASS, "Email entered");

		WebElement pass = this.navigation.chooseElement(WebelementType.ID, "pass");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("pass")));
		pass.sendKeys("welcome@123");

		logger.log(Status.PASS, "Pass entered");

		WebElement SignIn = this.navigation.chooseElement(WebelementType.XPATH, "//button[@name='send']");
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		SignIn.click();


		WebElement searchItem = this.navigation.chooseElement(WebelementType.XPATH, "//input[@type='text']");
		searchItem.sendKeys("vodka");
		searchItem.submit();
		
		Thread.sleep(3000);

		JavascriptExecutor js1 = (JavascriptExecutor)this.navigation.getDriver();
		js1.executeScript("window.scrollBy(0,350)", "");


		List<WebElement> listOfItem = this.navigation.getDriver().findElements(By.className("product-item-info"));

		for(int i=0;i<listOfItem.size();i++)
		{
			String listOfItemText = listOfItem.get(i).getText();

			System.out.println(listOfItemText);

			if(listOfItemText.contains("360 VODKA"))
			{	
				WebElement productAdd = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"product-item-info_52382\"]/a/span/span/img");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"product-item-info_52382\"]/a/span/span/img")));
				productAdd.click();			
			}
			
			break;
		}

		Thread.sleep(5000);

		WebElement addToCart = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Add to Cart']");
		addToCart.click();

		Thread.sleep(5000);
		
		js1.executeScript("", "window.scrollBy(0,350)");

		WebElement proceedToCheck = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"maincontent\"]/div[3]/div/div[2]/div[1]/ul/li[1]/button")));
		proceedToCheck.click();

		Thread.sleep(8000);
		List<WebElement> listOfElements = this.navigation.getDriver().findElements(By.xpath("//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr/td"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"label_method_flatrate_flatrate\"]")));

		for(int j=0;j<listOfElements.size();j++)
		{
			String listOfItemText = listOfElements.get(j).getText();
			System.out.println(listOfItemText);

			Thread.sleep(500);
			if(listOfItemText.contains("Flat Rate"))
			{
				WebElement radio = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"label_method_flatrate_flatrate\"]");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"label_method_flatrate_flatrate\"]")));
				radio.click();
			}
		}


		WebElement next = this.navigation.chooseElement(WebelementType.XPATH, "//button[@type='submit']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		next.click();

		Thread.sleep(5000);

		WebElement proceedToCheckout = this.navigation.chooseElement(WebelementType.XPATH, "//*[@id=\"checkout-payment-method-load\"]/div/div/div[2]/div[2]/div[4]/div/button");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout-payment-method-load\"]/div/div/div[2]/div[2]/div[4]/div/button")));
		proceedToCheckout.click();

		Thread.sleep(5000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rccs__card--front")));

		WebElement creditCard = this.navigation.chooseElement(WebelementType.XPATH, "/html/body/input[16]");
		creditCard.sendKeys("4111 1111  1111 1111");

		WebElement cardholderName = this.navigation.chooseElement(WebelementType.XPATH, "//label[text()='Card Holder Name']");
		cardholderName.sendKeys("Sonal K");

		WebElement cvvNum = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='Data']");
		cvvNum.sendKeys("1234");

		WebElement date = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='expiry']");
		date.sendKeys("0222");

		WebElement pay = this.navigation.chooseElement(WebelementType.XPATH, "//button[text()='PAY']");
		pay.click();
	}
	@AfterMethod
	public void getResult(ITestResult result) throws Exception
	{
		try {
			@SuppressWarnings("static-access")
			String screenshotPath = this.navigation.getScreenshot(this.navigation.getDriver(),result.getName());
			if(result.getStatus() == ITestResult.FAILURE){
				logger.addScreenCaptureFromPath(screenshotPath);

			}else if(result.getStatus() == ITestResult.SKIP){
				logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			}}catch(Exception e)
		{
				//logger.log(Status.ERROR, "Test Error");
		}
	}
	@AfterTest
	public void tearDown()
	{
		//	this.navigation.getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
	}
}

