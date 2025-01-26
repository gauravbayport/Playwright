package test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Page;

import Utils.PlaywrightFactory;

public class LaunchTest {

    private PlaywrightFactory playwrightFactory;
    private Page page;
    private ExtentReports extentReports;
    private ExtentTest extentTest;

    // Initialize ExtentReports
    @BeforeMethod
    public void setUp() {
        // Set up the extent report
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);

        // Start a new test in the report
        extentTest = extentReports.createTest("Launch Browser Test", "Test for launching browser and generating report");

        // Initialize PlaywrightFactory and browser
        playwrightFactory = new PlaywrightFactory();
        page = playwrightFactory.initBrowser("chromium");

        extentTest.info("Browser launched successfully.");
    }

    @Test
    public void testLaunchBrowser() {
        // Navigate to a website
        page.navigate("https://www.bannerbuzz.com/");

        // Verify page title
        String title = page.title();
        System.out.println("Page Title: " + title);
        Assert.assertEquals(title, "Custom Banner Printing, Banners & Signs Online - BannerBuzz");

        // Log information in Extent Report
        extentTest.pass("Navigated to Example Domain successfully.");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser and report
        playwrightFactory.tearDown();
        extentReports.flush();  // Finalize the report

        extentTest.info("Browser closed and report finalized.");
    }
}
