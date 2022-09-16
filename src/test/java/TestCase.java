import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

public class TestCase {
    private static WebDriver driver;
    private static BatchInfo myTestBatch;
    Eyes eyes;

    @BeforeAll
    public static void beforeAll() {
        driver = WebDriverManager.chromedriver().create();
        myTestBatch = new BatchInfo("Test Cases");
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        eyes = new Eyes();
        eyes.setBatch(myTestBatch);
        eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        eyes.open(driver, "My First Tests", testInfo.getTestMethod().get().getName(), new RectangleSize(1000, 600));
    }
    @Test
    public void applitoolsHelloWorld() {
        try {
            driver.get("https://applitools.com/helloworld/");
            eyes.check(Target.window());
        } catch(Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void example() {
        try {
            driver.get("https://example.com");
            eyes.check(Target.window());
        } catch(Exception e) {
            Assertions.fail(e);
        }
    }

    @AfterEach
    public void afterEach() {
        eyes.closeAsync();
    }

    @AfterAll
    public static void afterAll() {
        driver.close();
    }

}
