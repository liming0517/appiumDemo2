package com.google.appiumdemo.config;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.MalformedURLException;

@Configuration
@ConfigurationProperties(prefix = "desiredcapabilities")
@Getter
@Setter
public class AppiumConfig {
    private String deviceName;
    private String automationname;
    private String platformname;
    private String platformversion;
    private String apppackage;
    private String appactivity;
    private String sessionOverride;
    private String url;
    private String appWaitActivity;

    @Bean
    public DesiredCapabilities setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(AppiumConstantPool.DEVICE_NAME,deviceName);
        capabilities.setCapability(AppiumConstantPool.AUTOMATION_NAME,automationname);
        capabilities.setCapability(AppiumConstantPool.PLATFORM_NAME,platformname);
        capabilities.setCapability(AppiumConstantPool.PLATFORM_VERSION,platformversion);
        capabilities.setCapability(AppiumConstantPool.APP_PACKAGE,apppackage);
        capabilities.setCapability(AppiumConstantPool.APP_ACTIVITY,appactivity);
        capabilities.setCapability(AppiumConstantPool.SESSION_OVERRIDE,sessionOverride);
        capabilities.setCapability(AppiumConstantPool.URL,url);
        //debug
        System.err.println(deviceName);
        System.err.println(automationname);
        return capabilities;
    }


}
