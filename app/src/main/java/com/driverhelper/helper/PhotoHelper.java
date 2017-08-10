package com.driverhelper.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jaydenxiao.common.commonutils.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/9.
 */

public class PhotoHelper {

    public static final String TAG = "PhotoHelper";

    public static String saveBitmap(Context context, byte[] data) {
        Log.e(TAG, "保存图片");
        File f = new File(context.getExternalCacheDir(), TimeUtil.getTime() + ".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            Bitmap bitmap = Bytes2Bitmap(data);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存" + f.getPath());
            return f.getPath();
//            bitmap.recycle();
//            bitmap = null;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    static Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }

    static public Bitmap loadBitmap(String path) {

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        return bitmap;
    }

}
