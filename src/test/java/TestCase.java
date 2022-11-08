import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.*;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aopalliance.reflect.Class;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestCase {
    private static WebDriver driver;
    private static BatchInfo myTestBatch;
    private static EyesRunner testRunner;
    private static Configuration suiteConfig;
    Eyes eyes;

    @BeforeAll
    public static void beforeAll() {
        driver = WebDriverManager.chromedriver().create();
        myTestBatch = new BatchInfo("Test Cases");
        testRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

        suiteConfig = new Configuration();
        suiteConfig.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        suiteConfig.setBatch(myTestBatch);
        suiteConfig.setStitchMode(StitchMode.CSS);
        suiteConfig.addBrowser(1000, 600, BrowserType.CHROME);
//        suiteConfig.addBrowser(1600, 1200, BrowserType.FIREFOX);
//        suiteConfig.addBrowser(1024, 768, BrowserType.SAFARI);
//        suiteConfig.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);
//        suiteConfig.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.LANDSCAPE);
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        eyes = new Eyes(testRunner);
        eyes.setConfiguration(suiteConfig);
        eyes.open(driver, "My First Tests", testInfo.getTestMethod().get().getName(), new RectangleSize(1000, 600));
    }

    @Test
    public void regionScreenshot() {
        driver.get("https://developer.mozilla.org/en-US/docs/Web/API/Element/scroll");
        eyes.check(Target.region(By.cssSelector("#sidebar-quicklinks")).fully());
    }

    @AfterEach
    public void afterEach() {
        eyes.closeAsync();
    }

    @AfterAll
    public static void afterAll() {
        driver.close();
        TestResultsSummary results = testRunner.getAllTestResults();
        System.out.println(results);
    }
}
