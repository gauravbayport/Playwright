package Utils;

import com.microsoft.playwright.*;

import java.util.HashMap;
import java.util.Map;

public class PlaywrightFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    // ThreadLocal for parallel execution
    private static final ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> threadLocalBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();

    // Configurable browser options
    private static final Map<String, BrowserType.LaunchOptions> browserOptions = new HashMap<>();

    static {
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(false) // Set to true if running in CI/CD
                .setSlowMo(50);    // Adds a delay between actions for debugging
        browserOptions.put("chromium", launchOptions);
        browserOptions.put("firefox", launchOptions);
        browserOptions.put("webkit", launchOptions);
    }

    /**
     * Initializes Playwright and the selected browser.
     *
     * @param browserName Name of the browser (chromium, firefox, webkit)
     * @return Page object for further interactions
     */
    public Page initBrowser(String browserName) {
        // Initialize Playwright
        playwright = Playwright.create();
        threadLocalPlaywright.set(playwright);

        // Launch the specified browser
        BrowserType.LaunchOptions options = browserOptions.getOrDefault(browserName.toLowerCase(), browserOptions.get("chromium"));
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(options);
                break;
        }
        threadLocalBrowser.set(browser);

        // Create a browser context and page
        browserContext = browser.newContext();
        threadLocalBrowserContext.set(browserContext);

        page = browserContext.newPage();
        threadLocalPage.set(page);

        return page;
    }

    /**
     * Gets the current Playwright instance for the thread.
     *
     * @return Playwright instance
     */
    public static Playwright getPlaywright() {
        return threadLocalPlaywright.get();
    }

    /**
     * Gets the current Browser instance for the thread.
     *
     * @return Browser instance
     */
    public static Browser getBrowser() {
        return threadLocalBrowser.get();
    }

    /**
     * Gets the current BrowserContext instance for the thread.
     *
     * @return BrowserContext instance
     */
    public static BrowserContext getBrowserContext() {
        return threadLocalBrowserContext.get();
    }

    /**
     * Gets the current Page instance for the thread.
     *
     * @return Page instance
     */
    public static Page getPage() {
        return threadLocalPage.get();
    }

    /**
     * Closes the Playwright instance and all associated resources.
     */
    public void tearDown() {
        if (browserContext != null) {
            browserContext.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }

        threadLocalPlaywright.remove();
        threadLocalBrowser.remove();
        threadLocalBrowserContext.remove();
        threadLocalPage.remove();
    }
}
