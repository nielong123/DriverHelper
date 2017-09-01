package com.driverhelper.helper;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.beans.gen.StudyInfoDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class DbHelper {

    private StudyInfo studyInfo;

    private static StudyInfoDao studyInfoDao;

    static DbHelper dbHelper;

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            synchronized (DbHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DbHelper();
                    studyInfoDao = MyApplication.getInstance().getDaoSession().getStudyInfoDao();
                }
            }
        }
        return dbHelper;
    }

    public void addStudyInfo(int waterCode, String studentId, String coachId,
                             String classId, String photoPath, String makeTime, String type, int vehicleSpeed,
                             int distance, int speed, long time, boolean isUpdata, float speedGPS,
                             float direction, double lat, double lon, long timeGPS) {
        studyInfo = new StudyInfo(null, waterCode, studentId, coachId,
                classId, photoPath, makeTime, type, vehicleSpeed,
                distance, speed, time, isUpdata, speedGPS,
                direction, lat, lon, timeGPS);
        Log.e("dbhelper", "write db studyInfoDao , " + studyInfo.toString());
        studyInfoDao.insert(studyInfo);
    }

    public void addLocationInfo(int vehicleSpeed, int distance,
                                int speed, long time, boolean isUpdata,
                                float speedGPS, float direction, double lat,
                                double lon, long timeGPS) {
        studyInfo = new StudyInfo(
                null,
                0,
                null,
                null,
                null,
                null,
                null,
                null,
                vehicleSpeed,
                distance,
                speed,
                time,
                isUpdata,
                speedGPS,
                direction,
                lat,
                lon,
                timeGPS);
        studyInfoDao.insert(studyInfo);
        Log.e("dbhelper", "write db studyInfoDao , " + studyInfo.toString());
    }

    public void deteleStudyInfoById(long id) {
        studyInfoDao.deleteByKey(id);
    }

    public void deteleStudyInfoALl() {
        studyInfoDao.deleteAll();
    }

    public void deteleStudyInfoByTime(long time) {
        studyInfoDao.getDatabase().execSQL("DELETE  FROM Study_Info where TIME < " + time);
    }

    public void deteleStudyInfoUped() {
        studyInfoDao.getDatabase().execSQL("DELETE  FROM Study_Info where ISUPDATA = 1 ");
    }


    public List<StudyInfo> queryStudyInfoAll() {
        List<StudyInfo> list = studyInfoDao.loadAll();
        return list;
    }


    public List<StudyInfo> queryStudyInfoByTime(long startTime, long endTime) {
        List<StudyInfo> list = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(startTime, endTime)).build().list();
        return list;
    }

    /***
     * 删除已上传的学时信息
     * @return
     */
    public List<StudyInfo> queryStudyInfoByUp() {
        List<StudyInfo> list = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.IsUpdata.eq(0)).build().list();
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
        List<StudyInfo> studyInfos = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(startTime, endTime)).build().list();
//        List<StudyInfo> studyInfos = studyInfoDao.queryBuilder().where(StudyInfoDao.Properties.Time.between(1503392586697l, 1503392633606l)).build().list();
        if (studyInfos != null) {
            List<String> result = new ArrayList<>();
            for (StudyInfo info : studyInfos) {
                if (!TextUtils.isEmpty(info.getPhotoPath())) {
                    result.add(info.getPhotoPath());
                }
            }
            return result;
        }
        return null;
    }

    public void setUpState(int waterCode) {
        studyInfoDao.getDatabase().execSQL("UPDATE STUDY_INFO set IsUpdata = 1 where WATERCODE = " + waterCode);
    }
}
