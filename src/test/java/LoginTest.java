import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginTest {

	private WebDriver driver;
	private WebDriverWait wait;

	@Before
	public void setUp() {
		// Launch Chrome browser
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofMillis(5000));
		driver.manage().window().maximize();
	}

	@Test
	public void testLoginAndDisplayNameUpdate() {
		// Navigate to the login page
		driver.get("https://staging.app.holocene.eu/");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Email")));

		//Verify that the login page is displayed
		//assertTrue(driver.getTitle().contains("login"));

		// Enter valid email and click on the "Continue" button
		WebElement emailField = driver.findElement(By.id("input"));
		emailField.sendKeys("no-reply@holocene.eu");
		WebElement continueButton = driver.findElement(By.id("submit-btn"));
		continueButton.click();

		// Wait for the password field to be visible
		WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("input")));

		// Enter valid password and click on the "Log in" button
		passwordField.sendKeys("HoloceneBerlin@1");
		WebElement loginButton = driver.findElement(By.id("submit-btn"));
		loginButton.click();

		// Wait for the personal settings page to be displayed
		wait.until(ExpectedConditions.titleContains("Settings"));

		// Verify that the user is successfully logged in and navigated to the personal settings page
		assertTrue(driver.getTitle().contains("Personal"));

		// Enter the updated display name in the "Display name" field
		WebElement displayNameField = driver.findElement(By.name("displayName"));
		displayNameField.clear();
		displayNameField.sendKeys("testUser");

		// Click on the "Save changes" button
		WebElement saveChangesButton = driver.findElement(By.xpath("//button[contains(text(),'Save changes')]"));
		saveChangesButton.click();

		// Wait for the success message to be displayed
		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Changes saved successfully.')]")));

		// Verify that the display name is updated successfully
		String updatedDisplayName = displayNameField.getAttribute("displayName");
		assertEquals("testUser", updatedDisplayName);
	}

	@After
	public void tearDown() {
		// Close the browser
		driver.quit();
	}
}

