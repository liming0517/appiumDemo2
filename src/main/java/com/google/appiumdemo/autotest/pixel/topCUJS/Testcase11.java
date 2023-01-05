package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.Assert;
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
import java.time.Duration;

/**
 STEP:
 1.Tap into Storage.
 2.tap System
 3.tap into Apps page > tap Camera > Clear Storage
 4.tap videos/Images/Trash/Documents & other/Games/Audio storage

 Expect:
 1.It is no error when tap into the page.
 2.Popup window displayed 'System includes files used to run Android version 13' when click "System".
 3.The Storage of Camera has been cleared success and the User data and Cache changed to "0B".
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase11 {
    private static Logger logger = Logger.getLogger(Testcase11.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase11(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    @RequestMapping(value = "t11")
    public ResponseEntity checkStorage() {
        try {
            AndroidDriver driver = getDriver();
            String result ="";
            try {
                //STEP 1 :tap --> Tap into Storage.
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Storage).click();

                //STEP 2 : tap System > check point popup confirm box
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.System).click();
                //CHECK POINT 2 : Popup window displayed 'System includes files used to run Android version 13' when click "System"
                WebElement ok_button = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/button1");
                if(null != ok_button){
                    ok_button.click();
                }else{
                    result += "failed, The pop-up window haven't appeared when tap to the System button ";
                }

                //STEP 3 : tap Apps
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Apps).click();
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Camera).click();
                WebElement clear_storage = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/button1");
                if (clear_storage.isEnabled()) {
                    clear_storage.click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/button1").click();
                    //CHECK POINT 3 : The Storage of Camera has been cleared success and the User data and Cache changed to "0B".
                    WebElement data_value = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"User data\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");
                    WebElement cache_value = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Cache + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    if (!Assert.assertEquals("0 B", data_value.getText()) ||
                            !Assert.assertEquals("0 B", cache_value.getText())) {
                        result += ", The Cache value don't show 0 B after click clear storage button";
                    }
                }
                GenericTools.backToPrevious(driver);
                GenericTools.backToPrevious(driver);

                //STEP 4 :tap videos/Images/Trash/Documents & other/Games/Audio storage
                //videos
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Videos).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);

                //Images
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Images).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);

                //Audio
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Audio).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);

                //Trash
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Trash).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);

                //Documents & other
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Document_other).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);

                //Games
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Games).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
                GenericTools.backToPrevious(driver);


                //Check point 1：It is no error when tap into the page.

                result = "success";
                return ResponseEntity.success(result,null);
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
