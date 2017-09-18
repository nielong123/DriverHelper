package com.jaydenxiao.common.commonutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/9/15.
 */

public class BitmapUtils {

    public static Bitmap Compress(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 20) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bm = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        Log.e("wechat", "压缩后图片的大小" + bm.getByteCount()
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "quality=" + options);
        return bm;
    }

    public static Bitmap CompressByQuality(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.e("wechat", "压缩后图片的大小" + bm.getByteCount()
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "quality=" + quality + "  数据长度为 = " + bytes.length);
        return bm;
    }

    public static byte[] CompressGetBytes(Bitmap bitmap, int quality) {
        byte[] bytes;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            bytes = baos.toByteArray();
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.e("wechat", "压缩后图片的大小" + bm.getByteCount()
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "quality=" + quality + "  bytes.length = " + bytes.length);
        return bytes;
    }

    public static Bitmap CompressByMatrix(Bitmap bitmap, float scale) {

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
        return bm;
    }

    public static Bitmap CompressByScaled(Bitmap bitmap) {
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024) + "KB宽度为"
                + bm.getWidth() + "高度为" + bm.getHeight());
        return bm;
    }

//    public static Bitmap CompressByRGB_565(Bitmap bitmap){
//        BitmapFactory.Options options2 = new BitmapFactory.Options();
//        options2.inPreferredConfig = Bitmap.Config.RGB_565;
//
//        Bitmap bm = BitmapFactory.decodeFile(Environment
//                .getExternalStorageDirectory().getAbsolutePath()
//                + "/DCIM/Camera/test.jpg", options2);
//        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
//                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
//    }

//    /****
//     * 采样率
//     * @param bitmap
//     * @return
//     */
//    public static Bitmap CompressBySamplingRate(Bitmap bitmap) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//
//        Bitmap bm = BitmapFactory.decodeFile(Environment
//                .getExternalStorageDirectory().getAbsolutePath()
//                + "/DCIM/Camera/test.jpg", options);
//        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
//                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
//        return bm;
//    }
}
