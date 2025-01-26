package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import Pages.Homepage;
import Utils.ExtentManager;

public class HomePageTest extends BaseTest {

    @Test
    public void searchTest() {
        test = ExtentManager.getTest("Search Test");
        page.navigate("https://example.com");
        Homepage homePage = new Homepage(page);
        homePage.searchFor("Playwright");
        test.pass("Search functionality verified successfully.");
    }
}
