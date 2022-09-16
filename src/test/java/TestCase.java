import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class TestCase {
    WebDriver driver;
    Eyes eyes;

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        eyes = new Eyes();
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        driver = WebDriverManager.chromedriver().create();
        eyes.open(driver, "My First Tests", testInfo.getTestMethod().get().getName(), new RectangleSize(1000, 600));
    }
    @Test
    public void myTestCase() {
        try {
            driver.get("https://applitools.com/helloworld/");
            eyes.check(Target.window());
        } catch(Exception e) {
            Assertions.fail(e);
        }
    }

    @AfterEach
    public void afterEach() {
        eyes.closeAsync();
        driver.close();
    }
}
