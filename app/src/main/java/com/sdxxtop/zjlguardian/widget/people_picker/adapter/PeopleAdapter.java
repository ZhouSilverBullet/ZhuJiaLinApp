package com.sdxxtop.zjlguardian.widget.people_picker.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.widget.people_picker.data.PeopleData;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 10:21
 * Version: 1.0
 * Description:
 */
public class PeopleAdapter extends BaseQuickAdapter<PeopleData, BaseViewHolder> {
    public PeopleAdapter() {
        super(R.layout.people_picker_people_recycler_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PeopleData item) {
        LinearLayout llRoot = helper.getView(R.id.ll_root);
        ImageView ivCheck = helper.getView(R.id.iv_check);
        TextView tvPeople = helper.getView(R.id.tv_people);

        llRoot.setBackgroundResource(item.isStatus() ? R.drawable.item_click_gray_selector : R.drawable.item_click_selector);
        tvPeople.setTextColor(item.isStatus() ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.color_333333));
        tvPeople.setText(item.getLabel());
        ivCheck.setVisibility(item.isStatus() ? View.VISIBLE : View.INVISIBLE);

    }
}
