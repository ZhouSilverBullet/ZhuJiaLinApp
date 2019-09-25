package com.sdxxtop.zjlguardian.ui.self_handle.adapter;

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
public class SelfHandleListAdapter extends BaseQuickAdapter<EventReportItem, BaseViewHolder> {
    public SelfHandleListAdapter() {
        super(R.layout.event_report_recycler_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventReportItem item) {
        LinearLayout llRoot = helper.getView(R.id.ll_root);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvCategory = helper.getView(R.id.tv_category);
        TextView tvStartTime = helper.getView(R.id.tv_start_time);

        tvTitle.setText(item.getTitle());
        tvCategory.setText(item.getCat_name());
        tvStartTime.setText(item.getAdd_date());


    }
}
