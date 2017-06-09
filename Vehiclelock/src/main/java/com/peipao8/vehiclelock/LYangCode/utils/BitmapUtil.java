package com.peipao8.vehiclelock.LYangCode.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.peipao8.vehiclelock.LYangCode.utils.log.LYangLogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by ooo on 2016/1/21.
 * 图片处理器
 */
public class BitmapUtil {

    /**
     * 使图片变为灰色
     *
     * @param bitmap
     * @return
     */
    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

    /*
     * 创建带字母的圆形标记图片
     */
    public Bitmap createDrawable(Bitmap imgMarker, int width, int height, int letter) {
        Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint(); // 建立画笔
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);
        canvas.drawBitmap(imgMarker, src, dst, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(20.0f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.WHITE);
        if (letter < 10) {
            canvas.drawText(String.valueOf(letter), width / 2 - 6, height / 2 + 7,
                    textPaint);
        } else {
            canvas.drawText(String.valueOf(letter), width / 2 - 12, height / 2 + 7,
                    textPaint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return imgTemp;
    }

    public static class InnerBitmapEntity {
        public float x;
        public float y;
        public float width;
        public float height;
        public static int devide = 1;
        public int index = -1;

        @Override
        public String toString() {
            return "InnerBitmapEntity [x=" + x + ", y=" + y + ", width=" + width
                    + ", height=" + height + ", devide=" + devide + ", index="
                    + index + "]";
        }
    }

    private static final String TAG = "ECSDK.Demo.BitmapUtil";

    public static Bitmap getCombineBitmaps(List<InnerBitmapEntity> mEntityList,
                                           Bitmap... bitmaps) {
        LYangLogUtil.d ( TAG, "getCombineBitmaps--> mEntityList count=" + mEntityList.size() );    //调试输出----
        Bitmap newBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        LYangLogUtil.d ( TAG, "getCombineBitmaps--> newBitmap=" + newBitmap.getWidth() + ","+ newBitmap.getHeight());
        for (int i = 0; i < mEntityList.size(); i++) {
            newBitmap = mixtureBitmap(newBitmap, bitmaps[i], new PointF(
                    mEntityList.get(i).x, mEntityList.get(i).y));
        }
        return newBitmap;
    }

    public static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
                                       PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
                first.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newBitmap;
    }

    public static String saveBitmapToLocal(String outPath, Bitmap bitmap) {
        try {
            String imagePath = FileAccessor.getAvatarPathName() + "/" + DemoUtils.md5(outPath);
            File file = new File(imagePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.close();
            LYangLogUtil.d ( TAG, "saveBitmapToLocal-->  photo image from data, path:" + imagePath);
            return imagePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片路径生成图片
     *
     * @param pathName
     * @return
     */
    public static Bitmap createBitmap(String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }

    public static Bitmap getBitmap(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
