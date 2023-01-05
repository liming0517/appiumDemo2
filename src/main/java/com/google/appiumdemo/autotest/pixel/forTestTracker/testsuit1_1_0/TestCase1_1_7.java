package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.ElementId;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.Assert;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Change app Storage settings
 * Test steps:
 * 1. Go to All apps page.
 * 2. Use all app search function to randomly search for apps to test.
 * 3. Go to "Storage & cache" settings via App detail view.
 * 4. Click "Clear cache" button.
 * 5. Click "Clear storage" button and confirm 'OK'.

 * Pass Criteria:
 * Check point 1 : clear cache whether success,correct value will should show "0 B".
 * Check point 2 : the "clear cache" button should disable and don't allow tap it.
 * Check Point 3 : clear storage success.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_7 {
    private static Logger logger = Logger.getLogger(TestCase1_1_7.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestCase1_1_7(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "testcase1.1.7")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //STEP 1 : entre Apps page
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Apps).click();

                //STEP 2 : entre All app
                //driver.findElement(AppiumBy.androidUIAutomator("textContains(\"See all\")")).click();
                GenericTools.findElementByUiautomator(driver, "textContains(\"See all\")").click();

                //STEP 3 : enter Camera application info
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Camera).click();

                //STEP 4 : enter Storage page
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Storage_cache).click();

                //STEP 5 : click "Clear cache" button
                WebElement cache_button = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, ElementId.BUTTON2);
                if (cache_button.isEnabled()) {//can click
                    cache_button.click();

                    WebElement cache_value = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Cache + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");

                    //Check point 1 : clear cache whether success,correct value will should show "0 B".
                    if (!Assert.assertEquals("0 B", cache_value.getText())) {
                        logger.error("Assert failure,clear cache failed");
                        return ResponseEntity.failed("Assert failure,clear cache failed", null);
                    }
                    //Check point 2 : the "clear cache" button should disable and don't allow tap it.
                    if (cache_button.isEnabled()) {
                        logger.error("Assert failure, the clear cache button should disable");
                        return ResponseEntity.failed("Assert failure, the clear cache button should disable", null);
                    }
                }


                //STEP 6 : click "Clear Storage" button
                WebElement storage_button = GenericTools.findElementById(driver, ElementId.BUTTON1);
                if (storage_button.isEnabled()) {//can click
                    storage_button.click();
                    GenericTools.findElementById(driver, "android:id/button1").click();

                    //Check Point 3 : clear storage success
                    WebElement size_value = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"App size\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");
                    WebElement data_value = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"User data\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");
                    WebElement total_value = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"Total\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");

                    if (!Assert.assertEquals("0 B", size_value.getText()) ||
                            !Assert.assertEquals("0 B", data_value.getText()) ||
                            !Assert.assertEquals("0 B", total_value.getText())) {
                        logger.error("Assert failure,clear cache failed");
                        return ResponseEntity.failed("Assert failure,clear storage failed", null);
                    }
                }


                logger.info("testcase1.1.7 run success");
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
}
