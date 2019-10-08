package com.sdxxtop.zjlguardian.ui.department.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.sdxxtop.zjlguardian.R;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-10-08 22:56
 * Version: 1.0
 * Description:
 */
public class SelectorPopupWindow extends PopupWindow {

    private final Activity mActivity;
    private LayoutInflater inflater;

    public SelectorPopupWindow(Activity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(mActivity);

        View view = inflater.inflate(R.layout.pop_selector_layout, null);
        setContentView(view);
        setFocusable(true);
        setTouchable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable());

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.onDismiss();
                }

                handleWindow(false);
            }
        });

        initView(view);

    }

    private void initView(View view) {
        CheckBox cbShouli = view.findViewById(R.id.cb_shouli);
        CheckBox cbChulizhong = view.findViewById(R.id.cb_chulizhong);
        CheckBox cbComplete = view.findViewById(R.id.cb_complete);
        CheckBox cbEvent = view.findViewById(R.id.cb_event);
        CheckBox cbCommission = view.findViewById(R.id.cb_commission);
        CheckBox cbTousu = view.findViewById(R.id.cb_tousu);

        RadioButton rbSubmit = view.findViewById(R.id.rb_submit);
        RadioButton rbTimelost = view.findViewById(R.id.rb_timelost);

        LinearLayout llReset = view.findViewById(R.id.ll_reset);
        LinearLayout llComplete = view.findViewById(R.id.ll_complete);

        llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbShouli.setChecked(false);
                cbChulizhong.setChecked(false);
                cbComplete.setChecked(false);
                cbEvent.setChecked(false);
                cbCommission.setChecked(false);
                cbTousu.setChecked(false);
                rbSubmit.setChecked(false);
                rbTimelost.setChecked(false);
            }
        });

        llComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void showPop(View view) {
        handleWindow(false);
        showAsDropDown(view);
    }

    private void handleWindow(boolean isShow) {
        Window window = mActivity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (isShow) {
            lp.alpha = 0.5f;
        } else {
            lp.alpha = 1f;
        }
        window.setAttributes(lp);

        if (listener != null) {
            listener.onShow();
        }
    }

    private DisplayStatusListener listener;

    public void setDisplayStatusListener(DisplayStatusListener listener) {
        this.listener = listener;
    }

    public interface DisplayStatusListener {
        void onShow();
        void onDismiss();
    }
}
