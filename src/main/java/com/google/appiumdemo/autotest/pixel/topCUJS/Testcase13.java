package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.Activity;
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
 Step:
 1. go to settings > battery then observe ui
 2. go to battery usage
 3. go to adaptive
 4. tap Battery percentage button

 Expect:
 1.It is no error when tap into the page.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase13 {
    private static Logger logger = Logger.getLogger(Testcase13.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase13(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t13")
    public ResponseEntity checkBattery() {
        try {
            AndroidDriver driver = getDriver();
            try {
                driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));
                //STEP 1: go to settings > battery then observe ui
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Battery).click();

                //STEP 2: go to battery > battery usage
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Battery_usage).click();
                Thread.sleep(2000);
                GenericTools.backToPrevious(driver);

                //STEP 3: go to adaptive
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Adaptive_preferences).click();
                Thread.sleep(2000);
                GenericTools.backToPrevious(driver);

                //STEP 4: tap Battery percentage button
                WebElement battery_switch = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/switch_widget");
                if(null !=battery_switch){
                    battery_switch.click();
                    Thread.sleep(2000);
                }else{
                    return ResponseEntity.failed("no switch battery can be find",null);
                }

                // CHECK POINT 1:It is no error when tap into the page.
                return ResponseEntity.success("success",null);
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
