package com.frame.framelibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.frame.framelibrary.utils.LogUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a on 2018/10/18.
 */

public class OkHttpClientManager {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_FROM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private static OkHttpClientManager mInstance = null;

    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    private OkHttpClientManager() {
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
        mOkHttpClient = new OkHttpClient.Builder()//.retryOnConnectionFailure(true).build();
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addInterceptor(new NetInterceptor())
                .build();
//                .addInterceptor(sLoggingInterceptor)
//                .addInterceptor(sRewriteCacheControlInterceptor)
//                .addInterceptor(httpLoggingInterceptor);
    }

//    class NetInterceptor implements Interceptor{
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder()
//
//                    .addHeader("Connection","close").build();
//
//            return chain.proceed(request);
//        }
//    }


    private Handler mHandler;

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {

                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public void get(String url, Map<String, String> params, final HttpResultCallback callback) {
        if (params != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("?");
            for (String key : params.keySet()) {
                if (!TextUtils.isEmpty(key)) {
                    String value = params.get(key);
                    if (!TextUtils.isEmpty(value)) {
                        stringBuffer.append(key);
                        stringBuffer.append("=");
                        stringBuffer.append(value);
                        stringBuffer.append("&");
                    }

                }
            }
            url = url + stringBuffer.toString();
        }

       LogUtil.e( "tang 地址 : " + url);
       LogUtil.e( "tang 参数 : " + url);
        Request request = new Request.Builder()
                .url(url)
//                .addHeader("app_version", "1.0")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handlerResponse(response, callback);
                    }
                });
            }
        });
    }

    public void post(String url, Map<String, String> params, final HttpResultCallback callback) {
        StringBuffer stringBuffer = new StringBuffer();

        if (params != null) {
            for (String key : params.keySet()) {
                if (!TextUtils.isEmpty(key)) {
                    String value = params.get(key);
                    if (!TextUtils.isEmpty(value)) {
                        stringBuffer.append(key);
                        stringBuffer.append("=");
                        stringBuffer.append(value);
                        stringBuffer.append("&");
                    }
                }
            }
        }

       LogUtil.e( "tang 地址 : " + url);
       LogUtil.e( "tang 参数 : " + stringBuffer.toString());

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_FROM, stringBuffer.toString());
        final Request request = new Request.Builder().
                url(url).post(requestBody).addHeader("Connection", "close").build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handlerResponse(response, callback);
                    }
                });
            }
        });
    }

    public void postParamsToJSON(String url, Map<String, String> params, final HttpResultCallback callback) {
        JSONObject jsonObject = new JSONObject();
        if (params != null) {
            for (String key : params.keySet()) {
                try {
                    jsonObject.put(key, params.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


       LogUtil.e( "tang 地址 : " + url);
       LogUtil.e( "tang 参数 : " + jsonObject.toString());

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonObject.toString());
        Request request = new Request.Builder().
                url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        handlerResponse(response, callback);
                    }
                });
            }
        });
    }

    private void handlerResponse(Response response, HttpResultCallback callback) {
        try {
            if (response.code() == 200) {
                final HttpUrl url = response.request().url();
                final String result = response.body().string();
                printJson(result, url);

                if (callback.mType == String.class) {
                    callback.onSuccess(result);
                } else {
                    Object obj = mGson.fromJson(result, callback.mType);
                    callback.onSuccess(obj);
                }

            } else {
                callback.onError("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.toString());
        }
    }

    /**
     * 打印json数据
     *
     * @param json
     * @param url
     */
    private void printJson(String json, HttpUrl url) {
        try {
            JSONObject jsonObject = new JSONObject(json);
           LogUtil.e( "tang 数据 : " + jsonObject.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
