package com.google.mlkit.vision.demo.preference.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.preference.adapter.SettingsAdapter
import com.google.mlkit.vision.demo.preference.entity.SettingEntity

class SettingFragment : Fragment() {
    var inflate: View? = null
    var settingRecy:RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_setting, container, false)
        settingRecy = inflate!!.findViewById<RecyclerView>(R.id.settingRecy)
        var linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        settingRecy!!.layoutManager = linearLayoutManager
        var list = ArrayList<SettingEntity>()
        list.add(SettingEntity(R.drawable.icon1,"Update database"))
        list.add(SettingEntity(R.drawable.systemupdate,"Bluetooth"))
        list.add(SettingEntity(R.drawable.icon,"Data usage"))
        list.add(SettingEntity(R.drawable.more,"More"))
        list.add(SettingEntity(R.drawable.group2,"Display"))
        list.add(SettingEntity(R.drawable.shape,"Sound & notification"))
        list.add(SettingEntity(R.drawable.group1,"Apps"))



        var settingAdapter = SettingsAdapter(list)
        settingRecy!!.adapter = settingAdapter


        return inflate
    }
}