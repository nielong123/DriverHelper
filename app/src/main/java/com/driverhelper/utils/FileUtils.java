package com.driverhelper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jaydenxiao.common.commonutils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/18.
 */

public class FileUtils {

    static public void saveBitmap(String fullPath, Bitmap bm) {
        Log.e("", "保存图片");
        File f = new File(fullPath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static public byte[] loadBitmap(Context context, String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        if (bitmap != null) {
            return ByteUtil.bitmap2Bytes(bitmap);
        }
        return null;
    }

    public static File bitmap2File(String fullPath, Bitmap bitmap) {
        File file = new File(fullPath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            Log.e(TAG, "已保存");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static byte[] imageFile2Bitmap(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ByteUtil.bitmap2Bytes(BitmapFactory.decodeStream(fis));
    }
}

