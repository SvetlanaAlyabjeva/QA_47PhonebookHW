package experiments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ParametersTests {

    private WebDriver driver;

    @BeforeClass
    @Parameters("browser")
    public void openPage(@Optional("chrome") String browser){
        switch (browser.toLowerCase()){
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get("https://telranedu.web.app/home");
    }

    @Test
    @Parameters({"par1", "par2"})
    public void test1(@Optional("-1000") int v1, @Optional("500") int v2) {
        System.out.println("v1=" + v1 + " v2=" + v2);
        int result = v1 + v2;
        Assert.assertTrue(result > 0);
    }

    @AfterClass
    public void close(){
        driver.quit();
    }
}
