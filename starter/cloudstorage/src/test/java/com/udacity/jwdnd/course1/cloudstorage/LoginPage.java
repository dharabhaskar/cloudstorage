package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name="password")
    private WebElement passwordField;

    @FindBy(id="btnSubmit")
    private WebElement submitButton;

    @FindBy(id = "divError")
    private WebElement errorText;

    @FindBy(id = "divLogout")
    private WebElement logoutText;

    @FindBy(id = "logout_button")
    private WebElement logoutButton;

    private WebDriver driver;




    public LoginPage(WebDriver driver,int port){
        PageFactory.initElements(driver,this);
        this.driver=driver;
        this.driver.get("http://localhost:" + port + "/login");
    }


    public void waitForField(WebElement field){
        WebDriverWait wait=new WebDriverWait(driver,2000);
        wait.until(ExpectedConditions.elementToBeClickable(field));
    }

    public void submitPageWith(String username,String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        submitButton.click();
    }
    public String getError(){
        return errorText.getText();
    }

    public void logout() {
        logoutButton.submit();
    }

    public String getLogoutText(){
        waitForField(logoutText);
        return logoutText.getText();
    }
}
