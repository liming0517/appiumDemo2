package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
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
  Steps:
  1. Check each items under Date& Time one by one by click all clickable items and change all changeable items.
  2. Go to other page after change value, check the value, then restart phone.
  3. Check title bar/ AOD / lockscreen /Notification where date & time is displayed.(AOD:Always on Display 熄屏)

 Expected Result:
 1. When "Set time automatically" turned off, user could set Date/ Time manually without problems.
 When "Set time zone automatically" turned off, user could set Time zone by assigning region.
 2. UI change according to user change and those value are kept after return from other page or restart.
 3. Date & time are synced between all where date & time will show.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase46 {
    private static Logger logger = Logger.getLogger(Testcase46.class);

    @Autowired
    public final AppiumConfig config;

    //
    public Testcase46(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t46")
    public ResponseEntity changeTimeZone() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //enter into System > Date & time
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.System).click();
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Date_time).click();

                WebElement elementByUiautomator = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Set_time_automatically + "\")");
                //String bounds = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Cache + "\").fromParent()").getAttribute("bounds");

                System.err.println(elementByUiautomator.getText());
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
        return null;
    }
}
