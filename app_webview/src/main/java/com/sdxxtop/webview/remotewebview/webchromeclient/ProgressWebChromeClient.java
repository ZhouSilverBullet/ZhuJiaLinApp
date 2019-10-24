package com.sdxxtop.webview.remotewebview.webchromeclient;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.sdxxtop.webview.R;
import com.sdxxtop.webview.command.Command;
import com.sdxxtop.webview.remotewebview.ProgressWebView;
import com.sdxxtop.webview.utils.WebConstants;

import java.util.HashMap;

import static com.sdxxtop.webview.command.Command.COMMAND_UPDATE_TITLE_PARAMS_TITLE;

public class ProgressWebChromeClient extends WebChromeClient {

    private OpenFileChooserCallBack mOpenFileChooserCallBack;
    private Handler progressHandler;

    public ProgressWebChromeClient(Handler progressHandler) {
        this.progressHandler = progressHandler;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (view instanceof ProgressWebView) {
            if (!TextUtils.isEmpty(title)) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(COMMAND_UPDATE_TITLE_PARAMS_TITLE, title);
                ((ProgressWebView) view).getWebViewCallBack().exec(view.getContext(), WebConstants.LEVEL_LOCAL, Command.COMMAND_UPDATE_TITLE, new Gson().toJson(params), view);
            }
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Message message = new Message();
        if (newProgress == 100) {
            message.obj = newProgress;
            progressHandler.sendMessageDelayed(message, 200);
        } else {
            if (newProgress < 10) {
                newProgress = 10;
            }
            message.obj = newProgress;
            progressHandler.sendMessage(message);
        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsAlert(final WebView view, String url, String message, JsResult result) {
        AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialoginterface, int i) {
                                //按钮事件
//                                Toast.makeText(view.getContext(), view.getContext().getString(android.R.string.ok) + " clicked.", Toast.LENGTH_LONG).show();
                            }
                        }).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(((AlertDialog) dialog).getContext().getResources().getColor(R.color.colorPrimary));

            }
        });
        alertDialog.show();
        result.confirm();// 不加这行代码，会造成Alert劫持：Alert只会弹出一次，并且WebView会卡死
        return true;
    }

    public void setOpenFileChooserCallBack(OpenFileChooserCallBack openFileChooserCallBack) {
        mOpenFileChooserCallBack = openFileChooserCallBack;
    }

    //For Android  >5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        Log.i("---1", "111");
        if (mOpenFileChooserCallBack != null) {
            return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams);
        }
        return false;
    }

    public interface OpenFileChooserCallBack {

        // for API - Version above 5.0 (contais 5.0).
        boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                                FileChooserParams fileChooserParams);
    }
}
