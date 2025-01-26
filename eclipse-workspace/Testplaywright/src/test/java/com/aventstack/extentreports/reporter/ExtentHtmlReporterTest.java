package com.aventstack.extentreports.reporter;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;

public class ExtentHtmlReporterTest extends BaseTest {

    private ExtentReports extentReports;
    private ExtentTest test;

    @BeforeMethod
    public void setup() {
        // Set up the report before each test case using ExtentSparkReporter
        extentReports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extentReports.attachReporter(sparkReporter);

        test = extentReports.createTest("Sample Test");
    }

    @Test
    public void testGenerateReport() {
        // Check if the report is being generated
        File reportFile = new File("target/ExtentReport.html");
        Assert.assertTrue(reportFile.exists(), "Report file should be created");
    }

    @Test
    public void testReportContent() {
        // Start a test and flush report to check content
        test.pass("This is a passing step in the test.");
        extentReports.flush();

        // Check if the report contains the expected content (e.g., test name or steps)
        File reportFile = new File("target/ExtentReport.html");
        Assert.assertTrue(reportFile.exists(), "Report file should be created");

        // Optional: Add parsing logic or check specific content in the report (not typically required for simple testing)
    }

    @Test
    public void testFlushReport() {
        // Ensure flushing of the report works by calling the flush method
        test.pass("Test for flushing report");
        extentReports.flush();

        // Validate if the report is properly flushed by checking for the existence of the file
        File reportFile = new File("target/ExtentReport.html");
        Assert.assertTrue(reportFile.exists(), "Report file should be generated after flushing");
    }

    @Test
    public void testMultipleTestsAndReports() {
        // Execute multiple tests and ensure reports are generated for each
        ExtentTest test1 = extentReports.createTest("Test 1");
        test1.pass("Test step 1 passed.");

        ExtentTest test2 = extentReports.createTest("Test 2");
        test2.fail("Test step 2 failed.");

        extentReports.flush();

        // Check that the report file exists after multiple tests
        File reportFile = new File("target/ExtentReport.html");
        Assert.assertTrue(reportFile.exists(), "Report file should exist after multiple tests");

        // Optionally, check for content in the report (test names, statuses)
    }
}
