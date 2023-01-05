package com.google.appiumdemo.autotest.pixel.forTestTracker.testsuit1_1_0;

import com.google.appiumdemo.config.AppiumConfig;
import com.google.appiumdemo.config.AppiumConstantPool;
import com.google.appiumdemo.entity.ResponseEntity;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Adjust volume bars
 * 1. Go to All apps page.
 * 2. Use all app search function to search for random apps we want to change.
 * 3. Go to Notification settings of App detail page.
 * 4. If the switch of Notification is disabled, try to enable it.
 * 5. If the switch of Notification is enabled, try to enable Notification and
 * 'Allow notification dot'.
 *
 * Pass Criteria:
 * (1) When Notification is turned off, 'Allow notification dot' is hidden.
 * (2) After turning on/off Notification or 'Allow notification dot', no error is found.
 */
@Controller
@ResponseBody
@RequestMapping("pixel")
public class TestCase1_1_4 {
    private static Logger logger = Logger.getLogger(TestCase1_1_4.class);

    @Autowired
    public final AppiumConfig config;

    //constructor
    public TestCase1_1_4(AppiumConfig config) {
        this.config = config;
    }

    public AndroidDriver getDriver() throws MalformedURLException {
        return new AndroidDriver(new URL(AppiumConstantPool.REMOTE_URL), config.setup());
    }

    //Todo
    @RequestMapping(value = "testcase1.1.4")
    public ResponseEntity adjustVolume() {
        try {
            AndroidDriver driver = getDriver();
            try {
                //TODO
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
        return null;
    }
}
