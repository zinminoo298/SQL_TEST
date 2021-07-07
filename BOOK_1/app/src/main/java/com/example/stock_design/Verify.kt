package com.example.stock_design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stock_design.Database.*
import kotlinx.android.synthetic.main.activity_verify.*

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
                verify=verify_code.text.toString()
                db.Verify()

                if (check == "nodata") {
                    Toast.makeText(this, "Cannot Verify The Code", Toast.LENGTH_SHORT).show()
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

    }


}
