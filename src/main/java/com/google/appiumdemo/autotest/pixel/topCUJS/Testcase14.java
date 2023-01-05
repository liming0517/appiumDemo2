package com.google.appiumdemo.autotest.pixel.topCUJS;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.config.LanguageText;
import com.google.appiumdemo.entity.ResponseEntity;
import com.google.appiumdemo.tools.GenericTools;
import com.google.appiumdemo.tools.XpathConstant;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
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
 Step:
 1. Go to Settings -> Display page.
 2. click and adjust on Brightness level.
 3. Enable/Disable dark theme from in-line toggle in Display main page(dark theme toggle).
 4. Enable/Disable dark theme via main switch in Dark theme page(Dark theme->use Dark theme).

 Expect:
 1.Brightness bar will pop up when click on Brightness level setting.
 2.There is no crash or app not responding message pops after switch on/off dark theme then swipe up/down list view.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase14 {
    private static Logger logger = Logger.getLogger(Testcase14.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase14(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t14")
    public ResponseEntity adjustBrightness() {
        try {
            AndroidDriver driver = getDriver();
            try {
                driver.startActivity(new Activity("com.android.settings", "com.android.settings.Settings"));
                //STEP 1 : Go to Settings -> Display page.
                WebElement display = GenericTools.findElementSwipeDownByXpath(driver, "text", LanguageText.Display);

                //STEP 2 : click and adjust on Brightness level.
                if (null != display) {// can get the display element
                    display.click();
                    GenericTools.findElementByXpath(driver, XpathConstant.TEXT,LanguageText.Brightness_level ).click();
                    WebElement slider = driver.findElement(By.id("com.android.systemui:id/slider"));
                    if (null != slider) {
                        int height = slider.getRect().height;
                        int width = slider.getRect().width;
                        int x = slider.getRect().x + 20;
                        int y = slider.getRect().y;
                        int swipe_point_y = y + height / 2;
                        int swipe_point_x_end = (x + width) * 4 / 5;

                        //CHECK POINT 1: Brightness bar will pop up when click on Brightness level setting.
                        this.swipeLateral(driver, slider, x, swipe_point_x_end, swipe_point_y);
                        Thread.sleep(1000);
                        this.swipeLateral(driver, slider, swipe_point_x_end, x, swipe_point_y);
                        Thread.sleep(1000);
                        this.swipeLateral(driver,slider,x + 100,x+200,y+height+100);
                    } else {
                        return ResponseEntity.failed("can't find bright slider", null);
                    }

                } else {
                    logger.error("can't find Display Element");
                    return ResponseEntity.failed("can't find Display Element,please connect to develop", null);
                }

                //STEP 3 : Enable/Disable dark theme from in-line toggle in Display main page(dark theme toggle).
                WebElement dark_theme_switch = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.CONTENT_DESC, LanguageText.Dark_theme);
                if(null != dark_theme_switch){
                    dark_theme_switch.click();
                    Thread.sleep(1000);
                }else{
                    return ResponseEntity.failed("can't find Display duck theme switch button", null);
                }

                //STEP 4 : Enable/Disable dark theme via main switch in Dark theme page(Dark theme->use Dark theme).
                WebElement dark_theme = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Dark_theme);
                if(null != dark_theme_switch){
                    dark_theme.click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/switch_text").click();
                    Thread.sleep(2000);
                    GenericTools.backToPrevious(driver);
                    Thread.sleep(2000);
                }else {
                    return ResponseEntity.failed("can't find Display duck theme switch button", null);
                }
                //CHECK POINT 2 :  2.There is no crash or app not responding message pops after switch on/off dark theme then swipe up/down list view.
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
