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
import android.widget.RadioGroup;

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
        RadioGroup rg1 = view.findViewById(R.id.rg_1);
        RadioGroup rg2 = view.findViewById(R.id.rg_2);
        RadioGroup rg3 = view.findViewById(R.id.rg_3);
        RadioButton rbShouli = view.findViewById(R.id.rb_shouli);
        RadioButton rbChulizhong = view.findViewById(R.id.rb_chulizhong);
        RadioButton rbComplete = view.findViewById(R.id.rb_complete);

        RadioButton rbEvent = view.findViewById(R.id.rb_event);
        RadioButton rbCommission = view.findViewById(R.id.rb_commission);
        RadioButton rbTousu = view.findViewById(R.id.rb_tousu);

        RadioButton rbSubmit = view.findViewById(R.id.rb_submit);
        RadioButton rbTimelost = view.findViewById(R.id.rb_timelost);

        LinearLayout llReset = view.findViewById(R.id.ll_reset);
        LinearLayout llComplete = view.findViewById(R.id.ll_complete);

        llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rbShouli.setChecked(false);
//                rbChulizhong.setChecked(false);
//                rbComplete.setChecked(false);

                rg1.clearCheck();
                rg2.clearCheck();
                rg3.clearCheck();

//                rbEvent.setChecked(false);
//                rbCommission.setChecked(false);
//                rbTousu.setChecked(false);
//
//                rbSubmit.setChecked(false);
//                rbTimelost.setChecked(false);
            }
        });

        llComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedListener != null) {

                    int statusType = 0;
                    if (rbShouli.isChecked()) {
                        statusType = 1;
                    }
                    if (rbChulizhong.isChecked()) {
                        statusType = 2;
                    }
                    if (rbComplete.isChecked()) {
                        statusType = 3;
                    }

                    int classificationType = 0;

                    if (rbCommission.isChecked()) {
                        classificationType = 1;
                    }

                    if (rbTousu.isChecked()) {
                        classificationType = 2;
                    }

                    if (rbEvent.isChecked()) {
                        classificationType = 3;
                    }

                    int sortType = 0;
                    if (rbSubmit.isChecked()) {
                        sortType = 1;
                    }
                    if (rbTimelost.isChecked()) {
                        sortType = 2;
                    }

                    selectedListener.onSelected(statusType, classificationType, sortType);
                }
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

    private SelectedListener selectedListener;

    public void setSelectedListenerListener(SelectedListener listener) {
        this.selectedListener = listener;
    }

    public interface SelectedListener {
        void onSelected(int statusType, int classificationType, int sortType);
    }
}
