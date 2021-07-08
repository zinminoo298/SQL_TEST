package com.example.sql_test

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import net.sourceforge.jtds.jdbc.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_connect = findViewById<Button>(R.id.btn_connect)

        btn_connect.setOnClickListener {
            object : AsyncTask<Int?, Int?, Int?>() {
                lateinit var pgd: ProgressDialog
                override fun onPreExecute() {
                    pgd = ProgressDialog(this@MainActivity)
                    pgd.setMessage("Please Wait")
                    pgd.setTitle("Loading Data")
                    pgd.show()
                    pgd.setCancelable(false)

                    super.onPreExecute()

                }

                override fun onPostExecute(result: Int?) {
                    pgd.dismiss()

                    super.onPostExecute(result)
                }

                override fun doInBackground(vararg params: Int?): Int? {
                    connect()
                    return null
                }


            }.execute()

//            connect()
        }

    }

    fun connect(): Connection? {
        val LOG = "DEBUG"
        val ip = "192.168.1.47"
        val port = "1433"
        val classs = "net.sourceforge.jtds.jdbc.Driver"
        val db = "Test"
        val un = "test"
        val password = "123456"

        var conn: Connection? = null
        var ConnURL: String? = null
        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            Class.forName(classs)
            ConnURL = ("jdbc:jtds:sqlserver://192.168.1.47:1433/Test;encrypt=fasle;user=test;password=123456;")
            conn = DriverManager.getConnection(ConnURL,un,password)
            val statement = conn.createStatement()
            val result:ResultSet = statement.executeQuery("SELECT * FROM Table_1;")
//            val update  = statement.executeUpdate("UPDATE Table_1 " +
//                    "SET password = '12345'" +
//                    "WHERE user = 'user1';")
            while (result.next()){
                val ans = result.getString(1)
                println(ans)
            }
            conn.close()
        } catch (e: Exception) {
          e.printStackTrace()
        }
        return conn
    }
}