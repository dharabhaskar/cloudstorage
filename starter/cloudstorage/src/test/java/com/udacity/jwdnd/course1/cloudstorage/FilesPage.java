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

public class FilesPage {
    @FindBy(xpath = "//*[@id=\"nav-files-tab\"]")
    private WebElement navFiles;

    @FindBy(xpath="//*[@id=\"fileUpload\"]")
    private WebElement fileUploadField;

    @FindBy(xpath = "//*[@id=\"nav-files\"]/form/div/div/div[3]/button")
    private WebElement fileUploadButton;

    @FindBy(xpath = "//*[@id=\"fileTable\"]/tbody/tr/td/a[1]")
    private WebElement viewFileButton;

    @FindBy(xpath = "//*[@id=\"fileTable\"]/tbody/tr/td/a[2]")
    private WebElement deleteFileButton;

    @FindBy(id = "fileOpSuccess")
    private WebElement noteOpSuccessText;


    private final JavascriptExecutor executor;
    private final WebDriver driver;

    public FilesPage(WebDriver driver,int port) {
        PageFactory.initElements(driver, this);
        executor = (JavascriptExecutor) driver;
        this.driver = driver;
        this.driver.get("http://localhost:" + port + "/home");
    }

    public void waitForField(WebElement field) {
        WebDriverWait wait = new WebDriverWait(driver, 2000);
        wait.until(ExpectedConditions.elementToBeClickable(field));
    }

    public void openFilesTab() {
        waitForField(navFiles);
        executor.executeScript("arguments[0].click();", navFiles);
    }

    public void deleteFile(){
        openFilesTab();
        waitForField(deleteFileButton);
        deleteFileButton.click();
    }

    public int getFileRecCount() {
        openFilesTab();

        // Grab the table
        WebElement table = driver.findElement(By.id("fileTable"));
        waitForField(table);


        // Now get all the TR elements from the table
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        return allRows.size();
    }

    public void uploadFile(CharSequence filePath){
        openFilesTab();
        waitForField(fileUploadField);
        fileUploadField.sendKeys(filePath);

        waitForField(fileUploadButton);
        fileUploadButton.click();
    }

    public String getOpSuccessMessage(){
        return noteOpSuccessText.getText();
    }

}
