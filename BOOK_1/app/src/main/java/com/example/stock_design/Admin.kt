package com.example.stock_design

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.example.stock_design.Database.DataBaseHelper
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_password.view.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ProgressBar
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.CountDownTimer
import android.view.View
import java.io.*


var password:String? = "12345678"
var file:String? =null
var line:Int? = 0
var in_line:Int? = 0
var yo:String? = null
class Admin : AppCompatActivity() {

    internal lateinit var lbl: TextView
    internal lateinit var db: DataBaseHelper
    internal lateinit var btnimport: EditText
    val requestcode=1
    internal lateinit var scan: Button
    internal lateinit var exit:Button
    internal lateinit var cond:TextView
    internal lateinit var export: Button
    internal lateinit var dialog: AlertDialog
    private var progressBar: ProgressBar?=null
    private var progressBar1: ProgressBar? =null

    private val DB_PATH="/data/data/com.example.stock_design/databases"
    private val REAL_DATABASE="database.db"
    private var MASTER:String? = null
    private var Tran:String? = null
    private var All:String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val btn_pw = findViewById<Button>(R.id.btn_pwd)
        val btn_location = findViewById<Button>(R.id.btn_location)
        val btn_import = findViewById<Button>(R.id.btn_import)
        val btn_export = findViewById<Button>(R.id.btn_export)
        val btn_del = findViewById<Button>(R.id.btn_del)

        btn_pw.setOnClickListener{
            dialogLogin()
        }

        btn_location.setOnClickListener {
            val intent = Intent(this,Search::class.java)
            startActivity(intent)
        }

        btn_import.setOnClickListener {
            importDialog(R.style.DialogSlide,this)
        }

        btn_export.setOnClickListener {
            exportDialog(R.style.DialogSlide,this)
        }

        btn_del.setOnClickListener {
            clearDialog(this)
        }


    }

    private fun exportDialog(type: Int, context: Context) {

        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.activity_export, null)
        builder.setView(view)
        dialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage("THE DATA WILL BE EXPORTED TO Downloads/result.txt")
        progressBar1 = view.findViewById(R.id.progress_bar)
        progressBar1!!.visibility = View.GONE
        dialog.show()

        export = view.findViewById(R.id.btn_export)
        export.setOnClickListener {
            progressBar1!!.visibility = View.VISIBLE

            Handler().postDelayed({

                val filepath="/storage/emulated/0/Download/result.txt"
                val file=File(filepath)
                if(file.exists())
                {
                    export()
//                Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()

                }
                else{
                    generateNoteOnSD(this,"/result.txt/")
                    if(file.exists())
                    {
                        export()
//                    Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()
                    }

                    else{
                        Toast.makeText(this, "EXPORT UNSUCCESSFUL. MAKE SURE TO GIVE STORAGE ACCESS", Toast.LENGTH_LONG).show()
                    }

                }
            },1000)


        }
    }

    private fun dialogLogin(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.activity_password,null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.setMessage("Please Enter New Password")
        dialog.show()

        val brn_save =view.findViewById<Button>(R.id.btn_save)
        val edt_pw = view.findViewById<EditText>(R.id.edt_pw)


        brn_save.setOnClickListener{
            branch = edt_pw.text.toString()


            if(edt_pw.text == null){
                Toast.makeText(this,"Please Enter New Password",Toast.LENGTH_SHORT).show()
            }
            else{
                if(edt_pw.text.length >= 8 && edt_pw.text.length <= 14){
                    password = edt_pw.text.toString()
                    Toast.makeText(this,"Your New Password Is Saved",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                    setpwd(password.toString())
                    real_pwd = password.toString()
                }
                else{
                    Toast.makeText(this,"Your New Password Must Be Between 8 and 14 digits",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setpwd( v:String) {
        var editor=getSharedPreferences("yo", MODE_PRIVATE).edit()
        editor.putString("val", v)
        editor.apply()


    }

    /*import csv file to database*/
    private fun importDialog(type: Int,context: Context) {
        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.import__dialog, null)
        builder.setView(view)
        val dialog: AlertDialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage(context.getString(R.string.open_master))
        lbl=EditText(this)
        lbl=view.findViewById(R.id.edit_master)
        lbl.text=noti.toString()
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar!!.visibility = View.GONE
        cond = view.findViewById(R.id.in_cond)
        cond.setText("Importing ...")
        cond!!.visibility = View.GONE
        dialog.show()

        db=DataBaseHelper(this)

        btnimport=view.findViewById(R.id.edit_master)

        btnimport.setOnClickListener {
            cond!!.visibility = View.GONE
            val fileintent=Intent(Intent.ACTION_GET_CONTENT)
            fileintent.type="txt/csv"
            try {
                startActivityForResult(fileintent, requestcode)
                dialog.show()
            } catch (e: ActivityNotFoundException) {
                lbl.text="No activity can handle picking a file. Showing alternatives."
            }
        }

        scan=view.findViewById<Button>(R.id.btn_scan)
        scan.setOnClickListener {
//            progressBar!!.visibility = View.VISIBLE
                cond.setText("Importing ...")
            cond!!.visibility = View.VISIBLE
            Handler().postDelayed({
                import(this)

            },2000)
            scan.setEnabled(false)
            exit.setEnabled(false)
        }

        exit = view.findViewById<Button>(R.id.btn_cancle)
        exit.setOnClickListener {
            dialog.dismiss()
//            val intent=intent
//            finish()
//            startActivity(intent)

        }


    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        if (requestCode==requestcode) {
            val filepath=data.data
            file = filepath.toString()
            val cursor=contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            lbl.text=filepath.toString()
            master_path=filepath.toString()
            noti=cursor.toString()
            val db=this.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
            val tableName="Master"
            db.execSQL("delete from $tableName")
            val text =  StringBuilder()
            try {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val file=InputStreamReader(cursor)
                        val buffer=BufferedReader(file)
                        var lineCount = 0
                        db.beginTransaction()

                        while(true) {
                            val line1=buffer.readLine()
                            lineCount++
                            line=lineCount
                            if (line1 == null) break


                        }
                        println(line.toString())
//                        line = lineCount.toString().toInt()
                        db.setTransactionSuccessful()
                        db.endTransaction()
                    } catch (e: IOException) {
                        if (db.inTransaction())
                            db.endTransaction()
                        val d=Dialog(this)
                        d.setTitle(e.message.toString() + "first")
                        d.show()
                    }

                } else {
                    if (db.inTransaction())
                        db.endTransaction()
                    val d=Dialog(this)
                    d.setTitle("Only CSV files allowed")
                    d.show()
                }
            } catch (ex: Exception) {
                if (db.inTransaction())
                    db.endTransaction()

                val d=Dialog(this)
                d.setTitle(ex.message.toString() + "second")
                d.show()
            }

        }

    }

    fun import(context: Context) {

        val filepath=file
        val cursor=contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
        lbl.text=filepath.toString()
        master_path=filepath.toString()
        noti=cursor.toString()
        val db=this.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
        val tableName="Master"
        db.execSQL("delete from $tableName")
        val text =  StringBuilder()
        try {
            try {
                val file=InputStreamReader(cursor)
                var lineCount = 0
                val buffer=BufferedReader(file)
//                    buffer.readLine()
                val contentValues=ContentValues()
                db.beginTransaction()

                while(true) {

                    lineCount++
                    in_line = lineCount
                    val line=buffer.readLine()

                    if (line == null) break

                    val article_array = ArrayList<String>()
                    var desc_array = ArrayList<String>()
                    var price_array = ArrayList<String>()
                    var isbn_array = ArrayList<String>()
                    var total_array = ArrayList<String>()

                    for(i in 0..12){
                        val article = line[i].toString().replace(" ","")
                        article_array.add(article)
                    }

                    for(i in 13..62){
                        val desc = line[i].toString()
                        desc_array.add(desc)
                    }
                    for(i  in 63..70){
                        val price = line[i].toString().replace(" ","")
                        price_array.add(price)
                    }
                    for(i in 71..line.length-1){
                        val isbn = line[i].toString().replace(" ","")
                        isbn_array.add(isbn)
                    }

                    val article_list = article_array.toString().replace(", ","").replace("[","").replace("]","")
                    val desc_list = desc_array.toString().replace(", ","").replace("[","").replace("]","")
                    val price_list = price_array.toString().replace(", ","").replace("[","").replace("]","")
                    val isbn_list:String = isbn_array.toString().replace(", ","").replace("[","").replace("]","")

                    total_array.add(article_list)
                    total_array.add(desc_list)
                    total_array.add(price_list)
                    total_array.add(isbn_list)

                    println(total_array)
//                    yo = total_array.toString()

                    val article=total_array[0].toString()
                    val desc=total_array[1].toString()
                    val price=total_array[2].toString()
                    val isbn=total_array[3].toString()

                    contentValues.put("article", article)
                    contentValues.put("description", desc)
                    contentValues.put("price", price)
                    contentValues.put("isbn", isbn)
                    db.insert(tableName, null, contentValues)

                }
                cond.setText("Import Successful ")
                cond!!.visibility = View.VISIBLE
//                progressBar!!.visibility = View.GONE

//                    in_line = lineCount.toString().toInt()
                println(in_line.toString())


                db.setTransactionSuccessful()
                db.endTransaction()
                progressBar!!.visibility = View.GONE
                Toast.makeText(this,"IMPORT MASTER SUCCESSFUL",Toast.LENGTH_LONG).show()
                scan.setEnabled(true)
                exit.setEnabled(true)

            } catch (e: IOException) {
                if (db.inTransaction())
                    db.endTransaction()
//                progressBar!!.visibility = View.GONE
                Toast.makeText(this,"CANNOT IMPORT MASTER, ERROR AT LINE"+ in_line,Toast.LENGTH_LONG).show()
//                println(yo)
                cond.setText("Import Fail!!! ")
                cond!!.visibility = View.VISIBLE
                scan.setEnabled(true)
                exit.setEnabled(true)
                println("ERROR")
            }

        } catch (ex: Exception) {
            if (db.inTransaction())
                db.endTransaction()

            val d=Dialog(this)
            d.setTitle(ex.message.toString() + "second")
            d.show()
            println("ERROR!!!")
            cond.setText("Import Fail!!!")
            cond!!.visibility = View.VISIBLE
            Toast.makeText(this,"CANNOT IMPORT MASTER, ERROR AT LINE"+ in_line,Toast.LENGTH_LONG).show()
//            println(yo)
            scan.setEnabled(true)
            exit.setEnabled(true)

        }

    }

    private fun export() {

        progressBar1!!.visibility = View.GONE

        try {
            db=DataBaseHelper(this)
            val db=this.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
            val selectQuery=
                " SELECT T.branch, T.location, M.article, T.isbn, T.qty from Transaction_table T Left Outer Join Master M On T.isbn = M.isbn"
            val cursor=db.rawQuery(selectQuery, null)
            var rowcount: Int
            var colcount: Int

            val saveFile=File("/sdcard/Download/result.txt")
            val fw=FileWriter(saveFile)


            val bw=BufferedWriter(fw)
            rowcount=cursor.getCount()
            colcount=cursor.getColumnCount()


            if (rowcount>0) {

                for (i in 0 until rowcount) {
                    cursor!!.moveToPosition(i)

                    for (j in 0 until colcount) {
                        if (j == 0) {

                            bw.write(java.lang.String.format("%4s",cursor!!.getString(j)))
                            println(java.lang.String.format("%4s",cursor!!.getString(j)))

                        }
                        if (j == 1) {

                            bw.write(java.lang.String.format("%12s",cursor!!.getString(j)))

                        }
                        if (j == 2) {

                            bw.write(java.lang.String.format("%13s",cursor!!.getString(j)))

                        }
                        if (j == 3) {

                            bw.write(java.lang.String.format("%18s",cursor!!.getString(j)))

                        }
                        if (j == 4) {

                            bw.write(java.lang.String.format("%6s",cursor!!.getString(j)))

                        }

                    }
                    bw.newLine()
                }
                bw.flush()

            }

            Toast.makeText(this,"EXPORT SUCCESSFUL",Toast.LENGTH_LONG).show()
            dialog.dismiss()
            confirmDel()

        }
        catch (ex: Exception) {
            ex.printStackTrace()

        }

    }

    fun generateNoteOnSD(context: Context, sFileName: String) {
        try {
            val root=File( "/storage/emulated/0/Download/")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile=File(root, sFileName)
            val writer=FileWriter(gpxfile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun clearDialog(context: Context){
        lateinit var dialog: AlertDialog
        val listItems=arrayOf(context.getString(R.string.master1), context.getString(R.string.tran),context.getString(R.string.all))
        val builder=AlertDialog.Builder(this)

        val checkvalue=booleanArrayOf(
            false,
            false        )
        builder.setTitle(R.string.c_data)
        builder.setSingleChoiceItems(listItems,-1,
            DialogInterface.OnClickListener(){ dialoginterface, i->

                if (i == 0) {
                    MASTER = "Master"
                    Tran = null
                    All = null
                    println(MASTER)
                    println(Tran)
                    println(All)
                }

                if (i == 1) {
                    Tran = "Transaction_table"
                    MASTER = null
                    All = null
                    println(MASTER)
                    println(Tran)
                    println(All)
                }

                if (i== 2){
                    All = "Transaction_table"
                    Tran = null
                    MASTER = "Master"
                    println(MASTER)
                    println(Tran)
                    println(All)
                }

                if (i!=0 && i!=1 && i!=2){
                    MASTER = null
                    Tran = null
                    All = null
                    println(MASTER)
                    println(Tran)
                    println(All)
                }
            })

        builder.setPositiveButton("Clear",  DialogInterface.OnClickListener(){ _, _ ->

            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)



            if(MASTER==null){
                println("Master is null")
            }
            else {
                db.execSQL("delete from $MASTER")
                Toast.makeText(this, "Master Data Deleted!!",Toast.LENGTH_LONG).show()
            }


            if(Tran==null){
                println("Tran is null")
            }
            else {
                db.execSQL("delete from $Tran")
                Toast.makeText(this, "Scanned Data Deleted!!",Toast.LENGTH_LONG).show()

            }

            if(All==null){
                println("ALL is null")
            }
            else {
                db.execSQL("delete from $All")
                db.execSQL("delete from $MASTER")
                Toast.makeText(this, "ALL Data Deleted!!",Toast.LENGTH_LONG).show()

            }

            if(MASTER == null && Tran == null && All == null){
                Toast.makeText(this,"Select one option", Toast.LENGTH_SHORT).show()
            }

            MASTER = null
            Tran = null
            All = null

            println(MASTER+Tran+All)

            dialog.dismiss()
//            summery()
        })

        dialog= builder.create()
        dialog.show()
    }


    private fun confirmDel(){
        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.activity_confirmdelete, null)
        builder.setView(view)
        val dialog=builder.create()
        dialog.window?.attributes?.windowAnimations=R.style.DialogSlide
        dialog.setMessage("Do You Want To Choose Database to DELETE?")
        dialog.show()

        val btn_yes = view.findViewById<Button>(R.id.btn_yes)
        val btn_no = view.findViewById<Button>(R.id.btn_no)

        btn_yes.setOnClickListener {
            dialog.dismiss()
            clearDialog(this)
        }

        btn_no.setOnClickListener {

            dialog.dismiss()
        }
    }

}
