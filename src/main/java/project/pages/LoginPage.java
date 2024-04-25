package project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import project.BasePage;

import java.util.Properties;

import static project.properties.TestProperties.getInstance;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='_username']']")
    private WebElement loginRow;
    @FindBy(xpath = "//input[@name='_password']")
    private WebElement passwordRow;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitBtn;
    @FindBy(xpath = "//h1[@class='oro-subtitle']")
    private WebElement subTitle;

    public void enterLoginAndPassword(String login, String password){
        loginRow.sendKeys(login);
        passwordRow.sendKeys(password);
    }
    public void submitClick(){
        submitBtn.click();

        wait.until(ExpectedConditions.visibilityOf(subTitle));
    }
}
