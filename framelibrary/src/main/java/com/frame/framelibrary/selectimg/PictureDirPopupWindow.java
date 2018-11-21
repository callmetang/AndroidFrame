package com.frame.framelibrary.selectimg;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.frame.framelibrary.R;
import com.frame.framelibrary.utils.ScreenUtils;
import com.frame.framelibrary.utils.image.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by tangdehao on 2018/9/13.
 */

public class PictureDirPopupWindow {
    private Context context;
    private PopupWindow popupWindow;

    private int mScreenHeight;

    private PicItemClickCallBack picItemClickCallBack;

    public void setPicItemClickCallBack(PicItemClickCallBack picItemClickCallBack) {
        this.picItemClickCallBack = picItemClickCallBack;
    }

    public PictureDirPopupWindow(Context context, List<PictureBean> dirs) {

        popupWindow = new PopupWindow(context);

        final View view = View.inflate(context, R.layout.dialog_dir_list, null);
        popupWindow.setContentView(view);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ScreenUtils.getScreenHeight(context) / 2);
//这是设置popupwindow出现的动画
        popupWindow.setAnimationStyle(R.style.anim_photo_select);
        mScreenHeight = ScreenUtils.getScreenHeight(context) + ScreenUtils.getVirtualBarHeight(context);


        ListView listView = view.findViewById(R.id.list_view);

        listView.setAdapter(new CommonAdapter<PictureBean>(context, R.layout.item_dir, dirs) {
            @Override
            protected void convert(ViewHolder viewHolder, final PictureBean pictureBean, int position) {
                TextView textView = viewHolder.getView(R.id.tv_dir_name);
                TextView tvCount = viewHolder.getView(R.id.tv_count);
                ImageView imageView = viewHolder.getView(R.id.image);
                textView.setText(pictureBean.dirName);
                tvCount.setText(pictureBean.count + "张");


                GlideUtils.loadToFile(mContext, pictureBean.path, imageView);
//                Glide.with(mContext).load(new File(pictureBean.path)).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(imageView);
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        if (picItemClickCallBack != null) {
                            picItemClickCallBack.onCallBack(pictureBean.dirName);
                        }
                    }
                });
            }
        });

    }

    public void showAtLocation(View view) {
        if (popupWindow != null) {

            int[] a = new int[2];
            view.getLocationOnScreen(a);
            Log.e("tag", "x= " + a[0] + " | y= " + a[1]);
            int y = mScreenHeight - a[1];
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, y);
        }
    }

    public void dismiss() {
        if (popupWindow != null)
            popupWindow.dismiss();
    }
}
