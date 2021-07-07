package com.example.stock_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stock_design.Adapters.Adapter
import com.example.stock_design.Database.DataBaseHelper
import com.example.stock_design.Modle.Item_search
import kotlinx.android.synthetic.main.summery.*
import android.content.DialogInterface


class Search : AppCompatActivity() {

    internal lateinit var db: DataBaseHelper
    internal var seItem: MutableList<Item_search> = ArrayList<Item_search>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.summery)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db=DataBaseHelper(this)
        Summery()

    }


    private fun Summery() {
        seItem=db.Summery
        val adapter=Adapter(this, seItem, this)
        list_summery.adapter=adapter
    }

    /*override with onBackPressed to reload data */
    override fun onBackPressed() {
        val a=Intent(this, Admin::class.java)
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(a)
        super.onBackPressed()
    }


}
