package com.sdxxtop.zjlguardian.widget.people_picker.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.widget.people_picker.data.DepartmentData;

import java.util.List;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 09:37
 * Version: 1.0
 * Description:
 */
public class DepartAdapter extends BaseQuickAdapter<DepartmentData, BaseViewHolder> {
    public DepartAdapter(@Nullable List<DepartmentData> data) {
        super(R.layout.people_picker_depart_recycler_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentData item) {
        RelativeLayout rlRoot = helper.getView(R.id.rl_root);
        TextView tvDepart = helper.getView(R.id.tv_depart);

        rlRoot.setBackgroundResource(item.isStatus() ? R.drawable.item_click_gray_selector : R.drawable.item_click_selector);
        tvDepart.setTextColor(item.isStatus() ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.color_333333));

    }
}
