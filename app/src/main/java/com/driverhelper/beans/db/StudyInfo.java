package com.driverhelper.beans.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/8/9.
 */

@Entity
public class StudyInfo {

    @Id
    private Long id;

    @Property(nameInDb = "STUDENTID")       //学员id
    private String studentId;

    @Property(nameInDb = "COACHID")     //教练id
    private String coachId;

    @Property(nameInDb = "CLASSID")     //课堂id
    private long classId;

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

    @Property(nameInDb = "TIME")        //时间
    private long time;

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

    public long getClassId() {
        return this.classId;
    }

    public void setClassId(long classId) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 707719013)
    public StudyInfo(Long id, String studentId, String coachId, long classId,
            String photoPath, String makeTime, String type, int vehicleSpeed,
            int distance, int speed, long time) {
        this.id = id;
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
    }

    @Generated(hash = 1468203050)
    public StudyInfo() {
    }

    @Override
    public String toString() {
        return "StudyInfo{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", coachId='" + coachId + '\'' +
                ", classId=" + classId +
                ", photoPath='" + photoPath + '\'' +
                ", makeTime='" + makeTime + '\'' +
                ", type='" + type + '\'' +
                ", vehicleSpeed=" + vehicleSpeed +
                ", distance=" + distance +
                ", speed=" + speed +
                ", time=" + time +
                '}';
    }
}
