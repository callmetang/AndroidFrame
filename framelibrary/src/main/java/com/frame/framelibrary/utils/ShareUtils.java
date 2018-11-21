package com.frame.framelibrary.utils;

import android.content.Context;

import com.frame.framelibrary.beans.EventBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by a on 2018/11/3.
 */

public class ShareUtils {
    /**
     * 第三方登录
     *
     * @param context
     * @param platform
     */
    public static void login(Context context, String platform) {
        Platform weibo = ShareSDK.getPlatform(platform);

        if (!weibo.isClientValid()) {
            ToastUtil.show("没有安装客户端");
            return;
        }

        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                EventBus.getDefault().post(new EventBean(1, hashMap));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                EventBus.getDefault().post(new EventBean(2, throwable));
            }

            @Override
            public void onCancel(Platform platform, int i) {
                EventBus.getDefault().post(new EventBean(3, i));
            }
        }); // 设置分享事件回调


        weibo.authorize();//单独授权

    }


    /**
     * 下面为两种分享方式
     * 第一种SDK默认一键分享(配置文件中配置了哪些平台就显示哪些平台)
     * 第二种自定义分享
     * <p>
     * 微博分享需要在配置文件中配置 shareByAppClient = true;
     *
     * @param context
     * @param platform
     */
    public static void share(Context context, String platform) {
//        final OnekeyShare oks = new OnekeyShare();
//        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
////        if (platform != null) {
////            oks.setPlatform(platform);
////        }
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("标题");
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//
//        //启动分享
//        oks.show(context);


        Platform pf = ShareSDK.getPlatform(platform);

        if (pf != null) {

            if (!pf.isClientValid()) {
                ToastUtil.show("没有安装客户端");
                return;
            }


            Platform.ShareParams shareParams = new Platform.ShareParams();
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            shareParams.setTitle("标题");
            // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
            shareParams.setTitleUrl("http://sharesdk.cn");
            // text是分享文本，所有平台都需要这个字段
            shareParams.setText("我是分享文本");
            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
            shareParams.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            shareParams.setUrl("http://sharesdk.cn");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            shareParams.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            shareParams.setSite("ShareSDK");
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            shareParams.setSiteUrl("http://sharesdk.cn");

//        shareParams.setShareType(Platform.SHARE_WEBPAGE);

            pf.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    EventBus.getDefault().post(new EventBean(4, hashMap));
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {

                    EventBus.getDefault().post(new EventBean(5, throwable));
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    EventBus.getDefault().post(new EventBean(6, i));
                }
            });//设置分享事件回调


            pf.SSOSetting(false);  //设置false表示使用SSO授权方式
            pf.share(shareParams);

        }


    }
}
