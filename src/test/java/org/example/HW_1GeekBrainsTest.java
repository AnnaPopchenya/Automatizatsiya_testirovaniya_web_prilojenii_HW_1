package org.example;


import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.TimeoutException;


public class HW_1GeekBrainsTest {

    private WebDriver driver;
    private static String USERNAME;

    private static String PASSWORD;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        USERNAME = "Anna_Popchenya";
        PASSWORD = "7de1732fa8";
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://test-stand.gb.ru/login");


        WebElement usernameField = driver.findElement(By.cssSelector("form#login input[type='text']"));
        WebElement passwordField = driver.findElement(By.cssSelector("form#login input[type='password']"));
        usernameField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);
        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
    }

    @Test
    void addGroupTestWithCSS() {
        // Явное ожидание появления кнопки '+'
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mdc-icon-button")));
        addButton.click();

        // Вводим имя новой группы
        WebElement groupNameField = driver.findElement(By.cssSelector(".mdc-text-field__input"));
        groupNameField.sendKeys("Группа 23");

        // Нажимаем кнопку SAVE
        WebElement saveButton = driver.findElement(By.cssSelector(".mdc-button__label"));
        saveButton.click();

        // Явное ожидание появления группы в таблице
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.svelte-tv8alb.svelte-tv8alb ")));
        } catch (TimeoutException e) {
            System.out.println("Ошибка: Группа не появилась в таблице");
            e.printStackTrace(); // Выводим стек вызовов для отладки
        }

        // Проверяем, что группа с именем появилась в таблице
        try {
            WebElement groupTitle = driver.findElement(By.cssSelector("h1.svelte-tv8alb.svelte-tv8alb"));
            Assertions.assertEquals("Группа 23", groupTitle.getText(), "Группа 23 не найдена в таблице");
        } catch (NoSuchElementException ex) {
            System.out.println("Ошибка: Элемент с именем группы не найден в таблице");
            ex.printStackTrace(); // Выводим стек вызовов для отладки
        }
    }

    @AfterEach
    void tearDown() {
        // Сохранение скриншота окна браузера
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src\\main\\resources\\screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
