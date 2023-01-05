package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * step:
 * 1.Go Network & internet > Internet.the network need to connect wifi.
 * 2.Scroll down in Internet page, click Add network button.
 * 3.Switch on/off Aireplane mode in Network internet.
 * 4.Go Network > Private DNS.

 * check point:
 * 1.Airplane icon displayed/not displayed in top-right of screen.
 * 2.Wifi connected, selected wifi displayed in the top of wifi list and icon displayed in top-right of screen.
 * 3.Switch on/off Aireplane mod success.
 * 4.Private DNS can switch to OFF and Automatic

 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase9 {
    private static Logger logger = Logger.getLogger(Testcase9.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase9(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t9")
    public ResponseEntity ConnectInternet() {
        try {
            String result = "success";
            AndroidDriver driver = getDriver();

            try {
                //STEP 1's PRECONDITION :
                this.ClearSavedInternet(driver);
                //STEP 1: connect
                result = this.connectWifi(driver);
                if ("success" != result) {
                    return ResponseEntity.failed(result, null);
                }

                //STEP 2 : Scroll down in Internet page, click Add network button.
                result = this.addNetwork(driver);
                if ("success" != result) {
                    return ResponseEntity.failed(result, null);
                }

                //STEP 3 : switch to air_plane
                result = this.switchAirPlane(driver);
                if ("success" != result) {
                    return ResponseEntity.failed(result, null);
                }

                //STEP 4 : Go Network > Private DNS.
                result = this.switchPrivateDNS(driver);
                if ("success" != result) {
                    return ResponseEntity.failed(result, null);
                }

                return ResponseEntity.success("success", null);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.failed("testcase run failed,can't find connected text", null);

            } finally {
                driver.quit();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.failed("get Android driver error", e.getMessage());
        }
    }

    //clear saved networks
    public void ClearSavedInternet(AndroidDriver driver) throws MalformedURLException, InterruptedException {

        GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Network_internet + "']"), driver);
        GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Internet + "']"), driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        //precondition：remove saved internet
        WebElement swipeDownByXpath = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Saved_networks);
        if (null != swipeDownByXpath) {
            System.out.println("find");
            //cancel connected
            //driver.findElement(By.xpath("//*[@text='" + LanguageText.Saved_networks + "']")).click();
            swipeDownByXpath.click();
            GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Wifi_name + "']"), driver);
            GenericTools.elementIsExistAndClick(By.id("com.android.settings:id/button1"), driver);
            logger.info("the network has been disconnected");
        } else {
            logger.info("the network don't need to be disconnect");
        }

    }

    //connect wifi
    public String connectWifi(AndroidDriver driver) throws Exception {
        driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));
        //STEP 1,tap --> Network & Internet

        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Network_internet).click();
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Internet).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        WebElement wifinameElement = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Wifi_name);
        if (null != wifinameElement) {

            //entrance to the setup password page
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            wifinameElement.click();
            driver.findElement(By.id("com.android.settings:id/password")).sendKeys(LanguageText.Wifi_password);
            driver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));

            //Thread.sleep(5000);//wait wifi-connected
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
            //Check point1: Wifi connected success.
            if (GenericTools.elementIsExist(By.xpath("//*[@text='" + LanguageText.Connected + "']"), driver)) {
                return "success";
            } else {
                logger.error("testcase run failed,can't find connected text");
                return "wifi connect failed(can't find connected text)";
                //return ResponseEntity.failed("testcase run failed,can't find connected text", null);
            }
        } else {
            logger.error("can't search" + LanguageText.Wifi_name);
            return "testcase run failed,the wifiName：<" + LanguageText.Wifi_name + "> not found";
        }
    }

    //STEP 2 : Scroll down in Internet page, click Add network button.
    public String addNetwork(AndroidDriver driver) throws Exception {
        driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));

        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Network_internet).click();
        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Internet).click();

        WebElement add_network = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Add_network);
        if (null != add_network) {
            add_network.click();
            //add network
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/ssid").sendKeys("my_new_network");
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/button1").click();//click save button

            //CHECK POINT: when enter the saved network page we can show that the network has been saved,and then we need to remove it;
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Saved_networks).click();
            WebElement my_new_network = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "my_new_network");
            if (null != my_new_network) {
                my_new_network.click();
                //remove
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/button1").click();
                return "success";

            } else {
                return "Add network error,the network has been added but not found in Saved networks page";
            }

        } else {
            return "testcase run failed,the button：<" + LanguageText.Add_network + "> not found";
        }
    }

    //STEP 3: Switch on/off Airplane mode in Network internet.
    public String switchAirPlane(AndroidDriver driver) throws Exception {
        driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));

        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Network_internet).click();
        WebElement air_plane = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/switch_widget");
        if (null != air_plane) {
            air_plane.click();//switch to
            Thread.sleep(1000);
            air_plane.click();
            Thread.sleep(1000);
        } else {
            return "It is can't find Airplane mode button in Network & internet page";
        }
        return "success";
    }


    //4.Go Network > Private DNS.
    private String switchPrivateDNS(AndroidDriver driver) throws Exception {
        String result = "";
        driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));

        GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Network_internet).click();
        WebElement privateDNS = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Private_DNS);
        if (null != privateDNS) {
            // private DNS off
            privateDNS.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/private_dns_mode_off").click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/button1").click();//save
            // CHECK POINT : The text should exchange to "Off" in the Network & internet page.
            WebElement off = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Private_DNS + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");
            if ("Off".equals(off.getText())) {

            } else {
                result = "The private DNS has been updated to Off mode, but the text have not showed off";
            }

            //private DNS on
            privateDNS.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/private_dns_mode_opportunistic").click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
            GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/button1").click();//save
            // CHECK POINT : The text should exchange to "Off" in the Network & internet page.
            WebElement on = GenericTools.findElementByUiautomator(driver, "new UiSelector().text(\"" + LanguageText.Private_DNS + "\").fromParent(new UiSelector().className(\"android.widget.TextView\").index(1))");

            if ("On".equals(on.getText())) {

            } else {
                result = "The private DNS has been updated to On mode, but the text have not showed on";
            }

            if (result.equals("")) {
                result = "success";
            }

            return result;
        } else {
            return "It is can't find privateDNS button in Network & internet page";
        }
    }

}
