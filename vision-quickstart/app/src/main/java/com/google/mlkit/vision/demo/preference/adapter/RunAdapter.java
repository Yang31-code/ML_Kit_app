package com.google.mlkit.vision.demo.preference.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.entity.RunEntity;

import java.util.List;

public class RunAdapter extends BaseQuickAdapter<RunEntity, BaseViewHolder> {
    public RunAdapter( @Nullable List<RunEntity> data) {
        super(R.layout.item_run, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RunEntity item) {
        helper.setText(R.id.itemRunTitle,item.getTitle());
        Glide.with(helper.itemView.getContext()).load(item.getImgInt()).centerCrop().into((ImageView) helper.getView(R.id.itemRunImg));
    }
}
