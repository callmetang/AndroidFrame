package com.frame.framelibrary.utils.image;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.frame.framelibrary.R;

import java.io.File;

/**
 * Created by tangdehao on 2018/5/30.
 */

public class GlideUtils {
    public static void loadToHttp(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(getRequestOptions())
                .into(imageView);
    }

    public static void loadToHttpOfListener(Context context, String path, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context).asBitmap()
                .load(path)
                .apply(getRequestOptions())
                .into(simpleTarget);
    }

    public static void loadToRes(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .apply(getRequestOptions())
                .into(imageView);
    }

    public static void loadToFile(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(new File(path))
                .apply(getRequestOptions())
                .into(imageView);
    }

    public static void loadToFileOfListener(Context context, String path, ImageView imageView, RequestListener listener) {
        Glide.with(context).asBitmap()
                .load(new File(path))
                .apply(getRequestOptions())
                .listener(listener)
                .into(imageView);
    }

    public static RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.ic_launcher)
//                .error(R.drawable.ic_launcher)
                ;
        return options;
    }
}
