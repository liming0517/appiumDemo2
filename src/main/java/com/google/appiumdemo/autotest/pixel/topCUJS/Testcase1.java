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
import java.util.List;

/**
step:
 1. Go to Settings -> Sound & vibration.
 2. Adjust alarm volume of volume bars.
 3. Adjust call volume of volume bars.
 4. Adjust media volume of volume bars.
 5. Adjust Ring & notification volume of volume bars.

 expect:
 1.There is no error found when adjust on alarm of volume bars.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase1 {
    private static Logger logger = Logger.getLogger(Testcase1.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase1(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t1")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT,LanguageText.Sound_vibration).click();
                //
                List<WebElement> sliders = driver.findElements(By.id("android:id/seekbar"));
                for(WebElement slider: sliders){
                    Thread.sleep(2000);
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
                }
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

    public void swipeLateral(AndroidDriver driver, WebElement slider, int x, int swipe_point_x_end, int swipe_point_y) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(swipe_point_x_end, swipe_point_y))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(x, swipe_point_y))
                .release()
                .perform();
    }
}
