package com.sdxxtop.zjlguardian.ui.message.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.ui.message.data.MessageItemData;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-09-24 17:48
 * Version: 1.0
 * Description:
 */
public class MessageAdapter extends BaseQuickAdapter<MessageItemData, BaseViewHolder> {
    public MessageAdapter() {
        super(R.layout.message_recycler_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageItemData item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTime = helper.getView(R.id.tv_time);

        tvTitle.setText(item.getTitle());
        tvTime.setText(item.getAdd_time());


    }
}
