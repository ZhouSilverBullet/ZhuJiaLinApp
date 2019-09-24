package com.sdxxtop.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.sdxxtop.common.utils.UIUtils;

/**
 * Created by Administrator on 2018/5/15.
 */

public class TextAndEditView extends LinearLayout implements TextWatcher {

    private String endValue;
    private TextView editNameText;
    private EditText editText;
    private View editLine;
    private String textViewValue;
    private String editHintValue;
    private boolean isShow;
    private TextView endText;

    public TextAndEditView(Context context) {
        this(context, null);
    }

    public TextAndEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextAndEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextAndEditView, defStyleAttr, 0);
        textViewValue = a.getString(R.styleable.TextAndEditView_taev_text_view);
        editHintValue = a.getString(R.styleable.TextAndEditView_taev_edit_text_hint);
        endValue = a.getString(R.styleable.TextAndEditView_taev_end_text_view);
        isShow = a.getBoolean(R.styleable.TextAndEditView_taev_line_is_show, true);
        a.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_and_edit, this, true);
        editNameText = (TextView) findViewById(R.id.text_and_edit_name);
        editText = (EditText) findViewById(R.id.text_and_edit_edit);
        endText = (TextView) findViewById(R.id.text_and_edit_end_text);
        editLine = findViewById(R.id.text_and_edit_line);

        if (!isShow) {
            editLine.setVisibility(GONE);
        }

        editNameText.setText(textViewValue);
        editText.setHintTextColor(getResources().getColor(R.color.color_666666));
        editText.setTextColor(getResources().getColor(R.color.color_333333));
        editText.setHint(editHintValue);
        if (!TextUtils.isEmpty(endValue)) {
            endText.setVisibility(VISIBLE);
            endText.setText(endValue);
        }

//        editText.addTextChangedListener(this);
    }



    public EditText getEditText() {
        return editText;
    }

    public void setShowLine(boolean isShow) {
        editLine.setVisibility(isShow ? VISIBLE : GONE);
    }

    @BindingAdapter(value = "editContentText", requireAll = false)
    public static void setEditContentText(TextAndEditView view, String text) {
        final CharSequence oldText =  view.getEditText().getText();
        if (text == oldText || (text == null && oldText.length() == 0)) {
            return;
        }
        if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        view.getEditText().setText(text);

    }

    @InverseBindingAdapter(attribute = "editContentText", event = "displayAttrChanged")
    public static String setEditContentText(TextAndEditView view) {
        return view.getEditText().getText().toString();
    }

    @BindingAdapter(value = "displayAttrChanged")
    public static void setChangeListener(TextAndEditView view, InverseBindingListener listener) {
        view.getEditText().addTextChangedListener(new TextWatcher() {
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (onTextChangeListener != null) {
            onTextChangeListener.onChange();
        }
    }

    private OnTextChangeListener onTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    public interface OnTextChangeListener {
        void onChange();
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
