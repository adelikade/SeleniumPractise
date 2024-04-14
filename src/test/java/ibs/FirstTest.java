package ibs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class FirstTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10, 2000);

        //Шаг 1: Перейти на страницу

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://training.appline.ru/user/login");
    }

    @Test
    public void test() {

        //Шаг 2: Авторизоваться

        driver.findElement(By.xpath("//input[@name='_username']")).sendKeys("Taraskina Valeriya");
        driver.findElement(By.xpath("//input[@name='_password']")).sendKeys("testing");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        //Шаг 3: Проверить наличие заголовка "Панель быстрого запуска"

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='oro-subtitle']"))));

        //Шаг 4:  Нажать на "Командировки"
        WebElement costsList = driver.findElement(By.xpath(
                "//ul[contains(@class, 'main-menu')]/li/a/span[text()='Расходы']"));
        costsList.click();
        wait.until(ExpectedConditions.visibilityOf(costsList.findElement(By.xpath(
                "./ancestor::li//ul[@class='dropdown-menu menu_level_1']"))));
        driver.findElement(By.xpath(
                "//span[text()='Командировки']")).click();
        loading();

        //Шаг 5: Нажать на "Создать командировку"
        driver.findElement(By.xpath("//a[@title='Создать командировку']")).click();
        loading();

        //Шаг 6: Проверить наличие на странице заголовка "Создать командировку"
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='user-name']"))));

        //Шаг 7: Заполнить или выбрать следующие поля:
        // --Подразделение - выбрать Отдел внутренней разработки
        driver.findElement(By.xpath(
                "//select[@data-ftid='crm_business_trip_businessUnit']")).click();
        WebElement internalDevelopment = driver.findElement(By.xpath(
                "//option[text()='Отдел внутренней разработки']"));
        internalDevelopment.click();
        // --Принимающая организация  - нажать "Открыть список" и в поле "Укажите организацию" выбрать любое значение
        driver.findElement(By.xpath(
                "//a[@id='company-selector-show']")).click();
        driver.findElement(By.xpath(
                "//div[@class='select2-container select2 input-widget']")).click();
        WebElement selectOrganization = driver.findElement(By.xpath(
                "//div[text()='(Edge) Призрачная Организация Охотников']"));
        selectOrganization.click();

        // --В задачах поставить чекбокс на "Заказ билетов"
        driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_tasks_1']")).click();
        // --Указать города выбытия и прибытия
        driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_departureCity']")).clear();
        WebElement departureCity = driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_departureCity']"));
        departureCity.sendKeys("Омск");
        WebElement arrivalCity = driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_arrivalCity']"));
        arrivalCity.sendKeys("Москва");
        // --Указать даты выезда и возвращения
        WebElement departureDatePlan = driver.findElement(By.xpath(
                "//input[contains(@name, 'date_selector_crm_business_trip_departureDatePlan')]"));
        departureDatePlan.sendKeys("12.06.2025");
        WebElement returnDatePlan = driver.findElement(By.xpath(
                "//input[contains(@name, 'date_selector_crm_business_trip_returnDatePlan')]"));
        returnDatePlan.sendKeys("26.06.2025");

        //Шаг 8: Проверить, что все поля заполнены правильно
        WebElement actualDevelopment = driver.findElement(By.xpath(
                "//select[@data-ftid='crm_business_trip_businessUnit']/preceding-sibling::*"));
        Assert.assertEquals
                ("Выбрано неверное подразделение", "Отдел внутренней разработки",
                        actualDevelopment.getText());
        WebElement actualOrganization = driver.findElement(By.xpath(
                "//span[@class='select2-chosen']"));
        Assert.assertEquals
                ("Выбрана неверная организация", "(Edge) Призрачная Организация Охотников",
                        actualOrganization.getText());
        WebElement actualDepartureCity = driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_departureCity']"));
        Assert.assertEquals
                ("Измените город отправления", "Омск",
                        actualDepartureCity.getAttribute("value"));
        WebElement actualArrivalCity = driver.findElement(By.xpath(
                "//input[@data-ftid='crm_business_trip_arrivalCity']"));
        Assert.assertEquals
                ("Измените город прибытия", "Москва",
                        actualArrivalCity.getAttribute("value"));
        WebElement actualDepartureDatePlan = driver.findElement(By.xpath(
                "//input[contains(@name, 'date_selector_crm_business_trip_departureDatePlan')]"));
        Assert.assertEquals
                ("Измените дату отправления", "12.06.2025",
                        actualDepartureDatePlan.getAttribute("value"));
        WebElement actualReturnDatePlan = driver.findElement(By.xpath(
                "//input[contains(@name, 'date_selector_crm_business_trip_returnDatePlan')]"));
        Assert.assertEquals
                ("Измените дату прибытия", "26.06.2025",
                        actualReturnDatePlan.getAttribute("value"));
        //Нажать "Сохранить и закрыть"
        WebElement saveButton = driver.findElement(By.xpath("//button[@class='btn btn-success action-button']"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", saveButton);

        //Проверить, что на странице появилось сообщение: "Список командируемых сотрудников не может быть пустым"
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(
                "//div[@class='loader-content']"))));
        WebElement emptyList = driver.findElement(By.xpath(
                "//div[contains(@id, 'crm_business_trip_users')]/parent::*/following-sibling::*"));
        Assert.assertEquals
                ("Отсутствует или не совпадает текст предупреждения", "Список командируемых сотрудников не может быть пустым",
                        emptyList.getText());
    }

    @After
    public void after() {
        driver.quit();
    }

    public void loading() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(
                "//div[@class='loader-mask shown']"))));
    }

}
