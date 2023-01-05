package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
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
 * Adjust privacy preferences
 * step:
 * 1. Go to permission manager then click all clickable items.
 * 2. Try change the value from Allow to Deny or from Deny to Allow.
 *
 * check point:
 * 1.no runtime exception
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase5 {
    private static Logger logger = Logger.getLogger(Testcase5.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase5(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //
    @RequestMapping(value = "t5")
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
                //STEP 6: setting camera permissions
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                List<WebElement> elements = GenericTools.findElementsByClass(driver, "android.widget.RadioButton");
                for (WebElement element : elements) {
                    // confirm -> pop window
                    clickPermission(driver, element);
                }

                driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));//back
                Thread.sleep(1000);
                driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));//back

                //check point 1: no runtime exception

                return ResponseEntity.success("success", null);

            } catch (Exception e) {
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
