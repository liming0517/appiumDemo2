package com.google.appiumdemo.autotest.tablet.tango_testsuit1_1_0;

import com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0.TestCase1_1_1;
import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.ElementId;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.GenericToolsForTango;
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

@Controller
@ResponseBody
@RequestMapping("tangor")
public class Tangor_TestCase1_1_1 {

    private static Logger logger = Logger.getLogger(TestCase1_1_1.class);

    @Autowired
    public final AppiumConfig config;


    public Tangor_TestCase1_1_1(AppiumConfig config) {
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
    public ResponseEntity ConnectWifi(){
        AndroidDriver driver = null;
        try {

            driver =getDriver();
            this.ClearSavedInternet(driver);

            //tap --> Network & Internet
            GenericTools.elementIsExistAndClick(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Network_internet), driver);
            GenericToolsForTango.switchToRightPage(driver);
            GenericTools.elementIsExistAndClick(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Internet), driver);

            Thread.sleep(3000);//wait wifi-search
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
            if (GenericTools.elementIsExist(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Wifi_name), driver)) {

                WebElement internet = driver.findElement(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Wifi_name));
                //entrance to the setup password page
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
                internet.click();
                driver.findElement(By.id(ElementId.PASSWORD)).sendKeys(LanguageText.Wifi_password);
                driver.pressKey(new KeyEvent().withKey(AndroidKey.ENTER));

                Thread.sleep(5000);//wait wifi-connected
                if (GenericTools.elementIsExist(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Connected),driver)) {
                    //System.out.println("testcase run success");
                    logger.info("testcase run success");
                    driver.quit();
                    return ResponseEntity.success("success", null);
                } else {
                    //System.out.println("testcase run failed,can't find connected text");
                    logger.info("testcase run failed,can't find connected text");
                    driver.quit();
                    return ResponseEntity.failed("testcase run failed,can't find connected text", null);
                }
            }

            driver.quit();
            //System.err.println("failed,can't find wifi to connect");
            logger.error("failed,can't find wifi to connect");
            return ResponseEntity.failed("failed,can't find wifi to connect", null);

        }catch (Exception e){
            System.err.println("run testcase 1_1_1 failed,please check log");
            logger.error("run testcase 1_1_1 failed,please check log");
            e.printStackTrace();
            driver.quit();
            return ResponseEntity.failed("run testcase 1_1_1 failed,please check log",e.getMessage());
        }

    }


    public void ClearSavedInternet(AndroidDriver driver) throws Exception {

        GenericTools.findElementByXpath(driver, XpathConstant.TEXT, LanguageText.Network_internet).click();
        GenericToolsForTango.switchToRightPage(driver);
        GenericTools.findElementByXpath(driver, XpathConstant.TEXT, LanguageText.Internet).click();
        Thread.sleep(3000);

        //precondition：remove saved internet
        GenericToolsForTango.swipeToNextPageFromRight(driver);
        Thread.sleep(5000);
        if (GenericTools.elementIsExist(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Saved_networks), driver)) {
            //System.out.println("find element");
            logger.info("find element");

            //cancel connected
            GenericTools.findElementByXpath(driver,XpathConstant.TEXT , LanguageText.Saved_networks).click();
            GenericTools.elementIsExistAndClick(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Wifi_name), driver);
            GenericTools.elementIsExistAndClick(By.id(ElementId.BUTTON1), driver);
            driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));

        } else {
            logger.info("can't find");
            //System.out.println("cant find");
        }

    }
}
