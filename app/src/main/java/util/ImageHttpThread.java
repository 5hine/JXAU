package util;

/**
 * Created by lanyu on 2018/12/13.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageHttpThread extends Thread{
    private Bitmap bitmap;
    private String imgUrl;

    public ImageHttpThread(String imgUrl){
        this.imgUrl = imgUrl;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(HttpHelp.URL + imgUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(String.valueOf(MethodEnum.GET));
            InputStream is = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
