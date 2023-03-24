package com.google.mlkit.vision.demo.preference.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.adapter.RecordsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecordsFragment extends Fragment {

    private RecyclerView recy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {

        recy = (RecyclerView) view.findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("11");
        list.add("111");
        list.add("1111");
        list.add("11111");
        RecordsAdapter adapter = new RecordsAdapter(R.layout.item_data_layout,list);
        recy.setAdapter(adapter);
    }
}
