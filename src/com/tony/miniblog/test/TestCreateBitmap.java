package com.tony.miniblog.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.api.share.WeiboDownloader;
import com.tony.miniblog.view.MainActivity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tony on 2015/3/17 0017.
 */
public class TestCreateBitmap {

    public static String getIcon(Context ctx, String iconUrl) {
        try {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost post = new HttpPost("http://tp1.sinaimg.cn/1404376560/180/0/1");
//
//            HttpResponse response = httpClient.execute(post);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                HttpEntity entity = response.getEntity();
//                Bitmap bitmap = BitmapFactory.decodeStream(entity.getContent());
//                System.out.print(bitmap.getHeight());
//
//            } else {
//                System.out.print("Get the pic failed!");
//            }
            URL url = new URL("http://tp1.sinaimg.cn/1404376560/180/0/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            File f = new File(ctx.getCacheDir(), "blog_head_icon.jpg");
            Log.e("Main","dir==>>" + ctx.getCacheDir());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E://icon.jpg")));

            byte[] b = new byte[512];
            int len = 0;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.flush();
            bos.close();
            bis.close();
            System.out.print(conn.getContentLength() + "finished!");
            Log.e("Main","path----> = "+f.getAbsolutePath());
            return f.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}