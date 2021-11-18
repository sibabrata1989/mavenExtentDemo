package extentReportWithTestNG;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import commonCallForReport.ExcelHelper;
import commonCallForReport.commonLib;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExceptionWithScreenshot {

	// Report will generate in Project Directory only.
	// After execution, refresh project directory.

	// This is the object of extentTest class, by which log is generate.
	public static String baseDir, testDataDir;
	ExtentTest testlog;
	WebDriver driver;
	ExtentReports reports;
	ExcelHelper objExcel = new ExcelHelper();;
	commonLib common = new commonLib();

	@BeforeTest
	public void logWithScreenshot() throws IOException, InterruptedException {

		baseDir = System.getProperty("user.dir");
		testDataDir = baseDir + "/TestData/";
		//Sets header of the Test Data
		objExcel.SetListHeader(testDataDir + "TestData.xlsx", 0);
		//setup extent report
		reports = common.definition("Extent_Report_with_Screenshot");
		// Chrome Driver should be installed in your System
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/TestResources/chromedriver_85_win32/chromedriver");
		driver = new ChromeDriver();
		Properties prp = common.getPropertyFile();
		driver.get(prp.getProperty("Url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


	}

	@Test(priority = 1)
	public void PositiveTest() {
		try {
			testlog = reports.createTest("TC01_Pass");
			//handled the pop
			WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("at-cv-lightbox-close")));
			boolean popup = driver.findElement(By.id("at-cv-lightbox-close")).isDisplayed();
			if(popup) {
				driver.findElement(By.id("at-cv-lightbox-close")).click();
			}
			//fetch the test data and set into an array
			objExcel.SetListData(testDataDir + "TestData.xlsx", "TC01_Pass");
			driver.findElement(By.xpath("//li[@class='tree-branch']//li[1]//i[1]")).click();
			driver.findElement(By.xpath("//li[@class='tree-branch']//ul//li//a[contains(text(),'Input Form Submit')]")).click();
			driver.findElement(By.name("first_name")).sendKeys(objExcel.GetValue(0, "FirstName"));
			driver.findElement(By.name("last_name")).sendKeys(objExcel.GetValue(0, "LastName"));
			driver.findElement(By.name("email")).sendKeys(objExcel.GetValue(0, "Email"));
			driver.findElement(By.name("phone")).sendKeys(objExcel.GetValue(0, "Phone"));
			driver.findElement(By.name("address")).sendKeys(objExcel.GetValue(0, "Address"));
			driver.findElement(By.name("city")).sendKeys(objExcel.GetValue(0, "City"));
			driver.findElement(By.name("state")).click();
			Select select = new Select(driver.findElement(By.name("state")));
			select.selectByVisibleText(objExcel.GetValue(0, "State"));
			driver.findElement(By.name("zip")).sendKeys(objExcel.GetValue(0, "Zip"));
			driver.findElement(By.name("website")).sendKeys(objExcel.GetValue(0, "Website"));
			driver.findElement(By.xpath("//input[@type='radio' and @value='yes']")).click();
			driver.findElement(By.name("comment")).sendKeys(objExcel.GetValue(0, "Description"));
			//takes screenshot after form is filled
			common.takeScreenshot(driver, testlog,"TC01_Pass");
			driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
			//Logs the pass result
			testlog.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed is PositiveTest", ExtentColor.GREEN));
		}
		catch (Exception e)
		{
			Assert.fail("PositiveTest test case failed", e.fillInStackTrace());
		}
	}

	@Test(priority = 2)
	public void NegativeTest() {
		try {
			testlog = reports.createTest("TC02_Fail");
			objExcel.SetListData(testDataDir + "TestData.xlsx", "TC02_Fail");
			driver.findElement(By.xpath("//li[@class='tree-branch']//li[1]//i[1]")).click();
			driver.findElement(By.xpath("//li[@class='tree-branch']//ul//li//a[contains(text(),'Input Form Submit')]")).click();
			driver.findElement(By.name("first_name")).sendKeys(objExcel.GetValue(0, "FirstName"));
			driver.findElement(By.name("last_name")).sendKeys(objExcel.GetValue(0, "LastName"));
			driver.findElement(By.name("email")).sendKeys(objExcel.GetValue(0, "Email"));
			driver.findElement(By.name("phone")).sendKeys(objExcel.GetValue(0, "Phone"));
			driver.findElement(By.name("address")).sendKeys(objExcel.GetValue(0, "Address"));
			driver.findElement(By.name("city")).sendKeys(objExcel.GetValue(0, "City"));
			driver.findElement(By.name("state")).click();
			Select select = new Select(driver.findElement(By.name("state")));
			select.selectByVisibleText(objExcel.GetValue(0, "State"));
			driver.findElement(By.name("zip")).sendKeys(objExcel.GetValue(0, "Zip"));
			driver.findElement(By.name("website")).sendKeys(objExcel.GetValue(0, "Website"));
			driver.findElement(By.xpath("//input[@type='radio' and @value='yes']")).click();
			driver.findElement(By.name("comment")).sendKeys(objExcel.GetValue(0, "Description"));
			common.takeScreenshot(driver,testlog,"TC02_Fail");
			driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
			// TODO: 16-09-2020  //The application doesn't throws any error message for invalid email and phone number, So I cannot put any assertion
			testlog.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed is NegativeTest", ExtentColor.GREEN));
		}
		catch (Exception e)
		{
			Assert.fail("NegativeTest test case failed", e.fillInStackTrace());
		}
	}

//Generate the result for failure and skipped
	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			testlog.log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			testlog.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable().fillInStackTrace() + " - Test Case Failed", ExtentColor.RED));

		} else if (result.getStatus() == ITestResult.SKIP) {
			// testlog.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			testlog.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
		}
		//Clear the test data array for next test case
		objExcel.ClearDataList();
	}

	@AfterTest
	public void quit() {
		// If flush method did not call, Report will not generate.
		common.flushReport();
		driver.close();
	}

}
