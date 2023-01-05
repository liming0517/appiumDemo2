package com.google.appiumdemo.tools;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class GenericToolsForTango{

    /**
     * in settings app,Switch to the left page
     */
    public static void switchToRightPage(AndroidDriver driver) throws InterruptedException {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(width / 12*11, height / 10))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 10 *9, height / 9))
                .release()
                .perform();
    }

    /**
     * in settings app,Switch to the left page
     */
    public static void switchToLeftPage(AndroidDriver driver) throws InterruptedException {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(width / 15, height / 10))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 15, height / 3))
                .release()
                .perform();
    }

    /**
     * search what you want to find text until to the bottom,
     * Returns null if it not found,
     * else return found WebElement object.
     *
     * @param driver AndroidDriver
     * @param type   xpath type,you can reference <https://www.cnblogs.com/cnkemi/p/9180525.html>
     * @param text   what text you want to find
     * @return if found text return WebElement, else return null.
     * @throws InterruptedException
     */
    public static WebElement swipeDownToFindElementFromLeft(AndroidDriver driver, String type, String text) throws InterruptedException {
        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width

        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 15, 700))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 15, 200))
                    .release()
                    .perform();

            Thread.sleep(1000);

            if (GenericTools.elementIsExist(By.xpath("//*[@" + type + "='" + text + "']"), driver)) {
                return driver.findElement(By.xpath("//*[@" + type + "='" + text + "']"));
            }

            // get current page,check if it has scrolled
            cur_pageSource = driver.getPageSource();
            pre_pageSource = cur_pageSource;

        }
        return null;
    }

    /**
     * swipe down to the next page from the left section
     * @param driver
     */
    public static void swipeToNextPageFromLeft(AndroidDriver driver) {
        TouchAction action = new TouchAction(driver);
        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;//current page width
        action.press(PointOption.point(width / 4, (height * 8 / 10)))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 4, height / 9))
                .release()
                .perform();
    }

    /**
     * swipe down to the next page from the right section
     * @param driver AndroidDriver
     */
    public static void swipeToNextPageFromRight(AndroidDriver driver) {
        TouchAction action = new TouchAction(driver);
        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;//current page width
        action.press(PointOption.point(width * 3 / 4, (height * 8 / 10)))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width * 3 / 4, height / 9))
                .release()
                .perform();
    }


}
