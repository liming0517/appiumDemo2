package com.google.appiumdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "language")
public class LanguageText {
    @Value("${language.path}")
    private String path;


    // Text value
    public static String Network_internet;
    public static String Internet;
    public static String Wifi_name;
    public static String Connected;
    public static String Wifi_password;
    public static String Saved_networks;
    public static String Display;
    public static String Brightness_level;
    public static String DisplaySize;
    public static String Apps;
    public static String Camera;
    public static String Notifications;
    public static String Permissions;
    public static String Microphone;
    public static String Storage_cache;
    public static String Clear_storage;
    public static String Cache;
    public static String App_security;
    public static String Device_lock;
    public static String Google_security_checkup;
    public static String Find_my_device;
    public static String Security_update;
    public static String Google_play_system_update;
    public static String Privacy;
    public static String More_security_settings;
    public static String More_privacy_settings;
    public static String Screen_lock;
    public static String Face_Fingerprint_unlock;
    public static String Security_privacy;
    public static String Updates;
    public static String Sound_vibration;
    public static String Add_network;
    public static String Air_plane;
    public static String Private_DNS;
    public static String Hotspot;
    public static String Wifi_hotspot;
    public static String Hotspot_name;
    public static String Storage;
    public static String System;
    public static String Videos;
    public static String Images;
    public static String Trash;
    public static String Document_other;
    public static String Games;
    public static String Audio;
    public static String Battery;
    public static String Battery_usage;
    public static String Adaptive_preferences;
    public static String Dark_theme;
    public static String Accessibility;
    public static String About_phone;
    public static String Device_name;
    public static String Android_version;
    public static String IP_address;
    public static String Wifi_MAC_address;
    public static String Device_Wifi_MAC_address;
    public static String Bluetooth_address;
    public static String Up_time;
    public static String Connected_devices;
    public static String Connection_preferences;
    public static String Bluetooth;
    public static String Send_feedback;
    public static String Languages_input;
    public static String Languages;
    public static String Us_language;
    public static String Special_app_access;
    public static String All_files_access;
    public static String Device_admin_apps;
    public static String Display_over_other_apps;
    public static String Do_Not_Disturb_access;
    public static String Media_management_apps;
    public static String Modify_system_settings;
    public static String Device_app_notifications;
    public static String Picture_in_picture;
    public static String Premium_SMS_access;
    public static String Unrestricted_data;
    public static String Install_unknown_apps;
    public static String Alarms_reminders;
    public static String Usage_access;
    public static String VR_helper_services;
    public static String Wi_Fi_control;
    public static String Date_time;
    public static String Set_time_automatically;

    @Bean
    public void register() {
        FileReader reader = null;
        try {
            reader = new FileReader(path);
            Properties p = new Properties();
            p.load(reader);

            //set value
            Network_internet = p.getProperty("Network&internet");
            Internet = p.getProperty("Internet");
            Wifi_name = p.getProperty("Wifi_name");
            Connected = p.getProperty("Connected");
            Wifi_password = p.getProperty("Wifi_password");
            Saved_networks = p.getProperty("Saved_networks");
            Display = p.getProperty("Display");
            Brightness_level = p.getProperty("Brightness_level");
            DisplaySize = p.getProperty("DisplaySize");
            Apps = p.getProperty("Apps");
            Camera = p.getProperty("Camera");
            Notifications = p.getProperty("Notifications");
            Permissions = p.getProperty("Permissions");
            Microphone = p.getProperty("Microphone");
            Storage_cache = p.getProperty("Storage_cache");
            Clear_storage = p.getProperty("Clear_storage");
            Cache = p.getProperty("Cache");
            App_security = p.getProperty("App_security");
            Device_lock = p.getProperty("Device_lock");
            Google_security_checkup = p.getProperty("Google_security_checkup");
            Find_my_device = p.getProperty("Find_my_device");
            Security_update = p.getProperty("Security_update");
            Google_play_system_update = p.getProperty("Google_play_system_update");
            Privacy = p.getProperty("Privacy");
            More_security_settings = p.getProperty("More_security_settings");
            More_privacy_settings = p.getProperty("More_privacy_settings");
            Screen_lock = p.getProperty("Screen_lock");
            Face_Fingerprint_unlock = p.getProperty("Face_Fingerprint_unlock");
            Security_privacy = p.getProperty("Security_privacy");
            Updates = p.getProperty("Updates");
            Sound_vibration = p.getProperty("Sound_vibration");
            Add_network = p.getProperty("Add_network");
            Air_plane = p.getProperty("Air_plane");
            Private_DNS = p.getProperty("Private_DNS");
            Hotspot = p.getProperty("Hotspot");
            Wifi_hotspot = p.getProperty("Wifi_hotspot");
            Hotspot_name = p.getProperty("Hotspot_name");
            Storage = p.getProperty("Storage");
            System = p.getProperty("System");
            Videos = p.getProperty("Videos");
            Images = p.getProperty("Images");
            Trash = p.getProperty("Trash");
            Document_other = p.getProperty("Document_other");
            Games = p.getProperty("Games");
            Audio = p.getProperty("Audio");
            Battery = p.getProperty("Battery");
            Battery_usage = p.getProperty("Battery_usage");
            Adaptive_preferences = p.getProperty("Adaptive_preferences");
            Dark_theme = p.getProperty("Dark_theme");
            Accessibility = p.getProperty("Accessibility");
            About_phone = p.getProperty("About_phone");
            Device_name = p.getProperty("Device_name");
            Android_version = p.getProperty("Android_version");
            IP_address = p.getProperty("IP_address");
            Wifi_MAC_address = p.getProperty("Wifi_MAC_address");
            Device_Wifi_MAC_address = p.getProperty("Device_Wifi_MAC_address");
            Bluetooth_address = p.getProperty("Bluetooth_address");
            Up_time = p.getProperty("Up_time");
            Connected_devices = p.getProperty("Connected_devices");
            Connection_preferences = p.getProperty("Connection_preferences");
            Bluetooth = p.getProperty("Bluetooth");
            Send_feedback = p.getProperty("Send_feedback");
            Languages_input = p.getProperty("Languages_input");
            Languages = p.getProperty("Languages");
            Us_language = p.getProperty("Us_language");
            Special_app_access = p.getProperty("Special_app_access");
            All_files_access = p.getProperty("All_files_access");
            Device_admin_apps = p.getProperty("Device_admin_apps");
            Display_over_other_apps = p.getProperty("Display_over_other_apps");
            Do_Not_Disturb_access = p.getProperty("Do_Not_Disturb_access");
            Media_management_apps = p.getProperty("Media_management_apps");
            Modify_system_settings = p.getProperty("Modify_system_settings");
            Device_app_notifications = p.getProperty("Device_app_notifications");
            Picture_in_picture = p.getProperty("Picture_in_picture");
            Premium_SMS_access = p.getProperty("Premium_SMS_access");
            Unrestricted_data = p.getProperty("Unrestricted_data");
            Install_unknown_apps = p.getProperty("Install_unknown_apps");
            Alarms_reminders = p.getProperty("Alarms_reminders");
            Usage_access = p.getProperty("Usage_access");
            VR_helper_services = p.getProperty("VR_helper_services");
            Wi_Fi_control = p.getProperty("Wi_Fi_control");
            Date_time = p.getProperty("Date_time");
            Set_time_automatically = p.getProperty("Set_time_automatically");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
