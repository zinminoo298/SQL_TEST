package com.example.login_retro.Api.model;

public class Location {
    private String gps_latitude;
    private String   gps_longitude;
    private String battery_voltage;
    private String battery_level;
    private String is_armable;

    public Location(String battery_voltage, String battery_level, String is_armable) {
        this.battery_voltage = battery_voltage;
        this.battery_level = battery_level;
        this.is_armable = is_armable;
    }

    public String getBattery_voltage() {
        return battery_voltage;
    }

    public void setBattery_voltage(String battery_voltage) {
        this.battery_voltage = battery_voltage;
    }

    public String getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(String battery_level) {
        this.battery_level = battery_level;
    }

    public String getIs_armable() {
        return is_armable;
    }

    public void setIs_armable(String is_armable) {
        this.is_armable = is_armable;
    }

    public String getGps_latitude (){return gps_latitude;}

    public void setGps_latitude (String gps_latitude) { this.gps_latitude = gps_latitude;}

    public String getGps_longitude (){return gps_longitude;}

    public void setGps_longitude (String gps_longitude) { this.gps_longitude = gps_longitude;}
}
