package com.github.fuzzdbunit.sample.selenium;

import com.github.fuzzdbunit.params.provider.FuzzFile;
import com.github.fuzzdbunit.params.provider.FuzzSource;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SeleniumJupiter.class)
public class FuzzDbUnitSeleniumTest {

    private static FirefoxDriver driver;

    @BeforeAll
    public static void initDriver() {
        System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
        driver = new FirefoxDriver();
        login();
        navigateToTestedPage();
    }

    @AfterAll
    public static void closeTest() {
        driver.quit();
    }

    @ParameterizedTest
    @FuzzSource(file = FuzzFile.XSS_XSS_OTHER)
    public void testWebgoatWithSeleniumFuzzingOptimized(String field) {
        System.out.println("Testing [" + field + "]");

        // execute test
        typeInField(field);
        driver.findElement(By.cssSelector("td:nth-child(1) > input")).click();

        // Assert results. Four cases happen:
        // 1) a popup confirming the order
        // 2) a popup notifying an error of the input; this is the expected behaviour
        // 3) no popup at all
        // 4) a second popup opens because of XSS
        Alert alertPopup = null;
        try {
            // covers cases 1 and 2
            alertPopup = driver.switchTo().alert();
            assertThat(alertPopup.getText(), containsString("Whoops: You entered an incorrect access code of"));
        } catch (NoAlertPresentException nape) {
            // case 3
            fail("Popup has not been opened: " + nape.getClass());
        } finally {
            // return to initial state
            if (alertPopup != null) {
                alertPopup.accept();
            }
            // case 4 throws exception below
            navigateToTestedPage();
        }
    }

    private static void login() {
        driver.get("http://localhost:8080/WebGoat/login.mvc");
        driver.manage().window().setSize(new Dimension(800, 700));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn")));
        driver.findElement(By.id("exampleInputEmail1")).click();
        driver.findElement(By.id("exampleInputEmail1")).sendKeys("guest");
        driver.findElement(By.id("exampleInputPassword1")).click();
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("guest");
        driver.findElement(By.cssSelector(".btn")).click();
    }

    private static void navigateToTestedPage() {
        driver.get("http://localhost:8080/WebGoat/start.mvc#attack/136634854/400");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("AJAX Security")));
    }

    private void typeInField(String input) {
        WebElement fieldElement = driver.findElement(By.id("field1"));
        fieldElement.clear();
        fieldElement.sendKeys(input);
    }
}
