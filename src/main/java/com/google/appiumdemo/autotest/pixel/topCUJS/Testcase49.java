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
import org.openqa.selenium.Point;
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
 * 1. Go to Languages main page.
 * 2. Add one language into selection list.
 * 3. Change system language to what we just added.
 *
 * check point
 * New locale could be added and be applied successfully.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase49 {
    private static Logger logger = Logger.getLogger(Testcase49.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase49(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "t49")
    public ResponseEntity changeLanguage() {
        try {
            AndroidDriver driver = getDriver();
            try {


                //step 1 :Go to Languages main page.
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.System).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Languages_input).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Languages).click();

                //pre delete all language
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.CONTENT_DESC,"More options").click();
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"Remove").click();
                List<WebElement> del_lang_list = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.CheckBox");
                for(int i=1;i<del_lang_list.size();i++){
                    del_lang_list.get(i).click();
                }

                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.CONTENT_DESC,"Remove").click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"Remove").click();
                //step 2: Add one language into selection list.
                WebElement chinese=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "简体中文（中国）");
                if(null ==chinese){// add chinese languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("简体");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"简体中文").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"中国").click();
                }

                //step 3: Change system language to what we just added.
                List<WebElement> languages1 = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.ImageView");
                TouchAction touchAction = new TouchAction(driver);
                Point location = languages1.get(0).getLocation();
                Point new_location = new Point(location.x,location.y-50);
                touchAction.press(PointOption.point(languages1.get(1).getLocation()))
                        .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(new_location))
                        .release()
                        .perform();

                Thread.sleep(2000);
                String language_changed = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/add_language").getText();
                if(!"添加语言".equals(language_changed)){
                    return ResponseEntity.failed("failed, When the language change to the Chinese, it should show <添加语言>,but instead of "+language_changed,null);
                }

                // switch to the default
                List<WebElement> languages2 = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.ImageView");
                Point location2 = languages2.get(0).getLocation();
                Point new_location2 = new Point(location2.x,location2.y-50);

                touchAction.press(PointOption.point(languages2.get(1).getLocation()))
                        .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(new_location2))
                        .release()
                        .perform();
                Thread.sleep(2000);
                language_changed = GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.ID, "com.android.settings:id/add_language").getText();

                if(!"Add a language".equals(language_changed)){
                    return ResponseEntity.failed("failed, When the language change to the Chinese, it should show <添加语言>,but instead of "+language_changed,null);
                }

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
