package com.sdxxtop.webview.remotewebview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.luck.picture.lib.permissions.RxPermissions;
import com.sdxxtop.webview.remotewebview.callback.WebViewCallBack;
import com.sdxxtop.webview.remotewebview.javascriptinterface.WebviewJavascriptInterface;
import com.sdxxtop.webview.remotewebview.settings.WebviewDefaultSetting;
import com.sdxxtop.webview.remotewebview.webchromeclient.ProgressWebChromeClient;
import com.sdxxtop.webview.remotewebview.webviewclient.XuxinWebviewClient;
import com.sdxxtop.webview.utils.ImageUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class BaseWebView extends WebView implements XuxinWebviewClient.WebviewTouch, ProgressWebChromeClient.OpenFileChooserCallBack {
    private static final String TAG = "XuxinWebView";
    public static final String CONTENT_SCHEME = "file:///android_asset/";
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private ActionMode.Callback mCustomCallback;
    protected Context context;
    private WebViewCallBack webViewCallBack;
    private Map<String, String> mHeaders;
    private WebviewJavascriptInterface remoteInterface = null;
    private XuxinWebviewClient mXuxinWebviewClient;

    public ValueCallback<Uri[]> mUploadMsgForAndroid5;
    private RxPermissions rxPermissions;

    private Intent mSourceIntent;
    private String imagePath;

    public BaseWebView(Context context) {
        super(context);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public WebViewCallBack getWebViewCallBack() {
        return webViewCallBack;
    }

    public void registerdWebViewCallBack(WebViewCallBack webViewCallBack) {
        this.webViewCallBack = webViewCallBack;
    }

    public void setHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }

    protected void init(Context context) {
        this.context = context;
        rxPermissions = new RxPermissions(((Activity) context));
        WebviewDefaultSetting.getInstance().toSetting(this);
        mXuxinWebviewClient = new XuxinWebviewClient(this, webViewCallBack, mHeaders, this);
        setWebViewClient(mXuxinWebviewClient);

        /**
         * Web Native交互触发
         */
        if (remoteInterface == null) {
            remoteInterface = new WebviewJavascriptInterface(context);
            remoteInterface.setJavascriptCommand(new WebviewJavascriptInterface.JavascriptCommand() {
                @Override
                public void exec(Context context, String cmd, String params) {
                    if (webViewCallBack != null) {
                        webViewCallBack.exec(context, webViewCallBack.getCommandLevel(), cmd, params, BaseWebView.this);
                    }
                }
            });
        }
        addJavascriptInterface(remoteInterface, "webview");
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        final ViewParent parent = getParent();
        if (parent != null) {
            return parent.startActionModeForChild(this, wrapCallback(callback));
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        final ViewParent parent = getParent();
        if (parent != null) {
            return parent.startActionModeForChild(this, wrapCallback(callback), type);
        }
        return null;
    }

    private ActionMode.Callback wrapCallback(ActionMode.Callback callback) {
        if (mCustomCallback != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return new CallbackWrapperM(mCustomCallback, callback);
            } else {
                return new CallbackWrapperBase(mCustomCallback, callback);
            }
        }
        return callback;
    }

    public void setCustomActionCallback(ActionMode.Callback callback) {
        mCustomCallback = callback;
    }

    @Override
    public boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        mUploadMsgForAndroid5 = filePathCallback;

        showOptions();
        return true;
    }

    private static class CallbackWrapperBase implements ActionMode.Callback {
        private final ActionMode.Callback mWrappedCustomCallback;
        private final ActionMode.Callback mWrappedSystemCallback;

        public CallbackWrapperBase(ActionMode.Callback customCallback, ActionMode.Callback systemCallback) {
            mWrappedCustomCallback = customCallback;
            mWrappedSystemCallback = systemCallback;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return mWrappedCustomCallback.onCreateActionMode(mode, menu);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return mWrappedCustomCallback.onPrepareActionMode(mode, menu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return mWrappedCustomCallback.onActionItemClicked(mode, item);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            try {
                mWrappedCustomCallback.onDestroyActionMode(mode);
                mWrappedSystemCallback.onDestroyActionMode(mode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static class CallbackWrapperM extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrappedCustomCallback;
        private final ActionMode.Callback mWrappedSystemCallback;

        public CallbackWrapperM(ActionMode.Callback customCallback, ActionMode.Callback systemCallback) {
            mWrappedCustomCallback = customCallback;
            mWrappedSystemCallback = systemCallback;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return mWrappedCustomCallback.onCreateActionMode(mode, menu);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return mWrappedCustomCallback.onPrepareActionMode(mode, menu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return mWrappedCustomCallback.onActionItemClicked(mode, item);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mWrappedCustomCallback.onDestroyActionMode(mode);
            mWrappedSystemCallback.onDestroyActionMode(mode);
        }

        @Override
        public void onGetContentRect(ActionMode mode, View view, Rect outRect) {
            if (mWrappedCustomCallback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) mWrappedCustomCallback).onGetContentRect(mode, view, outRect);
            } else if (mWrappedSystemCallback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) mWrappedSystemCallback).onGetContentRect(mode, view, outRect);
            } else {
                super.onGetContentRect(mode, view, outRect);
            }
        }
    }

    public void setContent(String htmlContent) {
        try {
            loadDataWithBaseURL(CONTENT_SCHEME, htmlContent, "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadUrl(String url) {
        if (mHeaders == null) {
            super.loadUrl(url);
        } else {
            super.loadUrl(url, mHeaders);
        }
        Log.e(TAG, "DWebView load url: " + url);
        resetAllStateInternal(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
        Log.e(TAG, "DWebView load url: " + url);
        resetAllStateInternal(url);
    }

    public void handleCallback(String response) {
        if (!TextUtils.isEmpty(response)) {
            String trigger = "javascript:" + "dj.callback" + "(" + response + ")";
            evaluateJavascript(trigger, null);
        }
    }

    public void loadJS(String cmd, Object param) {
        String trigger = "javascript:" + cmd + "(" + new Gson().toJson(param) + ")";
        evaluateJavascript(trigger, null);
    }

    public void dispatchEvent(String name) {
        Map<String, String> param = new HashMap<>(1);
        param.put("name", name);
        loadJS("dj.dispatchEvent", param);
    }

    private boolean mTouchByUser;

    @Override
    public boolean isTouchByUser() {
        return mTouchByUser;
    }

    private void resetAllStateInternal(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("javascript:")) {
            return;
        }
        resetAllState();
    }

    // 加载url时重置touch状态
    protected void resetAllState() {
        mTouchByUser = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchByUser = true;
                break;
        }
        return super.onTouchEvent(event);
    }


    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setOnCancelListener(new DialogOnCancelListener());
        alertDialog.setTitle("请选择操作");
        String[] options = {"相册", "拍照"};
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    toSelector(which);
                                }
                            }
                        });

                    }
                }
        ).show();
    }

    private void toSelector(int which) {
        if (which == 0) {
            try {
                mSourceIntent = ImageUtil.choosePicture();
                ((Activity) context).startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(),
                        "请去\"设置\"中开启本应用的图片媒体访问权限",
                        Toast.LENGTH_SHORT).show();
                restoreUploadMsg();
            }

        } else {
            try {
                mSourceIntent = ImageUtil.takeBigPicture(getContext(), new ImageUtil.FilePathCallback() {
                    @Override
                    public void onCallN(String path) {
                        imagePath = path;
                    }
                });
                ((Activity) context).startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(),
                        "请去\"设置\"中开启本应用的相机和图片媒体访问权限",
                        Toast.LENGTH_SHORT).show();

                restoreUploadMsg();
            }
        }
    }

    private class DialogOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            restoreUploadMsg();
        }
    }

    private void restoreUploadMsg() {
        if (mUploadMsgForAndroid5 != null) {
            mUploadMsgForAndroid5.onReceiveValue(null);
            mUploadMsgForAndroid5 = null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            if (mUploadMsgForAndroid5 != null) {         // for android 5.0+
                mUploadMsgForAndroid5.onReceiveValue(null);
            }
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (mUploadMsgForAndroid5 == null) {        // for android 5.0+
                        return;
                    }
                    String sourcePath = ImageUtil.retrievePath(getContext(), mSourceIntent, data);
                    if (imagePath != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
                        sourcePath = imagePath;
                    }
                    if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {
                        break;
                    }
                    Uri uri = Uri.fromFile(new File(sourcePath));
                    mUploadMsgForAndroid5.onReceiveValue(new Uri[]{uri});
                    imagePath = null;
                } catch (Exception e) {
                    e.printStackTrace();
                    imagePath = null;
                }
                break;
            }
//            case PictureConfig.CHOOSE_REQUEST:
//                // 图片、视频、音频选择结果回调
//                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                if (selectList != null && selectList.size() > 0) {
//                    Uri uri = Uri.fromFile(new File(selectList.get(0).getCompressPath()));
//                    mUploadMsgForAndroid5.onReceiveValue(new Uri[]{uri});
//                }
//                break;
            default:
                break;
        }
    }

}