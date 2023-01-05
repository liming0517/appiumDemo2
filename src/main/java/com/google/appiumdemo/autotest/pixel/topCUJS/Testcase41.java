package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
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
 * Steps:
 1. Go to About Phone
 2. Check thought Google Account / Phone number / Android version

 * Check point:
   1.All the info is correct and display properly
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase41 {
    private static Logger logger = Logger.getLogger(Testcase41.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase41(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }


    @RequestMapping(value = "t41")
    public ResponseEntity checkText() {
        try {
            AndroidDriver driver = getDriver();
            try {
                String result = "";
                String androidVersion ="";
                String ipAddress ="";
                String macAddress = "";
                String wifiMacAddress ="";
                String bluetoothAddress ="";
                String upTime = "";
                //STEP 1 ：Go to About Phone
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.About_phone).click();

                //STEP 2: Check through android version/IP address/Wi-Fi MAC address/Device Wi-fi MAC address/Bluetooth address/Up time
                WebElement android_version = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.Android_version+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                androidVersion = android_version.getText();
                if(Assert.assertEquals("",android_version.getText())){
                    result += "The Android version should not null,";
                }

                WebElement Ip_address = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.IP_address+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                ipAddress = Ip_address.getText();
                if(Assert.assertEquals("",Ip_address.getText())){
                    result += "The Ip address should not null,";
                }

                WebElement wifi_mac_address = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.Wifi_MAC_address+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                macAddress=wifi_mac_address.getText();
                if(Assert.assertEquals("",wifi_mac_address.getText())){
                    result += "The WIFI mac address should not null,";
                }

                WebElement dev_wifi_mac_address = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.Device_Wifi_MAC_address+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                wifiMacAddress = dev_wifi_mac_address.getText();
                if(Assert.assertEquals("",dev_wifi_mac_address.getText())){
                    result += "The device wifi mac address should not null,";
                }

                WebElement bluetooth_address = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.Bluetooth_address+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                bluetoothAddress = bluetooth_address.getText();
                if(Assert.assertEquals("",bluetooth_address.getText())){
                    result += "The bluetooth address should not null,";
                }

                WebElement up_time = GenericTools.findElementSwipeDownByUiautomator(driver, "new UiSelector().text(\""+LanguageText.Up_time+"\").fromParent(new UiSelector().resourceId(\"android:id/summary\"))");
                upTime = up_time.getText();
                if(Assert.assertEquals("",up_time.getText())){
                    result += "The Up time should not null,";
                }

                GenericTools.backToPrevious(driver);
                Thread.sleep(2000);
                if(!result.equals("")){
                    return ResponseEntity.failed(result,null);
                }else {
                    return ResponseEntity.success("success","android version: "+androidVersion+" IP address: "+ipAddress+" Wi-Fi MAC address: "+macAddress+" Device Wi-fi MAC address: "+wifiMacAddress+" Bluetooth address: "+bluetoothAddress+" Up time: "+upTime);
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
