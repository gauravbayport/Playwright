package Pages;

import com.microsoft.playwright.Page;

public class Homepage {
    private final Page page;

    // Locators
    private final String searchBox = "input#search";
    private final String searchButton = "button#search-btn";

    // Constructor
    public Homepage(Page page) {
        this.page = page;
    }

    // Actions
    public void searchFor(String query) {
        page.fill(searchBox, query);
        page.click(searchButton);
    }
}
