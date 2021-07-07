package com.example.stock_design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stock_design.Database.*
import com.example.stock_design.Modle.Item
import kotlinx.android.synthetic.main.activity_counting.*
import kotlinx.android.synthetic.main.activity_verify.*
import kotlinx.android.synthetic.main.activity_verify.edt_id
import kotlinx.android.synthetic.main.activity_verify.txt_description
import kotlinx.android.synthetic.main.activity_verify.txt_isbn
import kotlinx.android.synthetic.main.activity_verify.txt_price

var verify:String? = null

class Verify : AppCompatActivity() {
    internal lateinit var db: DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        db=DataBaseHelper(this)


        val btn_search = findViewById<Button>(R.id.btn_ok)
        val verify_code = findViewById<EditText>(R.id.edt_id)

        btn_search.setOnClickListener{

            if(verify_code.text.toString()=="" || verify_code.text.toString()==null)
            {
                Toast.makeText(this,"Please Enter Code!",Toast.LENGTH_SHORT).show()
            }
            else {
                verify=verify_code.text.toString().replace(" ","")
                println(verify)
                db.Verify()
//                println(verify)

                if (check == "nodata") {
                    Toast.makeText(this, "Cannot Verify The Code", Toast.LENGTH_SHORT).show()
                    txt_isbn.setText("")
                    txt_code.setText("")
                    txt_price.setText("")
                    txt_description.setText("")
                    check = null
                } else {
                    txt_isbn.setText(isbn)
                    txt_code.setText(verify)
                    txt_price.setText(price)
                    txt_description.setText(desc)
                    verify_code.setText("")
                    verify_code.requestFocus()
                }
            }

        }

        verify_code.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (event.keyCode == KeyEvent.KEYCODE_SPACE && event.action == KeyEvent.ACTION_UP ) {

                if (edt_id.text.toString() == "" || edt_id.text.toString() == null) {
                    Toast.makeText(this, "Make Sure To Enter Code", Toast.LENGTH_SHORT)
                        .show()
                    edt_id.text.clear()
                    println(verify)


                } else{
                    try {

                        verify = verify_code.text.toString().replace(" ","")
                        db.Verify()

                        if (check == "nodata") {
                            Toast.makeText(this, "Cannot Verify The Code", Toast.LENGTH_SHORT).show()
                            txt_isbn.setText("")
                            txt_code.setText("")
                            txt_price.setText("")
                            txt_description.setText("")
                            check = null
                        } else {
                            txt_isbn.setText(isbn)
                            txt_code.setText(verify)
                            txt_price.setText(price)
                            txt_description.setText(desc)
                            verify_code.setText("")
                            verify_code.requestFocus()
                        }

                    } catch (nfe: NumberFormatException) {
                        nfe.printStackTrace()
                    }
                }
            }

            false

        })


    }


}
