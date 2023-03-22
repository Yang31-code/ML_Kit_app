package com.google.mlkit.vision.demo.preference.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.adapter.SettingsAdapter;
import com.google.mlkit.vision.demo.preference.entity.SettingEntity;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    private View inflate;
    private RecyclerView settingRecy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_setting, container, false);
        settingRecy = inflate.findViewById(R.id.settingRecy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        settingRecy.setLayoutManager(linearLayoutManager);

        ArrayList<SettingEntity> list = new ArrayList<>();
        list.add(new SettingEntity(R.drawable.icon1, "Update database"));
        list.add(new SettingEntity(R.drawable.systemupdate, "Bluetooth"));
        list.add(new SettingEntity(R.drawable.icon, "Data usage"));
        list.add(new SettingEntity(R.drawable.more, "More"));
        list.add(new SettingEntity(R.drawable.group2, "Display"));
        list.add(new SettingEntity(R.drawable.shape, "Sound & notification"));
        list.add(new SettingEntity(R.drawable.group1, "Apps"));

        SettingsAdapter settingAdapter = new SettingsAdapter(list);
        settingRecy.setAdapter(settingAdapter);

        return inflate;
    }
}
