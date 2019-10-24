package com.sdxxtop.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

import com.sdxxtop.common.utils.UIUtils;

/**
 * Created by Administrator on 2018/5/15.
 */
//@InverseBindingMethods({
//        @InverseBindingMethod(
//                type = TextContentView.class,
//                attribute = "tcv_content_text",
//                event = "checkedValueAttrChanged",
//                method = "getTvContentText")
//})
public class TextContentView extends LinearLayout {

    private String contentViewHintValue;
    private boolean isRightImgShow;
    private String contentViewValue;
    private String endValue;
    private TextView editNameText;
    private View editLine;
    private String textViewValue;
    private boolean isShow;
    private TextView endText;
    private TextView tvContent;
    private View tvRightImage;

    public TextContentView(Context context) {
        this(context, null);
    }

    public TextContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextContentView, defStyleAttr, 0);
        textViewValue = a.getString(R.styleable.TextContentView_tcv_text_view);
        contentViewValue = a.getString(R.styleable.TextContentView_tcv_content_text);
        contentViewHintValue = a.getString(R.styleable.TextContentView_tcv_content_hint_text);
        endValue = a.getString(R.styleable.TextContentView_tcv_end_text_view);
        isShow = a.getBoolean(R.styleable.TextContentView_tcv_line_is_show, true);
        isRightImgShow = a.getBoolean(R.styleable.TextContentView_tcv_text_img_is_show, false);

        a.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_content, this, true);
        editNameText = (TextView) findViewById(R.id.text_and_edit_name);

        tvContent = findViewById(R.id.tv_content);
        endText = (TextView) findViewById(R.id.text_and_edit_end_text);
        tvRightImage = findViewById(R.id.tv_right_image);
        editLine = findViewById(R.id.text_and_edit_line);

        if (!TextUtils.isEmpty(contentViewHintValue)) {
            tvContent.setHint(contentViewHintValue);
        }

        if (!TextUtils.isEmpty(contentViewValue)) {
            tvContent.setText(contentViewValue);
        }

        if (!isShow) {
            editLine.setVisibility(GONE);
        }

        if (!isRightImgShow) {
            tvRightImage.setVisibility(GONE);
        }

        editNameText.setText(textViewValue);
        if (!TextUtils.isEmpty(endValue)) {
            endText.setVisibility(VISIBLE);
            endText.setText(endValue);
        }
    }

    public void setShowLine(boolean isShow) {
        editLine.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setTvContentText(String tvContentText) {
        tvContent.setText(tvContentText);
    }

    private String tvContentText;

    public String getTvContentText() {
        return tvContentText;
    }

    public void setEditNameText(String value) {
        editNameText.setText(value);
    }

    public TextView getTvContent() {
        return tvContent;
    }

    //    @BindingAdapter(value = "contentText", requireAll = false)
//    public static void setTcvContentText(TextContentView view, String value) {
//       view.setTvContentText(value);
//    }

    @BindingAdapter(value = "contentText", requireAll = false)
    public static void setTcvContentText(TextContentView view, String text) {

        final CharSequence oldText =  view.getTvContent().getText();
        if (text == oldText || (text == null && oldText.length() == 0)) {
            return;
        }
        if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        view.setTvContentText(text);

    }

    @InverseBindingAdapter(attribute = "contentText", event = "contentTextAttrChanged")
    public static String setEditContentText(TextContentView view) {
        return view.getTvContent().getText().toString();
    }

    @BindingAdapter(value = "contentTextAttrChanged")
    public static void setChangeListener(TextContentView view, InverseBindingListener listener) {
        view.getTvContent().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listener != null) {
                    listener.onChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private static boolean haveContentsChanged(CharSequence str1, CharSequence str2) {
        if ((str1 == null) != (str2 == null)) {
            return true;
        } else if (str1 == null) {
            return false;
        }
        final int length = str1.length();
        if (length != str2.length()) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return true;
            }
        }
        return false;
    }
}
