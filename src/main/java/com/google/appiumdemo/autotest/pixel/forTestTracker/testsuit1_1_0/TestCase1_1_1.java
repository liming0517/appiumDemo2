package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
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
 * Connect Wifi
 * Test steps:
 * 0. Connect to WiFi.
 * 1. Go to Wi-Fi settings page to turn off then turn on Wi-Fi again
 * 2. Click on the wifi that connected to go to Network detail view, choose to 'Disconnect'.
 * <p>
 * Expected Result:
 * 1. Wi-Fi could successfully connected and the sub-string indicates that it is in 'connected'
 * 2. When click Disconnect, the string indicates it is in "Disconnected" status.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_1 {
    private static Logger logger = Logger.getLogger(TestCase1_1_1.class);

    @Autowired
    public final AppiumConfig config;


    public TestCase1_1_1(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    /**
     * https://testtracker.googleplex.com/testplans/testcase/detail/4964122?id=27840&revision=139
     *
     * @return test result
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    @RequestMapping(value = "testcase1.1.1")
    public ResponseEntity ConnectWifi() throws MalformedURLException, InterruptedException {
        try {
            this.ClearSavedInternet();
            AndroidDriver driver = getDriver();
            try {
                //tap --> Network & Internet
                GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Network_internet + "']"), driver);
                GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Internet + "']"), driver);

                Thread.sleep(3000);//wait wifi-search
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                WebElement wifinameElement = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Wifi_name);
                if (null != wifinameElement) {

                    //entrance to the setup password page
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
                    wifinameElement.click();
                    driver.findElement(By.id("com.android.settings:id/password")).sendKeys(LanguageText.Wifi_password);
                    driver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));

                    Thread.sleep(5000);//wait wifi-connected
                    if (GenericTools.elementIsExist(By.xpath("//*[@text='" + LanguageText.Connected + "']"), driver)) {
                        System.out.println("testcase run success");
                        driver.quit();
                        return ResponseEntity.success("success", null);
                    } else {
                        System.out.println("testcase run failed,can't find connected text");
                        driver.quit();
                        return ResponseEntity.failed("testcase run failed,can't find connected text", null);
                    }
                }else{
                    logger.error("can't find wifiname");
                    return ResponseEntity.failed("testcase run failed,can't find wifinameElement", null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.failed("testcase run failed,can't find connected text", null);

            } finally {
                driver.quit();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.failed("failed,get Android driver error", null);
        }
    }

    public void ClearSavedInternet() throws MalformedURLException, InterruptedException {

        AndroidDriver driver = getDriver();

        GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Network_internet + "']"), driver);
        GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Internet + "']"), driver);

        Thread.sleep(3000);

        //precondition：remove saved internet
        WebElement swipeDownByXpath = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Saved_networks);
        if (null != swipeDownByXpath) {
            System.out.println("find");
            //cancel connected
            //driver.findElement(By.xpath("//*[@text='" + LanguageText.Saved_networks + "']")).click();
            swipeDownByXpath.click();
            GenericTools.elementIsExistAndClick(By.xpath("//*[@text='" + LanguageText.Wifi_name + "']"), driver);
            GenericTools.elementIsExistAndClick(By.id("com.android.settings:id/button1"), driver);
            driver.quit();

        } else {
            System.out.println("cant find");
            driver.quit();
        }

    }
}
