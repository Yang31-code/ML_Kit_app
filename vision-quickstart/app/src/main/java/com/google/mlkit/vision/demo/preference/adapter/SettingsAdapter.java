package com.google.mlkit.vision.demo.preference.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.entity.SettingEntity;

import java.util.List;

public class SettingsAdapter extends BaseQuickAdapter<SettingEntity, BaseViewHolder> {
    public SettingsAdapter(@Nullable List<SettingEntity> data) {
        super(R.layout.item_setting, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingEntity item) {
        TextView view = (TextView) helper.getView(R.id.settingApp);

        if (item.getTitle().equals("Update database")){
            view.setText("App");
            view.setVisibility(View.VISIBLE);
        }else if (item.getTitle().equals("Display")){
            view.setText("Device");
            view.setVisibility(View.VISIBLE);
        }
        ImageView lineImg = (ImageView) helper.getView(R.id.lineSetting);
        if (item.getTitle().equals("Apps")){
            lineImg.setVisibility(View.GONE);
        }else if (item.getTitle().equals("More")){

        }
        else {
            Glide.with(helper.itemView.getContext()).load(R.drawable.line).centerCrop().into(lineImg);
        }
        Glide.with(helper.itemView.getContext()).load(item.getImg()).into((ImageView) helper.getView(R.id.settingImg));
        helper.setText(R.id.settingTitle,item.getTitle());

    }
}
