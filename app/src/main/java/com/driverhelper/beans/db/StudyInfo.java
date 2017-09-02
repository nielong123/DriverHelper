package com.driverhelper.beans.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/8/9.
 */

@Entity
public class StudyInfo {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "WATERCODE")       //流水号
    private int waterCode;

    @Property(nameInDb = "STUDENTID")       //学员id
    private String studentId;

    @Property(nameInDb = "COACHID")     //教练id
    private String coachId;

    @Property(nameInDb = "CLASSID")     //课堂id
    private String classId;

    @Property(nameInDb = "PHOTOPATH")       //照片的路径
    private String photoPath;

    @Property(nameInDb = "MAKETIME")          //记录产生时间
    private String makeTime;

    @Property(nameInDb = "TYPE")        //课程种类
    private String type;

    @Property(nameInDb = "VEHICLESPEED")        //车速
    private int vehicleSpeed;

    @Property(nameInDb = "DISTANCE")        //距离
    private int distance;

    @Property(nameInDb = "SPEED")       //发动机转速
    private int speed;

    @Index(unique = true)
    @Property(nameInDb = "TIME")        //时间
    private long time;

    @Property(nameInDb = "ISUPDATA")       //是否上传
    private boolean isUpdata;

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

    public boolean getIsUpdata() {
        return this.isUpdata;
    }

    public void setIsUpdata(boolean isUpdata) {
        this.isUpdata = isUpdata;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getVehicleSpeed() {
        return this.vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMakeTime() {
        return this.makeTime;
    }

    public void setMakeTime(String makeTime) {
        this.makeTime = makeTime;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCoachId() {
        return this.coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getWaterCode() {
        return this.waterCode;
    }

    public void setWaterCode(int waterCode) {
        this.waterCode = waterCode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "StudyInfo{" +
                "id=" + id +
                ", waterCode=" + waterCode +
                ", studentId='" + studentId + '\'' +
                ", coachId='" + coachId + '\'' +
                ", classId='" + classId + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", makeTime='" + makeTime + '\'' +
                ", type='" + type + '\'' +
                ", vehicleSpeed=" + vehicleSpeed +
                ", distance=" + distance +
                ", speed=" + speed +
                ", time=" + time +
                ", isUpdata=" + isUpdata +
                ", speedGPS=" + speedGPS +
                ", direction=" + direction +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timeGPS=" + timeGPS +
                '}';
    }

    public void setSpeedGPS(float speedGPS) {
        this.speedGPS = speedGPS;
    }

    @Generated(hash = 556779751)
    public StudyInfo(Long id, int waterCode, String studentId, String coachId,
                     String classId, String photoPath, String makeTime, String type,
                     int vehicleSpeed, int distance, int speed, long time,
                     boolean isUpdata, float speedGPS, float direction, double lat,
                     double lon, long timeGPS) {
        this.id = id;
        this.waterCode = waterCode;
        this.studentId = studentId;
        this.coachId = coachId;
        this.classId = classId;
        this.photoPath = photoPath;
        this.makeTime = makeTime;
        this.type = type;
        this.vehicleSpeed = vehicleSpeed;
        this.distance = distance;
        this.speed = speed;
        this.time = time;
        this.isUpdata = isUpdata;
        this.speedGPS = speedGPS;
        this.direction = direction;
        this.lat = lat;
        this.lon = lon;
        this.timeGPS = timeGPS;
    }

    @Generated(hash = 1468203050)
    public StudyInfo() {
    }

}
