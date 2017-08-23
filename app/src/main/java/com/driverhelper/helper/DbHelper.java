package com.driverhelper.helper;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.db.GpsInfo;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.beans.gen.GpsInfoDao;
import com.driverhelper.beans.gen.StudyInfoDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
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


    public void addStudyInfoDao(Long id, int waterCode, String studentId, String coachId, long classId,
                                String photoPath, String makeTime, String type, int vehicleSpeed,
                                int distance, int speed, long time) {
        Log.w("dbhelper", "write db studyInfoDao");
        studyInfo = new StudyInfo(id, waterCode, studentId, coachId, classId,
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


    public List<StudyInfo> queryStudyInfoByTime(long startTime, long endTime) {
        List<StudyInfo> list = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(startTime, endTime)).build().list();
        return list;
    }

    public List<StudyInfo> queryStudyInfoByNum(int num) {
        List list = studyInfoDao.queryBuilder()
                .where(StudyInfoDao.Properties.Id.le(num))
                .orderDesc(StudyInfoDao.Properties.Id)
                .list();
        return list;
    }

    public List<String> queryPictureByTime(long startTime, long endTime) {
//        List<StudyInfo> studyInfos = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(startTime, endTime)).build().list();
        List<StudyInfo> studyInfos = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(1503392586697l, 1503392633606l)).build().list();
        if (studyInfos != null) {
            List<String> result = new ArrayList<>();
            for (StudyInfo info : studyInfos) {
                if (!TextUtils.isEmpty(info.getPhotoPath())) {
                    result.add(info.getPhotoPath());
                }
            }

            Log.e("123","1111111111111");
            return result;
        }
        return null;
    }
}
