package com.frame.framelibrary.utils;

import android.graphics.Bitmap;

/**
 * 二维码辅助工具
 * Created by tangdehao on 2018/9/26.
 */

public class QRCodeUtils {

    /**
     * 生成二维码
     *
     * @param str 二维码内容
     * @return
     */
    public static Bitmap createBitmap(String str) {
        Bitmap bitmap = null;
//        BitMatrix result = null;
//        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//        try {
//            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
//            hints.put(EncodeHintType.MARGIN, 0);//设置白边
//            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 300, 300, hints);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            bitmap = barcodeEncoder.createBitmap(result);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException iae) { // ?
//            iae.printStackTrace();
//            return null;
//        }
        return bitmap;
    }
}
