package com.sdxxtop.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.sdxxtop.common.utils.UIUtils;

public class NumberEditTextView extends RelativeLayout implements TextWatcher {
    EditText etContent;
    TextView tvNumber;

    //textview显示的edittext的最大长度
    private int maxLength;
    private int mEtHeight;

    public NumberEditTextView(Context context) {
        this(context, null);
    }

    public NumberEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.NumberEditTextView, defStyleAttr, 0);
        mEtHeight = UIUtils.dip2px(a.getInt(R.styleable.NumberEditTextView_netv_et_height, 120));
        a.recycle();

        init();
        //default
        maxLength = 100;

        settingView();
    }

    private void settingView() {
        editTextInScrollView(R.id.et_content);
        etContent.addTextChangedListener(this);
        etContent.setText("");
        //修改高度
        LayoutParams layoutParams = (LayoutParams) etContent.getLayoutParams();
        layoutParams.height = mEtHeight;
        etContent.setLayoutParams(layoutParams);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_number_edit_text_view, this, true);
        etContent = findViewById(R.id.et_content);
        tvNumber = findViewById(R.id.tv_number);
    }

    public void editTextInScrollView(final @IdRes int editId) {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((view.getId() == editId && canVerticalScroll(etContent))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }


    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        if (editText.canScrollVertically(-1) || editText.canScrollVertically(1)) {
            //垂直方向上可以滚动
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (TextUtils.isEmpty(s)) {
            tvNumber.setText(new StringBuilder().append(0).append("/").append(maxLength));
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Editable editable = etContent.getText();
        int len = editable.length();

        if (len > maxLength) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            //截取新字符串
            String newStr = str.substring(0, maxLength);
            etContent.setText(newStr);
            editable = etContent.getText();

            //新字符串的长度
            int newLen = editable.length();
            //旧光标位置超过字符串长度
            if (selEndIndex > newLen) {
                selEndIndex = editable.length();
            }
            //设置新光标所在的位置
            Selection.setSelection(editable, selEndIndex);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            int length = s.length();
            if (length > maxLength) {
                length = maxLength;
            }
            tvNumber.setText(new StringBuilder().append(length).append("/").append(maxLength));
//            tvNumber.setText(length + "/200");
            if (length == maxLength) {
                StringBuilder append = new StringBuilder().append("已超过").append(maxLength).append("字");
                UIUtils.showToast(append.toString());
            }
        } else {
            tvNumber.setText(new StringBuilder().append(0).append("/").append(maxLength));
        }
    }

    /**
     * 设置最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        etContent.setText("");
    }

    public void setEditHint(String hint) {
        etContent.setHint(hint);
    }

    public String getEditValue() {
        return etContent.getText().toString().trim();
    }

    public EditText getEtContent() {
        return etContent;
    }

    @BindingAdapter(value = "numEditText", requireAll = false)
    public static void setTextNumEditText(NumberEditTextView view, String text) {

        final CharSequence oldText =  view.getEtContent().getText();
        if (text == oldText || (text == null && oldText.length() == 0)) {
            return;
        }
        if (!haveContentsChanged(text, oldText)) {
            return; // No content changes, so don't set anything.
        }
        view.getEtContent().setText(text);

    }

    @InverseBindingAdapter(attribute = "numEditText", event = "numEditTextAttrChanged")
    public static String setEditContentText(NumberEditTextView view) {
        return view.getEtContent().getText().toString();
    }

    @BindingAdapter(value = "numEditTextAttrChanged")
    public static void setChangeListener(NumberEditTextView view, InverseBindingListener listener) {
        view.getEtContent().addTextChangedListener(new TextWatcher() {
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
