import com.applitools.eyes.*;
import com.applitools.eyes.selenium.*;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestCase {
    private static WebDriver driver;
    private static BatchInfo myTestBatch;
    private static EyesRunner testRunner;
    private static Configuration suiteConfig;
    Eyes eyes;

    @BeforeAll
    public static void beforeAll() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();

        myTestBatch = new BatchInfo("Test Cases");
        myTestBatch.setSequenceName("Advanced Visual Testing");
        testRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

        suiteConfig = new Configuration();
        suiteConfig.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        suiteConfig.setBatch(myTestBatch);
//        suiteConfig.setBranchName("childBranch");
//        suiteConfig.setParentBranchName("featureBranch1");
//        suiteConfig.setBaselineBranchName("default");
        suiteConfig.addBrowser(1400, 600, BrowserType.CHROME);
        suiteConfig.addBrowser(1600, 1200, BrowserType.FIREFOX);
        suiteConfig.addBrowser(1024, 768, BrowserType.SAFARI);
        suiteConfig.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);
        suiteConfig.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.LANDSCAPE);
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        eyes = new Eyes(testRunner);
        eyes.setConfiguration(suiteConfig);
        eyes.open(driver, "My First Tests", testInfo.getTestMethod().get().getName(), new RectangleSize(1400, 600));
    }

    @Test
    public void GithubIntegrationTest() {
        driver.get("https://applitools.com/helloworld/?diff1");
        eyes.check(Target.window());
    }

    @Test
    public void example() {
        driver.get("https://example.com");
        eyes.check(Target.window());
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
