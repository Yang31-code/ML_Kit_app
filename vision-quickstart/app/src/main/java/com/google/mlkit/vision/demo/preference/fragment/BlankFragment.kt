package com.google.mlkit.vision.demo.preference.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.preference.DetailActivity
import com.google.mlkit.vision.demo.preference.adapter.RunAdapter
import com.google.mlkit.vision.demo.preference.entity.RunEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.TimerTask
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView

class BlankFragment : Fragment(), RunAdapter.OnItemClickListener {
    var inflate: View? = null
    var blankRecy:RecyclerView? = null
    var runAdapter:RunAdapter?= null
    var timeTitle: TextView?= null
    var timer = Timer()

    var handler = Handler(object :Handler.Callback{
        @RequiresApi(Build.VERSION_CODES.O)
        override fun handleMessage(p0: Message): Boolean {
            if (p0.what == 200){
                var now = LocalDateTime.now()
                val ofPattern = DateTimeFormatter.ofPattern("HH:mm:ss")
                timeTitle!!.setText(now.format(ofPattern))
            }
            return true
        }

    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_blank, container, false)
        blankRecy = inflate!!.findViewById<RecyclerView>(R.id.blankRecy)

        val searchText: EditText = inflate!!.findViewById<EditText>(R.id.searchEditText)
        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                runAdapter?.filter?.filter(s)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        var gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        blankRecy!!.layoutManager = gridLayoutManager
        /**
         * 初始数据
         */
        var list = ArrayList<RunEntity>()
        list.add(RunEntity("asda","",R.drawable.quan,"index"))
        list.add(RunEntity("123","",R.drawable.ta,"index"))
        list.add(RunEntity("a234234234a","",R.drawable.hige,"index"))
        list.add(RunEntity("as8956985a","",R.drawable.im,"index"))
        runAdapter = RunAdapter(list, this)
        var view = LayoutInflater.from(activity).inflate(R.layout.header_item, null)
        var headerImg = view.findViewById<ImageView>(R.id.headerImg)
        timeTitle = view.findViewById<TextView>(R.id.timeTitle)

        timer.schedule(object :TimerTask(){
            override fun run() {
                var obtainMessage = handler.obtainMessage()
                obtainMessage.what = 200
                handler.sendMessage(obtainMessage)
            }

        },1,1000)
        Glide.with(activity!!).load(R.drawable.header).centerCrop().into(headerImg)
        runAdapter!!.addHeaderView(view)
        blankRecy!!.adapter = runAdapter

        return inflate
    }

    override fun onItemClick(runEntity: RunEntity) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("name", runEntity.name)
        startActivity(intent)
    }
}

