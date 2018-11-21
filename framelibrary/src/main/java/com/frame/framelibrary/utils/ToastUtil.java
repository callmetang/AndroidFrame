package com.frame.framelibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.framelibrary.R;
import com.frame.framelibrary.core.App;

/**
 * Created by a on 2018/10/19.
 */

public class ToastUtil {
    public static void show(String msg) {
        if (App.getInstance() != null && !TextUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(App.getInstance(), "", Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        }
    }


    public static void toastSuccess(String text) {
        toastMessage(R.drawable.icon_toast_success, text);
    }

    public static void toastFail(String text) {
        toastMessage(R.drawable.icon_toast_fail, text);
    }

    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void toastMessage(int icon, String text) {

        if (icon < 0 || TextUtils.isEmpty(text) || App.getInstance() == null) return;
        LayoutInflater inflater = (LayoutInflater) App.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        View view = inflater.inflate(R.layout.view_toast, null); //加載layout下的布局

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView textView = (TextView) view.findViewById(R.id.text);
        imageView.setImageResource(icon);
        textView.setText(text);
        Toast toast = new Toast(App.getInstance());
        toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }

}
