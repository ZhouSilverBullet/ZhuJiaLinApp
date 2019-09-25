package com.sdxxtop.zjlguardian.ui.contact.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdxxtop.common.dialog.IosAlertDialog;
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.ui.contact.data.ContactDataKt;
import com.sdxxtop.zjlguardian.ui.contact.data.UserData;

import java.util.ArrayList;

/**
 *
 */
public class ContactAdapter extends BaseMultiItemQuickAdapter<UserData, BaseViewHolder> {
    public ContactAdapter() {
        super(new ArrayList<>());

        addItemType(ContactDataKt.TYPE_USER_DATA, R.layout.item_contact_recycler);
        addItemType(ContactDataKt.TYPE_PART_DATA, R.layout.item_contact_header);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserData item) {
        switch (helper.getItemViewType()) {
            case ContactDataKt.TYPE_USER_DATA:
                TextView tvName = helper.getView(R.id.tv_name);
                String name = item.getUsername();
                tvName.setText(name);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new IosAlertDialog(mContext)
                                .builder()
                                .setMsg(item.getMobile())
                                .setPositiveButton("呼叫", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getMobile()));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //填写事件
                                    }
                                }).show();
                    }
                });
                break;
            case ContactDataKt.TYPE_PART_DATA:
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setText(item.getPart_name());
                break;
            default:
                break;
        }

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.text_and_text_line).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.text_and_text_line).setVisibility(View.VISIBLE);
        }

    }

    public long getHeaderId(int position) {
//        return getData().get(position).sortLetters.charAt(0);
        return getData().get(position).getPart_id();
    }

    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_header, null, false);
        return new RecyclerView.ViewHolder(view) {

        };
    }

    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        String showValue = String.valueOf(getData().get(position).sortLetters.charAt(0));
        String showValue = String.valueOf(getData().get(position).getPart_name());
//        ((TextView) viewHolder.itemView).setText(showValue);
        ((TextView) viewHolder.itemView.findViewById(R.id.tv_title)).setText(showValue);
    }

//    public int getPositionForSection(char section) {
//        for (int i = 0; i < getItemCount(); i++) {
//            String sortStr = getData().get(i).sortLetters;
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section) {
//                return i;
//            }
//        }
//        return -1;
//    }
}
