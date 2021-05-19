package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class )
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

	//--------------- L O G I N  AND  S I G N U P  T E S T ---------------------

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testLoginFail() {
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskaar","1234");
		Assertions.assertEquals("Invalid username or password",loginPage.getError());
	}

	@Test
	@Order(2)
	public void testLogin(){
		//Login
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");

		//Checking successful login
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(1)
	public void testSignup() {
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
	}

	@Test
	public void checkSuccessfulLogout(){
		LoginPage loginPage=new LoginPage(driver,port);
		loginPage.submitPageWith("bhaskar","1234");

		Assertions.assertEquals("Home",driver.getTitle());

		loginPage.logout();

		Assertions.assertEquals("You have been logged out",loginPage.getLogoutText());
	}

	//--------------------- N O T E S   T E S T I N G ---------------------


	@Test
	@Order(31)
	public void addNotes() throws InterruptedException {
		testLogin();
		NotePage notePage=new NotePage(driver,port);
		notePage.addNote("Hello","Sample Description");

		Assertions.assertEquals("Note added successfully",notePage.getNoteOperationSuccessText());
		Thread.sleep(2000);

	}

	@Test
	@Order(32)
	public void viewNotes() throws InterruptedException {
		testLogin();
		NotePage notePage=new NotePage(driver,port);
		//notePage.editNote();

		int rowCount=notePage.getNotesRecCount();

		System.out.println(rowCount);

		Assertions.assertEquals(true,rowCount>1);

		Thread.sleep(2000);
	}

	@Test
	@Order(33)
	public void editNotes() throws InterruptedException {
		testLogin();

		NotePage notePage=new NotePage(driver,port);
		addNotes();

		notePage.editNote();

		Assertions.assertTrue(
				notePage.getFirstTitleValue().contains("updated")
		);

		Assertions.assertEquals("Note updated successfully.",notePage.getNoteOperationSuccessText());

		Thread.sleep(2000);
	}

	@Test
	@Order(34)
	public void testNoteDelete(){
		testLogin();
		NotePage notePage=new NotePage(driver,port);
		int rowCountBeforeDelete=notePage.getNotesRecCount();

		notePage.deleteNote();

		int rowCountAfterDelete=notePage.getNotesRecCount();

		Assertions.assertEquals(true,rowCountAfterDelete<rowCountBeforeDelete);
	}

	//-------------------------  F I L E S  T E S T ---------------------
	@Test
	@Order(41)
	public void addFileTest() throws InterruptedException {
		testLogin();
		FilesPage filesPage=new FilesPage(driver,port);
		filesPage.uploadFile("/home/tablu/Desktop/demo.txt");

		Assertions.assertEquals(true,
				filesPage.getOpSuccessMessage().contains("uploaded successfully."));

		Thread.sleep(2000);
	}

	@Test
	@Order(42)
	public void viewFilesTest() throws InterruptedException {
		testLogin();

		FilesPage filesPage=new FilesPage(driver,port);
		int rowCount=filesPage.getFileRecCount();

		System.out.println(rowCount);

		Assertions.assertEquals(true,rowCount>1);

		Thread.sleep(5000);

	}

	@Test
	@Order(43)
	public void deleteFileTest() throws InterruptedException {
		testLogin();
		FilesPage filesPage=new FilesPage(driver,port);
		filesPage.deleteFile();

		Assertions.assertEquals(true,
				filesPage.getOpSuccessMessage().contains("deleted successfully."));

		Thread.sleep(2000);
	}



	//-------------- C R E D E N T I A L   T E S T S -------------------
	@Test
	@Order(51)
	public void addCredentialTest()throws InterruptedException{

		testLogin();

		CredentialPage credentialPage=new CredentialPage(driver,port);
		credentialPage.addCredential("http://mysite.com","bhaskar","1234");

		Assertions.assertEquals("Your credentials added successfully.",credentialPage.getOpSuccessMessage());

		Thread.sleep(2000);
	}

	@Test
	@Order(52)
	public void viewCredentials() throws InterruptedException {
		testLogin();
		CredentialPage credentialPage=new CredentialPage(driver,port);
		//notePage.editNote();

		int rowCount=credentialPage.getRowCount();

		System.out.println(rowCount);

		Assertions.assertEquals(true,rowCount>1);

		Thread.sleep(2000);
	}


	@Test
	@Order(53)
	public void deleteCredentialTest() throws InterruptedException {
		testLogin();

		CredentialPage credentialPage=new CredentialPage(driver,port);
		credentialPage.deleteCredential();

		Assertions.assertEquals(true,
				credentialPage.getOpSuccessMessage().contains("successfully deleted"));

		Thread.sleep(2000);
	}
}
