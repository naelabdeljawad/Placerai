package pom;

import com.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class BrowserPage extends AbstractPage {

    public BrowserPage(WebDriver chromeDriver) {
        super(chromeDriver);
        PageFactory.initElements(chromeDriver, this);
    }

    public boolean openPage(String url) {
        try {
            chromeDriver.manage().window().maximize();
            chromeDriver.get(PropertiesReader.getInstance().getProperty("base.url").concat(url));
            chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
