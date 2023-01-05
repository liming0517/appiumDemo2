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
 Steps:
 1. Go to About Phone
 2. Tap "Send feedback about this device"
 3. When the sending content is blank, click send and prompt "Write your feedback before sending".

 Expected Result:
 1. When the sending content is blank, the prompt "Write your feedback before sending" should be popup.
 2. Press back key could smoothly go back to previous layer.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase44 {
    private static Logger logger = Logger.getLogger(Testcase44.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase44(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }


    @RequestMapping(value = "t44")
    public ResponseEntity sendFeedback() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //1. Go to About Phone
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.About_phone).click();

                //2. Tap "Send feedback about this device"
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Send_feedback).click();
                WebElement access_button = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/log_access_dialog_allow_button");
                if(null !=access_button){
                    access_button.click();
                }

                //3.When the sending content is blank, click send and prompt "Write your feedback before sending".
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.google.android.gms:id/common_send").click();
                WebElement ok_pop = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "OK");
                if(null ==ok_pop){
                    return ResponseEntity.failed("When the sending content is blank, click send and prompt 'Write your feedback before sending',no ok button can be found.",null);
                }
                //Check point 1 : When the sending content is blank, the prompt "Write your feedback before sending" should be popup.
                ok_pop.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

                //check point 2: Press back key could smoothly go back to previous layer.
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
