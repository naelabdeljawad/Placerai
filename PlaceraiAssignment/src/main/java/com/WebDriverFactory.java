package com;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    private static DesiredCapabilities webCapabilities;
    private static EventFiringWebDriver listenerDriver;
    private static WebEventListener eventListener;
    private static ChromeDriverService service;
    private static ChromeDriver chromeDriver;

    private static ChromeOptions getChromeCapabilities() {
        /**
         * Webdriver caps with chrome options
         */
        Map<String, Object> prefs = new HashMap<String, Object>();
        ChromeOptions chromeOptions;
        chromeOptions = new ChromeOptions();
        prefs.put("profile.default_content_settings.popups", 0);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("window-size=1920,1080");

        /**
         * Set chrome browser capabilities to launch browser
         */
        webCapabilities = DesiredCapabilities.chrome();
        webCapabilities.setCapability("platform", Platform.MAC);
        webCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions.addArguments("start-maximized"));

        return chromeOptions.merge(webCapabilities);
    }

    /**
     * Browser events listener - driver registration is needed
     *
     * @param webDriver
     * @return
     */
    private static EventFiringWebDriver registerWebDriverToListener(WebDriver webDriver) {
        listenerDriver = new EventFiringWebDriver(webDriver);
        eventListener = new WebEventListener();
        return listenerDriver.register(eventListener);
    }

    /**
     * Create chrome browser instance
     *
     * @return
     */
    public static WebDriver createChromeDriver() {
        StringBuffer buffer = new StringBuffer();
        try {
            service = new ChromeDriverService.Builder().usingDriverExecutable(
                    new File(String.valueOf(buffer.append(System.getProperty("user.dir")).append(File.separator)
                            .append("resources").append(File.separator).append("drivers").append(File.separator).append("chromedriverMac"))))
                    .usingAnyFreePort().build();
            service.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        chromeDriver = new ChromeDriver(service, getChromeCapabilities());
        return registerWebDriverToListener(chromeDriver);
    }

    /**
     * Close driver and stop service if running
     */
    public static void closeDriver() {
        chromeDriver.close();

        if (service.isRunning())
            service.stop();
    }
}
