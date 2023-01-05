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
 Steps:
 1. Randomly add 5 languages.
 2. Rearrange the list.
 3. Delete language.

 Check point:
 1. When removed the current applied language , there is pop up messages inform user.
 2. Language could be removed successfully.
 3. User could rearrange languages without problem.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class Testcase48 {
    private static Logger logger = Logger.getLogger(Testcase48.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public Testcase48(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //
    @RequestMapping(value = "t48")
    public ResponseEntity adjustLanguage() {
        try {
            AndroidDriver driver = getDriver();
            try {
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.System).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Languages_input).click();
                GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Languages).click();

                //Step 1 Randomly add 5 languages.
                WebElement Us_language=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, LanguageText.Us_language);
                if(null ==Us_language){// add English languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("english");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"English").click();

                }

                WebElement chinese=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "简体中文（中国）");
                if(null ==chinese){// add chinese languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("简体");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"简体中文").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"中国").click();
                }

                WebElement japanese=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "日本語 (日本)");
                if(null ==japanese){// add chinese languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("日本");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"日本語").click();
                }

                WebElement koren=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "한국어(대한민국)");
                if(null ==koren){// add chinese languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("한국");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"한국어").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"대한민국").click();
                }

                WebElement chinese_tai=GenericTools.findElementSwipeDownByXpath(driver, XpathConstant.TEXT, "繁體中文（台灣）");
                if(null ==chinese_tai){// add chinese languages
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"com.android.settings:id/add_language").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/locale_search_menu").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.ID,"android:id/search_src_text").sendKeys("繁體");
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"繁體中文").click();
                    GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"台灣").click();
                }
                //check point 3: User could rearrange languages without problem.

                //Step 2 Rearrange the list.
                List<WebElement> languages1 = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.ImageView");
                TouchAction touchAction = new TouchAction(driver);
                touchAction.press(PointOption.point(languages1.get(3).getLocation()))
                        .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(languages1.get(0).getLocation()))
                        .release()
                        .perform();

                List<WebElement> languages2 = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.ImageView");

                touchAction.press(PointOption.point(languages2.get(1).getLocation()))
                        .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(languages2.get(4).getLocation()))
                        .release()
                        .perform();

                //Step 3 Delete language. basic for language of english
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.CONTENT_DESC,"More options").click();
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"Remove").click();

                List<WebElement> del_lang_list = GenericTools.findElementsByClassSwipeDown(driver, "android.widget.CheckBox");
                for(int i=1;i<del_lang_list.size();i++){
                    del_lang_list.get(i).click();
                }

                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.CONTENT_DESC,"Remove").click();
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
                //check point 1: When removed the current applied language , there is pop up messages inform user.
                GenericTools.findElementSwipeDownByXpath(driver,XpathConstant.TEXT,"Remove").click();
                //check point 2: Language could be removed successfully.
                Thread.sleep(2000);
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
