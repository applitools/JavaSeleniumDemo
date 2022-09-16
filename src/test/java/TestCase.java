import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCase {
    WebDriver driver;

    @BeforeEach
    public void beforeAll() {
        driver = WebDriverManager.chromedriver().create();
    }


    @Test
    public void myTestCase() {
        try {
            driver.get("https://applitools.com/helloworld/");
            WebElement numbers = driver.findElement(By.cssSelector("span.primary"));
            WebElement button = driver.findElement(By.cssSelector("div.section:nth-child(3) > button:nth-child(1)"));
            WebElement titleH = driver.findElement(By.cssSelector("div.fancy:nth-child(1) > span:nth-child(1)"));
            WebElement titleD = driver.findElement(By.cssSelector("div.fancy:nth-child(1) > span:nth-child(11)"));

            Assertions.assertEquals(numbers.isDisplayed(), true);
            Assertions.assertEquals(numbers.getText(), "123456");
            Assertions.assertEquals(numbers.getCssValue("color"), "rgba(78, 90, 99, 1)");

            Assertions.assertEquals(button.isDisplayed(), true);
            Assertions.assertEquals(button.getText(), "Click me!");
            Assertions.assertEquals(button.getCssValue("color"), "rgba(255, 255, 255, 1)");

            Assertions.assertEquals(titleH.isDisplayed(), true);
            Assertions.assertEquals(titleH.getText(), "H");
            Assertions.assertEquals(titleH.getCssValue("color"), "rgba(255, 0, 0, 1)");

            Assertions.assertEquals(titleD.isDisplayed(), true);
            Assertions.assertEquals(titleD.getText(), "D");
            Assertions.assertEquals(titleD.getCssValue("color"), "rgba(70, 0, 255, 1)");
        } catch(Exception e) {
            Assertions.fail(e);
        }
    }

    @AfterEach
    public void afterAll() {
        driver.close();
    }
}
