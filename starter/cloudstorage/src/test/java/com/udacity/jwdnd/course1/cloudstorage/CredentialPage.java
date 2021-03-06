package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {
    @FindBy(xpath = "//*[@id=\"nav-credentials-tab\"]")
    private WebElement navCred;

    @FindBy(xpath="//*[@id=\"credential-url\"]")
    private WebElement credentialUrl;

     @FindBy(xpath="//*[@id=\"credential-username\"]")
    private WebElement credentialUsername;

     @FindBy(xpath="//*[@id=\"credential-password\"]")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td/a[1]")
    private WebElement viewFileButton;

//    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[1]/td[1]/a]")
    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteFileButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[1]/td[1]/button")
    WebElement firstEditButton;

    @FindBy(id = "credOpSuccess")
    private WebElement credOpSuccessText;

    @FindBy(xpath = "//*[@id=\"nav-credentials\"]/button")
    private WebElement addCredential;


    private final JavascriptExecutor executor;
    private final WebDriver driver;

    public CredentialPage(WebDriver driver,int port) {
        PageFactory.initElements(driver, this);
        executor = (JavascriptExecutor) driver;
        this.driver = driver;
        this.driver.get("http://localhost:" + port + "/home");
    }

    public void waitForField(WebElement field) {
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        wait.until(ExpectedConditions.elementToBeClickable(field));
    }

    public void openCredTab() {
        waitForField(navCred);
        executor.executeScript("arguments[0].click();", navCred);
    }

    public void addCredential(String url, String username,String password) {
        openCredTab();
        waitForField(addCredential);
        addCredential.click();

        waitForField(credentialUrl);
        credentialUrl.sendKeys(url);

        waitForField(credentialUsername);
        credentialUsername.sendKeys(username);

        waitForField(credentialPassword);
        credentialPassword.sendKeys(password);

        credentialPassword.submit();
    }

    public void editCredential() {
        openCredTab();

        waitForField(firstEditButton);
        firstEditButton.click();
        //executor.executeScript("arguments[0].click();", editButton);

        waitForField(credentialUsername);
        credentialUsername.sendKeys(credentialUsername.getText() + " updated");

        waitForField(credentialPassword);
        credentialPassword.sendKeys(credentialPassword.getText() + " update");

        waitForField(credentialUrl);
        credentialUrl.sendKeys(credentialUrl.getText() + " update");

        credentialUrl.submit();
    }


    public void deleteCredential(){
        openCredTab();
        waitForField(deleteFileButton);
        deleteFileButton.click();
    }

    public int getRowCount() {
        openCredTab();

        // Grab the table
        WebElement table = driver.findElement(By.id("credentialTable"));
        waitForField(table);


        // Now get all the TR elements from the table
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        return allRows.size();
    }



    public String getOpSuccessMessage(){
        waitForField(credOpSuccessText);
        return credOpSuccessText.getText();
    }

}
