package com.google.appiumdemo.autotest.tablet.tango_testsuit1_1_0;

import com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0.TestCase1_1_1;
import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.GenericToolsForTango;
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

@Controller
@ResponseBody
@RequestMapping("tangor")
public class Tangor_TestCase1_1_2 {
    private static Logger logger = Logger.getLogger(TestCase1_1_1.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Tangor_TestCase1_1_2(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    @RequestMapping(value = "testcase1.1.2")
    public ResponseEntity adjustBrightness() {
        try {
            AndroidDriver driver = getDriver();
            try {
                WebElement display = GenericToolsForTango.swipeDownToFindElementFromLeft(driver, "text", LanguageText.Display);
                if (null != display) {// can get the display element
                    display.click();

                    GenericToolsForTango.switchToRightPage(driver);
                    GenericTools.elementIsExistAndClick(GenericTools.byXpath(XpathConstant.TEXT, LanguageText.Brightness_level), driver);

                    WebElement slider = driver.findElement(By.id("com.android.systemui:id/slider"));
                    if (null != slider) {
                        int height = slider.getRect().height;
                        int width = slider.getRect().width;
                        int x = slider.getRect().x + 20;
                        int y = slider.getRect().y;
                        int swipe_point_y = y + height / 2;
                        int swipe_point_x_end = (x + width) * 4 / 5;

                        this.swipeLateral(driver, slider, x, swipe_point_x_end, swipe_point_y);
                        Thread.sleep(1000);
                        this.swipeLateral(driver, slider, swipe_point_x_end, x, swipe_point_y);
                        Thread.sleep(1000);

                        this.swipeLateral(driver,slider,x + 100,x+200,y+height+100);
                        return ResponseEntity.success("success",null);
                    } else {
                       return ResponseEntity.failed("can't find bright slider", null);
                    }

                } else {
                    logger.error("can't find Display Element");
                    return ResponseEntity.failed("can't find Display Element,please connect to develop", null);
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                return ResponseEntity.failed("failed",e.getMessage());
            }finally {
                driver.quit();
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public void swipeLateral(AndroidDriver driver, WebElement slider, int x, int swipe_point_x_end, int swipe_point_y) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(swipe_point_x_end, swipe_point_y))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(x, swipe_point_y))
                .release()
                .perform();
    }

}
