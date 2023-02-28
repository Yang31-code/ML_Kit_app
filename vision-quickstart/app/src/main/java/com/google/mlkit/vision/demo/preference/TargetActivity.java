package com.google.mlkit.vision.demo.preference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.chaychan.library.BottomBarLayout;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.preference.fragment.BlankFragment;
import com.google.mlkit.vision.demo.preference.fragment.RecordsFragment;
import com.google.mlkit.vision.demo.preference.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class TargetActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomBarLayout mBottomBarLayout;
    List<Fragment> pageLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        initView();
        initData();
    }

    /*
     *
     * viewpager适配器
     *
     * */
    public class fragmentAdapter extends FragmentPagerAdapter {
        public fragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pageLists.get(position);
        }

        @Override
        public int getCount() {
            return pageLists.size();
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
    }

    private void initData() {
        pageLists = new ArrayList<>();//list里面fragment,
        pageLists.add(new RecordsFragment());
        pageLists.add(new BlankFragment());
        pageLists.add(new SettingFragment());

        mViewPager.setAdapter(new fragmentAdapter(getSupportFragmentManager()));
        mBottomBarLayout.setViewPager(mViewPager);//底部bottombar;
    }
}