package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
    @FindBy(css = "a[href='#nav-notes']")
    private WebElement navNotes;

    @FindBy(xpath = "//*[contains(text(),'Add a New Note')]")
    private WebElement addNoteButton;

    @FindBy(xpath = "//*[@id=\"note-title\"]")
    private WebElement noteTitleField;

    @FindBy(xpath = "//*[@id=\"note-description\"]")
    private WebElement noteDescriptionField;

    @FindBy(xpath = "//*[@id=\"noteModal\"]/div/div/div[3]/button[2]")
    private WebElement noteAddButton;

    @FindBy(id = "noteOpSuccess")
    private WebElement noteOpSuccessText;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr[1]/td[1]/button")
    WebElement editButton;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr[1]/th")
    WebElement firstNoteTitle;



    private final JavascriptExecutor executor;
    private final WebDriver driver;

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver,this);
         executor = (JavascriptExecutor)driver;
         this.driver=driver;

    }

    public void waitForField(WebElement field){
        WebDriverWait wait=new WebDriverWait(driver,2000);
        wait.until(ExpectedConditions.elementToBeClickable(field));
    }

    public void openNoteTab(){
        waitForField(navNotes);
        executor.executeScript("arguments[0].click();", navNotes);
    }

    public void addNote(String title,String description) {
        openNoteTab();

        waitForField(addNoteButton);
        addNoteButton.click();

        waitForField(noteTitleField);
        noteTitleField.sendKeys(title);

        waitForField(noteDescriptionField);
        noteDescriptionField.sendKeys(description);

        waitForField(noteAddButton);
        noteAddButton.click();
    }

    public void editNote() {
        openNoteTab();

        waitForField(editButton);
        editButton.click();
        //executor.executeScript("arguments[0].click();", editButton);

        waitForField(noteTitleField);
        noteTitleField.sendKeys(noteTitleField.getText()+" updated");

        waitForField(noteDescriptionField);
        noteDescriptionField.sendKeys(noteDescriptionField.getText()+" update");

        waitForField(noteAddButton);
        noteAddButton.click();
    }

    public String getNoteOperationSuccessText(){
        waitForField(noteOpSuccessText);
        return noteOpSuccessText.getText();
    }

    public String getFirstTitleValue(){
        waitForField(firstNoteTitle);
        return firstNoteTitle.getText();
    }
}
