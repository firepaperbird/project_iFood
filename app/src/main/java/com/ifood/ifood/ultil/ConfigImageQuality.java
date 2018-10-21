package com.ifood.ifood.ultil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class ConfigImageQuality {
    public static BitmapDrawable getBitmapImage(Resources resources, int imageId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
        options.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, imageId, options);
        BitmapDrawable image = new BitmapDrawable(bitmap);

        return image;
    }
}
