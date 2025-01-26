package test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page.ScreenshotOptions;

public class Launchbrowser {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private ExtentReports extentReports;
    private ExtentTest extentTest;

    // Initialize ExtentReports
    @BeforeMethod
    public void setUp() {
        // Set up the ExtentSparkReporter
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);

        // Start a new test in the report
        extentTest = extentReports.createTest("Launch Browser Test", "Test for launching browser in Chrome and generating report");

        // Initialize Playwright and launch Chrome
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setChannel("chrome") // Specify Google Chrome
                .setHeadless(false)); // Launch in visible mode

        // Create a new browser context and page
        context = browser.newContext(new NewContextOptions());
        page = context.newPage();

        extentTest.info("Chrome browser launched successfully.");
    }

    @Test
    public void testLaunchBrowser() {
        try {
            // Navigate to a website
            page.navigate("https://www.bannerbuzz.com/");

            // Verify page title
            String title = page.title();
            System.out.println("Page Title: " + title);
            Assert.assertEquals(title, "Example Domain"); // Intentional failure for screenshot capture

            // Log information in Extent Report
            extentTest.pass("Navigated to Example Domain successfully.");
        } catch (Exception e) {
            // Capture screenshot if test fails
            try {
                String screenshotPath = captureScreenshot();
                extentTest.fail("Test failed due to exception: " + e.getMessage())
                         .addScreenCaptureFromPath(screenshotPath);
            } catch (IOException ioException) {
                extentTest.fail("Failed to capture screenshot: " + ioException.getMessage());
            }
        }
    }

    // Helper method to capture screenshot
    private String captureScreenshot() throws IOException {
        // Create the screenshots directory if it does not exist
        Path screenshotDir = Paths.get("target/screenshots");
        if (!screenshotDir.toFile().exists()) {
            screenshotDir.toFile().mkdirs();
        }

        // Save the screenshot in the target/screenshots directory
        Path screenshotPath = screenshotDir.resolve("failure-screenshot.png");
        page.screenshot(new ScreenshotOptions().setPath(screenshotPath).setFullPage(true));

        // Return the relative path for use in the report
        return "screenshots/failure-screenshot.png";
    }

    @AfterMethod
    public void tearDown() {
        try {
            // Log the browser close event in the report
            extentTest.info("Closing the Chrome browser...");

            // Close the browser
            if (context != null) {
                context.close();
            }
            if (browser != null) {
                browser.close();
            }
        } finally {
            // Finalize the report (write the results to the file)
            extentReports.flush();
            System.out.println("Extent report generated.");
        }

        // Ensure Playwright is stopped
        if (playwright != null) {
            playwright.close();
        }
    }
}
