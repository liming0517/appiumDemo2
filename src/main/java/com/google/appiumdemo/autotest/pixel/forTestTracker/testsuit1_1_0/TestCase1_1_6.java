package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

/**
 * Change app Permission settings
 * Test steps:
 * 1. Go to All apps page.
 * 2. Use all app search function to search for random apps to change.
 * 3. Go to Permission settings of App detail page.
 * 4. Enter the permission type (e.g. Camera, Location....etc)randomly picked.
 * 5. Change the permission setting (e.g. Allow, Ask every time...etc) randomly picked.

 * Pass Criteria:
 * There is no error found after changing permission settings.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_6 {
    private static Logger logger = Logger.getLogger(TestCase1_1_6.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestCase1_1_6(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "testcase1.1.6")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {

                //STEP 1 : entre Apps page
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Apps).click();

                //STEP 2 : entre All app
                //driver.findElement(AppiumBy.androidUIAutomator("textContains(\"See all\")")).click();
                GenericTools.findElementByUiautomator(driver, "textContains(\"See all\")").click();

                //STEP 3 : enter Camera application and setting notification
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Camera).click();

                //STEP 4 : entre Permissions page
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Permissions).click();

                //STEP 5 : entre camera permissions settings
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Microphone).click();

                //STEP 6: setting camera permissions
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                List<WebElement> elements = GenericTools.findElementsByClass(driver, "android.widget.RadioButton");
                for (WebElement element : elements) {
                    // confirm -> pop window
                    clickPermission(driver, element);
                }

                return ResponseEntity.success("success", null);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return ResponseEntity.failed("occur an error", e.getMessage());
            } finally {
                driver.quit();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.failed("get Android driver error", e.getMessage());
        }
    }


    private void clickPermission(AndroidDriver driver, WebElement element) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        element.click();

        if (GenericTools.elementIsExist(By.id("android:id/button1"), driver)) {
            driver.findElement(By.id("android:id/button1")).click();
            Thread.sleep(1000);
        }
    }
}
