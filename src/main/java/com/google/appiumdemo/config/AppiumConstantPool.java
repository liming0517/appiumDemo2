package com.google.appiumdemo.config;

import org.springframework.stereotype.Component;

@Component
public class AppiumConstantPool {
    public final static String DEVICE_NAME="appium:deviceName";
    public final static String AUTOMATION_NAME="appium:automationname";
    public final static String PLATFORM_NAME="platformName";
    public final static String PLATFORM_VERSION="appium:platformVersion";
    public final static String APP_PACKAGE="appium:appPackage";
    public final static String APP_ACTIVITY="appium:appActivity";
    public final static String SESSION_OVERRIDE="appium:sessionOverride";
    public final static String URL="appium:url";
    public static final String REMOTE_URL = "http://127.0.0.1:4723/wd/hub";
}
