package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.autotest.pixel.Template;
import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.entity.ResponseEntity;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestForMyself {
    private static Logger logger = Logger.getLogger(Template.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestForMyself(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "test")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                Thread.sleep(10000);
                //while (true) {
                int width = driver.manage().window().getSize().width;//current page width
                int middle = width / 2;
                TouchAction touchAction = new TouchAction(driver);
                int bottom_y = 1250;
                int top_y = 510;
                int tap_y = 350;
                System.out.println("");

              /*      touchAction.press(PointOption.point(middle, bottom_y))
                            .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(middle, top_y))
                            .release()
                            .perform();*/
                //explicit wait
/*                    WebDriverWait wait = new WebDriverWait(driver, 10);
                    WebElement elem = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tencent.mm:id/yn")));
                    return ResponseEntity.success("success",null);*/
                //
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                System.err.println(driver.findElement(By.id("com.tencent.mm:id/yn")).getText());
                return ResponseEntity.success("success",null);
                //Thread.sleep(2000);
                //touchAction.tap(PointOption.point(new Point(middle, tap_y))).perform();
                //Thread.sleep(5000);
                //}
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ResponseEntity.failed("cannot find", e.getMessage());
            } finally {
                driver.quit();
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.failed("get Android driver error", e.getMessage());
        }
    }
}
