package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(id = "signupSuccess")
    private WebElement successText;

    public SignupPage(WebDriver driver,int port){
        PageFactory.initElements(driver,this);
        driver.get("http://localhost:" + port + "/signup");
    }

    public void signupWithValues(String firstName,String lastName,String username,String password){
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        passwordInput.submit();
    }

    public String getSuccessText(){
        return successText.getText();
    }

}
