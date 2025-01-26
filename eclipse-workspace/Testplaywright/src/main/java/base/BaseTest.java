package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected ExtentTest test;
    protected ExtentReports extentReports;
    protected ExtentSparkReporter htmlReporter;

    @BeforeSuite
    public void setupReport() {
        // Initialize ExtentReport
        htmlReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    @BeforeMethod
    @Parameters({"browserName"})
    public void setup(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);

        if (browserName != null) {
            switch (browserName.toLowerCase()) {
                case "firefox":
                    browser = playwright.firefox().launch(launchOptions);
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(launchOptions);
                    break;
                default:
                    browser = playwright.chromium().launch(launchOptions);
                    break;
            }
        } else {
            // Default to chromium if browserName is null
            browser = playwright.chromium().launch(launchOptions);
        }
        context = browser.newContext();
        page = context.newPage();

        // Start the test for Extent Reports
        test = extentReports.createTest("Test Case: " + browserName);
    }

    @AfterMethod
    public void tearDown() {
        // Close browser and context
        context.close();
        browser.close();
        playwright.close();

        // Flush the report after each test method
        extentReports.flush();
    }

    @AfterSuite
    public void flushReport() {
        // Finalize the report at the end of the test suite
        extentReports.flush();
    }
}
