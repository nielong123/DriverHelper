package com.driverhelper.beans;

/**
 * Created by Administrator on 2017/9/14.
 */

public class GpsModel {

    public float speedGPS;
    public float direction;
    public double lat, lon;
    public long timeGPS;
    public boolean isLocation;              //是否定位成功

    public float getSpeedGPS() {
        return speedGPS;
    }

    public void setSpeedGPS(float speedGPS) {
        this.speedGPS = speedGPS;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTimeGPS() {
        return timeGPS;
    }

    public void setTimeGPS(long timeGPS) {
        this.timeGPS = timeGPS;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
