package com.example.stock_design

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.stock_design.Database.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import java.nio.file.Files.exists
import android.os.Environment.getExternalStorageDirectory
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.stock_design.Modle.Item_search
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.import__dialog.*
import kotlin.collections.ArrayList


var noti: String?="Select Master File"
var master_date: String?="Master File not selected"
var master_path: String?=null
var branch:String? = null
var real_pwd:String? = null

class MainActivity : AppCompatActivity() {
    internal lateinit var db: DataBaseHelper
    internal lateinit var lbl: TextView
    internal lateinit var btnimport: EditText
    internal lateinit var scan: Button
    private val DB_PATH="/data/data/com.example.stock_design/databases"
    private val REAL_DATABASE="database.db"
    private var MASTER:String? = null
    private var Tran:String? = null
    private var All:String? = null
    val requestcode=1
    var selectedItems= -1
    var STORAGE_PERMISSION_CODE = 1;


    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLoacle()
        loadvalue()
        loadpwd()
        setContentView(R.layout.activity_main)

        db=DataBaseHelper(this)
        db.openDatabase()


        /*Drawer for menu setting*/
        val mDrawerLayout=findViewById<DrawerLayout>(R.id.drawlayout)
        mToggle=ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val btn_verify = findViewById<LinearLayout>(R.id.verify)
        val btn_scan = findViewById<LinearLayout>(R.id.scan)
        val btn_admin = findViewById<LinearLayout>(R.id.admin)

        btn_verify.setOnClickListener {
            val intent=Intent(this, Verify::class.java)
            startActivity(intent)        }

        btn_scan.setOnClickListener{
            dialogBranch()
        }

        btn_admin.setOnClickListener {
            dialogAdmin()
        }

        var value1 = 1111
        var value2 = "YGN"
        var value3 = 3.0
        var value4 = 4.0
        println(java.lang.String.format("%-4d, %-12s, %-6f : %-3f", value1, value2, value3, value4))




        val navigationView: NavigationView=findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            menuItem.isChecked=!menuItem.isChecked

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_new -> {
                   dialogBranch()
                }

                R.id.nav_exit -> {
                    finish()
                    System.exit(0)
                }

                R.id.nav_about -> {
                    val intent = Intent(this,About::class.java)
                    startActivity(intent)
                }

                R.id.nav_verify -> {
                    val intent=Intent(this, Verify::class.java)
                    startActivity(intent)
                }

                R.id.nav_admin -> {
                    dialogAdmin()
                }


            }


            true
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Storage Permission is Granted",
                Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }

    }
    /*show summery data on main page*/
//    private fun summery() {
//        db.getTransactionSummery()
//        db.getMasterSummery()
//
//        val itm_tran=findViewById<TextView>(R.id.tran_itm)
//        itm_tran.text=item_tran.toString()
//
//        val qty_transaction=findViewById<TextView>(R.id.tran_qty)
//        qty_transaction.text=qty_tran.toString()
//
//        val mnewitem=findViewById<TextView>(R.id.m_new_itm)
//        mnewitem.text=new_item.toString()
//
//        val dt=findViewById<TextView>(R.id.tran_dt)
//        dt.text=date.toString()
//
//        val itm_master=findViewById<TextView>(R.id.master_itm)
//        itm_master.text=item_master.toString()
//
//        val master_qty=findViewById<TextView>(R.id.master_qty)
//        master_qty.text=qty_master.toString()
//
//    }

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE
            )
        }

        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dialogBranch(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.activity_branch,null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.setMessage("Please Fill The Branch Name")
        dialog.show()

        val btn_next =view.findViewById<Button>(R.id.btn_next)
        val edt_branch = view.findViewById<EditText>(R.id.edt_branch)

        btn_next.setOnClickListener{
            record_date = edt_branch.text.toString()
            println(edt_branch.text.toString())

            if(edt_branch.text == null || edt_branch.text.toString() == ""){
                Toast.makeText(this,"Please fill the branch",Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this,Counting::class.java)
                startActivity(intent)
            }
        }
    }

    /*dialog page for user record*/
    private fun showDialogAnimation(type: Int,context: Context) {
        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.layout_dialog, null)
        builder.setView(view)
        val dialog: AlertDialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage(context.getString(R.string.info))
        dialog.show()

        val Date=view.findViewById<TextView>(R.id.edit_date)
        val Location=view.findViewById<TextView>(R.id.edit_location)
        val Name=view.findViewById<TextView>(R.id.edit_name)

        val dateF=SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
        val date=dateF.format(Calendar.getInstance().time)
        Date.text=date.toString()


        val c=Calendar.getInstance()
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        Date.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mday ->
                day=mday
                month=mmonth + 1
                year=myear

                Date.text=("" + day + "/" + month + "/" + year)

                record_date=Date.text.toString()
            }, year, month, day)
            dpd.datePicker.setMinDate(System.currentTimeMillis())
            dpd.show()
        }


        val OK_button=view.findViewById<Button>(R.id.btn_ok)
        OK_button.setOnClickListener {
            if (Date.text.toString() == "" || Name.text.toString() == "" || Location.text.toString() == "") {
                Toast.makeText(this, "Please Fill The Fields", Toast.LENGTH_SHORT).show()
            } else {
                record_location=Location.text.toString()
                record_date=Date.text.toString()
                println("Date" + record_date)
                dialog.dismiss()
                val intent=Intent(this, Counting::class.java)
                startActivity(intent)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
        }
    }

    /*export data to csv file*/
    private fun export() {
        try {
            db=DataBaseHelper(this)
            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
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

        }
        catch (ex: Exception) {
            ex.printStackTrace()

        }

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
        dialog.show()

        db=DataBaseHelper(this)

        btnimport=view.findViewById(R.id.edit_master)

        btnimport.setOnClickListener {
            val fileintent=Intent(Intent.ACTION_GET_CONTENT)
            fileintent.type="txt/csv"
            try {
                startActivityForResult(fileintent, requestcode)
            } catch (e: ActivityNotFoundException) {
                lbl.text="No activity can handle picking a file. Showing alternatives."
            }
        }

        scan=view.findViewById<Button>(R.id.btn_scan)
        scan.setOnClickListener {

            showDialogAnimation(R.style.DialogSlide1, this)

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
    /*import main function*/
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        if (requestCode==requestcode) {
            val filepath=data.data
            val cursor=contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            lbl.text=filepath.toString()
            master_path=filepath.toString()
            noti=cursor.toString()
            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val tableName="Master"
            db.execSQL("delete from $tableName")
            try {
                println("gg")
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val file=InputStreamReader(cursor)
                        var lineCount = 0
                        val buffer=BufferedReader(file)
                        buffer.readLine()
                        val contentValues=ContentValues()
                        db.beginTransaction()

                        while(true) {
                            val line=buffer.readLine()

                            if (line == null) break
//                            lineCount++

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

//                            println(total_array)

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

                        db.setTransactionSuccessful()

                        val dateF=SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                        val date=dateF.format(Calendar.getInstance().time)
                        master_date=date.toString()

                        db.endTransaction()
//                        summery()
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
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: androidx.appcompat.app.AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder=androidx.appcompat.app.AlertDialog.Builder(this)

        // Set a title for alert dialog
//        builder.setTitle("Wanna Quit?")

        // Set a message for alert dialog
        builder.setMessage(R.string.sure )


        // On click listener for dialog buttons
        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
                    val tableName="Transaction_table"
                    db.execSQL("delete from $tableName")
                    dialog.dismiss()
                    val a=Intent(this, MainActivity::class.java)
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
                    Toast.makeText(this,"Database Cleared",Toast.LENGTH_SHORT).show()
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


    private fun clearDialog(context: Context){
        lateinit var dialog: AlertDialog
        val listItems=arrayOf(context.getString(R.string.master1), context.getString(R.string.tran),context.getString(R.string.all))
        val builder=AlertDialog.Builder(this)

        val checkvalue=booleanArrayOf(
            false,
            false        )
        builder.setTitle(R.string.c_data)
        builder.setSingleChoiceItems(listItems,-1,DialogInterface.OnClickListener(){dialoginterface,i->

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
            }


            if(Tran==null){
                println("Tran is null")
            }
            else {
                db.execSQL("delete from $Tran")
            }

            if(All==null){
                println("ALL is null")
            }
            else {
                db.execSQL("delete from $All")
                db.execSQL("delete from $MASTER")
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

//    private fun clearDialog() {
//        // Late initialize an alert dialog object
//        lateinit var dialog: androidx.appcompat.app.AlertDialog
//
//
//        // Initialize a new instance of alert dialog builder object
//        val builder=androidx.appcompat.app.AlertDialog.Builder(this)
//
//        // Set a message for alert dialog
//        builder.setMessage("Are you sure??")
//
//
//        // On click listener for dialog buttons
//        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
//            when (which) {
//                DialogInterface.BUTTON_POSITIVE -> {
//                    val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
//                    val tableName="Transaction_table"
//                    db.execSQL("delete from $tableName")
//                    dialog.dismiss()
//                    summery()
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    Toast.makeText(this,"Database Cleared",Toast.LENGTH_SHORT).show()
//                }
//                DialogInterface.BUTTON_NEUTRAL -> {
//                    dialog.dismiss()
//                    summery()
//                }
//            }
//        }
//        // Set the alert dialog positive/yes button
//        builder.setPositiveButton("YES", dialogClickListener)
//
//        // Set the alert dialog neutral/cancel button
//        builder.setNeutralButton("CANCEL", dialogClickListener)
//
//
//        // Initialize the AlertDialog using builder object
//        dialog=builder.create()
//
//        // Finally, display the alert dialog
//        dialog.show()
//    }

    private fun changeLanguage(context: Context){
        lateinit var dialog: AlertDialog
        val listItems=arrayOf(context.getString(R.string.english),context.getString(R.string.thai),context.getString(R.string.myanmar))
        val builder=AlertDialog.Builder(this)
        builder.setTitle(R.string.c_language)
        builder.setSingleChoiceItems(listItems,selectedItems,DialogInterface.OnClickListener() {dialogInterface,i->
            if (i == 0) {
                setLocale("en")
                recreate()
            }

            if (i == 1) {
                setLocale("th")
                recreate()
            }

            if (i == 2) {
                setLocale("my")
                recreate()
            }
            setvalue(i)
            dialogInterface.dismiss()

        })
        dialog= builder.create()
        dialog.show()

    }

    private fun setLocale( lang:String) {

        val locale=Locale(lang)
        Locale.setDefault(locale)
        val config=Configuration()
        config.locale=locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        var editor=getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()

    }

    private fun loadLoacle(){
        var prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language:String? = prefs.getString("My_Lang","")
//        println("RESULT"+language)
        setLocale(language!!)
    }

    private fun setvalue( v:Int) {
        var editor=getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putInt("value", v)
        editor.apply()

    }

    private fun loadvalue(){
        var prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language:Int? = prefs.getInt("value",0)
        selectedItems = prefs.getInt("value",0)
//        println("RESULT"+language)
        setvalue(language!!)

    }

    private fun setpw( v:String) {
        var editor=getSharedPreferences("yo", MODE_PRIVATE).edit()
        editor.putString("val", v)
        editor.apply()

    }

    fun loadpwd(){
        var prefs=getSharedPreferences("yo", Activity.MODE_PRIVATE)
        val language:String? = prefs.getString("val", "12345678")
//        password = prefs.getString("val",null)
        println("RESULT"+language)
        setpw(language.toString())
        real_pwd = language.toString()
    }

    private fun dialogAdmin(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.activity_login,null)
        builder.setView(view)
        val dialog: AlertDialog= builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogSlide
        dialog.setMessage("Please Enter Password!!")
        dialog.show()

        val btn_next =view.findViewById<Button>(R.id.btn_enter)
        val edt_pw = view.findViewById<EditText>(R.id.ent_pw)

        btn_next.setOnClickListener{
            branch = edt_pw.text.toString()

            println("Password is "+ real_pwd)
            if(edt_pw.text == null){
                Toast.makeText(this,"Please Enter Password!!", Toast.LENGTH_SHORT).show()
            }
            else{
                if(edt_pw.text.toString()== real_pwd){
                    val intent = Intent(this,Admin::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Your Password is Wrong!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (mToggle.onOptionsItemSelected(item)) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mToggle.onConfigurationChanged(newConfig)
    }



}
