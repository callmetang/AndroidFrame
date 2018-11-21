package com.frame.framelibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by tangdehao on 2018/11/9.
 */

public class ClipboardUtils {
    public static void copy(Context context, String copy) {
        if (context == null) return;

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, copy));  // 将内容set到剪贴板
    }
}
