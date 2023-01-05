package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * step:
 * 1.Go settings "Security & privacy > App security" .
 * 2.Go settings "Security & privacy > Find My Device".
 * 3.Go settings "Security & privacy > Security update".
 * 4.Go settings "Security & privacy > Screen lock".
 * 5.Go settings "Security & privacy > Face & Fingerprint Unlock".
 * 6.Go settings "Security & privacy > Google Security Checkup".
 * 7.Go settings "Security & privacy > Google Play system update".
 * 8.Go settings "Security & privacy > More sevurity settings".
 *
 * check point 1: Every page jumps successfully.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase6 {
    private static Logger logger = Logger.getLogger(Testcase6.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase6(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t6")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //entre Security & privacy page
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Security_privacy).click();

                //Step 1.Go settings "Security & privacy > App security".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.App_security).click();
                GenericTools.backToPrevious(driver);

                //pre: expand the "Device lock"
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Device_lock).click();
                //Step 4.Go settings "Security & privacy > Screen lock".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Screen_lock).click();
                GenericTools.backToPrevious(driver);

                //Step 5.Go settings "Security & privacy > Face & Fingerprint Unlock".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Face_Fingerprint_unlock).click();
                GenericTools.backToPrevious(driver);

                //Step 6.Go settings "Security & privacy > Google Security Checkup".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Google_security_checkup).click();
                GenericTools.backToPrevious(driver);

                //Step 2.Go settings "Security & privacy > Find My Device".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Find_my_device).click();
                GenericTools.backToPrevious(driver);

                //Step 3.Go settings "Security & privacy > Security update".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Updates).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Security_update).click();
                GenericTools.backToPrevious(driver);

                //Step 7.Go settings "Security & privacy > Google Play system update".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Google_play_system_update).click();
                GenericTools.backToPrevious(driver);

                //Step 8.Go settings "Security & privacy > More security settings".
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.More_security_settings).click();
                GenericTools.backToPrevious(driver);

                //Step 9.Go settings "Security & privacy > More privacy settings".
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.More_privacy_settings).click();
                GenericTools.backToPrevious(driver);

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
