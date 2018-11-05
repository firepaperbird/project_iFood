package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;

import com.loopj.android.image.SmartImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ConfigImageQuality {
    public static BitmapDrawable getBitmapImage(Context context, Resources resources, String imageLink){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 2;
            URL url = new URL(imageLink);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent(), null, options);
            BitmapDrawable image = new BitmapDrawable(bitmap);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BitmapDrawable getBitmapImage(Resources resources, int imageId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, imageId, options);
        BitmapDrawable image = new BitmapDrawable(bitmap);

        return image;
    }
}
