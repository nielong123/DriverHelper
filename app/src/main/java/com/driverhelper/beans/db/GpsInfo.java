package com.driverhelper.beans.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/8/9.
 */
@Entity
public class GpsInfo {

    @Id
    private Long id;

    @Property(nameInDb = "SPEEDGPS")
    private float speedGPS;

    @Property(nameInDb = "DIRECTION")
    private float direction;

    @Property(nameInDb = "LAT")
    private double lat;

    @Property(nameInDb = "LON")
    private double lon;

    @Property(nameInDb = "TIMEGPS")
    private long timeGPS;

    public long getTimeGPS() {
        return this.timeGPS;
    }

    public void setTimeGPS(long timeGPS) {
        this.timeGPS = timeGPS;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public float getDirection() {
        return this.direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getSpeedGPS() {
        return this.speedGPS;
    }

    public void setSpeedGPS(float speedGPS) {
        this.speedGPS = speedGPS;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 126897118)
    public GpsInfo(Long id, float speedGPS, float direction, double lat,
            double lon, long timeGPS) {
        this.id = id;
        this.speedGPS = speedGPS;
        this.direction = direction;
        this.lat = lat;
        this.lon = lon;
        this.timeGPS = timeGPS;
    }

    @Generated(hash = 1430255473)
    public GpsInfo() {
    }

    @Override
    public String toString() {
        return "GpsInfo{" +
                "id=" + id +
                ", speedGPS=" + speedGPS +
                ", direction=" + direction +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timeGPS=" + timeGPS +
                '}';
    }
}
