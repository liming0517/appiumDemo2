package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
 * Test steps:
 * 1. Go to Font size settings page.
 * 2. Adjust font size level to increase / decrease.
 * 3. Reset font size settings by clicking 'Reset settings'.
 * 4. Hit back to previous layer then swipe up and down.
 * <p>
 * CheckPoint：
 * 1.  There is no error when enter font/display size page then make adjustment.
 * 2.  There is no error found when reset display/font settings back to default.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase20 {
    private static Logger logger = Logger.getLogger(Testcase20.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase20(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t20")
    public ResponseEntity adjustFontText() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //STEP 1 : Go to Font size settings page.
                WebElement display = GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.Display);
                if (null != display) {// can get the display element
                    display.click();
                    WebElement displaySize = GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.DisplaySize);
                    if (null != displaySize) {
                        displaySize.click();
                        //STEP 2 : Adjust font size level to increase / decrease.
                        WebElement bar = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "android:id/seekbar");

                        if (null != bar) {
                            int height = bar.getRect().height;
                            int width = bar.getRect().width;
                            int x = bar.getRect().x + 20;
                            int y = bar.getRect().y;
                            int swipe_point_y = y + height / 2;
                            int swipe_point_x_end = (x + width) * 4 / 5;

                            //CHECK POINT 1: Brightness bar will pop up when click on Brightness level setting.
                            this.swipeLateral(driver, x, swipe_point_x_end, swipe_point_y);
                            Thread.sleep(1000);

                        } else {
                            return ResponseEntity.failed("can't find bar", null);
                        }

                        //STEP 3 : Reset font size settings by clicking 'Reset settings'.
                        WebElement reset = GenericTools.findElementSwipeDownFromBottomByXpath(driver, "resource-id", "com.android.settings:id/reset_button");
                        if (null != reset) {
                            reset.click();
                            GenericTools.elementIsExistAndClick(By.id("android:id/button1"), driver);
                            //CheckPoint 2 : There is no error found when reset display/font settings back to default.
                            Thread.sleep(2000);

                            // STEP 4 : Hit back to previous layer then swipe up and down.
                            GenericTools.backToPrevious(driver);
                            Thread.sleep(2000);
                            GenericTools.backToPrevious(driver);

                            // swipe to the bottom and head.
                            GenericTools.swipeToBottom(driver);
                            GenericTools.swipeToHead(driver);


                        } else {
                            ResponseEntity.failed("can't find reset button", null);
                        }

                        //Check Point 3: There is no error found when swiping in page after font size changed.
                        return ResponseEntity.success("reset font size success", null);
                    } else {
                        return ResponseEntity.failed("can't find displaySize", null);
                    }

                } else {
                    return ResponseEntity.failed("can't find display", null);
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

    public void swipeLateral(AndroidDriver driver, int x, int swipe_point_x_end, int swipe_point_y) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(swipe_point_x_end, swipe_point_y))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(x, swipe_point_y))
                .release()
                .perform();
    }
}
