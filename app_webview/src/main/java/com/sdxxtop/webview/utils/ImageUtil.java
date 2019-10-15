package com.sdxxtop.webview.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * @author :  lwb
 * Date: 2019/4/18
 * Desc:
 */
public class ImageUtil {
    private static final String TAG = "ImageUtil";

    public static Intent choosePicture() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        return Intent.createChooser(intent, null);

    }


    /**
     * 拍照后返回
     */

    public static Intent takeBigPicture(Context context, FilePathCallback fileCallback) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String newPhotoPath = getNewPhotoPath(context);
        Uri pictureUri = newPictureUri(context, newPhotoPath);
        if (fileCallback != null) {
            fileCallback.onCallN(newPhotoPath);
        }
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        return intent;

    }


    public static String getDirPath(Context context) {
        return Environment.getExternalStorageDirectory().getPath() + "/WebViewUploadImage";
    }


    private static String getNewPhotoPath(Context context) {
        return getDirPath(context) + "/" + System.currentTimeMillis() + ".jpg";
    }


    public static String retrievePath(Context context, Intent sourceIntent, Intent dataIntent) {
        String picPath = null;
        try {
            Uri uri;
            if (dataIntent != null) {
                uri = dataIntent.getData();
                if (uri != null) {
                    picPath = ContentUtil.getPath(context, uri);
                }

                if (isFileExists(picPath)) {
                    return picPath;
                }
            }
            if (sourceIntent != null) {
                uri = sourceIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                if (uri != null) {
                    String scheme = uri.getScheme();
                    if (scheme != null && scheme.startsWith("file")) {
                        picPath = uri.getPath();
                    }
                }
                if (!TextUtils.isEmpty(picPath)) {
                    File file = new File(picPath);
                    if (!file.exists() || !file.isFile()) {
                    }
                }
            }
            return picPath;
        } finally {

        }
    }

    private static Uri newPictureUri(Context context, String path) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".provider", new File(path));
        } else {
            uri = Uri.fromFile(new File(path));
        }
        return uri;

    }


    private static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File f = new File(path);

        if (!f.exists()) {
            return false;
        }
        return true;

    }

    public interface FilePathCallback {
        void onCallN(String path);
    }
}
