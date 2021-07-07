package com.example.stock_design

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.example.stock_design.Database.*
import com.example.stock_design.Modle.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_counting.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.security.Key


var record_location: String?=null
var record_date: String?= "1111"
var record_code: String?=null
var master_record: Int?=0
var tran_qty: Int?=0
var record_id: String?=null
var up_tran_qty: Int?=0

var qt:Int = 0

class Counting : AppCompatActivity() {


    val quan: Int=1
    internal lateinit var db: DataBaseHelper
    internal lateinit var gg:MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gg = MainActivity()
        setContentView(R.layout.activity_counting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db=DataBaseHelper(this)//Initiate the database

        var ID=findViewById<EditText>(R.id.edt_id)
        edt_id.requestFocus()


        /*Add data to database*/
        btn_add.setOnClickListener {

            println("Password is" + password)
            if (edt_id.text.toString() == "" || edt_location.text.toString() == "" || edt_qty.text.toString() == "") {
                Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
            } else {
                val item=Item(
                    edt_id.text.toString().replace(" ",""),
                    Integer.parseInt(edt_qty.text.toString()),
                    edt_location.text.toString()
                )
                qt = edt_qty.text.toString().toInt()
                record_code=edt_id.text.toString().replace(" ","")
                record_location = edt_location.text.toString()
                db.addItem(item)

                if(check=="nodata"){
                    Toast.makeText(this,"BARCODE NOT FOUND !!!",Toast.LENGTH_SHORT).show()
                    check = null
                }
                else {
                    db.viewData()

                    txt_article.setText(code.toString())
                    txt_price.setText(price.toString())
                    txt_isbn.setText(record_code.toString())
                    txt_description.setText(desc.toString())
                    txt_tran_qty.setText(qty.toString())

                    record_id=edt_id.text.toString().replace(" ", "")
                    edt_id.text=null
                    edt_id.requestFocus()
                }
            }

        }


        /*Add data to database by onKeyListener*/
        ID.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (event.keyCode == KeyEvent.KEYCODE_SPACE && event.action == KeyEvent.ACTION_UP ) {

                if (edt_location.text.toString() == "" || edt_qty.text.toString() == "") {
                    Toast.makeText(this, "Make Sure To Enter Location And Qty", Toast.LENGTH_SHORT)
                        .show()
                    edt_id.text.clear()

                } else{
                    try {

                        val item=Item(
                            edt_id.text.toString().replace(" ",""),
                            Integer.parseInt(edt_qty.text.toString()),
                            edt_location.text.toString()
                        )
                        qt=edt_qty.text.toString().toInt()
                        record_code=edt_id.text.toString().replace(" ","")
                        println("GGGGGGG"+ record_code)
                        println("DSDD"+qt)
                        record_location=edt_location.text.toString()
                        println("locaiton"+ record_location)
                        println("code"+ record_code)
                        db.addItem(item)

                        if(check=="nodata"){
                            Toast.makeText(this,"BARCODE NOT FOUND !!!",Toast.LENGTH_SHORT).show()
                            check = null
                        }

                        else {
                            db.viewData()

                            txt_article.setText(code.toString())
                            txt_price.setText(price.toString())
                            txt_description.setText(desc.toString())
                            txt_isbn.setText(record_code.toString())
                            txt_tran_qty.setText(qty.toString())
                            println(qty)

                            record_id=edt_id.text.toString().replace(" ", "")
                            edt_id.text.clear()
                            edt_id.requestFocus()
                        }

                    } catch (nfe: NumberFormatException) {
                        nfe.printStackTrace()
                    }
                }
            }

            false

        })

        /*Bottom Nav Bar actions*/
        val bottomnavigationview: BottomNavigationView=findViewById(R.id.bottom_navigation)
        bottomnavigationview.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.action_edit -> {
                    if (txt_isbn.text == "") {
                        Toast.makeText(this, "Please scan first", Toast.LENGTH_SHORT).show()
                    } else {
                        showDialogAnimation(R.style.DialogSlide, "Edit Quantity")
                    }
                }

                R.id.action_delete -> {
                    Delete()
                }

                R.id.action_close -> {
                    showDialog()
                }

            }
            true

        }
    }

//    private fun setFocus(){
//        edt_id.setText("")
//        val length = edt_id.getText().length
//        println(length)
//        val cursor = edt_id.selectionStart
//        Toast.makeText(this, "Please scan first"+edt_id.lineCount, Toast.LENGTH_SHORT).show()
//
//        if(length>0)
//            edt_id.getText().delete(length-1,length)
//        edt_id.requestFocus()
//    }

    /*Delete data from database*/
    private fun Delete() {
        if (txt_isbn.text.toString() == "") {
            Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
        } else {
            val item=Item(
                txt_isbn.text.toString(),
                Integer.parseInt(txt_tran_qty.text.toString()),
                edt_location.text.toString()
            )
            db.deleteItem(item)
            Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
            txt_tran_qty.text=""
            txt_article.text=""
            txt_description.text=""
            txt_isbn.text=""
            txt_price.text=""
        }
    }

    /*show dialog for edit function*/
    private fun showDialogAnimation(type: Int, message: String) {
        val builder=android.app.AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.edit_layout, null)
        builder.setView(view)
        val dialog: android.app.AlertDialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage(message)

        var tran_edit=view.findViewById<EditText>(R.id.edt_edit)
        tran_edit.setText(qty.toString())

        dialog.show()

        var btn_edt=view.findViewById<Button>(R.id.btn_edit)
        btn_edt.setOnClickListener {

            if(tran_edit.text.toString() == ""){
                Toast.makeText(this,"Please Add Qty",Toast.LENGTH_SHORT).show()
            }

            else {
                val item=Item(
                    txt_isbn.text.toString(),
                    Integer.parseInt(tran_edit.text.toString()),
                    edt_location.text.toString()
                )
                db.updateItem(item)
                up_tran_qty=tran_edit.text.toString().toInt()
                txt_tran_qty.text=up_tran_qty.toString()
                Toast.makeText(this, "Item Updated", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }


    }

    /*Dialog to confirm quitting ths scan activity*/
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder=AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Wanna Quit?")

        // Set a message for alert dialog
        builder.setMessage("Are you sure??")


        // On click listener for dialog buttons
        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val a=Intent(this, MainActivity::class.java)
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
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

    /*override title bar back key*/
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onBackPressed() {
        showDialog()
//         super.onBackPressed()
        return
    }
}



