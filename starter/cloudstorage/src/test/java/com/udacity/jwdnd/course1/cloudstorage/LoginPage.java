package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(name = "username")
    WebElement eleUsername;

    @FindBy(name="password")
    WebElement elePassword;

    @FindBy(id="btnSubmit")
    WebElement eleSubmit;

    @FindBy(id = "divError")
    WebElement eleDivError;

    public LoginPage(WebDriver driver,int port){
        PageFactory.initElements(driver,this);
        driver.get("http://localhost:" + port + "/login");
    }

    public void submitPageWith(String username,String password){
        eleUsername.sendKeys(username);
        elePassword.sendKeys(password);

        eleSubmit.click();
    }

    public String getError(){
        return eleDivError.getText();
    }




}
