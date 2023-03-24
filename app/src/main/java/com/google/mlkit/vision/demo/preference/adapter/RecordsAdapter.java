package com.google.mlkit.vision.demo.preference.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.mlkit.vision.demo.R;

import java.util.List;


public class RecordsAdapter extends BaseQuickAdapter<String,RecordsAdapter.ViewHolder> {


    public RecordsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public RecordsAdapter(@Nullable List<String> data) {
        super(data);
    }

    public RecordsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        int number = (int)(10.0*Math.random()) + 1;
        if(number >5) {
            helper.layout.setBackground(helper.itemView.getResources().getDrawable(R.drawable.shape_common_outbg));
            helper.layoutr1.setVisibility(View.GONE);
            helper.layoutr2.setVisibility(View.GONE);
            helper.bar.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends BaseViewHolder {
        private ConstraintLayout layout;
        private RelativeLayout layoutr1,layoutr2;
        private SeekBar bar;
        public ViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            layoutr1 = view.findViewById(R.id.layout1);
            layoutr2 = view.findViewById(R.id.layout2);
            bar = view.findViewById(R.id.seekbar_progress);
        }
    }
}
