package com.google.mlkit.vision.demo.preference.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.DetailActivity;
import com.google.mlkit.vision.demo.preference.adapter.RunAdapter;
import com.google.mlkit.vision.demo.preference.entity.RunEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BlankFragment extends Fragment implements RunAdapter.OnItemClickListener {
    private View inflate;
    private RecyclerView blankRecy;
    private RunAdapter runAdapter;
    private TextView timeTitle;
    private Timer timer = new Timer ();

    private Handler handler = new Handler (new Handler . Callback () {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean handleMessage(Message p0) {
            if (p0.what == 200) {
                LocalDateTime now = LocalDateTime . now ();
                DateTimeFormatter ofPattern = DateTimeFormatter . ofPattern ("HH:mm:ss");
                timeTitle.setText(now.format(ofPattern));
            }
            return true;
        }
    });

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        blankRecy = inflate.findViewById(R.id.blankRecy);

        EditText searchText = inflate . findViewById (R.id.searchEditText);
        searchText.addTextChangedListener(new TextWatcher () {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (runAdapter != null && runAdapter.getFilter() != null) {
                    runAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        blankRecy.setLayoutManager(gridLayoutManager);
        /**
         * Initial data
         */
        ArrayList<RunEntity> list = new ArrayList<>();
        list.add(new RunEntity ("asda", "", R.drawable.quan, "index"));
        list.add(new RunEntity ("123", "", R.drawable.ta, "index"));
        list.add(new RunEntity ("a234234234a", "", R.drawable.hige, "index"));
        list.add(new RunEntity ("as8956985a", "", R.drawable.im, "index"));
        runAdapter = new RunAdapter (list, this);
        View view = LayoutInflater . from (getActivity()).inflate(R.layout.header_item, null);
        ImageView headerImg = view . findViewById (R.id.headerImg);
        timeTitle = view.findViewById(R.id.timeTitle);

        timer.schedule(new TimerTask () {
            @Override
            public void run() {
                Message obtainMessage = handler . obtainMessage ();
                obtainMessage.what = 200;
                handler.sendMessage(obtainMessage);
            }
        }, 1, 1000);
        Glide.with(requireActivity()).load(R.drawable.header).centerCrop().into(headerImg);
        runAdapter.addHeaderView(view);
        blankRecy.setAdapter(runAdapter);


        return inflate;
    }

    @Override
    public void onItemClick(RunEntity runEntity) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("name", runEntity.name);
        startActivity(intent);
    }
}