import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected WebDriver driver;
    protected Actions actions;
    protected WebDriverWait wait;

    private By privacyPolicyAcceptanceButton = By.cssSelector("button[data-role='accept-consent']");

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.manage().window().setSize(new Dimension(1300, 800));
        driver.manage().window().setPosition(new Point(10, 30));
        actions = new Actions(driver);
        driver.navigate().to("https://allegro.pl/");
        driver.findElement(privacyPolicyAcceptanceButton).click();

        wait = new WebDriverWait(driver, 10);
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }
}


