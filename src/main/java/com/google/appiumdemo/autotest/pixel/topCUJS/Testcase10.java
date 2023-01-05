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
import java.time.Duration;

/**
 * 1.Go Network & internet > Hotspot & tethering > Wifi hotspot.
 * 2.Click Hotspot name to edit it and save.
 * 3.Switch on/off Use Wifi hotspot button.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase10 {
    private static Logger logger = Logger.getLogger(Testcase10.class);

    @Autowired
    public final AppiumConfig config;


    private String update_name = "my_hotspot_test";

    //constructor
    public Testcase10(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t10")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                // run testcase
                String result = this.updateHotspotName(driver);
                if ("success" != result) {
                    return ResponseEntity.failed(result, null);
                }
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

    public String updateHotspotName(AndroidDriver driver) throws Exception {
        driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));
        //STEP 1,tap --> Network & Internet > Hotspot & tethering >WIFI hotspot
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Network_internet).click();
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Hotspot).click();
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Wifi_hotspot).click();

        //click Hotspot name
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Hotspot_name).click();

        //update name
        WebElement input_text = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/edit");
        input_text.clear();
        input_text.sendKeys(update_name);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        // click ok
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/button1").click();
        //check point: update name success
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        WebElement updated_name = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Hotspot_name + "\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
        if (update_name.equals(updated_name.getText())) {
            return "success";
        }

        return "update failed, the Hotspot name can't consistent with update before";
    }
}
