package testdata;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    /**
     * Provides test data for test methods.
     *
     * @return Object[][] containing test data for parameterized tests
     */
    @DataProvider(name = "loginData")
    public Object[][] provideLoginData() {
        return new Object[][] {
            {"testuser1", "password1", true},   // valid username and password
            {"testuser2", "password2", true},   // valid username and password
            {"invalidUser", "password", false}, // invalid username
            {"testuser1", "wrongPassword", false} // valid username, invalid password
        };
    }

    /**
     * Provides test data for test methods that test the search functionality.
     *
     * @return Object[][] containing test data for the search test
     */
    @DataProvider(name = "searchData")
    public Object[][] provideSearchData() {
        return new Object[][] {
            {"Laptop", true},  // valid search term
            {"InvalidProduct", false}, // invalid search term
            {"Playwright", true}  // valid search term
        };
    }

    /**
     * Provides test data for checkout process.
     *
     * @return Object[][] containing test data for checkout tests
     */
    @DataProvider(name = "checkoutData")
    public Object[][] provideCheckoutData() {
        return new Object[][] {
            {"item1", 2, 100.0, true},  // Item1, quantity 2, total 100.0, valid case
            {"item2", 0, 0.0, false},   // Item2, quantity 0, invalid case
            {"item3", 3, 150.0, true}   // Item3, quantity 3, valid case
        };
    }
}
