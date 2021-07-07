package com.example.stock_design

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.input.InputManager
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
import android.view.inputmethod.InputMethodManager
import java.security.Key
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_counting.txt_article
import kotlinx.android.synthetic.main.activity_counting.txt_description
import kotlinx.android.synthetic.main.activity_counting.txt_isbn
import kotlinx.android.synthetic.main.activity_counting.txt_price
import kotlinx.android.synthetic.main.activity_edit.*


var record_location: String?=null
var record_date: String?= null
var record_code: String?=null
var master_record: Int?=0
var tran_qty: Int?=0
var record_id: String?=null
var up_tran_qty: Int?=0

var st_qt:Int = 0
var nd_qt:Int = 0

var edt_lc:String? = null
var edt_br:String? = null
var edt_isbn:String? = null


class Counting : AppCompatActivity() {


    val quan: Int = 1
    internal lateinit var db: DataBaseHelper
    internal lateinit var gg: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gg = MainActivity()
        setContentView(R.layout.activity_counting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = DataBaseHelper(this)//Initiate the database


        var ID = findViewById<EditText>(R.id.edt_id)

        edt_location.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(edt_location, InputMethodManager.SHOW_IMPLICIT)

        db.viewQty()
        if (qty_check == "false") {
            st_qt = 0
            txt_qty.text = st_qt.toString()
        } else {
            st_qt = viewqty
            txt_qty.text = st_qt.toString()

        }


        edt_location.setOnEditorActionListener(TextView.OnEditorActionListener() { _, keyCode, event ->

            if (keyCode == EditorInfo.IME_ACTION_NEXT) {
                if (edt_location.text.toString() == "") {
                    Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show()
                } else {
                    record_location = edt_location.text.toString()
                    db.viewQty()
                    if (qty_check == "false") {
                        st_qt = 0
                        txt_qty.text = st_qt.toString()
                    } else {
                        st_qt = viewqty
                        txt_qty.text = st_qt.toString()

                    }

                    edt_id.requestFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)
                }

            }

            false
        })

        edt_qty.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->

            //            if ( event.keyCode == KeyEvent.FLAG_EDITOR_ACTION && event.action == KeyEvent.ACTION_UP) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (txt_isbn.text.toString() == "") {
                    Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()

                    edt_id.requestFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)
                } else {
                    if (edt_qty.text.toString() == "") {
                        Toast.makeText(this, "Add The Quantity Please!!", Toast.LENGTH_SHORT).show()
                    } else {
                        val item = Item(
                            txt_isbn.text.toString(),
                            Integer.parseInt(edt_qty.text.toString()),
                            edt_location.text.toString()
                        )
                        nd_qt = edt_qty.text.toString().toInt()
                        record_code = txt_isbn.text.toString().replace(" ", "")
                        record_location = edt_location.text.toString()
                        db.addItem(item)

                        edt_lc = edt_location.text.toString()
                        edt_br = record_date
                        edt_isbn = txt_isbn.text.toString()

                        val sum = Integer.parseInt(nd_qt.toString()) + st_qt
                        txt_qty.setText(sum.toString())
                        st_qt = txt_qty.text.toString().toInt()


                        txt_article.setText("")
                        txt_description.setText("")
                        txt_isbn.setText("")
                        txt_price.setText("")
                        txt_tran_qty.setText("")
                        edt_qty.setText("1")

                        edt_id.text.clear()
                        edt_id.requestFocus()
                        val imm =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)
                    }
                }
            }

            false
        })

        /*Add data to database*/
        btn_add.setOnClickListener {

            if (txt_isbn.text.toString() == "") {
                Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
            } else {
                if (edt_qty.text.toString() == "") {
                    Toast.makeText(this, "Add The Quantity Please!!", Toast.LENGTH_SHORT).show()
                } else {
                    val item = Item(
                        txt_isbn.text.toString(),
                        Integer.parseInt(edt_qty.text.toString()),
                        edt_location.text.toString()
                    )
                    nd_qt = edt_qty.text.toString().toInt()
                    record_code = txt_isbn.text.toString().replace(" ", "")
                    record_location = edt_location.text.toString()
                    db.addItem(item)

                    edt_lc = edt_location.text.toString()
                    edt_br = record_date
                    edt_isbn = txt_isbn.text.toString()

                    val sum = Integer.parseInt(nd_qt.toString()) + st_qt
                    txt_qty.setText(sum.toString())
                    st_qt = txt_qty.text.toString().toInt()

                    txt_article.setText("")
                    txt_description.setText("")
                    txt_isbn.setText("")
                    txt_price.setText("")
                    txt_tran_qty.setText("")
                    edt_qty.setText("1")


                    edt_id.requestFocus()
                    edt_id.text.clear()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)


                }
            }
        }


        /*Add data to database by onKeyListener*/
        ID.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (event.keyCode == KeyEvent.KEYCODE_SPACE && event.action == KeyEvent.ACTION_UP || event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                if (edt_location.text.toString() == "") {
                    Toast.makeText(this, "Make Sure To Enter Location", Toast.LENGTH_SHORT)
                        .show()
                    edt_id.text.clear()

                } else {
                    try {
                        record_code = edt_id.text.toString().replace(" ", "")
                        db.viewData()

                        if (check == "nodata") {
                            Toast.makeText(this, "BARCODE NOT FOUND !!!", Toast.LENGTH_SHORT).show()
                            edt_id.text.clear()
                            check = null
                        } else {
                            txt_article.setText(code.toString())
                            txt_price.setText(price.toString())
                            txt_description.setText(desc.toString())
                            txt_isbn.setText(isb.toString())
                            txt_tran_qty.setText(cur_qty.toString())
                            println(qty)

                            record_id = edt_id.text.toString().replace(" ", "")
//                            edt_id.text.clear()
                            edt_qty.requestFocus()
                            val imm =
                                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(edt_qty, InputMethodManager.SHOW_IMPLICIT)
                        }

                    } catch (nfe: NumberFormatException) {
                        nfe.printStackTrace()
                    }
                }
            }

            false

        })

        /*Bottom Nav Bar actions*/
        val bottomnavigationview: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomnavigationview.setOnNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.action_edit -> {
                    dialogEdit()
                }

                R.id.action_delete -> {
                    if (txt_tran_qty.text.toString() == "0") {
                        Toast.makeText(this, "No Scanned Data", Toast.LENGTH_SHORT).show()
                    } else {
                        Delete()
                    }
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
            dialogDelete()
        }
    }

    /*show dialog for edit function*/
    private fun showDialogAnimation(type: Int, message: String) {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.edit_layout, null)
        builder.setView(view)
        val dialog: android.app.AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = type
        dialog.setMessage(message)

        var tran_edit = view.findViewById<EditText>(R.id.edt_edit)
        tran_edit.setText(cur_qty.toString())

        dialog.show()

        var btn_edt = view.findViewById<Button>(R.id.btn_edit)
        btn_edt.setOnClickListener {

            if (tran_edit.text.toString() == "") {
                Toast.makeText(this, "Please Add Qty", Toast.LENGTH_SHORT).show()
            } else {
                val item = Item(
                    txt_isbn.text.toString(),
                    Integer.parseInt(tran_edit.text.toString()),
                    edt_location.text.toString()
                )
                db.updateItem(item)
                db.viewQty()

                txt_article.setText("")
                txt_description.setText("")
                txt_isbn.setText("")
                txt_price.setText("")
                txt_tran_qty.setText("")
                edt_qty.setText("")
                edt_id.text.clear()
                println(viewqty)
                txt_qty.setText(viewqty.toString())
                up_tran_qty = tran_edit.text.toString().toInt()
//                txt_tran_qty.text=up_tran_qty.toString()
                Toast.makeText(this, "Item Updated", Toast.LENGTH_LONG).show()
                dialog.dismiss()

                edt_id.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)

            }
        }


    }

    /*Dialog to confirm quitting ths scan activity*/
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Wanna Quit?")

        // Set a message for alert dialog
        builder.setMessage("Are you sure??")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val a = Intent(this, MainActivity::class.java)
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
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun dialogEdit() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.activity_login, null)
        builder.setView(view)
        val dialog: android.app.AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.setMessage("Please Enter Password!!")
        dialog.show()

        val btn_next = view.findViewById<Button>(R.id.btn_enter)
        val edt_pw = view.findViewById<EditText>(R.id.ent_pw)

        btn_next.setOnClickListener {
            branch = edt_pw.text.toString()

            println("Password is " + real_pwd)
            if (edt_pw.text == null) {
                Toast.makeText(this, "Please Enter Password!!", Toast.LENGTH_SHORT).show()
            } else {
                if (edt_pw.text.toString() == real_pwd) {
                    dialog.dismiss()
//                    showDialogAnimation(R.style.DialogSlide, "Edit Quantity")
                    var Async = AsyncEdit(this)
                    Async.execute()
                } else {
                    Toast.makeText(this, "Your Password is Wrong!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dialogDelete() {
        val builder = android.app.AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.activity_login, null)
        builder.setView(view)
        val dialog: android.app.AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.setMessage("Please Enter Password!!")
        dialog.show()

        val btn_next = view.findViewById<Button>(R.id.btn_enter)
        val edt_pw = view.findViewById<EditText>(R.id.ent_pw)

        btn_next.setOnClickListener {
            branch = edt_pw.text.toString()

            println("Password is " + real_pwd)
            if (edt_pw.text == null) {
                Toast.makeText(this, "Please Enter Password!!", Toast.LENGTH_SHORT).show()
            } else {
                if (edt_pw.text.toString() == real_pwd) {

                    val item = Item(
                        txt_isbn.text.toString(),
                        Integer.parseInt(txt_tran_qty.text.toString()),
                        edt_location.text.toString()
                    )
                    db.deleteItem(item)
                    db.viewQty()

                    Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
                    txt_article.text = ""
                    txt_description.text = ""
                    txt_isbn.text = ""
                    txt_price.text = ""
                    edt_id.text.clear()
                    dialog.dismiss()

                    var rd = Integer.parseInt(txt_tran_qty.text.toString())
                    val sum = st_qt - rd
                    txt_qty.setText(sum.toString())
                    st_qt = txt_qty.text.toString().toInt()
                    txt_tran_qty.text = ""
                    edt_id.requestFocus()


                } else {
                    Toast.makeText(this, "Your Password is Wrong!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    override fun onRestart() {
        super.onRestart()
        if (edt_location.text.toString() == "") {
            Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show()
        } else {
            record_location = edt_location.text.toString()
            db.viewQty()
            if (qty_check == "false") {
                st_qt = 0
                txt_qty.text = st_qt.toString()
            } else {
                st_qt = viewqty
                txt_qty.text = st_qt.toString()

            }

            edt_id.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)
        }
    }

    private class AsyncEdit(var context: Context) : AsyncTask<String, String, String>() {


        internal lateinit var db: DataBaseHelper
        internal lateinit var pgd: ProgressDialog
        var resp: String? = null
        var cancel: String? = null


        override fun doInBackground(vararg params: String?): String {
            db = DataBaseHelper(context)
            resp = "Asyn Working"
            println(resp)
            try {
                seItem.removeAll(seItem)
                db.getEdit
            } catch (e: Exception) {
                println(e)
            }
            return resp!!
        }

        override fun onPostExecute(result: String?) {
            pgd.dismiss()
            val intent: Intent = Intent(context, Edit::class.java)
            context.startActivity(intent)
            super.onPostExecute(result)
        }

        override fun onPreExecute() {
            pgd = ProgressDialog(context)
            pgd.setMessage("Please Wait")
            pgd.setTitle("Loading Data")
            pgd.show()
            pgd.setCancelable(false)

            super.onPreExecute()
        }
    }
}



