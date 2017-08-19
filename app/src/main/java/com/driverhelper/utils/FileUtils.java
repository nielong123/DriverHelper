package com.driverhelper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/18.
 */

public class FileUtils {

    static public void saveBitmap(Context context, Bitmap bm) {
        Log.e("", "保存图片");
        File f = new File(context.getCacheDir(), new Date().getTime() + ".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 50, out);
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

    static public void saveBitmap(Context context, Bitmap bm, String fileName) {
        Log.e("", "保存图片");
        File f = new File(context.getFilesDir(), fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 50, out);
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


    static public byte[] loadBitmap(Context context, String name) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(context.getCacheDir() + name);
//            fis = new FileInputStream("/data/data/com.driverhelper1/cache/1503053714367.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        if (bitmap != null) {
            return ByteUtil.bitmap2Bytes(bitmap);
        }
        return null;
    }
}
