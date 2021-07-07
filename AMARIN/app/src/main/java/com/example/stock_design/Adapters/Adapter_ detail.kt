package com.example.stock_design.Adapters

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.stock_design.*
import com.example.stock_design.Database.DataBaseHelper
import com.example.stock_design.Modle.Item
import com.example.stock_design.Modle.Item_Detail
import kotlinx.android.synthetic.main.detail_row.view.*

/*Custom view adapter for summery data detail  */
class Adapter_detail(
    internal var activity: Activity,
    internal var stitem: MutableList<Item_Detail>,
    val context: Context

) : BaseAdapter() {

    internal lateinit var db: DataBaseHelper
    internal var inflater: LayoutInflater

    init {
        inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }




    /*view for each entry data*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView: View
        rowView=inflater.inflate(R.layout.detail_row, null)
        rowView.detail_id.text=stitem[position].id.toString()
        rowView.detail_qty.text=stitem[position].qty.toString()

        /*view click listener for each data list */
        rowView.btn_detail.setOnClickListener {
            db=DataBaseHelper(context)//Initiate the database
            val builder=android.app.AlertDialog.Builder(context)
            inflater=activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            /*show dialog update data*/
            val view=inflater.inflate(R.layout.edit_layout, null)
            builder.setView(view)
            val dialog: android.app.AlertDialog=builder.create()

            var tran_edit=view.findViewById<EditText>(R.id.edt_edit)
            tran_edit.setText(stitem[position].qty.toString())
            dialog.show()

            var btn_edt=view.findViewById<Button>(R.id.btn_edit)
            /*button click listener for edit*/
            btn_edt.setOnClickListener {
                val item=Item(
                    stitem[position].id.toString(),
                    Integer.parseInt(tran_edit.text.toString()),
                    record_location.toString()
                )
                db.updateItem(item)
                up_tran_qty=tran_edit.text.toString().toInt()
                dialog.dismiss()
                refresh(db.Detail)


            }
        }
        /*Delete data on the view form transaction table*/
        rowView.btn_delete.setOnClickListener {

            // Late initialize an alert dialog object
            lateinit var dialog: AlertDialog
            db=DataBaseHelper(context)


            // Initialize a new instance of alert dialog builder object
            val builder=AlertDialog.Builder(context)

            // Set a title for alert dialog
            builder.setTitle("Wanna Delete?")

            // Set a message for alert dialog
            builder.setMessage("Are you sure??")


            // On click listener for dialog buttons
            val dialogClickListener=DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val item=Item(
                            stitem[position].id.toString(),
                            Integer.parseInt(1.toString()),
                            record_location.toString()
                        )
                        db.deleteItem(item)
                        dialog.dismiss()
                        refresh(db.Detail)
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


        }

        return rowView

    }

    /*Refresh data after deleting data*/
    fun refresh(newList: List<Item_Detail>) {
        stitem.clear()
        stitem.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return stitem[position]
    }

    override fun getItemId(position: Int): Long {
        return stitem[position].id.toLong()
    }

    override fun getCount(): Int {
        return stitem.size
    }
}