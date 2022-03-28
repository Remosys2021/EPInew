package com.pms.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.pms.util.TestData;
import com.pms.util.WebPageNavigation;
import com.pms.util.WebelementType;
import jxl.read.biff.BiffException;

public class UserSignUp {

	private TestData testData;
	private WebPageNavigation navigation;
	public static ExtentReports extent =new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger;

	@BeforeSuite
	public void beforeSuiteSetup() {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		htmlReporter = new ExtentHtmlReporter("./Reports/UserLogin "+ timeStamp +".html");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setDocumentTitle("r");
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
	@Test
	public void loginURL() throws IOException, Exception
	{	
		WebPageNavigation.openBrowser("https://devstore.epicommercestore.com/pr-splash-page/account/login/");	

		logger = extent.createTest("Select Location");

		WebElement createOne = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Create one']");
		createOne.click();

		Thread.sleep(3000);
		
		WebElement firstName = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='firstname']");
		firstName.sendKeys("xyz");
		
		WebElement lastName = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='lastname']");
		lastName.sendKeys("abc");
		
		WebElement checkSubs = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='is_subscribed']");
		checkSubs.click();
		
		WebElement remoteAssist = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='assistance_allowed_checkbox']");
		remoteAssist.click();
		
		WebElement email = this.navigation.chooseElement(WebelementType.ID, "email_address");
		email.clear();
		email.sendKeys("skashyap@gmail.com");
		
		logger.log(Status.PASS, "Email entered");

		WebDriverWait wait = new WebDriverWait(this.navigation.getDriver(),90);	
		
		WebElement pass = this.navigation.chooseElement(WebelementType.XPATH, "//input[@name='password']");
		pass.clear();
		pass.sendKeys("welcome@123");
		
		WebElement cnfrmPass = this.navigation.chooseElement(WebelementType.ID, "password-confirmation");
		cnfrmPass.sendKeys("welcome@123");

		logger.log(Status.PASS, "Pass entered");

		WebElement createAnAccount = this.navigation.chooseElement(WebelementType.XPATH, "//button[@type='submit']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		createAnAccount.click();

		Thread.sleep(3000);

		WebElement welcomeName = this.navigation.chooseElement(WebelementType.CLASS_NAME, "logged-in");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sign Out']")));

		String welcomeNameText = welcomeName.getText();

		logger.log(Status.PASS, "New account created succesfully");
		
		logger.log(Status.PASS, "Sign in successful as :"+welcomeNameText);

		WebElement signOut = this.navigation.chooseElement(WebelementType.XPATH, "//a[text()='Sign Out']");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Sign Out']")));
		signOut.click();

		logger.log(Status.PASS, "Logout Successful");

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
		this.navigation.getDriver().quit();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		extent.flush();
	}

}
