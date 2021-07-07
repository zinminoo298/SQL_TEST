package com.example.stock_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stock_design.Adapters.Adapter
import com.example.stock_design.Adapters.Adapter_detail
import com.example.stock_design.Adapters.rowview_date
import com.example.stock_design.Adapters.rowview_location
import com.example.stock_design.Database.DataBaseHelper
import com.example.stock_design.Modle.Item_Detail
import com.example.stock_design.Modle.Item_search
import kotlinx.android.synthetic.main.activity_detail.*

class Detail : AppCompatActivity() {

    internal lateinit var db: DataBaseHelper
    internal var seItem: MutableList<Item_Detail> = ArrayList<Item_Detail>()
    internal var seItem1: MutableList<Item_search> = ArrayList<Item_search>()
    internal lateinit var adapter: Adapter_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        db=DataBaseHelper(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detail_date.text=rowview_date.toString()
        detail_location.text=rowview_location.toString()
        adapter=Adapter_detail(this, seItem, this)
        list_detail.adapter=adapter//show summery data detail form adapter view on listview
        getDetail()



    }

    private fun getDetail() {
        adapter.refresh(db.Detail)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    /*override with onBackPressed to reload data */
    override fun onBackPressed() {
        val a=Intent(this, Search::class.java)
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(a)
        super.onBackPressed()
    }
}
