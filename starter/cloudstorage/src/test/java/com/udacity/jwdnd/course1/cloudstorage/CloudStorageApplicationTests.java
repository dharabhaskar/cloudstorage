package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testLoginFail() {
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");
		Assertions.assertEquals("Invalid username or password",loginPage.getError());
	}

	@Test
	public void signupLoginSuccess() {
		//Signup
		SignupPage signupPage=new SignupPage(driver,port);
		signupPage.signupWithValues("Bhaskar",
									"Dhara",
									"bhaskar",
									"1234"
		);
		Assertions.assertTrue(
				signupPage.getSuccessText().contains("successfully signed up")
		);

		//Login
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");

		//Checking successful login
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void addNotes() throws InterruptedException {
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");

		NotePage notePage=new NotePage(driver);
		notePage.addNote("Hello","Sample Description");

		Assertions.assertEquals("Note added successfully",notePage.getNoteOperationSuccessText());

		Thread.sleep(5000);

	}

	@Test
	public void editNotes() throws InterruptedException {
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");

		NotePage notePage=new NotePage(driver);
		notePage.editNote();

		Assertions.assertTrue(
				notePage.getFirstTitleValue().contains("updated")
		);

		Assertions.assertEquals("Note updated successfully.",notePage.getNoteOperationSuccessText());

		Thread.sleep(5000);

	}
}
