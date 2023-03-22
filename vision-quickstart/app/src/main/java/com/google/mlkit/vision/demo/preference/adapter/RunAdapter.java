package com.google.mlkit.vision.demo.preference.adapter;

import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.entity.RunEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RunAdapter extends BaseQuickAdapter<RunEntity, BaseViewHolder> implements Filterable {
    private OnItemClickListener itemClickListener;
    private List<RunEntity> originalList;

    public RunAdapter(@Nullable List<RunEntity> data, OnItemClickListener itemClickListener) {
        super(R.layout.item_run, data);
        this.itemClickListener = itemClickListener;
        this.originalList = new ArrayList<>(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RunEntity item) {
        helper.setText(R.id.itemRunTitle, item.getTitle());
        Glide.with(helper.itemView.getContext()).load(item.getImgInt()).centerCrop().into((ImageView) helper.getView(R.id.itemRunImg));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(item);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(RunEntity runEntity);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<RunEntity> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim();
                    for (RunEntity item : originalList) {
                        if (item.getTitle().toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData.clear();
                mData.addAll((List<RunEntity>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
