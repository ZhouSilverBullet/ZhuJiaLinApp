package com.sdxxtop.zjlguardian.widget.people_picker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdxxtop.common.utils.ItemDivider;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.widget.people_picker.adapter.DepartAdapter;
import com.sdxxtop.zjlguardian.widget.people_picker.adapter.PeopleAdapter;
import com.sdxxtop.zjlguardian.widget.people_picker.data.DepartmentData;
import com.sdxxtop.zjlguardian.widget.people_picker.data.IPickerData;
import com.sdxxtop.zjlguardian.widget.people_picker.data.PeopleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 09:20
 * Version: 1.0
 * Description:
 */
public class PeoplePickerView extends Dialog {

    private final Context context;
    private final List<DepartmentData> list;
    private LinearLayout llContainer;
    private TextView tvConfirm;
    private DepartAdapter departAdapter;
    private PeopleAdapter peopleAdapter;

    private int oldDepartPosition = -1;
    private int oldPeoplePosition = -1;

    /**
     * 选中的数据集合
     */
    private IPickerData[] pickerData = new IPickerData[2];

    public PeoplePickerView(@NonNull Context context, List<DepartmentData> list) {
        super(context, R.style.picker_dialog);
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_picker_dialog);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        /**
         * 设置弹出动画
         */
        window.setWindowAnimations(R.style.PickerAnim);

        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;

        llContainer = findViewById(R.id.ll_container);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(v -> {
//            UIUtils.showToast("pickerData -> " + pickerData[0] + "--" + pickerData[1]);
            //监听回调
            if (selectorListener != null) {
                selectorListener.onSelector(pickerData[0], pickerData[1]);
            }
            dismiss();
        });

        ImageView ivBtn = findViewById(R.id.iv_btn);
        ivBtn.setOnClickListener(v -> {
            dismiss();
        });


        tvConfirmStatus();

        //左边RecyclerView
        RecyclerView departRecyclerView = new RecyclerView(context);
        departRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        departRecyclerView.addItemDecoration(new ItemDivider());
        departAdapter = new DepartAdapter(list);
        departRecyclerView.setAdapter(departAdapter);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widthPixels / 2, LinearLayout.LayoutParams.MATCH_PARENT);
        llContainer.addView(departRecyclerView, lp);

        //添加中间的竖线 分割线
        View lineView = new View(context);
        lineView.setBackgroundColor(context.getResources().getColor(R.color.color_EFEFF4));
        lp = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
        llContainer.addView(lineView, lp);

        //右边RecyclerView
        RecyclerView peopleRecyclerView = new RecyclerView(context);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        peopleRecyclerView.addItemDecoration(new ItemDivider());
        peopleAdapter = new PeopleAdapter();
        TextView emptyTextView = new TextView(context);
        emptyTextView.setText("选择成员后显示数据");
        emptyTextView.setGravity(Gravity.CENTER);
        peopleAdapter.setEmptyView(emptyTextView);
        peopleRecyclerView.setAdapter(peopleAdapter);
        lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        llContainer.addView(peopleRecyclerView, lp);

        departAdapter.setOnItemClickListener((adapter, view, position) -> {
            DepartmentData departmentData = departAdapter.getData().get(position);
            departmentData.setStatus(true);
            //选中的状态加入
            pickerData[0] = departmentData;

            if (oldDepartPosition != -1 && oldDepartPosition != position) {
                departAdapter.getData().get(oldDepartPosition).setStatus(false);
                //depart选择不相等的情况下，需要把people选择的status重新换个状态
                //否则下次进来时，会出现任然选择的问题
                if (oldPeoplePosition != -1) {
                    for (PeopleData peopleData : peopleAdapter.getData()) {
                        peopleData.setStatus(false);
                    }
                    oldPeoplePosition = -1;
                }
                //把选中的people的类型去除掉
                pickerData[1] = null;
            }

            oldDepartPosition = position;

            departAdapter.notifyDataSetChanged();
            List<PeopleData> peopleDataList = departmentData.getPeopleDataList();
            //当department里面数据为null的时候，做一些奔溃兼容
            if (peopleDataList == null) {
                peopleDataList = new ArrayList<>();
            }
            peopleAdapter.replaceData(peopleDataList);

            tvConfirmStatus();

            emptyTextView.setText("该部门成员为空");
        });

        peopleAdapter.setOnItemClickListener((adapter, view, position) -> {
            PeopleData peopleData = peopleAdapter.getData().get(position);
            peopleData.setStatus(true);
            pickerData[1] = peopleData;

            if (oldPeoplePosition != -1 && oldPeoplePosition != position) {
                peopleAdapter.getData().get(oldPeoplePosition).setStatus(false);
            }

            oldPeoplePosition = position;

            peopleAdapter.notifyDataSetChanged();

            tvConfirmStatus();
        });

    }

    private void tvConfirmStatus() {
        tvConfirm.setEnabled(pickerData[1] != null);
    }

    private SelectorListener selectorListener;

    public void setSelectorListener(SelectorListener selector) {
        this.selectorListener = selector;
    }

    public interface SelectorListener {
        void onSelector(IPickerData departmentData, IPickerData data);
    }
}
