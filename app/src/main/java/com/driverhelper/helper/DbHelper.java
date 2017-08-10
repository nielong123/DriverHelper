package com.driverhelper.helper;

import android.support.annotation.Nullable;
import android.util.Log;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.db.GpsInfo;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.beans.gen.GpsInfoDao;
import com.driverhelper.beans.gen.StudyInfoDao;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class DbHelper {

    private GpsInfo gpsInfo;
    private StudyInfo studyInfo;

    private static GpsInfoDao gpsInfoDao;
    private static StudyInfoDao studyInfoDao;

    static DbHelper dbHelper;

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
            gpsInfoDao = MyApplication.getInstance().getDaoSession().getGpsInfoDao();
            studyInfoDao = MyApplication.getInstance().getDaoSession().getStudyInfoDao();
        }
        return dbHelper;
    }


    public void addGpsInfo(@Nullable Long id, float speedGPS, float direction, double lat, double lon, long timeGPS) {
        Log.w("dbhelper", "write db addGpsInfo");
        gpsInfo = new GpsInfo(null, speedGPS, direction, lat, lon, timeGPS);
        gpsInfoDao.insert(gpsInfo);
    }

    public void deleteGpsInfoById(long id) {
        gpsInfoDao.deleteByKey(id);
    }

    public void deleteGpsInfoALl() {
        gpsInfoDao.deleteAll();
    }


    public List<GpsInfo> queryGpsInfoAll() {

        List<GpsInfo> list = gpsInfoDao.loadAll();
        return list;
    }

    public GpsInfo queryGpsInfoByTime(long time) {
        List<GpsInfo> list = gpsInfoDao.loadAll();
        for (GpsInfo data : list) {
            if (time == data.getTimeGPS()) {
                return data;
            }
        }
        return null;
    }


    public void addStudyInfoDao(Long id, String studentId, String coachId, long classId,
                                String photoPath, String makeTime, String type, int vehicleSpeed,
                                int distance, int speed, long time) {
        Log.w("dbhelper", "write db studyInfoDao");
        studyInfo = new StudyInfo(id, studentId, coachId, classId,
                photoPath, makeTime, type, vehicleSpeed,
                distance, speed, time);
        studyInfoDao.insert(studyInfo);
    }

    public void deleteStudyInfoById(long id) {
        studyInfoDao.deleteByKey(id);
    }

    public void deleteStudyInfoALl() {
        studyInfoDao.deleteAll();
    }

    public List<StudyInfo> queryStudyInfoAll() {
        List<StudyInfo> list = studyInfoDao.loadAll();
        return list;
    }


    public StudyInfo queryStudyInfoByTime(long time) {
        List<StudyInfo> list = studyInfoDao.loadAll();
        for (StudyInfo data : list) {
            if (time == data.getTime()) {
                return data;
            }
        }
        return null;
    }

//    public void deleteGpsInfoByOther(){
//        gpsInfoDao.deleteInTx();
//    }

}
