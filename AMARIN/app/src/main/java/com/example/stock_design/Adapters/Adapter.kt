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
import android.app.AlertDialog
import android.widget.Button
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import com.example.stock_design.Database.DataBaseHelper
import com.example.stock_design.Modle.Item
import com.example.stock_design.record_location


var rowview_date: String?=null
var rowview_location: String?=null

/*Custom view adapter for summery data  */
class Adapter(
    internal var activity: Activity,
    internal var stitem: MutableList<Item_search>,
    val context: Context

) : BaseAdapter() {

    internal val inflater: LayoutInflater
    internal lateinit var db: DataBaseHelper


    init {
//        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.inflater=LayoutInflater.from(context)
    }



    /*view for each summery data*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
//        val name = DataBaseHelper()

        rowView=inflater.inflate(com.example.stock_design.R.layout.summery_row, null)

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
        rowView.setOnLongClickListener {
            // Late initialize an alert dialog object
            lateinit var dialog: androidx.appcompat.app.AlertDialog
            db=DataBaseHelper(context)


            // Initialize a new instance of alert dialog builder object
            val builder= androidx.appcompat.app.AlertDialog.Builder(context)

            // Set a title for alert dialog
            builder.setTitle("Delete Record!!!")

            // Set a message for alert dialog
            builder.setMessage("Are you sure?")


            // On click listener for dialog buttons
            val dialogClickListener=DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val item= Item_search(
                            stitem[position].location.toString(),
                            stitem[position].date.toString(),
                            Integer.parseInt(1.toString()),
                            Integer.parseInt(1.toString())
                        )
                        db.deleteItem_Search(item)
                        dialog.dismiss()
                        refresh(db.Summery)
                    }
                    DialogInterface.BUTTON_NEUTRAL -> {
                        dialog.dismiss()
                    }
                }
            }

            // Set the alert dialog positive/yes button
            builder.setPositiveButton("YES", dialogClickListener)

            // Set the alert dialog neutral/cancel button
            builder.setNeutralButton("CANCEL", dialogClickListener)


            // Initialize the AlertDialog using builder object
            dialog=builder.create()

            // Finally, display the alert dialog
            dialog.show()
            return@setOnLongClickListener true
        }
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