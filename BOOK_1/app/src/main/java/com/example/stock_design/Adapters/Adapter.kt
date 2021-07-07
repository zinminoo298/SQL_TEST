package com.example.stock_design.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.stock_design.Detail
import com.example.stock_design.Modle.Item_search
import com.example.stock_design.R
import kotlinx.android.synthetic.main.summery_row.view.*
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.content.SharedPreferences
import android.content.DialogInterface





var rowview_date: String?=null
var rowview_location: String?=null

/*Custom view adapter for summery data  */
class Adapter(
    internal var activity: Activity,
    internal var stitem: MutableList<Item_search>,
    val context: Context

) : BaseAdapter() {

    internal val inflater: LayoutInflater

    init {
//        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.inflater=LayoutInflater.from(context)
    }



    /*view for each summery data*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
//        val name = DataBaseHelper()

        rowView=inflater.inflate(R.layout.summery_row, null)

        rowView.summery_date.text=stitem[position].date.toString()
        rowView.summery_location.text=stitem[position].location.toString()
        rowView.summery_item.text=stitem[position].itm.toString()
        rowView.summery_qty.text=stitem[position].qty.toString()

//        rowView.setOnClickListener {
//            rowview_date=stitem[position].date.toString()
//            rowview_location=stitem[position].location.toString()
//            println(rowview_location)
//
//            val i=Intent(context, Detail::class.java)
//            context.startActivity(i)
//
//        }
        return rowView
    }

    fun refresh(newList: List<Item_search>) {
        stitem.clear()
        stitem.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return stitem[position]
    }

    override fun getItemId(position: Int): Long {
        return stitem[position].qty.toLong()
    }

    override fun getCount(): Int {
        return stitem.size
    }
}