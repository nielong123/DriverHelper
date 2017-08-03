package com.driverhelper.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/3.
 */

public class AssetsHelper {

    public static Bitmap getImageFromAssetsFile(Context mContext, String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
