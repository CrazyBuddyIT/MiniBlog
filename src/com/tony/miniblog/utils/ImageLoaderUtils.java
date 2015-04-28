package com.tony.miniblog.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tony on 2015/3/19 0019.
 */
public class ImageLoaderUtils extends Thread {

    private Context ctx;
    private android.os.Handler handler;
    private String imageUrl;

    public ImageLoaderUtils(Context ctx, String path, android.os.Handler handler) {
        this.ctx = ctx;
        this.handler = handler;
        this.imageUrl = path;
    }

    /**
     * 根据图片URL地址，下载图片
     *
     * @return
     */
    public Bitmap getIcon(String path) {

        Bitmap bitmap = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            InputStream is = null;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
            } else {
                is = null;
            }
            if (is == null) {
                throw new RuntimeException("stream is null");
            } else {
                byte[] data = readStream(is);
                if (data != null) {
                    bitmap = BitmapFactory
                            .decodeByteArray(data, 0, data.length);
                }
                saveFile4Cache(this.ctx, bitmap, "blog_head_icon.jpg");
                is.close();
                conn.disconnect();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将网络图片保存在用户SD卡种
     *
     * @param ctx      应用程序上下文
     * @param bitmap   从网络URL获取生成的BITMAP
     * @param fileName 保存后的文件名
     */
    private static void saveFile4Cache(Context ctx, Bitmap bitmap,
                                       String fileName) {
        // ctx.getExternalCacheDir();
        BufferedOutputStream bos = null;
        Log.e("MainActivity", "cache dirs ====>"
                + Environment.getExternalStorageDirectory().toString());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File cacheDirs = new File(Environment.getExternalStorageDirectory()
                    + "/blogmini/images/");
            if (!cacheDirs.exists()) {
                cacheDirs.mkdirs();
                Log.e("MainActivity", "Dirs has been created successfully!"
                        + cacheDirs.getAbsolutePath());
            }
            String path = cacheDirs.getAbsolutePath() + "/" + fileName;
            Log.e("MainActivity", "========|path=" + path);
            File iconFile = new File(path);
            try {
                if (!iconFile.exists()) {
                    iconFile.createNewFile();
                    iconFile.setWritable(true);
                }
                bos = new BufferedOutputStream(new FileOutputStream(
                        iconFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            } finally {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

    }

    /**
     * 获取图像文件输入流
     *
     * @param inStream 网络文件输入流
     * @return 二进制数组
     */
    private static byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inStream.close();
            return outStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        Bitmap bitmap = getIcon(this.imageUrl);
        if (bitmap != null) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = bitmap;
            handler.sendMessage(msg);
        } else {
            Log.e("MainActivity", "bitmap is null");
        }
    }

}
