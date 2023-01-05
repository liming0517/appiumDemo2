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
import java.util.List;

/**
 * Steps:
 * 1. Create multi-user and guest.
 * 2. Switch between different user/ guest then go to Settings app.
 * <p>
 * Checkpoint:
 * 1. Settings could open successfully.
 * 2. There is no delay or half pan become blank situation if device support 2 panes.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase62 {
    private static Logger logger = Logger.getLogger(Testcase62.class);

    @Autowired
    public final AppiumConfig config;


    public Testcase62(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }


    @RequestMapping(value = "t62")
    public ResponseEntity adjustSpecialAppsAccess() {
        try {
            AndroidDriver driver = getDriver();
            try {
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Apps).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Special_app_access).click();

                //Step ： 1 Click through <all files access>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.All_files_access).click();
                //Step ： 2 Try to disable some access for apps
                List<WebElement> eles = GenericTools.findElementsById(driver, "android:id/title");
                if (null != eles && eles.size() > 0) {
                    eles.get(0).click();
                    GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "Allow access to manage all files").click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Device admin apps>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Device_admin_apps).click();
                List<WebElement> eles2 = GenericTools.findElementsById(driver, "android:id/title");
                if (null != eles2 && eles2.size() > 0) {
                    eles2.get(0).click();
                    GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/action_button").click();
                }
                Thread.sleep(2000);
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Display over other apps>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Display_over_other_apps).click();
                List<WebElement> eles3 = GenericTools.findElementsById(driver, "android:id/title");
                if (null != eles3 && eles3.size() > 0) {
                    eles3.get(0).click();
                    GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "Allow display over other apps").click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                GenericTools.backToPrevious(driver);
                //Step ： 1 Click through <Do not disturb access>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Do_Not_Disturb_access).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Media management apps>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Media_management_apps).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Modify system settings>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Modify_system_settings).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Device & app notification>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Device_app_notifications).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Picture-in-picture>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Picture_in_picture).click();
                List<WebElement> eles4 = GenericTools.findElementsById(driver, "android:id/title");
                if (null != eles4 && eles4.size() > 0) {
                    eles4.get(0).click();
                    GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "Allow picture-in-picture").click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Premium SMS access>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Media_management_apps).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Unrestricted data>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Unrestricted_data).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Install unknown apps>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Install_unknown_apps).click();
                List<WebElement> eles5 = GenericTools.findElementsById(driver, "android:id/title");
                if (null != eles5 && eles5.size() > 0) {
                    eles5.get(0).click();
                    GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "Allow from this source").click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.backToPrevious(driver);
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                GenericTools.backToPrevious(driver);


                //Step ： 1 Click through <Alarms & reminders>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Alarms_reminders).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Usage access>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Usage_access).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <VR helper services>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.VR_helper_services).click();
                GenericTools.backToPrevious(driver);

                //Step ： 1 Click through <Wi-Fi control>
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Wi_Fi_control).click();
                GenericTools.backToPrevious(driver);

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
}
