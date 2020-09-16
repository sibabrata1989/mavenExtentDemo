package commonCallForReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class commonLib {

	public ExtentHtmlReporter htmlreport;
	public static ExtentReports reports;
	// This is the object of extentTest class, by which log is generate.
	public static ExtentTest testlog;

	public ExtentReports definition(String reportName) {
		// Report will generate in Project Directory only.
		// After execution, refresh project directory.
		htmlreport = new ExtentHtmlReporter(".\\Report\\" + reportName + ".html");
		reports = new ExtentReports();
		reports.attachReporter(htmlreport);

		// Customize Report property
		htmlreport.config().setReportName("Test Report");
		reports.setSystemInfo("Host Name", "Test Host");
		reports.setSystemInfo("Environment", "Automation Testing");
		reports.setSystemInfo("User Name", "QA Automation");
		htmlreport.config().setDocumentTitle("Automation Report");
		htmlreport.config().setTestViewChartLocation(ChartLocation.TOP);

		// Two default theme of report
		htmlreport.config().setTheme(Theme.STANDARD);
		// htmlreport.config().setTheme(Theme.DARK);
		return reports;
	}

	public void flushReport() {
		// If flush method did not call, Report will not generate.
		reports.flush();
	}

	public void takeScreenshot(WebDriver driver,ExtentTest testlog, String Test) throws IOException {
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage img = ImageIO.read(screen);
		File filetest = Paths.get(".").toAbsolutePath().normalize().toFile();
		ImageIO.write(img, "png", new File(filetest + "\\Screenshots\\" +Test+".png"));
		testlog.info("Details of " + "Test screenshot", MediaEntityBuilder
				.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\" + Test+".png").build());
	}
	//Properties file configuration
	public static Properties getPropertyFile() throws IOException
	{
		Properties prp = new Properties();
		FileInputStream fs = new FileInputStream("./TestConfig/testConfig.properties");
		prp.load(fs);
		return prp;
	}
}
