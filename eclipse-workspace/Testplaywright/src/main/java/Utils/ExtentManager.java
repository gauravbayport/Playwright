package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;
    private static ExtentSparkReporter htmlReporter;

    public static void initReport() {
        htmlReporter = new ExtentSparkReporter("target/ExtentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }

    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

	public static ExtentTest getTest(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
