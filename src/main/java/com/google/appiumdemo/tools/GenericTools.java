package com.google.appiumdemo.tools;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/**
 * common test tools
 */
public class GenericTools {
    private static Logger logger = Logger.getLogger(GenericTools.class);


    /**
     * check the element is exists
     *
     * @param by     Liming
     * @param driver AndroidDriver
     * @return if exists return true ,else return false
     */
    public static boolean elementIsExist(By by, AppiumDriver driver) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            logger.warn("call elementExist method, can't find elements");
            return false;
        }
    }



    /**
     * check the element is exists, if exists then click it,else just return false
     *
     * @param by     Liming
     * @param driver AndroidDriver
     * @return if exists return true ,else return false
     */
    public static boolean elementIsExistAndClick(By by, AppiumDriver driver) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            driver.findElement(by).click();
            return true;
        } catch (Exception e) {
            return false;
        }
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
    public static WebElement findElementSwipeDownByXpath(AndroidDriver driver, String type, String text) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        if (GenericTools.elementIsExist(By.xpath("//*[@" + type + "='" + text + "']"), driver)) {
            return driver.findElement(By.xpath("//*[@" + type + "='" + text + "']"));
        }

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width

        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, 1200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, 200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

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
     * @param driver AndroidDriver
     * @param type   xpath type,you can reference <https://www.cnblogs.com/cnkemi/p/9180525.html>
     * @param text   what text you want to find
     * @return if found text return WebElement, else return null.
     * @throws InterruptedException
     */
    public static WebElement findElementSwipeDownFromBottomByXpath(AndroidDriver driver, String type, String text) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        if (GenericTools.elementIsExist(By.xpath("//*[@" + type + "='" + text + "']"), driver)) {
            return driver.findElement(By.xpath("//*[@" + type + "='" + text + "']"));
        }

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;
        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, height - 200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, height - 1200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

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
     * search what you want to find text until to the head,
     * Returns null if it not found,
     * else return found WebElement object.
     *
     * @param driver AndroidDriver
     * @param type   xpath type,you can reference <https://www.cnblogs.com/cnkemi/p/9180525.html>
     * @param text   what text you want to find
     * @return if found text return WebElement, else return null.
     * @throws InterruptedException
     */
    public static WebElement findElementSwipeUpByXpath(AndroidDriver driver, String type, String text) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        if (GenericTools.elementIsExist(By.xpath("//*[@" + type + "='" + text + "']"), driver)) {
            return driver.findElement(By.xpath("//*[@" + type + "='" + text + "']"));
        }

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width

        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, 500))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, 1500))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

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
     *
     * @param driver
     */
    public static void swipeToNextPage(AndroidDriver driver) {
        TouchAction action = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;//current page width

        action.press(PointOption.point(width / 2, (height * 8 / 10)))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 2, height / 9))
                .release()
                .perform();
    }


    /**
     * find Element by text name,If method can't find element then throw exception
     *
     * @return what you find element,if not found throw an exception
     */
    public static WebElement findElementByXpath(AndroidDriver driver, String type, String text) {
        if (elementIsExist(By.xpath("//*[@" + type + "='" + text + "']"), driver)) {
            return driver.findElement(By.xpath("//*[@" + type + "='" + text + "']"));
        } else {
            return null;
        }
    }


    /**
     * @param type xpath type,reference to XpathConstant class
     * @param text textName
     * @return selector
     */
    public static By byXpath(String type, String text) {
        return By.xpath("//*[@" + type + "='" + text + "']");
    }


    /**
     * findElement by resource id ,if you find then return webElement object otherwise return null.
     *
     * @param driver Android Driver
     * @param id     resource-id
     * @return
     */
    public static WebElement findElementById(AndroidDriver driver, String id) {
        if (elementIsExist(By.id(id), driver)) {
            return driver.findElement(By.id(id));
        } else {
            return null;
        }
    }

    /**
     * @param driver Android Driver
     * @param id
     * @return if you find element return element object otherwise return null
     */
    public static List<WebElement> findElementsById(AndroidDriver driver,String id){
        if(elementIsExist(By.id(id),driver)){
            return driver.findElements(By.id(id));
        }else {
            return null;
        }
    }

    /**
     Find element by Uiautomator
     * @param driver
     * @param text
     * @return if element exist return element object otherwise return null.
     */
    public static WebElement findElementByUiautomator(AndroidDriver driver, String text) {
        if (elementIsExist(AppiumBy.androidUIAutomator(text), driver)) {
            return driver.findElement(AppiumBy.androidUIAutomator(text));
        } else {
            return null;
        }
    }


    /**
     * back to the previous page
     * @param driver Andorid driver
     */
    public static void backToPrevious(AndroidDriver driver){
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    public static List<WebElement> findElementsByClass(AndroidDriver driver, String classname){
        if (elementIsExist(By.className(classname),driver)) {
            return driver.findElements(By.className(classname));
        }else{
            return null;
        }
    }


    public static WebElement findElementSwipeDownByUiautomator(AndroidDriver driver, String text) {
        if (elementIsExist(AppiumBy.androidUIAutomator(text), driver)) {
            return driver.findElement(AppiumBy.androidUIAutomator(text));
        }

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width

        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, 1200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, 200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

            if (elementIsExist(AppiumBy.androidUIAutomator(text), driver)) {
                return driver.findElement(AppiumBy.androidUIAutomator(text));
            }

            // get current page,check if it has scrolled
            cur_pageSource = driver.getPageSource();
            pre_pageSource = cur_pageSource;

        }
        return null;

    }


    /**
     * swipe to the bottom
     * @param driver
     * @throws InterruptedException
     */
    public static void swipeToBottom(AndroidDriver driver) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;
        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, height - 200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, height - 1200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

            // get current page,check if it has scrolled
            cur_pageSource = driver.getPageSource();
            pre_pageSource = cur_pageSource;

        }
    }


    /**
     * swipe to the head
     * @param driver
     * @throws InterruptedException
     */
    public static void swipeToHead(AndroidDriver driver) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;
        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, height - 1200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, height - 200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

            // get current page,check if it has scrolled
            cur_pageSource = driver.getPageSource();
            pre_pageSource = cur_pageSource;

        }
    }

    public static List<WebElement> findElementsByClassSwipeDown(AndroidDriver driver, String classname){
        if (elementIsExist(By.className(classname),driver)) {
            return driver.findElements(By.className(classname));
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

        if (elementIsExist(By.className(classname),driver)) {
            return driver.findElements(By.className(classname));
        }

        TouchAction touchAction = new TouchAction(driver);

        int width = driver.manage().window().getSize().width;//current page width
        int height = driver.manage().window().getSize().height;
        String pageSource = driver.getPageSource();
        String cur_pageSource = "";
        String pre_pageSource = pageSource;

        while (!pageSource.equals(cur_pageSource)) {
            pageSource = pre_pageSource;

            //scroll once
            touchAction.press(PointOption.point(width / 2, height - 200))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(width / 2, height - 1200))
                    .release()
                    .perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));

            if (elementIsExist(By.className(classname),driver)) {
                return driver.findElements(By.className(classname));
            }

            // get current page,check if it has scrolled
            cur_pageSource = driver.getPageSource();
            pre_pageSource = cur_pageSource;

        }
        return null;
    }
}
