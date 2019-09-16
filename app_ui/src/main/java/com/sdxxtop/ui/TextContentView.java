package com.sdxxtop.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/5/15.
 */

public class TextContentView extends LinearLayout {

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
}
