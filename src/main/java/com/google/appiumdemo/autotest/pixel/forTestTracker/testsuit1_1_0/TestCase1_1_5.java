package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

/**
 Change app Notification settings

 * Test steps:
 * 1. Go to All apps page.
 * 2. Use all app search function to search for random apps we want to change.
 * 3. Go to Notification settings of App detail page.
 * 4. If the switch of Notification is disabled, try to enable it.
 * 5. If the switch of Notification is enabled, try to enable Notification and
 * 'Allow notification dot'.
 *
 *
 * Pass Criteria:
 * (1) When Notification is turned off, 'Allow notification dot' is hidden.
 * (2) After turning on/off Notification or 'Allow notification dot', no error is found.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_5 {
    private static Logger logger = Logger.getLogger(TestCase1_1_5.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestCase1_1_5(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }


    @RequestMapping(value = "testcase1.1.5")
    public ResponseEntity changeAppNotification() {
        try {
            AndroidDriver driver = getDriver();
            try {

                //STEP 1 : entre Apps page
                GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.Apps).click();

                //STEP 2 : entre All app
                driver.findElement(AppiumBy.androidUIAutomator("textContains(\"See all\")")).click();

                //STEP 3 : enter Camera application and setting notification
                GenericTools.findElementSwipeDownByXpath(driver,"text",LanguageText.Camera).click();

                //STEP 4 : entre notification page
                GenericTools.findElementSwipeDownByXpath(driver,"text",LanguageText.Notifications).click();

                //STEP 5 ： open to camera's notifications
                GenericTools.elementIsExistAndClick(By.id("android:id/switch_widget"), driver);

                Thread.sleep(2000);
                return ResponseEntity.success("success",null);
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
}
