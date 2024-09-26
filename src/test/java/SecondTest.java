import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.extension.DriverExtension;
import project.steps.LoginSteps;
import project.steps.MainPageSteps;


import java.util.Properties;

import static project.DriverManager.*;
import static project.properties.TestProperties.*;

@ExtendWith(DriverExtension.class)
public class SecondTest {
    private final WebDriver driver = getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, 10, 2000);
    private LoginSteps loginSteps = new LoginSteps();
    private MainPageSteps mainPageSteps = new MainPageSteps();
    private final Properties properties = getInstance().getProperties();

    @Test
    public void test() {

        //Шаг 1: Авторизоваться
        loginSteps.login(properties.getProperty("LOGIN"), properties.getProperty("PASSWORD"));

        //Шаг 2: Перейти в командировки
        mainPageSteps.filterByAssignment();

    }
}
