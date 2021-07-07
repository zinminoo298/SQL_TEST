package com.example.stock_design

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.stock_design.Modle.Item_Detail
import com.example.stock_design.Modle.Item_Edit
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ContentView
import com.example.stock_design.Database.*
import com.example.stock_design.Modle.Isbn_search
import kotlinx.android.synthetic.main.activity_counting.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.txt_article
import kotlinx.android.synthetic.main.activity_edit.txt_description
import kotlinx.android.synthetic.main.activity_edit.txt_price
import kotlinx.android.synthetic.main.activity_edit.view.*
import org.w3c.dom.Text

var qty_isbn:String? = null
var qty_lc:String? = null
var qty_br:String? = null
var qty_qty:String? = null
var sc_isbn:String? = null


var fx = 0
var fc = 0

internal lateinit var ibn:TextView
internal lateinit var br:TextView
internal lateinit var lc:TextView
internal lateinit var art:TextView
internal lateinit var prc:TextView
internal lateinit var dec:TextView
internal lateinit var qt:EditText
internal lateinit var tot:TextView
class Edit: AppCompatActivity() {

    internal lateinit var db:DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        db = DataBaseHelper(this)

        ibn = findViewById(R.id.txt_isbn)
        br = findViewById(R.id.txt_br)
        lc = findViewById(R.id.txt_lc)
        art = findViewById(R.id.txt_article)
        prc = findViewById(R.id.txt_price)
        dec = findViewById(R.id.txt_description)
        qt = findViewById(R.id.edt_qt)
        tot = findViewById(R.id.txt_total)


        val txt = findViewById<TextView>(R.id.txt_isbn)
        btn_clear.visibility = View.GONE

        if (ck_edt == "false") {
            Toast.makeText(this, "NO Scanned Data", Toast.LENGTH_SHORT).show()
            txt.text = ""
            txt_br.text = ""
            txt_lc.text = ""
            txt_article.text = ""
            txt_price.text = ""
            txt_description.text = ""
            edt_qt.setText("")
            bn_st.isEnabled = false
            bn_ls.isEnabled = false
            bn_pv.isEnabled = false
            bn_up.isEnabled = false
            bt_nx.isEnabled = false
            ck_edt = null

        } else {

            txt_total.text = "" + seItem.size + " / " + seItem.size
            txt.text = seItem[seItem.size - 1].edt_isbn.toString()
            txt_br.text = seItem[seItem.size - 1].edt_branch.toString()
            txt_lc.text = seItem[seItem.size - 1].edt_location.toString()
            txt_article.text = seItem[seItem.size - 1].edt_code.toString()
            txt_price.text = seItem[seItem.size - 1].edt_price.toString()
            txt_description.text = seItem[seItem.size - 1].edt_description.toString()
            edt_qt.setText(seItem[seItem.size - 1].edt_qty.toString())

            val btn_next = findViewById<Button>(R.id.bt_nx)
            fx= seItem.size
            fc = seItem.size

            btn_next.setOnClickListener {

                if (fx == seItem.size) {
                    txt_total.text = "1 / " + seItem.size
                    txt.text = seItem[0].edt_isbn.toString()
                    txt_br.text = seItem[0].edt_branch.toString()
                    txt_lc.text = seItem[0].edt_location.toString()
                    txt_article.text = seItem[0].edt_code.toString()
                    txt_price.text = seItem[0].edt_price.toString()
                    txt_description.text = seItem[0].edt_description.toString()
                    println("1")
                    edt_qt.setText(seItem[0].edt_qty.toString())
                    fx=1
                } else {
                    println("2")
                    var size = fx
                    size++
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[fx].edt_isbn.toString()
                    txt_br.text = seItem[fx].edt_branch.toString()
                    txt_lc.text = seItem[fx].edt_location.toString()
                    txt_article.text = seItem[fx].edt_code.toString()
                    txt_price.text = seItem[fx].edt_price.toString()
                    txt_description.text = seItem[fx].edt_description.toString()
                    edt_qt.setText(seItem[fx].edt_qty.toString())
                    fx++
//                    fc = fx
                }

            }

            val btn_pv = findViewById<Button>(R.id.bn_pv)
            btn_pv.setOnClickListener {

                if (fx == 1) {
                    var size = seItem.size
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[seItem.size - 1].edt_isbn.toString()
                    txt_br.text = seItem[seItem.size - 1].edt_branch.toString()
                    txt_lc.text = seItem[seItem.size - 1].edt_location.toString()
                    txt_article.text = seItem[seItem.size - 1].edt_code.toString()
                    txt_price.text = seItem[seItem.size - 1].edt_price.toString()
                    txt_description.text = seItem[seItem.size - 1].edt_description.toString()
                    edt_qt.setText(seItem[seItem.size - 1].edt_qty.toString())
                    fx = seItem.size
//                    fc = seItem.size
                } else {
                    fx--
                    println(fx--)
                    var size = fx
                    size++
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[fx].edt_isbn.toString()
                    txt_br.text = seItem[fx].edt_branch.toString()
                    txt_lc.text = seItem[fx].edt_location.toString()
                    txt_article.text = seItem[fx].edt_code.toString()
                    txt_price.text = seItem[fx].edt_price.toString()
                    txt_description.text = seItem[fx].edt_description.toString()
                    edt_qt.setText(seItem[fx].edt_qty.toString())
                    fx++
//                    fx=fc

                }
            }

            val btn_st = findViewById<Button>(R.id.bn_st)
            btn_st.setOnClickListener {
                txt_total.text = "1 / " + seItem.size
                txt.text = seItem[0].edt_isbn.toString()
                txt_br.text = seItem[0].edt_branch.toString()
                txt_lc.text = seItem[0].edt_location.toString()
                txt_article.text = seItem[0].edt_code.toString()
                txt_price.text = seItem[0].edt_price.toString()
                txt_description.text = seItem[0].edt_description.toString()
                edt_qt.setText(seItem[0].edt_qty.toString())
                fx = 1
            }

            val btn_ls = findViewById<Button>(R.id.bn_ls)
            btn_ls.setOnClickListener {
                fx = seItem.size - 1
                txt_total.text = "" + seItem.size + " / " + seItem.size
                txt.text = seItem[fx].edt_isbn.toString()
                txt_br.text = seItem[fx].edt_branch.toString()
                txt_lc.text = seItem[fx].edt_location.toString()
                txt_article.text = seItem[fx].edt_code.toString()
                txt_price.text = seItem[fx].edt_price.toString()
                txt_description.text = seItem[fx].edt_description.toString()
                edt_qt.setText(seItem[fx].edt_qty.toString())
                fx = seItem.size
            }

            val btn_up = findViewById<Button>(R.id.bn_up)
            btn_up.setOnClickListener {
                if (edt_qt.text.toString() == "0") {
                    Toast.makeText(this, "Quantity cannot be '0'", Toast.LENGTH_SHORT).show()
                } else {
                    qty_br = txt_br.text.toString()
                    qty_lc = txt_lc.text.toString()
                    qty_qty = edt_qt.text.toString()
                    qty_isbn = txt.text.toString()

                    db.updateQty()
                    Toast.makeText(this, "Quantity Updated", Toast.LENGTH_SHORT).show()

                    println("If"+fx)
                    seItem.set(
                        fx-1, Item_Edit(
                            seItem[fx-1].edt_branch.toString(),
                            seItem[fx-1].edt_location.toString()
                            ,
                            seItem[fx-1].edt_isbn.toString(),
                            seItem[fx-1].edt_code.toString(),
                            seItem[fx-1].edt_price.toString(),
                            seItem[fx-1].edt_description.toString(),
                            edt_qt.text.toString()
                        )
                    )
                }

            }
        }

        search.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (event.keyCode == KeyEvent.KEYCODE_SPACE && event.action == KeyEvent.ACTION_UP) {

                scIsbn.removeAll(scIsbn)
                sc_isbn = search.text.toString()
                db.getSearch
                if (ck_edt == "false") {
                    Toast.makeText(this, "ISBN Not Found", Toast.LENGTH_SHORT).show()
                    ck_edt = null
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0)
                    search.requestFocus()
                } else {
                    btn_clear.visibility = View.VISIBLE
                    txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                    txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                    txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                    txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                    txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                    txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                    txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                    edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())

                    val btn_next = findViewById<Button>(R.id.bt_nx)
                    fx= scIsbn.size
                    fc = scIsbn.size

                    btn_next.setOnClickListener {

                        if (fx == scIsbn.size) {
                            txt_total.text = "1 / " + scIsbn.size
                            txt.text = scIsbn[0].edt_isbn.toString()
                            txt_br.text = scIsbn[0].edt_branch.toString()
                            txt_lc.text = scIsbn[0].edt_location.toString()
                            txt_article.text = scIsbn[0].edt_code.toString()
                            txt_price.text = scIsbn[0].edt_price.toString()
                            txt_description.text = scIsbn[0].edt_description.toString()
                            println("1")
                            edt_qt.setText(scIsbn[0].edt_qty.toString())
                            fx=1
                        } else {
                            println("2")
                            var size = fx
                            size++
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[fx].edt_isbn.toString()
                            txt_br.text = scIsbn[fx].edt_branch.toString()
                            txt_lc.text = scIsbn[fx].edt_location.toString()
                            txt_article.text = scIsbn[fx].edt_code.toString()
                            txt_price.text = scIsbn[fx].edt_price.toString()
                            txt_description.text = scIsbn[fx].edt_description.toString()
                            edt_qt.setText(scIsbn[fx].edt_qty.toString())
                            fx++
                        }

                    }

                    val btn_pv = findViewById<Button>(R.id.bn_pv)
                    btn_pv.setOnClickListener {

                        if (fx == 1) {
                            var size = scIsbn.size
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                            txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                            txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                            txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                            txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                            txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                            edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())
                            fx = scIsbn.size
                        } else {
                            fx--
                            println(fx--)
                            var size = fx
                            size++
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[fx].edt_isbn.toString()
                            txt_br.text = scIsbn[fx].edt_branch.toString()
                            txt_lc.text = scIsbn[fx].edt_location.toString()
                            txt_article.text = scIsbn[fx].edt_code.toString()
                            txt_price.text = scIsbn[fx].edt_price.toString()
                            txt_description.text = scIsbn[fx].edt_description.toString()
                            edt_qt.setText(scIsbn[fx].edt_qty.toString())
                            fx++

                        }
                    }

                    val btn_st = findViewById<Button>(R.id.bn_st)
                    btn_st.setOnClickListener {
                        txt_total.text = "1 / " + scIsbn.size
                        txt.text = scIsbn[0].edt_isbn.toString()
                        txt_br.text = scIsbn[0].edt_branch.toString()
                        txt_lc.text = scIsbn[0].edt_location.toString()
                        txt_article.text = scIsbn[0].edt_code.toString()
                        txt_price.text = scIsbn[0].edt_price.toString()
                        txt_description.text = scIsbn[0].edt_description.toString()
                        edt_qt.setText(scIsbn[0].edt_qty.toString())
                        fx = 1
                    }

                    val btn_ls = findViewById<Button>(R.id.bn_ls)
                    btn_ls.setOnClickListener {
                        fx = scIsbn.size - 1
                        txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                        txt.text = scIsbn[fx].edt_isbn.toString()
                        txt_br.text = scIsbn[fx].edt_branch.toString()
                        txt_lc.text = scIsbn[fx].edt_location.toString()
                        txt_article.text = scIsbn[fx].edt_code.toString()
                        txt_price.text = scIsbn[fx].edt_price.toString()
                        txt_description.text = scIsbn[fx].edt_description.toString()
                        edt_qt.setText(scIsbn[fx].edt_qty.toString())
                        fx = scIsbn.size
                    }

                    val btn_up = findViewById<Button>(R.id.bn_up)
                    btn_up.setOnClickListener {
                        if (edt_qt.text.toString() == "0") {
                            Toast.makeText(this, "Quantity cannot be '0'", Toast.LENGTH_SHORT).show()
                        } else {
                            qty_br = txt_br.text.toString()
                            qty_lc = txt_lc.text.toString()
                            qty_qty = edt_qt.text.toString()
                            qty_isbn = txt.text.toString()

                            db.updateQty()
                            Toast.makeText(this, "Quantity Updated", Toast.LENGTH_SHORT).show()

                            println("If"+fx)
                            scIsbn.set(
                                fx-1, Isbn_search(
                                    scIsbn[fx-1].edt_branch.toString(),
                                    scIsbn[fx-1].edt_location.toString()
                                    ,
                                    scIsbn[fx-1].edt_isbn.toString(),
                                    scIsbn[fx-1].edt_code.toString(),
                                    scIsbn[fx-1].edt_price.toString(),
                                    scIsbn[fx-1].edt_description.toString(),
                                    edt_qt.text.toString()
                                )
                            )
                        }

                    }

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0)
                }
            }

            false
        })

        search.setOnEditorActionListener(TextView.OnEditorActionListener() { _, keyCode, event ->

            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                if (search.text.toString() == "") {
                    Toast.makeText(this, "Please Enter ISBN", Toast.LENGTH_SHORT).show()

                } else {
                    scIsbn.removeAll(scIsbn)
                    sc_isbn = search.text.toString()
                    db.getSearch
                    if (ck_edt == "false") {
                        Toast.makeText(this, "ISBN Not Found", Toast.LENGTH_SHORT).show()
                        ck_edt = null
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(search.getWindowToken(), 0)
                        search.requestFocus()
                    } else {
                        btn_clear.visibility = View.VISIBLE
                        txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                        txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                        txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                        txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                        txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                        txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                        txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                        edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())

                        val btn_next = findViewById<Button>(R.id.bt_nx)
                        fx= scIsbn.size
                        fc = scIsbn.size

                        btn_next.setOnClickListener {

                            if (fx == scIsbn.size) {
                                txt_total.text = "1 / " + scIsbn.size
                                txt.text = scIsbn[0].edt_isbn.toString()
                                txt_br.text = scIsbn[0].edt_branch.toString()
                                txt_lc.text = scIsbn[0].edt_location.toString()
                                txt_article.text = scIsbn[0].edt_code.toString()
                                txt_price.text = scIsbn[0].edt_price.toString()
                                txt_description.text = scIsbn[0].edt_description.toString()
                                println("1")
                                edt_qt.setText(scIsbn[0].edt_qty.toString())
                                fx=1
                            } else {
                                println("2")
                                var size = fx
                                size++
                                txt_total.text = "" + size + " / " + scIsbn.size
                                txt.text = scIsbn[fx].edt_isbn.toString()
                                txt_br.text = scIsbn[fx].edt_branch.toString()
                                txt_lc.text = scIsbn[fx].edt_location.toString()
                                txt_article.text = scIsbn[fx].edt_code.toString()
                                txt_price.text = scIsbn[fx].edt_price.toString()
                                txt_description.text = scIsbn[fx].edt_description.toString()
                                edt_qt.setText(scIsbn[fx].edt_qty.toString())
                                fx++
                            }

                        }

                        val btn_pv = findViewById<Button>(R.id.bn_pv)
                        btn_pv.setOnClickListener {

                            if (fx == 1) {
                                var size = scIsbn.size
                                txt_total.text = "" + size + " / " + scIsbn.size
                                txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                                txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                                txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                                txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                                txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                                txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                                edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())
                                fx = scIsbn.size
                            } else {
                                fx--
                                println(fx--)
                                var size = fx
                                size++
                                txt_total.text = "" + size + " / " + scIsbn.size
                                txt.text = scIsbn[fx].edt_isbn.toString()
                                txt_br.text = scIsbn[fx].edt_branch.toString()
                                txt_lc.text = scIsbn[fx].edt_location.toString()
                                txt_article.text = scIsbn[fx].edt_code.toString()
                                txt_price.text = scIsbn[fx].edt_price.toString()
                                txt_description.text = scIsbn[fx].edt_description.toString()
                                edt_qt.setText(scIsbn[fx].edt_qty.toString())
                                fx++

                            }
                        }

                        val btn_st = findViewById<Button>(R.id.bn_st)
                        btn_st.setOnClickListener {
                            txt_total.text = "1 / " + scIsbn.size
                            txt.text = scIsbn[0].edt_isbn.toString()
                            txt_br.text = scIsbn[0].edt_branch.toString()
                            txt_lc.text = scIsbn[0].edt_location.toString()
                            txt_article.text = scIsbn[0].edt_code.toString()
                            txt_price.text = scIsbn[0].edt_price.toString()
                            txt_description.text = scIsbn[0].edt_description.toString()
                            edt_qt.setText(scIsbn[0].edt_qty.toString())
                            fx = 1
                        }

                        val btn_ls = findViewById<Button>(R.id.bn_ls)
                        btn_ls.setOnClickListener {
                            fx = scIsbn.size - 1
                            txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                            txt.text = scIsbn[fx].edt_isbn.toString()
                            txt_br.text = scIsbn[fx].edt_branch.toString()
                            txt_lc.text = scIsbn[fx].edt_location.toString()
                            txt_article.text = scIsbn[fx].edt_code.toString()
                            txt_price.text = scIsbn[fx].edt_price.toString()
                            txt_description.text = scIsbn[fx].edt_description.toString()
                            edt_qt.setText(scIsbn[fx].edt_qty.toString())
                            fx = scIsbn.size
                        }

                        val btn_up = findViewById<Button>(R.id.bn_up)
                        btn_up.setOnClickListener {
                            if (edt_qt.text.toString() == "0") {
                                Toast.makeText(this, "Quantity cannot be '0'", Toast.LENGTH_SHORT).show()
                            } else {
                                qty_br = txt_br.text.toString()
                                qty_lc = txt_lc.text.toString()
                                qty_qty = edt_qt.text.toString()
                                qty_isbn = txt.text.toString()

                                db.updateQty()
                                Toast.makeText(this, "Quantity Updated", Toast.LENGTH_SHORT).show()

                                println("If"+fx)
                                scIsbn.set(
                                    fx-1, Isbn_search(
                                        scIsbn[fx-1].edt_branch.toString(),
                                        scIsbn[fx-1].edt_location.toString()
                                        ,
                                        scIsbn[fx-1].edt_isbn.toString(),
                                        scIsbn[fx-1].edt_code.toString(),
                                        scIsbn[fx-1].edt_price.toString(),
                                        scIsbn[fx-1].edt_description.toString(),
                                        edt_qt.text.toString()
                                    )
                                )
                            }

                        }

                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(search.getWindowToken(), 0)
                    }
                }

            }

            false
        })


        btn_search.setOnClickListener {
            if (search.text.toString() == "") {
                Toast.makeText(this, "Please Enter ISBN", Toast.LENGTH_SHORT).show()
            } else {

                scIsbn.removeAll(scIsbn)
                sc_isbn = search.text.toString()
                db.getSearch
                if (ck_edt == "false") {
                    Toast.makeText(this, "ISBN Not Found", Toast.LENGTH_SHORT).show()
                    ck_edt = null
                } else {
                    btn_clear.visibility = View.VISIBLE
                    txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                    txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                    txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                    txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                    txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                    txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                    txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                    edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())

                    val btn_next = findViewById<Button>(R.id.bt_nx)
                    fx= scIsbn.size
                    fc = scIsbn.size

                    btn_next.setOnClickListener {

                        if (fx == scIsbn.size) {
                            txt_total.text = "1 / " + scIsbn.size
                            txt.text = scIsbn[0].edt_isbn.toString()
                            txt_br.text = scIsbn[0].edt_branch.toString()
                            txt_lc.text = scIsbn[0].edt_location.toString()
                            txt_article.text = scIsbn[0].edt_code.toString()
                            txt_price.text = scIsbn[0].edt_price.toString()
                            txt_description.text = scIsbn[0].edt_description.toString()
                            println("1")
                            edt_qt.setText(scIsbn[0].edt_qty.toString())
                            fx=1
                        } else {
                            println("2")
                            var size = fx
                            size++
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[fx].edt_isbn.toString()
                            txt_br.text = scIsbn[fx].edt_branch.toString()
                            txt_lc.text = scIsbn[fx].edt_location.toString()
                            txt_article.text = scIsbn[fx].edt_code.toString()
                            txt_price.text = scIsbn[fx].edt_price.toString()
                            txt_description.text = scIsbn[fx].edt_description.toString()
                            edt_qt.setText(scIsbn[fx].edt_qty.toString())
                            fx++
//                    fc = fx
                        }

                    }

                    val btn_pv = findViewById<Button>(R.id.bn_pv)
                    btn_pv.setOnClickListener {

                        if (fx == 1) {
                            var size = scIsbn.size
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[scIsbn.size - 1].edt_isbn.toString()
                            txt_br.text = scIsbn[scIsbn.size - 1].edt_branch.toString()
                            txt_lc.text = scIsbn[scIsbn.size - 1].edt_location.toString()
                            txt_article.text = scIsbn[scIsbn.size - 1].edt_code.toString()
                            txt_price.text = scIsbn[scIsbn.size - 1].edt_price.toString()
                            txt_description.text = scIsbn[scIsbn.size - 1].edt_description.toString()
                            edt_qt.setText(scIsbn[scIsbn.size - 1].edt_qty.toString())
                            fx = scIsbn.size
//                    fc = seItem.size
                        } else {
                            fx--
                            println(fx--)
                            var size = fx
                            size++
                            txt_total.text = "" + size + " / " + scIsbn.size
                            txt.text = scIsbn[fx].edt_isbn.toString()
                            txt_br.text = scIsbn[fx].edt_branch.toString()
                            txt_lc.text = scIsbn[fx].edt_location.toString()
                            txt_article.text = scIsbn[fx].edt_code.toString()
                            txt_price.text = scIsbn[fx].edt_price.toString()
                            txt_description.text = scIsbn[fx].edt_description.toString()
                            edt_qt.setText(scIsbn[fx].edt_qty.toString())
                            fx++
//                    fx=fc

                        }
                    }

                    val btn_st = findViewById<Button>(R.id.bn_st)
                    btn_st.setOnClickListener {
                        txt_total.text = "1 / " + scIsbn.size
                        txt.text = scIsbn[0].edt_isbn.toString()
                        txt_br.text = scIsbn[0].edt_branch.toString()
                        txt_lc.text = scIsbn[0].edt_location.toString()
                        txt_article.text = scIsbn[0].edt_code.toString()
                        txt_price.text = scIsbn[0].edt_price.toString()
                        txt_description.text = scIsbn[0].edt_description.toString()
                        edt_qt.setText(scIsbn[0].edt_qty.toString())
                        fx = 1
                    }

                    val btn_ls = findViewById<Button>(R.id.bn_ls)
                    btn_ls.setOnClickListener {
                        fx = scIsbn.size - 1
                        txt_total.text = "" + scIsbn.size + " / " + scIsbn.size
                        txt.text = scIsbn[fx].edt_isbn.toString()
                        txt_br.text = scIsbn[fx].edt_branch.toString()
                        txt_lc.text = scIsbn[fx].edt_location.toString()
                        txt_article.text = scIsbn[fx].edt_code.toString()
                        txt_price.text = scIsbn[fx].edt_price.toString()
                        txt_description.text = scIsbn[fx].edt_description.toString()
                        edt_qt.setText(scIsbn[fx].edt_qty.toString())
                        fx = scIsbn.size
                    }

                    val btn_up = findViewById<Button>(R.id.bn_up)
                    btn_up.setOnClickListener {
                        if (edt_qt.text.toString() == "0") {
                            Toast.makeText(this, "Quantity cannot be '0'", Toast.LENGTH_SHORT).show()
                        } else {
                            qty_br = txt_br.text.toString()
                            qty_lc = txt_lc.text.toString()
                            qty_qty = edt_qt.text.toString()
                            qty_isbn = txt.text.toString()

                            db.updateQty()
                            Toast.makeText(this, "Quantity Updated", Toast.LENGTH_SHORT).show()

                            println("If"+fx)
                            scIsbn.set(
                                fx-1, Isbn_search(
                                    scIsbn[fx-1].edt_branch.toString(),
                                    scIsbn[fx-1].edt_location.toString()
                                    ,
                                    scIsbn[fx-1].edt_isbn.toString(),
                                    scIsbn[fx-1].edt_code.toString(),
                                    scIsbn[fx-1].edt_price.toString(),
                                    scIsbn[fx-1].edt_description.toString(),
                                    edt_qt.text.toString()
                                )
                            )
                        }

                    }
                }
            }
        }

        btn_clear.setOnClickListener {

            val btn_next = findViewById<Button>(R.id.bt_nx)
            fx= seItem.size
            fc = seItem.size
            search.text.clear()
            val inflater = this.layoutInflater
            var async = AsyncClear(this)
            async.execute()
            btn_clear.visibility = View.GONE
//            seItem.removeAll(seItem)
//            db.getEdit

//            txt_total.text = "" + seItem.size + " / " + seItem.size
//            txt.text = seItem[seItem.size - 1].edt_isbn.toString()
//            txt_br.text = seItem[seItem.size - 1].edt_branch.toString()
//            txt_lc.text = seItem[seItem.size - 1].edt_location.toString()
//            txt_article.text = seItem[seItem.size - 1].edt_code.toString()
//            txt_price.text = seItem[seItem.size - 1].edt_price.toString()
//            txt_description.text = seItem[seItem.size - 1].edt_description.toString()
//            edt_qt.setText(seItem[seItem.size - 1].edt_qty.toString())
//


            btn_next.setOnClickListener {

                if (fx == seItem.size) {
                    txt_total.text = "1 / " + seItem.size
                    txt.text = seItem[0].edt_isbn.toString()
                    txt_br.text = seItem[0].edt_branch.toString()
                    txt_lc.text = seItem[0].edt_location.toString()
                    txt_article.text = seItem[0].edt_code.toString()
                    txt_price.text = seItem[0].edt_price.toString()
                    txt_description.text = seItem[0].edt_description.toString()
                    println("1")
                    edt_qt.setText(seItem[0].edt_qty.toString())
                    fx=1
                } else {
                    println("2")
                    var size = fx
                    size++
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[fx].edt_isbn.toString()
                    txt_br.text = seItem[fx].edt_branch.toString()
                    txt_lc.text = seItem[fx].edt_location.toString()
                    txt_article.text = seItem[fx].edt_code.toString()
                    txt_price.text = seItem[fx].edt_price.toString()
                    txt_description.text = seItem[fx].edt_description.toString()
                    edt_qt.setText(seItem[fx].edt_qty.toString())
                    fx++
//                    fc = fx
                }

            }

            val btn_pv = findViewById<Button>(R.id.bn_pv)
            btn_pv.setOnClickListener {

                if (fx == 1) {
                    var size = seItem.size
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[seItem.size - 1].edt_isbn.toString()
                    txt_br.text = seItem[seItem.size - 1].edt_branch.toString()
                    txt_lc.text = seItem[seItem.size - 1].edt_location.toString()
                    txt_article.text = seItem[seItem.size - 1].edt_code.toString()
                    txt_price.text = seItem[seItem.size - 1].edt_price.toString()
                    txt_description.text = seItem[seItem.size - 1].edt_description.toString()
                    edt_qt.setText(seItem[seItem.size - 1].edt_qty.toString())
                    fx = seItem.size
//                    fc = seItem.size
                } else {
                    fx--
                    println(fx--)
                    var size = fx
                    size++
                    txt_total.text = "" + size + " / " + seItem.size
                    txt.text = seItem[fx].edt_isbn.toString()
                    txt_br.text = seItem[fx].edt_branch.toString()
                    txt_lc.text = seItem[fx].edt_location.toString()
                    txt_article.text = seItem[fx].edt_code.toString()
                    txt_price.text = seItem[fx].edt_price.toString()
                    txt_description.text = seItem[fx].edt_description.toString()
                    edt_qt.setText(seItem[fx].edt_qty.toString())
                    fx++
//                    fx=fc

                }
            }

            val btn_st = findViewById<Button>(R.id.bn_st)
            btn_st.setOnClickListener {
                txt_total.text = "1 / " + seItem.size
                txt.text = seItem[0].edt_isbn.toString()
                txt_br.text = seItem[0].edt_branch.toString()
                txt_lc.text = seItem[0].edt_location.toString()
                txt_article.text = seItem[0].edt_code.toString()
                txt_price.text = seItem[0].edt_price.toString()
                txt_description.text = seItem[0].edt_description.toString()
                edt_qt.setText(seItem[0].edt_qty.toString())
                fx = 1
            }

            val btn_ls = findViewById<Button>(R.id.bn_ls)
            btn_ls.setOnClickListener {
                fx = seItem.size - 1
                txt_total.text = "" + seItem.size + " / " + seItem.size
                txt.text = seItem[fx].edt_isbn.toString()
                txt_br.text = seItem[fx].edt_branch.toString()
                txt_lc.text = seItem[fx].edt_location.toString()
                txt_article.text = seItem[fx].edt_code.toString()
                txt_price.text = seItem[fx].edt_price.toString()
                txt_description.text = seItem[fx].edt_description.toString()
                edt_qt.setText(seItem[fx].edt_qty.toString())
                fx = seItem.size
            }

            val btn_up = findViewById<Button>(R.id.bn_up)
            btn_up.setOnClickListener {
                if (edt_qt.text.toString() == "0") {
                    Toast.makeText(this, "Quantity cannot be '0'", Toast.LENGTH_SHORT).show()
                } else {
                    qty_br = txt_br.text.toString()
                    qty_lc = txt_lc.text.toString()
                    qty_qty = edt_qt.text.toString()
                    qty_isbn = txt.text.toString()

                    db.updateQty()
                    Toast.makeText(this, "Quantity Updated", Toast.LENGTH_SHORT).show()

                    println("If"+fx)
                    seItem.set(
                        fx-1, Item_Edit(
                            seItem[fx-1].edt_branch.toString(),
                            seItem[fx-1].edt_location.toString()
                            ,
                            seItem[fx-1].edt_isbn.toString(),
                            seItem[fx-1].edt_code.toString(),
                            seItem[fx-1].edt_price.toString(),
                            seItem[fx-1].edt_description.toString(),
                            edt_qt.text.toString()
                        )
                    )
                }

            }
        }


    }

    private class AsyncClear(var context: Context): AsyncTask<String, String, String>() {


        internal lateinit var db: DataBaseHelper
        internal lateinit var pgd: ProgressDialog
        var resp:String? = null
        var cancel:String? = null




        override fun doInBackground(vararg params: String?): String {
            db = DataBaseHelper(context)
            resp ="Asyn Working"
            println(resp)
            try{
                seItem.removeAll(seItem)
                db.getEdit
            }
            catch(e:Exception){
                println(e)
            }
            return resp!!
        }

        override fun onPostExecute(result: String?) {

            pgd.dismiss()
            println("SEITEM"+ seItem.size)
            tot.text = "" + seItem.size + " / " + seItem.size
            ibn.text = seItem[seItem.size - 1].edt_isbn.toString()
            br.text = seItem[seItem.size - 1].edt_branch.toString()
            lc.text = seItem[seItem.size - 1].edt_location.toString()
            art.text = seItem[seItem.size - 1].edt_code.toString()
            prc.text = seItem[seItem.size - 1].edt_price.toString()
            dec.text = seItem[seItem.size - 1].edt_description.toString()
            qt.setText(seItem[seItem.size - 1].edt_qty.toString())
            super.onPostExecute(result)
        }

        override fun onPreExecute() {
            pgd = ProgressDialog(context)
            pgd.setMessage("Please Wait")
            pgd.setTitle("Reloading Data")
            pgd.show()
            pgd.setCancelable(false)

            super.onPreExecute()
        }

    }}
