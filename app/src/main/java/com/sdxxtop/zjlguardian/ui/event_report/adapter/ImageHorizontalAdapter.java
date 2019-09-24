package com.sdxxtop.zjlguardian.ui.event_report.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdxxtop.zjlguardian.R;

import java.util.ArrayList;
import java.util.List;

public class ImageHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private List<LocalMedia> selectImgList = new ArrayList<>();

    public ImageHorizontalAdapter() {
        super(R.layout.item_image_horizontal_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(item)) {
            Glide.with(mContext).load(item).into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectImgList.size() == 0) {
                    for (String datum : getData()) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(datum);
                        selectImgList.add(localMedia);
                    }
                }
                PictureSelector.create((AppCompatActivity) mContext).themeStyle(R.style.picture_default_style)
                        .openExternalPreview(helper.getAdapterPosition(), selectImgList);
            }
        });
    }
}
