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
import java.time.Duration;

/**
 Test Steps:
 1. Check device name in About Phone
 2. Tap "Deice name" and change the Device name.

 Expected Result:
 1.Device name is correct.
 2.Device name can be changed it also sync to Wifi hotspot -> Hotspot name & Bluetooth -> Device name.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase42 {
    private static Logger logger = Logger.getLogger(Testcase42.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase42(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    @RequestMapping(value = "t42")
    public ResponseEntity adjustDeviceName() {
        try {
            AndroidDriver driver = getDriver();
            try {
                String updated_name ="";
                //Step 1 : Check device name in About Phone
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.About_phone).click();
                //WebElement device_name = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Device_name + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");

                //Step 2 :Tap "Deice name" and change the Device name.
                WebElement device_name = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Device_name);
                if(null != device_name){
                    device_name.click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/edit").clear();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/edit").sendKeys("my_new_device_name");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/button1").click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/button1").click();
                    updated_name = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Device_name + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))").getText();

                    //Check point 1 : Device name is correct.
                    logger.info("updated devices name is " +updated_name);

                    //check Point 2: Device name can be changed it also sync to Wifi hotspot -> Hotspot name & Bluetooth -> Device name.
                    GenericTools.backToPrevious(driver);
                    GenericTools.findElementSwipeUpByXpath(driver,XpathConstant.TEXT,LanguageText.Network_internet).click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Hotspot).click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Wifi_hotspot).click();
                    String hotspot_name = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Hotspot_name + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))").getText();
                    //check wifi hotspot name
                    if(!hotspot_name.equals(updated_name)){
                        return ResponseEntity.failed("The hotspot name haven't updated when the device name has been changed",null);
                    }
                    GenericTools.backToPrevious(driver);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);

                    //check Bluetooth -> Device name.
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Connected_devices).click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Connection_preferences).click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,LanguageText.Bluetooth).click();

                    String bluetooth_name = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Device_name + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))").getText();
                    if(!bluetooth_name.equals(updated_name)){
                        return ResponseEntity.failed("The hotspot name haven't updated when the device name has been changed",null);
                    }
                    Thread.sleep(2000);
                    return ResponseEntity.success("success",null);
                }else{
                    return ResponseEntity.failed("Device name not found",null);
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
