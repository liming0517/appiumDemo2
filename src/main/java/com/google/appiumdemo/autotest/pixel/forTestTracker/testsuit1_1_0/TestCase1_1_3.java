package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import io.appium.java_client.TouchAction;
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

/**
 * Adjust font size

 * Test steps:
 * 1. Go to Font size settings page.
 * 2. Reset font size settings by clicking 'Reset settings'.

 Pass Criteria:
 (1) There is no error when enter resetting button.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_3 {
    private static Logger logger = Logger.getLogger(TestCase1_1_3.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestCase1_1_3(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    @RequestMapping(value = "testcase1.1.3")
    public ResponseEntity adjustFontSize() {
        try {
            AndroidDriver driver = getDriver();
            try {
                TouchAction touchAction = new TouchAction(driver);
                WebElement display = GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.Display);
                if (null != display) {// can get the display element
                    display.click();
                    WebElement displaySize = GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.DisplaySize);
                    if(null != displaySize){
                        displaySize.click();
                        WebElement reset = GenericTools.findElementSwipeDownByXpath(driver, "resource-id", "com.android.settings:id/reset_button");
                        if(null!=reset){
                            reset.click();
                            GenericTools.elementIsExistAndClick(By.id("android:id/button1"),driver);
                            Thread.sleep(2000);
                        }else {
                            ResponseEntity.failed("can't find reset button",null);
                        }

                        return ResponseEntity.success("reset font size success",null);
                    }else {
                        return ResponseEntity.failed("can't find displaySize",null);
                    }

                }else {
                    return ResponseEntity.failed("can't find display",null);
                }
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
}
