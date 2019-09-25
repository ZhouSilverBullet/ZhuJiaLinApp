package com.sdxxtop.zjlguardian.ui.commission.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.ui.event_report.data.EventReportItem;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 17:48
 * Version: 1.0
 * Description:
 */
public class CommissionListAdapter extends BaseQuickAdapter<EventReportItem, BaseViewHolder> {
    public CommissionListAdapter() {
        super(R.layout.event_report_recycler_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReportItem item) {
        LinearLayout llRoot = helper.getView(R.id.ll_root);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvEventType = helper.getView(R.id.tv_event_type);
        TextView tvCategory = helper.getView(R.id.tv_category);
        TextView tvDepartPeople = helper.getView(R.id.tv_depart_people);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvEndTime = helper.getView(R.id.tv_end_time);
        TextView tvStartTime = helper.getView(R.id.tv_start_time);


        tvEventType.setText("事件类型：");
        tvCategory.setText(item.getType_name());
        tvDepartPeople.setText("所属分类：");
        tvName.setText(item.getCat_name());

        tvTitle.setText(item.getTitle());


        tvEndTime.setText(item.getEnd_day());
        tvStartTime.setText(item.getAdd_date());


    }
}
