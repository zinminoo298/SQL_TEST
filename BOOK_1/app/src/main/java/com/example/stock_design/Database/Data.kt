package com.example.stock_design.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.stock_design.*
import com.example.stock_design.Adapters.rowview_date
import com.example.stock_design.Adapters.rowview_location
import com.example.stock_design.Modle.Item
import com.example.stock_design.Modle.Item_Detail
import com.example.stock_design.Modle.Item_search
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

var name: String?=null
var quantity: Int?=0
//var price: Int?=0
var item_code: Int?=0
var master_name: String?=null

var item_tran: Int?=0
var qty_tran: Int?=0
var new_item: Int?=0
var date: String?=null

var item_master: Int?=0
var qty_master: Int?=0

/* Get Data for checking Barcode */
var isbn:String? = null
var code:String? = null
var price:String? = null
var desc:String? = null
var qty:String? = null

var check:String? = null

class DataBaseHelper(val context: Context) {

    companion object {
        private val REAL_DATABASE="database.db"
        private val DB_PATH="/data/data/com.example.stock_design/databases"

        //table
        private val TABLE_NAME="Master"
        private val TABLE_NAME1="Transaction_table"
        private val COL_ID="barcode1"
        private val COL_NAME="description"
        private val COL_QUANTITY="scanned_qty"
        private val COL_ID1="barcode"
        private val COL_QUANTITY1="onhand_qty"
        private val DATE="26/6/2019"
        private val COL_DATE="date"
        private val COL_LOCATION="location"

    }

    /*Open database and start copying database from assets folder*/
    fun openDatabase(): SQLiteDatabase {
        val dbFile=context.getDatabasePath(REAL_DATABASE)
        if (!dbFile.exists()) {
            try {
                val checkDB=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)

                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }

        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    /*Copy database from assets folder to package*/
    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is`=context.assets.open(REAL_DATABASE)
        val os=FileOutputStream(dbFile)

        val buffer=ByteArray(1024)
        while(`is`.read(buffer)>0) {
            os.write(buffer)
            Log.d("#DB", "writing>>")
        }

        os.flush()
        os.close()
        `is`.close()
        Log.d("#DB", "completed..")
    }

    /*Get data from transaction table for verify page */
    fun Verify() {
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select * From Master WHERE article = $verify"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    isbn=cursor.getString(cursor.getColumnIndex("isbn"))
                    price=cursor.getString(cursor.getColumnIndex("price"))
                    desc=cursor.getString(cursor.getColumnIndex("description"))
                } while(cursor.moveToNext())
            }

            else{
                check = "nodata"
            }
            db.close()
        }


    /*Get data from transaction table for Summery page */
    val Summery: MutableList<Item_search>
        get() {
            val seItem=ArrayList<Item_search>()
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select branch,location,COUNT(isbn)as item,SUM(qty) as qty FROM Transaction_table Group By location Order By branch"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val item=Item_search()
                    item.itm=cursor.getInt(cursor.getColumnIndex("item"))
                    item.qty=cursor.getInt(cursor.getColumnIndex("qty"))
                    item.date=cursor.getString(cursor.getColumnIndex("branch"))
                    item.location=cursor.getString(cursor.getColumnIndex("location"))
                    seItem.add(item)
                } while(cursor.moveToNext())
            }
            db.close()
            return seItem
        }

    /*Get data from transaction table for Summery detail page*/
    val Detail: List<Item_Detail>
        get() {
            val seItem=ArrayList<Item_Detail>()
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select * From Transaction_table Where date='$rowview_date' And location='$rowview_location'"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val item=Item_Detail()
                    item.id=cursor.getLong(cursor.getColumnIndex("barcode1"))
                    item.qty=cursor.getInt(cursor.getColumnIndex("scanned_qty"))
                    seItem.add(item)
                } while(cursor.moveToNext())
            }
            db.close()
            return seItem
        }

    /*Add data to transaction table*/
    fun addItem(item: Item) {

        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)

        val query = "SELECT * FROM Master WHERE isbn='$record_code'"
        val cur = db.rawQuery(query,null)
        if(cur.moveToFirst()){
            val values=ContentValues()
            values.put("isbn", item._id)
            values.put("qty", item.quantity)
            values.put(
                "branch",
                record_date
            )
            values.put(
                "location",
                record_location
            )
            val id=db.insertWithOnConflict(TABLE_NAME1, null, values, SQLiteDatabase.CONFLICT_IGNORE)
            if (id == -1L) {
                val selectQuery=
                    "SELECT qty FROM $TABLE_NAME1 WHERE isbn=? AND Transaction_table.location='$record_location'"
                val cursor=db.rawQuery(selectQuery, arrayOf(item._id.toString()))
                cursor.moveToFirst()
                var quantity=cursor.getInt(cursor.getColumnIndex("qty"))
                val valu=ContentValues()
                valu.put("qty", quantity + qt)

                db.update(
                    TABLE_NAME1, valu, "isbn=?", arrayOf(item._id.toString())
                )
            }
        }

        else{
            check="nodata"
        }


        db.close()
    }

    /*get data from transaction table for view after adding data*/
    fun viewData() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val selectQuery=
            "SELECT * FROM Transaction_table LEFT JOIN Master ON Master.isbn = Transaction_table.isbn WHERE Transaction_table.location='$record_location' AND Transaction_table.isbn= $record_code"
        val cursor=db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {

            do {
                price=cursor.getString(cursor.getColumnIndex("price"))
                code=cursor.getString(cursor.getColumnIndex("article"))
                desc=cursor.getString(cursor.getColumnIndex("description"))
                qty=cursor.getString(cursor.getColumnIndex("qty"))
                println("YOAS"+price)
                println("YOSH"+qty)
                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor))

            } while(cursor.moveToNext())
        }
        db.close()
    }

    /*get summery data from transaction table for main page*/
    fun getTransactionSummery() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val selectQuery="SELECT SUM ($COL_QUANTITY) FROM Transaction_table"
        val Query=db.rawQuery(selectQuery, null)
        if (Query.moveToFirst()) {
            qty_tran=Query.getInt(0)
        }

        val cursor=DatabaseUtils.queryNumEntries(db, "Transaction_table")
        item_tran=cursor.toInt()


        val selectQuery1=
            "SELECT COUNT ($COL_ID) FROM Transaction_table LEFT JOIN Master ON Master.barcode = Transaction_table.barcode1 WHERE Master.description='new item' "
        val Query1=db.rawQuery(selectQuery1, null)
        if (Query1.moveToFirst()) {
            new_item=Query1.getInt(0)

        }

        val selectQuery2="SELECT $COL_DATE FROM Transaction_table ORDER BY rowid DESC LIMIT 1"
        val Query2=db.rawQuery(selectQuery2, null)
        if (Query2.moveToFirst()) {
            date=Query2.getString(0)
            println("CCCCCCC" + date)

        }

        db.close()

    }

    /*get summery data from master table for main page*/
    fun getMasterSummery() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)

        val selectQuery="SELECT SUM ($COL_QUANTITY1) FROM Master"
        val Query=db.rawQuery(selectQuery, null)
        if (Query.moveToFirst()) {
            qty_master=Query.getInt(0)
        }

        val cursor=DatabaseUtils.queryNumEntries(db, "Master")
        item_master=cursor.toInt()


        db.close()
    }

    /*update data to transaction table */
    fun updateItem(item: Item): Int {

        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val values=ContentValues()
        values.put("isbn", item._id)
        values.put("qty", item.quantity)


        return db.update(TABLE_NAME1, values, "isbn=?", arrayOf(item._id.toString()))
    }

    /*delete data from transaction table*/
    fun deleteItem(item: Item) {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        db.delete(TABLE_NAME1, "isbn=?", arrayOf(item._id.toString()))
        db.close()
    }


}